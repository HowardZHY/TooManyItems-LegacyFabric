package net.minecraft.src;
import org.lwjgl.opengl.GL11;

public class TMINumField
extends TMITextField {
    public String label;
    public int labelColor = -1;
    public int inputWidth = 30;

    public TMINumField(String string) {
        this(string, 30);
    }

    public TMINumField(String string, int n) {
        this.label = string;
        this.inputWidth = n;
    }

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
        catch (IllegalAccessException illegalAccessException) {
            TMIDebug.logWithError("Drawing text field", illegalAccessException);
        }
    }

    public int intValue() {
        if (this.value().equals((Object)"")) {
            return 0;
        }
        try {
            return Integer.parseInt((String)this.value());
        }
        catch (NumberFormatException numberFormatException) {
            TMIDebug.reportException(numberFormatException);
            return 0;
        }
    }

    public void setValue(String string) {
        try {
            Integer.parseInt((String)string);
            super.setValue(string);
        }
        catch (NumberFormatException numberFormatException) {
            return;
        }
    }

    public void keyboardEvent(TMIEvent tMIEvent) {
        if (tMIEvent.keyCode != 14 && tMIEvent.keyCode != 211 && tMIEvent.keyCode != 15 && (tMIEvent.key < '0' || tMIEvent.key > '9')) {
            return;
        }
        super.keyboardEvent(tMIEvent);
    }
}