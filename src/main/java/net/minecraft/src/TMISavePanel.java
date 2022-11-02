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

    @Override
    public void layoutComponent() {
        this.buttonPanel.copyBounds(this);
        this.renamePanel.copyBounds(this);
        int i = this.x + 4;
        int j = this.x + this.width - 4 - 12;
        int k = this.y + 10;
        for (int l = 0; l < this.saveButtons.length; ++l) {
            this.saveButtons[l].setSize(this.width - 8 - 12 - 2, 12);
            this.saveButtons[l].setPosition(i, k);
            this.clearButtons[l].setSize(12, 12);
            this.clearButtons[l].setPosition(j, k);
            this.renameFields[l].setSize(this.width - 8, 12);
            this.renameFields[l].setPosition(i, k);
            k += 16;
        }
        this.renameButton.setYCenteredIn(k += 10, this);
        this.okButton.setYCenteredIn(k, this);
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

    @Override
    public void drawComponent() {
        for (int i = 0; i < this.saveButtons.length; ++i) {
            boolean flag = TMISaveFile.statesSaved[i];
            this.saveButtons[i].label = (flag ? "Load " : "Save ") + TMIConfigFile.getSaveName(i);
            this.clearButtons[i].shown = flag;
        }
    }

    @Override
    public void mouseEvent(TMIEvent p_mouseEvent_1_) {
        if (p_mouseEvent_1_.mouseButton == 0) {
            if (p_mouseEvent_1_.target instanceof TMIButton) {
                TMIButton tmibutton = (TMIButton)p_mouseEvent_1_.target;
                if (tmibutton == this.renameButton) {
                    this.showRename();
                } else if (tmibutton == this.okButton) {
                    this.saveRename();
                } else {
                    int i = (Integer)tmibutton.data;
                    if (i < 0) {
                        i = -(i + 1);
                        TMISaveFile.clearState(i);
                    } else if (TMISaveFile.statesSaved[i]) {
                        TMISaveFile.loadState(i);
                    } else {
                        TMISaveFile.saveState(i);
                    }
                }
            }
            p_mouseEvent_1_.cancel();
        }
    }
}