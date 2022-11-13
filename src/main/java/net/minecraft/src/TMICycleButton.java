package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TMICycleButton
extends TMIButton {
    private List<String> options;
    private int index = 0;
    private String prefix = "";

    public TMICycleButton(List<String> list, String string) {
        this.options = new ArrayList(list);
        this.height = 12;
        this.width = 0;
        for (String string2 : list) {
            int n = TMIDrawing.getTextWidth(string2);
            if (n <= this.width) continue;
            this.width = n;
        }
        this.doLabel();
    }

    public TMICycleButton(List<String> list) {
        this(list, "");
    }

    public TMICycleButton(String ... p_i46383_1_)
    {
        this(Arrays.asList(p_i46383_1_));
    }

    private void doLabel() {
        this.label = (this.prefix != null ? this.prefix : "") + (this.index >= 0 && this.index < this.options.size() ? (String)this.options.get(this.index) : "ERR");
    }

    public void setPrefix(String string) {
        this.prefix = string;
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
    public void mouseEvent(TMIEvent tMIEvent) {
        if (tMIEvent.mouseButton == 0) {
            ++this.index;
        } else if (tMIEvent.mouseButton == 1) {
            --this.index;
        } else if (tMIEvent.type == 2) {
            this.index -= tMIEvent.wheel;
        }
        if (this.index < 0) {
            this.index = this.options.size() - 1;
        }
        this.index %= this.options.size();
        this.doLabel();
        this.emit(TMIEvent.controlEvent(3, this));
    }
}