package net.minecraft.src;

public class TMITabset
extends TMIArea {
    public final Tab items = new Tab(0, 6, "Basic Items", false);
    public final Tab myItems = new Tab(1, 8, "My Items", false);
    public final Tab customs = new Tab(0, 8, "Custom", false);
    public final Tab controls = new Tab(1, 5, "Controls", false);
    public final Tab saves = new Tab(0, 7, "Saves", true);

    public TMITabset() {
        this.addChild(this.items);
        this.addChild(this.myItems);
        this.addChild(this.customs);
        this.addChild(this.controls);
        this.addChild(this.saves);
    }

    public void layoutComponent() {
        int n = this.getWidth() / this.children.size();
        int n2 = this.x;
        for (TMIArea tMIArea : this.children) {
            tMIArea.setSize(n, this.getHeight());
            tMIArea.setPosition(n2, this.y);
            n2 += n;
        }
    }

    class Tab
    extends TMIChromeButton {
        private final boolean isSingleplayerOnly;
        private final String name;

        public Tab(int n, int n2, String string, boolean bl) {
            super(n, n2, string);
            this.name = string;
            this.isSingleplayerOnly = bl;
        }

        public void layoutComponent() {
            this.setTooltip(this.name + (this.isSingleplayerOnly && TMIGame.isMultiplayer() ? "\nSingle player only" : ""));
        }
    }
}