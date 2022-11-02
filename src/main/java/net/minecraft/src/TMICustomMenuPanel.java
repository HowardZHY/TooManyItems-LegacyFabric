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

    @Override
    public void layoutComponent() {
        int i = this.y + 4;
        for (TMIArea tmiarea : this.children) {
            tmiarea.setSize(this.width, 16);
            tmiarea.setPosition(this.x, i);
            i += 18;
        }
    }

    @Override
    public void mouseEvent(TMIEvent p_mouseEvent_1_) {
        if (p_mouseEvent_1_.target instanceof TMIButton) {
            TMIEvent tmievent = TMIEvent.controlEvent(4, (TMIArea)((TMIButton)p_mouseEvent_1_.target).data);
            this.emit(tmievent);
        }
    }
}