package net.minecraft.src;

public class TMISidebar
extends TMIArea {
    private TMIArea content = null;
    private TMITabset tabset = new TMITabset();
    private TMIItemsPanel items = new TMIItemsPanel();
    private TMICustomMenuPanel customMenu = new TMICustomMenuPanel();
    private TMIMyItemsPanel myItems = new TMIMyItemsPanel();
    private TMIControlPanel controls = new TMIControlPanel();
    private TMISavePanel saves = new TMISavePanel();

    public TMISidebar() {
        this.content = this.items;
        this.addChild(this.tabset);
        this.addChild(this.content);
        this.customMenu.addEventListener(this);
    }

    private void setTab(TMIArea p_setTab_1_) {
        this.removeChild(this.content);
        this.content = p_setTab_1_;
        this.addChild(this.content);
        this.doLayout();
    }

    @Override
    public void layoutComponent() {
        this.tabset.setSize(this.width - 2, 16);
        this.tabset.setPosition(this.x + 1, this.y + 2);
        this.content.setSize(this.width - 2, this.height - 4 - 16);
        this.content.setPosition(this.x + 1, this.y + 2 + 16 + 2);
    }

    @Override
    public void drawComponent() {
        TMIDrawing.drawPanel(this.getX(), this.getY(), this.getWidth(), this.getHeight(), -14540506, -12369332, -13290437);
    }

    @Override
    public void mouseEvent(TMIEvent p_mouseEvent_1_) {
        if (p_mouseEvent_1_.mouseButton == 0) {
            if (p_mouseEvent_1_.target == this.tabset.items) {
                this.setTab(this.items);
            } else if (p_mouseEvent_1_.target == this.tabset.customs) {
                this.setTab(this.customMenu);
            } else if (p_mouseEvent_1_.target == this.tabset.controls) {
                this.setTab(this.controls);
            } else if (p_mouseEvent_1_.target == this.tabset.myItems) {
                this.setTab(this.myItems);
            } else if (p_mouseEvent_1_.target == this.tabset.saves && !TMIGame.isMultiplayer()) {
                this.setTab(this.saves);
            }
        }
    }

    @Override
    public void controlEvent(TMIEvent p_controlEvent_1_) {
        if (p_controlEvent_1_.type == 4) {
            this.setTab(p_controlEvent_1_.target);
        }
    }

    @Override
    public void keyboardEvent(TMIEvent p_keyboardEvent_1_) {
        if (p_keyboardEvent_1_.keyCode == 15) {
            this.content.keyboardEvent(p_keyboardEvent_1_);
        }
    }
}