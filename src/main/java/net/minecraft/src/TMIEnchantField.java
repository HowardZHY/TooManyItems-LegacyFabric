package net.minecraft.src;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CommonI18n;

public class TMIEnchantField
extends TMINumField {
    public Enchantment enchantment;

    public TMIEnchantField(Enchantment p_i10_1_, ItemStack p_i10_2_) {
        super(CommonI18n.translate(p_i10_1_.getTranslationKey()));
        this.enchantment = p_i10_1_;
        this.labelColor = p_i10_1_.target.isCompatible(p_i10_2_.getItem()) ? -1 : -2285022;
    }
}