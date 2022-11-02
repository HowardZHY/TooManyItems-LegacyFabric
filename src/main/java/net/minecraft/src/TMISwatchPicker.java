package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class TMISwatchPicker
extends TMIArea {
    private boolean isMulti;
    private int swatchWidth;

    public TMISwatchPicker(int[] p_i27_1_, int p_i27_2_, boolean p_i27_3_) {
        this.isMulti = p_i27_3_;
        this.swatchWidth = p_i27_2_;
        for (int i = 0; i < p_i27_1_.length; ++i) {
            Swatch tmiswatchpicker$swatch = new Swatch(p_i27_1_[i]);
            tmiswatchpicker$swatch.setSize(p_i27_2_, p_i27_2_);
            this.addChild(tmiswatchpicker$swatch);
        }
    }

    public TMISwatchPicker(int[] p_i28_1_, int p_i28_2_) {
        this(p_i28_1_, p_i28_2_, false);
    }

    @Override
    public void layoutComponent() {
        int i = this.width / this.swatchWidth;
        int j = 0;
        int k = 0;
        for (TMIArea tmiarea : this.children) {
            tmiarea.setPosition(this.x + k * this.swatchWidth, this.y + j * this.swatchWidth);
            if (++k != i) continue;
            k = 0;
            ++j;
        }
    }

    @Override
    public void mouseEvent(TMIEvent p_mouseEvent_1_) {
        if (p_mouseEvent_1_.target instanceof Swatch) {
            if (this.isMulti) {
                Swatch tmiswatchpicker$swatch1 = (Swatch)p_mouseEvent_1_.target;
                tmiswatchpicker$swatch1.selected = !tmiswatchpicker$swatch1.selected;
            } else {
                for (TMIArea tmiarea : this.children) {
                    Swatch tmiswatchpicker$swatch = (Swatch)tmiarea;
                    tmiswatchpicker$swatch.selected = tmiswatchpicker$swatch == p_mouseEvent_1_.target;
                }
            }
            this.emit(TMIEvent.controlEvent(3, this));
        }
    }

    public List<Integer> getValues() {
        ArrayList arraylist = new ArrayList();
        for (TMIArea tmiarea : this.children) {
            Swatch tmiswatchpicker$swatch = (Swatch)tmiarea;
            if (!tmiswatchpicker$swatch.selected) continue;
            arraylist.add((Object)tmiswatchpicker$swatch.color);
        }
        return arraylist;
    }

    public int[] getArray() {
        List<Integer> list = this.getValues();
        int[] aint = new int[list.size()];
        for (int i = 0; i < aint.length; ++i) {
            aint[i] = (Integer)list.get(i);
        }
        return aint;
    }

    public int getFirst() {
        for (TMIArea tmiarea : this.children) {
            Swatch tmiswatchpicker$swatch = (Swatch)tmiarea;
            if (!tmiswatchpicker$swatch.selected) continue;
            return tmiswatchpicker$swatch.color;
        }
        return -1;
    }

    class Swatch
    extends TMIArea {
        int color;
        boolean selected;

        public Swatch(int p_i26_2_) {
            this.color = p_i26_2_;
        }

        @Override
        public void drawComponent() {
            if (this.selected) {
                TMIDrawing.fillRect(this.x, this.y, this.width, this.height, -2236963);
            }
            TMIDrawing.fillRect(this.x + 1, this.y + 1, this.width - 2, this.height - 2, 0xFF000000 | this.color);
        }
    }
}