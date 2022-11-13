package net.minecraft.src;

import java.util.List;

public class TMIChromeButton
extends TMIButton {
    private final int iconX;
    private final int iconY;
    private final int iconWidth = 12;
    private final int iconHeight = 12;

    public TMIChromeButton(int n, int n2, String string) {
        this.iconX = n2 * 12;
        this.iconY = n * 12;
        this.setSize(12, 12);
        this.setTooltip(string);
    }

    @Override
    public void drawComponent(int n, int n2) {
        TMIDrawing.drawIcon(this.x + (this.width - 12) / 2, this.y + (this.height - 12) / 2, this.iconX, this.iconY, 12, 12);
    }

    @Override
    public List<String> getTooltip() {
        return this.tooltip;
    }
}