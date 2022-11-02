package net.minecraft.src;

class TMICustomSignPanel
extends TMIArea {
    private TMIItemButton sign = new TMIItemButton(new TMIStackBuilder("sign").stack());
    private TMITextField[] text = new TMITextField[4];
    public static final int LINES = 4;

    public TMICustomSignPanel() {
        this.addChild(this.sign);
        for (int i = 0; i < 4; ++i) {
            this.text[i] = new TMITextField();
            this.addChild(this.text[i]);
            this.text[i].addEventListener(this);
        }
    }

    @Override
    public void layoutComponent() {
        this.sign.setSize(16, 16);
        this.sign.x = this.x + (this.width - 16) / 2;
        this.sign.y = this.y + 10;
        for (int i = 0; i < 4; ++i) {
            this.text[i].setSize(this.width - 4, 12);
            this.text[i].setPosition(this.x + 2, this.y + 10 + 16 + 4 + i * 18);
        }
    }

    @Override
    public void controlEvent(TMIEvent p_controlEvent_1_) {
        if (p_controlEvent_1_.type == 3) {
            TMIStackBuilder tmistackbuilder = new TMIStackBuilder("sign").name("Sign with text");
            for (int i = 0; i < 4; ++i) {
                tmistackbuilder.lore(this.text[i].value());
                tmistackbuilder.blockEntity().putString("Text" + (i + 1), this.text[i].value());
            }
            this.sign.stack = tmistackbuilder.stack();
        }
    }

    @Override
    public void keyboardEvent(TMIEvent p_keyboardEvent_1_) {
        if (p_keyboardEvent_1_.keyCode == 15) {
            int i = this.children.indexOf((Object)this.focusArea) - 1;
            if (i < 0) {
                i = -1;
            }
            i = (i + 1) % 4;
            this.text[i].focus();
        }
    }
}