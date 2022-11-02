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

    @Override
    public void layoutComponent() {
        int i = this.getWidth() / this.children.size();
        int j = this.x;
        for (TMIArea tmiarea : this.children) {
            tmiarea.setSize(i, this.getHeight());
            tmiarea.setPosition(j, this.y);
            j += i;
        }
    }

    class Tab
    extends TMIChromeButton {
        private final boolean isSingleplayerOnly;
        private final String name;

        public Tab(int p_i29_2_, int p_i29_3_, String p_i29_4_, boolean p_i29_5_) {
            super(p_i29_2_, p_i29_3_, p_i29_4_);
            this.name = p_i29_4_;
            this.isSingleplayerOnly = p_i29_5_;
        }

        @Override
        public void layoutComponent() {
            this.setTooltip(this.name + (this.isSingleplayerOnly && TMIGame.isMultiplayer() ? "\nSingle player only" : ""));
        }
    }
}