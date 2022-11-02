package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TMICycleButton
extends TMIButton {
    private List<String> options;
    private int index = 0;
    private String prefix = "";

    public TMICycleButton(List<String> p_i6_1_, String p_i6_2_) {
        this.options = new ArrayList(p_i6_1_);
        this.height = 12;
        this.width = 0;
        for (String s : p_i6_1_) {
            int i = TMIDrawing.getTextWidth(s);
            if (i <= this.width) continue;
            this.width = i;
        }
        this.doLabel();
    }

    public TMICycleButton(List<String> p_i7_1_) {
        this(p_i7_1_, "");
    }

    public TMICycleButton(String ... p_i8_1_) {
        this(Arrays.asList(p_i8_1_));
    }

    private void doLabel() {
        this.label = (this.prefix != null ? this.prefix : "") + (this.index >= 0 && this.index < this.options.size() ? (String)this.options.get(this.index) : "ERR");
    }

    public void setPrefix(String p_setPrefix_1_) {
        this.prefix = p_setPrefix_1_;
        this.doLabel();
    }

    public String getValue() {
        return (String)this.options.get(this.index);
    }

    public int getIntValue() {
        return Integer.parseInt((String)((String)this.options.get(this.index)));
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public void mouseEvent(TMIEvent p_mouseEvent_1_) {
        if (p_mouseEvent_1_.mouseButton == 0) {
            ++this.index;
        } else if (p_mouseEvent_1_.mouseButton == 1) {
            --this.index;
        } else if (p_mouseEvent_1_.type == 2) {
            this.index -= p_mouseEvent_1_.wheel;
        }
        if (this.index < 0) {
            this.index = this.options.size() - 1;
        }
        this.index %= this.options.size();
        this.doLabel();
        this.emit(TMIEvent.controlEvent(3, this));
    }
}