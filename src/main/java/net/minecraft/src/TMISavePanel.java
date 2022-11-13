package net.minecraft.src;

public class TMISavePanel
extends TMIArea {
    private TMIArea buttonPanel = new TMIArea();
    private TMIArea renamePanel = new TMIArea();
    private TMIButton[] saveButtons = new TMIButton[TMISaveFile.statesSaved.length];
    private TMIButton[] clearButtons = new TMIButton[TMISaveFile.statesSaved.length];
    private TMITextField[] renameFields = new TMITextField[TMISaveFile.statesSaved.length];
    private TMIButton renameButton = new TMIButton(null, "Rename...");
    private TMIButton okButton = new TMIButton(null, "OK");

    public TMISavePanel() {
        for (int i = 0; i < this.saveButtons.length; ++i) {
            this.saveButtons[i] = new TMIButton(i, TMIConfigFile.getSaveName(i));
            this.saveButtons[i].centerText = false;
            this.clearButtons[i] = new TMIButton(-i - 1, "x");
            this.renameFields[i] = new TMITextField();
            this.buttonPanel.addChild(this.saveButtons[i]);
            this.buttonPanel.addChild(this.clearButtons[i]);
            this.renamePanel.addChild(this.renameFields[i]);
        }
        this.buttonPanel.addChild(this.renameButton);
        this.renamePanel.addChild(this.okButton);
        this.addChild(this.buttonPanel);
    }

    public void layoutComponent() {
        this.buttonPanel.copyBounds(this);
        this.renamePanel.copyBounds(this);
        int n = this.x + 4;
        int n2 = this.x + this.width - 4 - 12;
        int n3 = this.y + 10;
        for (int i = 0; i < this.saveButtons.length; ++i) {
            this.saveButtons[i].setSize(this.width - 8 - 12 - 2, 12);
            this.saveButtons[i].setPosition(n, n3);
            this.clearButtons[i].setSize(12, 12);
            this.clearButtons[i].setPosition(n2, n3);
            this.renameFields[i].setSize(this.width - 8, 12);
            this.renameFields[i].setPosition(n, n3);
            n3 += 16;
        }
        this.renameButton.setYCenteredIn(n3 += 10, this);
        this.okButton.setYCenteredIn(n3, this);
    }

    public void showRename() {
        this.removeChild(this.buttonPanel);
        this.addChild(this.renamePanel);
        for (int i = 0; i < this.renameFields.length; ++i) {
            this.renameFields[i].setValue(TMIConfigFile.getSaveName(i));
        }
    }

    public void saveRename() {
        for (int i = 0; i < this.renameFields.length; ++i) {
            TMIConfigFile.setSaveName(i, this.renameFields[i].value());
        }
        this.removeChild(this.renamePanel);
        this.addChild(this.buttonPanel);
    }

    public void drawComponent() {
        for (int i = 0; i < this.saveButtons.length; ++i) {
            boolean bl = TMISaveFile.statesSaved[i];
            this.saveButtons[i].label = (bl ? "Load " : "Save ") + TMIConfigFile.getSaveName(i);
            this.clearButtons[i].shown = bl;
        }
    }

    public void mouseEvent(TMIEvent tMIEvent) {
        if (tMIEvent.mouseButton == 0) {
            if (tMIEvent.target instanceof TMIButton) {
                TMIButton tMIButton = (TMIButton)tMIEvent.target;
                if (tMIButton == this.renameButton) {
                    this.showRename();
                } else if (tMIButton == this.okButton) {
                    this.saveRename();
                } else {
                    int n = (Integer)tMIButton.data;
                    if (n < 0) {
                        n = -(n + 1);
                        TMISaveFile.clearState(n);
                    } else if (TMISaveFile.statesSaved[n]) {
                        TMISaveFile.loadState(n);
                    } else {
                        TMISaveFile.saveState(n);
                    }
                }
            }
            tMIEvent.cancel();
        }
    }
}