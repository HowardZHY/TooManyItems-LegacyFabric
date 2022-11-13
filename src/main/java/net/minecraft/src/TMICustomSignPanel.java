package net.minecraft.src;

public class TMICustomSignPanel
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

    public void layoutComponent() {
        this.sign.setSize(16, 16);
        this.sign.x = this.x + (this.width - 16) / 2;
        this.sign.y = this.y + 10;
        for (int i = 0; i < 4; ++i) {
            this.text[i].setSize(this.width - 4, 12);
            this.text[i].setPosition(this.x + 2, this.y + 10 + 16 + 4 + i * 18);
        }
    }

    public void controlEvent(TMIEvent tMIEvent) {
        if (tMIEvent.type == 3) {
            TMIStackBuilder tMIStackBuilder = new TMIStackBuilder("sign").name("Sign with text");
            for (int i = 0; i < 4; ++i) {
                tMIStackBuilder.lore(this.text[i].value());
                tMIStackBuilder.blockEntity().putString("Text" + (i + 1), this.text[i].value());
            }
            this.sign.stack = tMIStackBuilder.stack();
        }
    }

    public void keyboardEvent(TMIEvent tMIEvent) {
        if (tMIEvent.keyCode == 15) {
            int n = this.children.indexOf((Object)this.focusArea) - 1;
            if (n < 0) {
                n = -1;
            }
            n = (n + 1) % 4;
            this.text[n].focus();
        }
    }
}