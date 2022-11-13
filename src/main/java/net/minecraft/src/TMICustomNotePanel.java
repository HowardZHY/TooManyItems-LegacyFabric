package net.minecraft.src;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;

public class TMICustomNotePanel
extends TMIItemGrid {
    public static final String[] notes = new String[]{"F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#"};
    private int counter;

    public TMICustomNotePanel() {
        TMIStackBuilder tMIStackBuilder = new TMIStackBuilder("noteblock");
        this.items = new ArrayList();
        for (int i = 0; i <= 24; ++i) {
            tMIStackBuilder.blockEntity().putByte("note", (byte)i);
            tMIStackBuilder.name("\u00a7r\u00a79Note Block (" + i + ", " + notes[i] + ")");
            this.items.add(tMIStackBuilder.stack());
        }
        this.TOP = 10;
    }

    public void drawComponent(int n, int n2) {
        this.counter = 0;
        super.drawComponent(n, n2);
    }

    protected void drawItem(int n, int n2, ItemStack itemStack) {
        TMIDrawing.drawItem(n, n2, itemStack);
        TMIDrawing.drawTextCentered(n, n2, 16, 16, "" + this.counter, -1, 2);
        ++this.counter;
    }
}