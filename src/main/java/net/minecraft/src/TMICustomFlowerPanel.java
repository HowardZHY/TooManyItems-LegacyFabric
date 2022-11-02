package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.itemgroup.ItemGroup;
import net.minecraft.util.Identifier;

class TMICustomFlowerPanel
extends TMIItemGrid {
    public static final List<String> plantNames = Arrays.asList(new String[]{"red_flower", "yellow_flower", "red_mushroom", "brown_mushroom", "sapling", "tallgrass", "deadbush", "cactus"});
    private ItemStack subitem = null;
    private int metaMargin = TMIDrawing.getTextWidth("Meta: ");

    public TMICustomFlowerPanel() {
        super(TMICustomFlowerPanel.getPlantStacks());
        this.TOP = 20;
    }

    private static List<ItemStack> getPlantStacks() {
        ArrayList arraylist = new ArrayList();
        for (String s : plantNames) {
            Item item = new TMIStackBuilder(s).stack().getItem();
            item.appendItemStacks(item, (ItemGroup)null, (List)arraylist);
        }
        return arraylist;
    }

    @Override
    public ItemStack getItemAtXY(int p_getItemAtXY_1_, int p_getItemAtXY_2_) {
        ItemStack itemstack = super.getItemAtXY(p_getItemAtXY_1_, p_getItemAtXY_2_);
        if (itemstack == null) {
            return null;
        }
        TMIStackBuilder tmistackbuilder = new TMIStackBuilder("flower_pot");
        tmistackbuilder.name(TMIGame.stackName(tmistackbuilder.stack()) + " (" + TMIGame.stackName(itemstack) + ")");
        String s = ((Identifier)Item.REGISTRY.getIdentifier(itemstack.getItem())).toString();
        tmistackbuilder.blockEntity().putString("Item", s);
        tmistackbuilder.blockEntity().putInt("Data", itemstack.getDamage());
        return tmistackbuilder.stack();
    }

    @Override
    public void drawComponent(int p_drawComponent_1_, int p_drawComponent_2_) {
        super.drawComponent(p_drawComponent_1_, p_drawComponent_2_);
        TMIDrawing.drawText(this.x + 4, this.y + 4, "Flower pot with:");
    }
}