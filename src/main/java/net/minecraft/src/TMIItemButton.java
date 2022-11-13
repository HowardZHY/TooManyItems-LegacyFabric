package net.minecraft.src;

import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

public class TMIItemButton
extends TMIButton {
    public boolean dropTarget = false;

    public TMIItemButton(ItemStack itemStack) {
        this(itemStack, false);
    }

    public TMIItemButton(ItemStack itemStack, boolean bl) {
        this.stack = itemStack;
        this.itemTooltip = true;
        this.dropTarget = bl;
        this.width = 16;
        this.height = 16;
    }

    @Override
    public List<String> getTooltip() {
        if (this.stack != null) {
            List list = this.stack.getTooltip((PlayerEntity)MinecraftClient.getInstance().player, true);
            list.add((Object)"\u00a7r\u00a7aClick: give stack");
            list.add((Object)"\u00a7r\u00a7aRight-click: give one");
            list.add((Object)"\u00a7r\u00a7aShift-click: add to My Items");
            if (this.dropTarget) {
                list.add((Object)"\u00a7r\u00a7aYou can drop an item here");
            }
            return list;
        }
        return null;
    }

    @Override
    public void mouseEvent(TMIEvent tMIEvent) {
        if (tMIEvent.mouseButton == 0) {
            ItemStack itemStack = TMIGame.getHeldItem();
            if (itemStack != null && this.dropTarget) {
                this.stack = itemStack.copy();
                this.emit(TMIEvent.controlEvent(4, this));
            } else if (Keyboard.isKeyDown((int)42) || Keyboard.isKeyDown((int)54)) {
                TMISaveFile.addFavorite(this.stack);
            } else {
                TMIGame.giveStack(new TMIStackBuilder(this.stack).maxStack());
            }
            tMIEvent.cancel();
        } else if (tMIEvent.mouseButton == 1) {
            TMIGame.giveStack(new TMIStackBuilder(this.stack).amount(1).stack());
            tMIEvent.cancel();
        }
    }
}