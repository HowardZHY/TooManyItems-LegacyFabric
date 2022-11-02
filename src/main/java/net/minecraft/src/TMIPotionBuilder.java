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

    public TMIPotionBuilder splash(boolean p_splash_1_) {
        int i = this.meta();
        if (p_splash_1_) {
            i &= 0xFFFFDFFF;
            i |= 0x4000;
        } else {
            i &= 0xFFFFBFFF;
            i |= 0x2000;
        }
        this.meta(i);
        return this;
    }

    public TMIPotionBuilder color(int p_color_1_) {
        int i = this.meta() & 0xFFFFFFF0;
        this.meta(i |= (p_color_1_ &= 0xF));
        return this;
    }
}