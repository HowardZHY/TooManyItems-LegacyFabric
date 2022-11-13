package net.minecraft.src;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.lwjgl.opengl.GL11;

public class TMITextField
extends TMIArea {
    public static final int LINE_HEIGHT = 12;
    public String placeholder = "";
    protected TextFieldWidget textField;

    public TMITextField() {
        this.textField = new TextFieldWidget(0, MinecraftClient.getInstance().textRenderer, 0, 0, 0, 0);
        this.setSize(40, 12);
    }

    public void drawComponent() {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        try {
            this.textField.x = this.x;
            this.textField.y = this.y;
            TMIPrivate.textFieldWidth.setInt((Object)this.textField, this.getWidth());
            TMIPrivate.textFieldHeight.setInt((Object)this.textField, this.getHeight());
            this.textField.render();
            if ((this.value() == null || this.value().equals((Object)"")) && !this.isFocused()) {
                TMIDrawing.drawText(this.getX() + 3, this.getY() + 3, this.placeholder, -7829368);
            }
        }
        catch (IllegalAccessException illegalAccessException) {
            TMIDebug.logWithError("Drawing text field", illegalAccessException);
        }
    }

    public void focus() {
        super.focus();
        try {
            Screen screen = MinecraftClient.getInstance().currentScreen;
            if (screen instanceof CreativeInventoryScreen) {
                TextFieldWidget textFieldWidget = (TextFieldWidget)TMIPrivate.creativeSearchBox.get((Object)screen);
                textFieldWidget.setFocusUnlocked(true);
                textFieldWidget.setFocused(false);
            }
        }
        catch (Exception exception) {
            TMIDebug.logWithError("Removing focus from creative search", exception);
        }
        this.textField.setFocused(true);
    }

    public void blur() {
        super.blur();
        this.textField.setFocused(false);
    }

    public String value() {
        return this.textField.getText();
    }

    public void setValue(String string) {
        this.textField.setText(string);
        this.textField.setSelectionEnd(0);
        if (this.isFocused()) {
            this.textField.setCursorToEnd();
        } else {
            this.textField.setCursorToStart();
        }
        this.emit(TMIEvent.controlEvent(3, this));
    }

    public void mouseEvent(TMIEvent tMIEvent) {
        if (tMIEvent.mouseButton == 0) {
            this.focus();
            this.textField.mouseClicked(tMIEvent.x, tMIEvent.y, tMIEvent.mouseButton);
            tMIEvent.cancel();
        }
    }

    public void keyboardEvent(TMIEvent tMIEvent) {
        if (tMIEvent.keyCode != 1 && this.isFocused()) {
            String string = this.value();
            this.textField.keyPressed(tMIEvent.key, tMIEvent.keyCode);
            this.emit(tMIEvent);
            tMIEvent.cancel();
            if (!string.equals((Object)this.value())) {
                this.emit(TMIEvent.controlEvent(3, this));
            }
        }
    }
}