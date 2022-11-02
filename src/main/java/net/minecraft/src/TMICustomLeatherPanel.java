package net.minecraft.src;

class TMICustomLeatherPanel
extends TMIArea {
    private String[] itemTypes = new String[]{"leather_chestplate", "leather_helmet", "leather_leggings", "leather_boots"};
    private TMIItemButton[] buttons = new TMIItemButton[4];
    private String[] colorLabels = new String[]{"Red (0-255)", "Green (0-255)", "Blue (0-255)"};
    private TMITextField[] colorInputs = new TMITextField[3];

    public TMICustomLeatherPanel() {
        for (int i = 0; i < 3; ++i) {
            this.colorInputs[i] = new TMITextField();
            this.colorInputs[i].setValue("255");
            this.colorInputs[i].addEventListener(this);
            this.addChild(this.colorInputs[i]);
        }
        for (int j = 0; j < 4; ++j) {
            this.buttons[j] = new TMIItemButton(null);
            this.buttons[j].setSize(16, 16);
            this.buttons[j].addEventListener(this);
            this.addChild(this.buttons[j]);
        }
        this.setStacksFromInputs();
    }

    private void setStacksFromInputs() {
        int i = this.getInputValue(0);
        int j = this.getInputValue(1);
        int k = this.getInputValue(2);
        int b0 = 0;
        int l = b0 | (i & 0xFF) << 16;
        l |= (j & 0xFF) << 8;
        l |= k & 0xFF;
        for (int i1 = 0; i1 < 4; ++i1) {
            TMIStackBuilder tmistackbuilder = new TMIStackBuilder(this.itemTypes[i1]);
            tmistackbuilder.display().putInt("color", l);
            this.buttons[i1].stack = tmistackbuilder.stack();
        }
    }

    private int getInputValue(int p_getInputValue_1_) {
        try {
            return Integer.parseInt((String)this.colorInputs[p_getInputValue_1_].value());
        }
        catch (NumberFormatException var3) {
            return 0;
        }
    }

    @Override
    public void drawComponent() {
        int i = this.y + 10 + 16 + 10;
        for (int j = 0; j < 3; ++j) {
            TMIDrawing.drawText(this.x + 4, i, this.colorLabels[j]);
            i += 32;
        }
    }

    @Override
    public void layoutComponent() {
        int i = this.y + 10;
        int j = this.x + (this.width - 64) / 2;
        for (int k = 0; k < 4; ++k) {
            this.buttons[k].setPosition(j, i);
            j += 16;
        }
        int j1 = this.x + 4;
        int l = this.y + 10 + 16 + 10 + 12 + 1;
        for (int i1 = 0; i1 < 3; ++i1) {
            this.colorInputs[i1].setSize(this.width - 8, 12);
            this.colorInputs[i1].setPosition(j1, l);
            l += 32;
        }
    }

    @Override
    public void controlEvent(TMIEvent p_controlEvent_1_) {
        if (p_controlEvent_1_.type == 3 && p_controlEvent_1_.target instanceof TMITextField) {
            TMITextField tmitextfield = (TMITextField)p_controlEvent_1_.target;
            int i = 0;
            if (!tmitextfield.value().equals((Object)"")) {
                try {
                    i = Integer.parseInt((String)tmitextfield.value());
                }
                catch (NumberFormatException var51) {
                    i = -1;
                }
            }
            if (i < 0) {
                boolean flag = false;
                tmitextfield.setValue("0");
            } else if (i > 255) {
                boolean flag1 = true;
                tmitextfield.setValue("255");
            }
            this.setStacksFromInputs();
        }
    }
}