package net.minecraft.src;

public class TMILabel
extends TMIArea {
    public String text = "";

    public TMILabel(String string) {
        this.text = string;
        this.height = 12;
        this.width = TMIDrawing.getTextWidth(string);
    }

    public void drawComponent() {
        TMIDrawing.drawText(this.x, this.y, this.text);
    }
}