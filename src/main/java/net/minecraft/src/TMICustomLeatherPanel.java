package net.minecraft.src;

class TMICustomLeatherPanel
extends TMIArea {
    private String[] itemTypes = new String[]{"leather_chestplate", "leather_helmet", "leather_leggings", "leather_boots"};
    private TMIItemButton[] buttons = new TMIItemButton[4];
    private String[] colorLabels = new String[]{"Red (0-255)", "Green (0-255)", "Blue (0-255)"};
    private TMITextField[] colorInputs = new TMITextField[3];

    public TMICustomLeatherPanel() {
        int n;
        for (n = 0; n < 3; ++n) {
            this.colorInputs[n] = new TMITextField();
            this.colorInputs[n].setValue("255");
            this.colorInputs[n].addEventListener(this);
            this.addChild(this.colorInputs[n]);
        }
        for (n = 0; n < 4; ++n) {
            this.buttons[n] = new TMIItemButton(null);
            this.buttons[n].setSize(16, 16);
            this.buttons[n].addEventListener(this);
            this.addChild(this.buttons[n]);
        }
        this.setStacksFromInputs();
    }

    private void setStacksFromInputs() {
        int n = this.getInputValue(0);
        int n2 = this.getInputValue(1);
        int n3 = this.getInputValue(2);
        int n4 = 0;
        n4 |= (n & 0xFF) << 16;
        n4 |= (n2 & 0xFF) << 8;
        n4 |= n3 & 0xFF;
        for (int i = 0; i < 4; ++i) {
            TMIStackBuilder tMIStackBuilder = new TMIStackBuilder(this.itemTypes[i]);
            tMIStackBuilder.display().putInt("color", n4);
            this.buttons[i].stack = tMIStackBuilder.stack();
        }
    }

    private int getInputValue(int n) {
        try {
            return Integer.parseInt((String)this.colorInputs[n].value());
        }
        catch (NumberFormatException numberFormatException) {
            return 0;
        }
    }

    public void drawComponent() {
        int n = this.y + 10 + 16 + 10;
        for (int i = 0; i < 3; ++i) {
            TMIDrawing.drawText(this.x + 4, n, this.colorLabels[i]);
            n += 32;
        }
    }

    public void layoutComponent() {
        int n;
        int n2 = this.y + 10;
        int n3 = this.x + (this.width - 64) / 2;
        for (n = 0; n < 4; ++n) {
            this.buttons[n].setPosition(n3, n2);
            n3 += 16;
        }
        n = this.x + 4;
        int n4 = this.y + 10 + 16 + 10 + 12 + 1;
        for (int i = 0; i < 3; ++i) {
            this.colorInputs[i].setSize(this.width - 8, 12);
            this.colorInputs[i].setPosition(n, n4);
            n4 += 32;
        }
    }

    public void controlEvent(TMIEvent tMIEvent) {
        if (tMIEvent.type == 3 && tMIEvent.target instanceof TMITextField) {
            TMITextField tMITextField = (TMITextField)tMIEvent.target;
            int n = 0;
            if (!tMITextField.value().equals((Object)"")) {
                try {
                    n = Integer.parseInt((String)tMITextField.value());
                }
                catch (NumberFormatException numberFormatException) {
                    n = -1;
                }
            }
            if (n < 0) {
                n = 0;
                tMITextField.setValue("0");
            } else if (n > 255) {
                n = 255;
                tMITextField.setValue("255");
            }
            this.setStacksFromInputs();
        }
    }
}