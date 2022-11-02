package net.minecraft.src;

public class TMILabel
extends TMIArea {
    public String text = "";

    public TMILabel(String p_i17_1_) {
        this.text = p_i17_1_;
        this.height = 12;
        this.width = TMIDrawing.getTextWidth(p_i17_1_);
    }

    @Override
    public void drawComponent() {
        TMIDrawing.drawText(this.x, this.y, this.text);
    }
}