package net.minecraft.src;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;

public class TMICustomNotePanel
extends TMIItemGrid {
    public static final String[] notes = new String[]{"F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#"};
    private int counter;

    public TMICustomNotePanel() {
        TMIStackBuilder tmistackbuilder = new TMIStackBuilder("noteblock");
        this.items = new ArrayList();
        for (int i = 0; i <= 24; ++i) {
            tmistackbuilder.blockEntity().putByte("note", (byte)i);
            tmistackbuilder.name("\u00a7r\u00a79Note Block (" + i + ", " + notes[i] + ")");
            this.items.add(tmistackbuilder.stack());
        }
        this.TOP = 10;
    }

    @Override
    public void drawComponent(int p_drawComponent_1_, int p_drawComponent_2_) {
        this.counter = 0;
        super.drawComponent(p_drawComponent_1_, p_drawComponent_2_);
    }

    @Override
    protected void drawItem(int p_drawItem_1_, int p_drawItem_2_, ItemStack p_drawItem_3_) {
        TMIDrawing.drawItem(p_drawItem_1_, p_drawItem_2_, p_drawItem_3_);
        TMIDrawing.drawTextCentered(p_drawItem_1_, p_drawItem_2_, 16, 16, "" + this.counter, -1, 2);
        ++this.counter;
    }
}