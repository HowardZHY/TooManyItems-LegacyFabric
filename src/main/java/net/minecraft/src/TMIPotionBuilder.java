package net.minecraft.src;

import net.minecraft.nbt.NbtList;

public class TMIPotionBuilder
extends TMIStackBuilder {
    public TMIPotionBuilder() {
        super("potion");
    }

    public NbtList potionEffects() {
        return TMIPotionBuilder.getTagListWithCreate(this.tag(), "CustomPotionEffects");
    }

    public TMIPotionBuilder splash(boolean bl) {
        int n = this.meta();
        if (bl) {
            n &= 0xFFFFDFFF;
            n |= 0x4000;
        } else {
            n &= 0xFFFFBFFF;
            n |= 0x2000;
        }
        this.meta(n);
        return this;
    }

    public TMIPotionBuilder color(int n) {
        int n2 = this.meta() & 0xFFFFFFF0;
        this.meta(n2 |= (n &= 0xF));
        return this;
    }
}