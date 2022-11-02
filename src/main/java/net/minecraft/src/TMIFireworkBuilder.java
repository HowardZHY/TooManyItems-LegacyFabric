package net.minecraft.src;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

public class TMIFireworkBuilder {
    public static final Random random = new Random();
    private byte flight = (byte)2;
    private List<NbtCompound> explosions = new ArrayList();

    public TMIFireworkBuilder flight(int p_flight_1_) {
        this.flight = (byte)p_flight_1_;
        return this;
    }

    public TMIStackBuilder firework() {
        TMIStackBuilder tmistackbuilder = new TMIStackBuilder("fireworks");
        NbtCompound nbttagcompound = TMIStackBuilder.getTagWithCreate(tmistackbuilder.tag(), "Fireworks");
        NbtList nbttaglist = TMIStackBuilder.getTagListWithCreate(nbttagcompound, "Explosions");
        nbttagcompound.putByte("Flight", this.flight);
        for (NbtCompound nbttagcompound1 : this.explosions) {
            nbttaglist.add((NbtElement)nbttagcompound1);
        }
        return tmistackbuilder;
    }

    public TMIStackBuilder charge() {
        TMIStackBuilder tmistackbuilder = new TMIStackBuilder("firework_charge");
        if (this.explosions.size() > 0) {
            tmistackbuilder.tag().put("Explosion", (NbtElement)this.explosions.get(0));
        }
        return tmistackbuilder;
    }

    public TMIFireworkBuilder explosion(int p_explosion_1_, int[] p_explosion_2_, int[] p_explosion_3_, boolean p_explosion_4_, boolean p_explosion_5_) {
        NbtCompound nbttagcompound = new NbtCompound();
        nbttagcompound.putBoolean("Flicker", p_explosion_4_);
        nbttagcompound.putBoolean("Trail", p_explosion_5_);
        nbttagcompound.putByte("Type", (byte)(p_explosion_1_ & 0xF));
        if (p_explosion_2_ != null && p_explosion_2_.length > 0) {
            nbttagcompound.putIntArray("Colors", p_explosion_2_);
        }
        if (p_explosion_3_ != null && p_explosion_3_.length > 0) {
            nbttagcompound.putIntArray("FadeColors", p_explosion_3_);
        }
        this.explosions.add(nbttagcompound);
        return this;
    }

    public static int randomBrightColor() {
        return Color.HSBtoRGB((float)random.nextFloat(), (float)random.nextFloat(), (float)(random.nextFloat() * 0.5f + 0.5f));
    }

    public static TMIStackBuilder randomFirework() {
        TMIFireworkBuilder tmifireworkbuilder = new TMIFireworkBuilder();
        tmifireworkbuilder.flight(random.nextInt(3) + 1);
        int[] aint = random.nextBoolean() ? new int[]{TMIFireworkBuilder.randomBrightColor(), TMIFireworkBuilder.randomBrightColor()} : new int[]{TMIFireworkBuilder.randomBrightColor()};
        int[] aint1 = random.nextBoolean() ? new int[]{TMIFireworkBuilder.randomBrightColor()} : null;
        tmifireworkbuilder.explosion(random.nextInt(4), aint, aint1, random.nextBoolean(), random.nextBoolean());
        return tmifireworkbuilder.firework().name("Random Firework");
    }
}