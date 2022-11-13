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
    private ItemStack stack = null;

    public TMIStackBuilder(Item item) {
        this.stack = new ItemStack(item);
    }

    public TMIStackBuilder(Block block) {
        this.stack = new ItemStack(block);
    }

    public TMIStackBuilder(ItemStack itemStack) {
        this.stack = itemStack.copy();
    }

    public TMIStackBuilder(String string) {
        this(Item.REGISTRY.get(new Identifier(string)));
    }

    public ItemStack stack() {
        return this.stack.copy();
    }

    public ItemStack maxStack() {
        ItemStack itemStack = this.stack.copy();
        itemStack.count = itemStack.getItem().getMaxCount();
        return itemStack;
    }

    public NbtCompound asTag() {
        NbtCompound nbtCompound = new NbtCompound();
        this.stack.toNbt(nbtCompound);
        return nbtCompound;
    }

    public TMIStackBuilder amount(int n) {
        this.stack.count = n;
        return this;
    }

    public TMIStackBuilder meta(int n) {
        this.stack.setDamage(n);
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

    public TMIStackBuilder name(String string) {
        if (string == null || string.equals((Object)"")) {
            this.clearName();
        } else {
            this.display().putString("Name", "\u00a7r" + string);
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

    public TMIStackBuilder lore(String string) {
        NbtList nbtList = TMIStackBuilder.getTagListWithCreate(this.display(), "Lore", 8);
        nbtList.add((NbtElement)new NbtString(string));
        return this;
    }

    public TMIStackBuilder ench(Enchantment enchantment, int n) {
        this.stack.addEnchantment(enchantment, n);
        return this;
    }

    public TMIStackBuilder effect(StatusEffect statusEffect, int n, int n2) {
        NbtList nbtList = TMIStackBuilder.getTagListWithCreate(this.tag(), "CustomPotionEffects");
        NbtCompound nbtCompound = new NbtCompound();
        StatusEffectInstance statusEffectInstance = new StatusEffectInstance(statusEffect.id, statusEffect.isInstant() ? 0 : n2, n);
        statusEffectInstance.toNbt(nbtCompound);
        nbtList.add((NbtElement)nbtCompound);
        return this;
    }

    public static NbtCompound getTagWithCreate(NbtCompound nbtCompound, String string) {
        if (!nbtCompound.contains(string)) {
            nbtCompound.put(string, (NbtElement)new NbtCompound());
        }
        return nbtCompound.getCompound(string);
    }

    public static NbtList getTagListWithCreate(NbtCompound nbtCompound, String string, int n) {
        if (!nbtCompound.contains(string)) {
            nbtCompound.put(string, (NbtElement)new NbtList());
        }
        return nbtCompound.getList(string, n);
    }

    public static NbtList getTagListWithCreate(NbtCompound nbtCompound, String string) {
        return TMIStackBuilder.getTagListWithCreate(nbtCompound, string, 10);
    }

    public static TMIStackBuilder commandBlock(String string) {
        TMIStackBuilder tMIStackBuilder = new TMIStackBuilder("command_block");
        tMIStackBuilder.blockEntity().putString("Command", string);
        return tMIStackBuilder;
    }
}