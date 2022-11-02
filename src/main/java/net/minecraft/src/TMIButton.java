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
    public int textScale = 2;
    public boolean centerText = true;
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

    public TMIButton item(String p_item_1_) {
        this.stack = new TMIStackBuilder(p_item_1_).stack();
        return this;
    }

    public TMIButton item(String p_item_1_, int p_item_2_) {
        this.stack = new TMIStackBuilder(p_item_1_).meta(p_item_2_).stack();
        return this;
    }

    public TMIButton center(boolean p_center_1_) {
        this.centerText = p_center_1_;
        return this;
    }

    public void setOwnWidth() {
        this.width = TMIDrawing.getTextWidth(this.label, this.textScale) + this.graphicWidth() + this.getMargin();
    }

    public int graphicWidth() {
        return this.stack != null ? 18 : 0;
    }

    public int getMargin() {
        return this.label != null && this.label.length() > 0 ? 6 : 2;
    }

    protected boolean drawGraphic(int p_drawGraphic_1_) {
        if (this.stack != null) {
            int i = this.y + (this.height - 16) / 2;
            TMIDrawing.drawItem(p_drawGraphic_1_, i, this.stack);
            return true;
        }
        return false;
    }

    @Override
    public void drawComponent(int p_drawComponent_1_, int p_drawComponent_2_) {
        TMIDrawing.fillRect(this.x, this.y, this.width, this.height, this.contains(p_drawComponent_1_, p_drawComponent_2_) ? 0x22FFFFFF : 0);
        TMIDrawing.drawText(this.x, this.y, "", -1);
        String s = this.label;
        int i = TMIDrawing.getTextWidth(s, this.textScale);
        int j = this.graphicWidth();
        while (i + j > this.width && s.length() > 0) {
            s = s.substring(0, s.length() - 1);
            i = TMIDrawing.getTextWidth(s, this.textScale);
        }
        int k = i + j;
        int l = this.x + this.getMargin();
        if (this.centerText) {
            l = this.x + (this.width - k) / 2;
        }
        int i1 = this.y + (this.height - 8) / 2;
        boolean flag = this.drawGraphic(l);
        l += this.graphicWidth();
        if (flag && i > 0) {
            l += 2;
        }
        TMIDrawing.drawText(l, i1, s, -1, this.textScale);
    }

    @Override
    public List<String> getTooltip() {
        return this.itemTooltip && this.stack != null ? this.stack.getTooltip((PlayerEntity)MinecraftClient.getInstance().player, true) : null;
    }
}