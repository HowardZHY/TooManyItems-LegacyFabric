package net.minecraft.src;/*
 * Decompiled with CFR 0.1.0 (FabricMC a830a72d).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.List
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
        ArrayList arrayList = new ArrayList();
        for (String string : plantNames) {
            Item item = new TMIStackBuilder(string).stack().getItem();
            item.appendItemStacks(item, null, arrayList);
        }
        return arrayList;
    }

    @Override
    public ItemStack getItemAtXY(int n, int n2) {
        ItemStack itemStack = super.getItemAtXY(n, n2);
        if (itemStack == null) {
            return null;
        }
        TMIStackBuilder tMIStackBuilder = new TMIStackBuilder("flower_pot");
        tMIStackBuilder.name(TMIGame.stackName(tMIStackBuilder.stack()) + " (" + TMIGame.stackName(itemStack) + ")");
        String string = Item.REGISTRY.getIdentifier(itemStack.getItem()).toString();
        tMIStackBuilder.blockEntity().putString("Item", string);
        tMIStackBuilder.blockEntity().putInt("Data", itemStack.getDamage());
        return tMIStackBuilder.stack();
    }

    @Override
    public void drawComponent(int n, int n2) {
        super.drawComponent(n, n2);
        TMIDrawing.drawText(this.x + 4, this.y + 4, "Flower pot with:");
    }
}