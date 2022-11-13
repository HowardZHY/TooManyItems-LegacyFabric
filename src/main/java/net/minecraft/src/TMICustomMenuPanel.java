package net.minecraft.src;

public class TMICustomMenuPanel
extends TMIItemGrid {
    private TMIItemGrid grid = new TMIItemGrid();

    public TMICustomMenuPanel() {
        this.addChild(new TMIButton(new TMICustomPotionPanel(), "Potions").item("potion").center(false));
        this.addChild(new TMIButton(new TMICustomEnchantPanel(), "Enchanting").item("enchanted_book").center(false));
        this.addChild(new TMIButton(new TMICustomFireworkPanel(), "Fireworks").item("fireworks").center(false));
        this.addChild(new TMIButton(new TMICustomSignPanel(), "Signs with text").item("sign").center(false));
        this.addChild(new TMIButton(new TMICustomLeatherPanel(), "Leather dying").item("leather_chestplate").center(false));
        this.addChild(new TMIButton(new TMICustomHeadPanel(), "Player heads").item("skull", 3).center(false));
        this.addChild(new TMIButton(new TMICustomNotePanel(), "Note blocks").item("noteblock").center(false));
        this.addChild(new TMIButton(new TMICustomFlowerPanel(), "Flower pots").item("flower_pot").center(false));
    }

    public void layoutComponent() {
        int n = this.y + 4;
        for (TMIArea tMIArea : this.children) {
            tMIArea.setSize(this.width, 16);
            tMIArea.setPosition(this.x, n);
            n += 18;
        }
    }

    public void mouseEvent(TMIEvent tMIEvent) {
        if (tMIEvent.target instanceof TMIButton) {
            TMIEvent tMIEvent2 = TMIEvent.controlEvent(4, (TMIArea)((TMIButton)tMIEvent.target).data);
            this.emit(tMIEvent2);
        }
    }
}