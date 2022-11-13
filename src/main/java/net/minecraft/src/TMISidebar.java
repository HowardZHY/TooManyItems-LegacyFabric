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

    private void setTab(TMIArea tMIArea) {
        this.removeChild(this.content);
        this.content = tMIArea;
        this.addChild(this.content);
        this.doLayout();
    }

    public void layoutComponent() {
        this.tabset.setSize(this.width - 2, 16);
        this.tabset.setPosition(this.x + 1, this.y + 2);
        this.content.setSize(this.width - 2, this.height - 4 - 16);
        this.content.setPosition(this.x + 1, this.y + 2 + 16 + 2);
    }

    public void drawComponent() {
        TMIDrawing.drawPanel(this.getX(), this.getY(), this.getWidth(), this.getHeight(), -14540506, -12369332, -13290437);
    }

    public void mouseEvent(TMIEvent tMIEvent) {
        if (tMIEvent.mouseButton == 0) {
            if (tMIEvent.target == this.tabset.items) {
                this.setTab(this.items);
            } else if (tMIEvent.target == this.tabset.customs) {
                this.setTab(this.customMenu);
            } else if (tMIEvent.target == this.tabset.controls) {
                this.setTab(this.controls);
            } else if (tMIEvent.target == this.tabset.myItems) {
                this.setTab(this.myItems);
            } else if (tMIEvent.target == this.tabset.saves && !TMIGame.isMultiplayer()) {
                this.setTab(this.saves);
            }
        }
    }

    public void controlEvent(TMIEvent tMIEvent) {
        if (tMIEvent.type == 4) {
            this.setTab(tMIEvent.target);
        }
    }

    public void keyboardEvent(TMIEvent tMIEvent) {
        if (tMIEvent.keyCode == 15) {
            this.content.keyboardEvent(tMIEvent);
        }
    }
}