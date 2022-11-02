package net.minecraft.src;

import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

public class TMIItemButton
extends TMIButton {
    public boolean dropTarget = false;

    public TMIItemButton(ItemStack p_i13_1_) {
        this(p_i13_1_, false);
    }

    public TMIItemButton(ItemStack p_i14_1_, boolean p_i14_2_) {
        this.stack = p_i14_1_;
        this.itemTooltip = true;
        this.dropTarget = p_i14_2_;
        this.width = 16;
        this.height = 16;
    }

    @Override
    public List<String> getTooltip() {
        if (this.stack != null) {
            List<String> list = this.stack.getTooltip((PlayerEntity)MinecraftClient.getInstance().player, true);
            list.add("\u00a7r\u00a7aClick: give stack");
            list.add("\u00a7r\u00a7aRight-click: give one");
            list.add("\u00a7r\u00a7aShift-click: add to My Items");
            if (this.dropTarget) {
                list.add("\u00a7r\u00a7aYou can drop an item here");
            }
            return list;
        }
        return null;
    }

    @Override
    public void mouseEvent(TMIEvent p_mouseEvent_1_) {
        if (p_mouseEvent_1_.mouseButton == 0) {
            ItemStack itemstack = TMIGame.getHeldItem();
            if (itemstack != null && this.dropTarget) {
                this.stack = itemstack.copy();
                this.emit(TMIEvent.controlEvent(4, this));
            } else if (!Keyboard.isKeyDown((int)42) && !Keyboard.isKeyDown((int)54)) {
                TMIGame.giveStack(new TMIStackBuilder(this.stack).maxStack());
            } else {
                TMISaveFile.addFavorite(this.stack);
            }
            p_mouseEvent_1_.cancel();
        } else if (p_mouseEvent_1_.mouseButton == 1) {
            TMIGame.giveStack(new TMIStackBuilder(this.stack).amount(1).stack());
            p_mouseEvent_1_.cancel();
        }
    }
}