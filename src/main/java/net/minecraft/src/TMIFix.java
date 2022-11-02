package net.minecraft.src;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;

public class TMIFix
extends TextFieldWidget {
    private TextFieldWidget Text;

    public TMIFix(int id, TextRenderer textRenderer, int x, int y, int width, int height) {
        super(id, textRenderer, x, y, width, height);
        this.Text = new TextFieldWidget(id, textRenderer, x, y, width, height);
    }
}