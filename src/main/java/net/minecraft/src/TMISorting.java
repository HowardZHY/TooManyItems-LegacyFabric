package net.minecraft.src;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TMISorting {
    public static void sortByCreativeTab(List<ItemStack> p_sortByCreativeTab_0_) {
        Collections.sort(p_sortByCreativeTab_0_, (Comparator)new Comparator(){

            public int compare(ItemStack p_compare_1_, ItemStack p_compare_2_) {
                int i = 255;
                int j = 255;
                try {
                    i = p_compare_1_.getItem().getItemGroup().getIndex();
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
                try {
                    j = p_compare_2_.getItem().getItemGroup().getIndex();
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
                int k = Item.getRawId((Item)p_compare_1_.getItem());
                int l = Item.getRawId((Item)p_compare_2_.getItem());
                return i == j ? (k == l ? (p_compare_1_.getDamage() > p_compare_2_.getDamage() ? 1 : -1) : (k > l ? 1 : -1)) : (i > j ? 1 : -1);
            }

            public int compare(Object p_compare_1_, Object p_compare_2_) {
                return this.compare((ItemStack)p_compare_1_, (ItemStack)p_compare_2_);
            }
        });
    }
}