package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class TMISwatchPicker
extends TMIArea {
    private boolean isMulti;
    private int swatchWidth;

    public TMISwatchPicker(int[] nArray, int n, boolean bl) {
        this.isMulti = bl;
        this.swatchWidth = n;
        for (int i = 0; i < nArray.length; ++i) {
            Swatch swatch = new Swatch(nArray[i]);
            swatch.setSize(n, n);
            this.addChild(swatch);
        }
    }

    public TMISwatchPicker(int[] nArray, int n) {
        this(nArray, n, false);
    }

    @Override
    public void layoutComponent() {
        int n = this.width / this.swatchWidth;
        int n2 = 0;
        int n3 = 0;
        for (TMIArea tMIArea : this.children) {
            tMIArea.setPosition(this.x + n3 * this.swatchWidth, this.y + n2 * this.swatchWidth);
            if (++n3 != n) continue;
            n3 = 0;
            ++n2;
        }
    }

    @Override
    public void mouseEvent(TMIEvent tMIEvent) {
        if (tMIEvent.target instanceof Swatch) {
            if (this.isMulti) {
                Swatch swatch = (Swatch)tMIEvent.target;
                swatch.selected = !swatch.selected;
            } else {
                for (TMIArea tMIArea : this.children) {
                    Swatch swatch = (Swatch)tMIArea;
                    swatch.selected = swatch == tMIEvent.target;
                }
            }
            this.emit(TMIEvent.controlEvent(3, this));
        }
    }

    public List<Integer> getValues() {
        ArrayList arrayList = new ArrayList();
        for (TMIArea tMIArea : this.children) {
            Swatch swatch = (Swatch)tMIArea;
            if (!swatch.selected) continue;
            arrayList.add((Object)swatch.color);
        }
        return arrayList;
    }

    public int[] getArray() {
        List<Integer> list = this.getValues();
        int[] nArray = new int[list.size()];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = (Integer)list.get(i);
        }
        return nArray;
    }

    public int getFirst() {
        for (TMIArea tMIArea : this.children) {
            Swatch swatch = (Swatch)tMIArea;
            if (!swatch.selected) continue;
            return swatch.color;
        }
        return -1;
    }

    class Swatch
    extends TMIArea {
        int color;
        boolean selected;

        public Swatch(int n) {
            this.color = n;
        }

        public void drawComponent() {
            if (this.selected) {
                TMIDrawing.fillRect(this.x, this.y, this.width, this.height, -2236963);
            }
            TMIDrawing.fillRect(this.x + 1, this.y + 1, this.width - 2, this.height - 2, 0xFF000000 | this.color);
        }
    }
}