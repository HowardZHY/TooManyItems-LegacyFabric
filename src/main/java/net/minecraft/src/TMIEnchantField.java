package net.minecraft.src;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CommonI18n;

public class TMIEnchantField
extends TMINumField {
    public Enchantment ench;

    public TMIEnchantField(Enchantment enchantment, ItemStack itemStack) {
        super(CommonI18n.translate(enchantment.getTranslationKey()));
        this.ench = enchantment;
        this.labelColor = enchantment.target.isCompatible(itemStack.getItem()) ? -1 : -2285022;
    }
}