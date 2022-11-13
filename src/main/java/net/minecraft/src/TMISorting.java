package net.minecraft.src;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TMISorting {
    public static void sortByCreativeTab(List<ItemStack> list) {
        Collections.sort(list, (Comparator)new Comparator<ItemStack>(){

            public int compare(ItemStack itemStack, ItemStack itemStack2) {
                int n = 255;
                int n2 = 255;
                try {
                    n = itemStack.getItem().getItemGroup().getIndex();
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
                try {
                    n2 = itemStack2.getItem().getItemGroup().getIndex();
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
                int n3 = Item.getRawId((Item)itemStack.getItem());
                int n4 = Item.getRawId((Item)itemStack2.getItem());
                if (n == n2) {
                    if (n3 == n4) {
                        return itemStack.getDamage() > itemStack2.getDamage() ? 1 : -1;
                    }
                    return n3 > n4 ? 1 : -1;
                }
                return n > n2 ? 1 : -1;
            }
        });
    }
}