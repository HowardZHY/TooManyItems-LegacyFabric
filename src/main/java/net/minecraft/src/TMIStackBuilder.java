package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;

public class TMIStackBuilder {
    private ItemStack stack;

    public int id = 1;

    public TMIStackBuilder(Item p_i22_1_) {
        this.stack = null;
        this.stack = new ItemStack(p_i22_1_);
    }

    public TMIStackBuilder(Block p_i23_1_) {
        this.stack = null;
        this.stack = new ItemStack(p_i23_1_);
    }

    public TMIStackBuilder(ItemStack p_i24_1_) {
        this.stack = null;
        this.stack = p_i24_1_.copy();
    }

    public TMIStackBuilder(String p_i25_1_) {
        this((Item)Item.REGISTRY.get(new Identifier(p_i25_1_)));
    }

    public TMIStackBuilder() {
    }

    public ItemStack stack() {
        return this.stack.copy();
    }

    public ItemStack maxStack() {
        ItemStack itemstack = this.stack.copy();
        itemstack.count = itemstack.getItem().getMaxCount();
        return itemstack;
    }

    public NbtCompound asTag() {
        NbtCompound nbttagcompound = new NbtCompound();
        this.stack.toNbt(nbttagcompound);
        return nbttagcompound;
    }

    public TMIStackBuilder amount(int p_amount_1_) {
        this.stack.count = p_amount_1_;
        return this;
    }

    public TMIStackBuilder meta(int p_meta_1_) {
        this.stack.setDamage(p_meta_1_);
        return this;
    }

    public int amount() {
        return this.stack.count;
    }

    public int meta() {
        return this.stack.getDamage();
    }

    public NbtCompound tag() {
        if (this.stack.getNbt() == null) {
            this.stack.setNbt(new NbtCompound());
        }
        return this.stack.getNbt();
    }

    public NbtCompound display() {
        return TMIStackBuilder.getTagWithCreate(this.tag(), "display");
    }

    public NbtCompound blockEntity() {
        return TMIStackBuilder.getTagWithCreate(this.tag(), "BlockEntityTag");
    }

    public TMIStackBuilder name(String p_name_1_) {
        if (p_name_1_ != null && !p_name_1_.equals((Object)"")) {
            this.display().putString("Name", "\u00a7r" + p_name_1_);
        } else {
            this.clearName();
        }
        return this;
    }

    public TMIStackBuilder clearName() {
        if (this.display().contains("Name")) {
            this.display().remove("Name");
        }
        return this;
    }

    public TMIStackBuilder clearEnch() {
        if (this.tag().contains("ench")) {
            this.tag().remove("ench");
        }
        return this;
    }

    public TMIStackBuilder lore(String p_lore_1_) {
        NbtList nbttaglist = TMIStackBuilder.getTagListWithCreate(this.display(), "Lore", 8);
        nbttaglist.add((NbtElement)new NbtString(p_lore_1_));
        return this;
    }

    public TMIStackBuilder ench(Enchantment p_ench_1_, int p_ench_2_) {
        this.stack.addEnchantment(p_ench_1_, p_ench_2_);
        return this;
    }

    public TMIStackBuilder effect(StatusEffect p_effect_1_, int p_effect_2_, int p_effect_3_) {
        NbtList nbttaglist = TMIStackBuilder.getTagListWithCreate(this.tag(), "CustomPotionEffects");
        NbtCompound nbttagcompound = new NbtCompound();
        StatusEffectInstance potioneffect = new StatusEffectInstance(StatusEffect.byIndex(this.id), p_effect_1_.isInstant() ? 0 : p_effect_3_, p_effect_2_);
        potioneffect.toNbt(nbttagcompound);
        nbttaglist.add(nbttagcompound);
        return this;
    }

    public static NbtCompound getTagWithCreate(NbtCompound p_getTagWithCreate_0_, String p_getTagWithCreate_1_) {
        if (!p_getTagWithCreate_0_.contains(p_getTagWithCreate_1_)) {
            p_getTagWithCreate_0_.put(p_getTagWithCreate_1_, (NbtElement)new NbtCompound());
        }
        return p_getTagWithCreate_0_.getCompound(p_getTagWithCreate_1_);
    }

    public static NbtList getTagListWithCreate(NbtCompound p_getTagListWithCreate_0_, String p_getTagListWithCreate_1_, int p_getTagListWithCreate_2_) {
        if (!p_getTagListWithCreate_0_.contains(p_getTagListWithCreate_1_)) {
            p_getTagListWithCreate_0_.put(p_getTagListWithCreate_1_, (NbtElement)new NbtList());
        }
        return p_getTagListWithCreate_0_.getList(p_getTagListWithCreate_1_, p_getTagListWithCreate_2_);
    }

    public static NbtList getTagListWithCreate(NbtCompound p_getTagListWithCreate_0_, String p_getTagListWithCreate_1_) {
        return TMIStackBuilder.getTagListWithCreate(p_getTagListWithCreate_0_, p_getTagListWithCreate_1_, 10);
    }

    public static TMIStackBuilder commandBlock(String p_commandBlock_0_) {
        TMIStackBuilder tmistackbuilder = new TMIStackBuilder("command_block");
        tmistackbuilder.blockEntity().putString("Command", p_commandBlock_0_);
        return tmistackbuilder;
    }
}