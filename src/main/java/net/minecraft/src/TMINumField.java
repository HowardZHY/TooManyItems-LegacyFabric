package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class TMINumField
extends TMITextField {
    public String label;
    public int labelColor = -1;
    public int inputWidth = 30;

    public TMINumField(String p_i18_1_) {
        this(p_i18_1_, 30);
    }

    public TMINumField(String p_i19_1_, int p_i19_2_) {
        this.label = p_i19_1_;
        this.inputWidth = p_i19_2_;
    }

    @Override
    public void drawComponent() {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        TMIDrawing.drawText(this.x, this.y + 1, TMIDrawing.cutTextToWidth(this.label, this.width - this.inputWidth - 2), this.labelColor);
        try {
            this.textField.x = this.x + this.width - this.inputWidth - 2;
            this.textField.y = this.y;
            TMIPrivate.textFieldWidth.setInt((Object)this.textField, this.inputWidth);
            TMIPrivate.textFieldHeight.setInt((Object)this.textField, 12);
            this.textField.render();
        }
        catch (IllegalAccessException illegalaccessexception) {
            TMIDebug.logWithError("Drawing text field", illegalaccessexception);
        }
    }

    public int intValue() {
        if (this.value().equals((Object)"")) {
            return 0;
        }
        try {
            return Integer.parseInt((String)this.value());
        }
        catch (NumberFormatException numberformatexception) {
            TMIDebug.reportException(numberformatexception);
            return 0;
        }
    }

    @Override
    public void setValue(String p_setValue_1_) {
        try {
            Integer.parseInt((String)p_setValue_1_);
            super.setValue(p_setValue_1_);
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
    }

    @Override
    public void keyboardEvent(TMIEvent p_keyboardEvent_1_) {
        if (p_keyboardEvent_1_.keyCode == 14 || p_keyboardEvent_1_.keyCode == 211 || p_keyboardEvent_1_.keyCode == 15 || p_keyboardEvent_1_.key >= '0' && p_keyboardEvent_1_.key <= '9') {
            super.keyboardEvent(p_keyboardEvent_1_);
        }
    }
}