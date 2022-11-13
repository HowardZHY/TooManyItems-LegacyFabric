package net.minecraft.src;

import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class TMIButton
extends TMIArea {
    public String label;
    public Object data;
    public ItemStack stack;
    public boolean showState;
    public boolean state;
    public int textScale;
    public boolean centerText;
    public boolean itemTooltip;

    public TMIButton(Object p_i1_1_, String p_i1_2_, ItemStack p_i1_3_) {
        this.stack = null;
        this.showState = false;
        this.state = false;
        this.textScale = 2;
        this.centerText = true;
        this.itemTooltip = false;
        this.data = p_i1_1_;
        this.label = p_i1_2_;
        this.stack = p_i1_3_;
        this.setOwnWidth();
        this.height = p_i1_3_ != null ? 16 : 12;
    }

    public TMIButton(Object p_i2_1_, String p_i2_2_) {
        this(p_i2_1_, p_i2_2_, (ItemStack) null);
    }

    public TMIButton() {
        this((Object) null, "");
    }

    public TMIButton item(String string) {
        this.stack = new TMIStackBuilder(string).stack();
        return this;
    }

    public TMIButton item(String string, int n) {
        this.stack = new TMIStackBuilder(string).meta(n).stack();
        return this;
    }

    public TMIButton center(boolean bl) {
        this.centerText = bl;
        return this;
    }

    public void setOwnWidth() {
        this.width = TMIDrawing.getTextWidth(this.label, this.textScale) + this.graphicWidth() + this.getMargin();
    }

    public int graphicWidth() {
        return this.stack != null ? 18 : 0;
    }

    public int getMargin() {
        if (this.label != null && this.label.length() > 0) {
            return 6;
        }
        return 2;
    }

    protected boolean drawGraphic(int n) {
        if (this.stack != null) {
            int n2 = this.y + (this.height - 16) / 2;
            TMIDrawing.drawItem(n, n2, this.stack);
            return true;
        }
        return false;
    }

    @Override
    public void drawComponent(int n, int n2) {
        TMIDrawing.fillRect(this.x, this.y, this.width, this.height, this.contains(n, n2) ? 0x22FFFFFF : 0);
        TMIDrawing.drawText(this.x, this.y, "", -1);
        String string = this.label;
        int n3 = TMIDrawing.getTextWidth(string, this.textScale);
        int n4 = this.graphicWidth();
        while (n3 + n4 > this.width && string.length() > 0) {
            string = string.substring(0, string.length() - 1);
            n3 = TMIDrawing.getTextWidth(string, this.textScale);
        }
        int n5 = n3 + n4;
        int n6 = this.x + this.getMargin();
        if (this.centerText) {
            n6 = this.x + (this.width - n5) / 2;
        }
        int n7 = this.y + (this.height - 8) / 2;
        boolean bl = this.drawGraphic(n6);
        n6 += this.graphicWidth();
        if (bl && n3 > 0) {
            n6 += 2;
        }
        TMIDrawing.drawText(n6, n7, string, -1, this.textScale);
    }

    @Override
    public List<String> getTooltip() {
        return this.itemTooltip && this.stack != null ? this.stack.getTooltip((PlayerEntity)MinecraftClient.getInstance().player, true) : null;
    }
}