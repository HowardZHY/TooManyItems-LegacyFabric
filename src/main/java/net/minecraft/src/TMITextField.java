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
    protected TMIFix textField;

    protected TMITextField() {
        this.textField = new TMIFix(0, MinecraftClient.getInstance().textRenderer, 0, 0, 0, 0);
        this.setSize(40, 12);
    }

    @Override
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
        catch (IllegalAccessException illegalaccessexception) {
            TMIDebug.logWithError("Drawing text field", illegalaccessexception);
        }
    }

    @Override
    public void focus() {
        super.focus();
        try {
            Screen guiscreen = MinecraftClient.getInstance().currentScreen;
            if (guiscreen instanceof CreativeInventoryScreen) {
                TextFieldWidget guitextfield = (TextFieldWidget)(TMIPrivate.creativeSearchBox.get(guiscreen));
                guitextfield.setFocusUnlocked(true);
                guitextfield.setFocused(false);
            }
        }
        catch (Exception exception) {
            TMIDebug.logWithError("Removing focus from creative search", exception);
        }
        this.textField.setFocused(true);
    }

    @Override
    public void blur() {
        super.blur();
        this.textField.setFocused(false);
    }

    public String value() {
        return this.textField.getText();
    }

    public void setValue(String p_setValue_1_) {
        this.textField.setText(p_setValue_1_);
        this.textField.setSelectionEnd(0);
        if (this.isFocused()) {
            this.textField.setCursorToEnd();
        } else {
            this.textField.setCursorToStart();
        }
        this.emit(TMIEvent.controlEvent(3, this));
    }

    @Override
    public void mouseEvent(TMIEvent p_mouseEvent_1_) {
        if (p_mouseEvent_1_.mouseButton == 0) {
            this.focus();
            this.textField.mouseClicked(p_mouseEvent_1_.x, p_mouseEvent_1_.y, p_mouseEvent_1_.mouseButton);
            p_mouseEvent_1_.cancel();
        }
    }

    @Override
    public void keyboardEvent(TMIEvent p_keyboardEvent_1_) {
        if (p_keyboardEvent_1_.keyCode != 1 && this.isFocused()) {
            String s = this.value();
            this.textField.keyPressed(p_keyboardEvent_1_.key, p_keyboardEvent_1_.keyCode);
            this.emit(p_keyboardEvent_1_);
            p_keyboardEvent_1_.cancel();
            if (!s.equals((Object)this.value())) {
                this.emit(TMIEvent.controlEvent(3, this));
            }
        }
    }
}