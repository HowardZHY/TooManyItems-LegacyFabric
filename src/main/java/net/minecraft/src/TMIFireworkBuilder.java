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

    public TMIFireworkBuilder flight(int n) {
        this.flight = (byte)n;
        return this;
    }

    public TMIStackBuilder firework() {
        TMIStackBuilder tMIStackBuilder = new TMIStackBuilder("fireworks");
        NbtCompound nbtCompound = TMIStackBuilder.getTagWithCreate(tMIStackBuilder.tag(), "Fireworks");
        NbtList nbtList = TMIStackBuilder.getTagListWithCreate(nbtCompound, "Explosions");
        nbtCompound.putByte("Flight", this.flight);
        for (NbtCompound nbtCompound2 : this.explosions) {
            nbtList.add((NbtElement)nbtCompound2);
        }
        return tMIStackBuilder;
    }

    public TMIStackBuilder charge() {
        TMIStackBuilder tMIStackBuilder = new TMIStackBuilder("firework_charge");
        if (this.explosions.size() > 0) {
            tMIStackBuilder.tag().put("Explosion", (NbtElement)this.explosions.get(0));
        }
        return tMIStackBuilder;
    }

    public TMIFireworkBuilder explosion(int n, int[] nArray, int[] nArray2, boolean bl, boolean bl2) {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putBoolean("Flicker", bl);
        nbtCompound.putBoolean("Trail", bl2);
        nbtCompound.putByte("Type", (byte)(n & 0xF));
        if (nArray != null && nArray.length > 0) {
            nbtCompound.putIntArray("Colors", nArray);
        }
        if (nArray2 != null && nArray2.length > 0) {
            nbtCompound.putIntArray("FadeColors", nArray2);
        }
        this.explosions.add(nbtCompound);
        return this;
    }

    public static int randomBrightColor() {
        return Color.HSBtoRGB((float)random.nextFloat(), (float)random.nextFloat(), (float)(random.nextFloat() * 0.5f + 0.5f));
    }

    public static TMIStackBuilder randomFirework() {
        TMIFireworkBuilder tMIFireworkBuilder = new TMIFireworkBuilder();
        tMIFireworkBuilder.flight(random.nextInt(3) + 1);
        int[] nArray = random.nextBoolean() ? new int[]{TMIFireworkBuilder.randomBrightColor(), TMIFireworkBuilder.randomBrightColor()} : new int[]{TMIFireworkBuilder.randomBrightColor()};
        int[] nArray2 = random.nextBoolean() ? new int[]{TMIFireworkBuilder.randomBrightColor()} : null;
        tMIFireworkBuilder.explosion(random.nextInt(4), nArray, nArray2, random.nextBoolean(), random.nextBoolean());
        return tMIFireworkBuilder.firework().name("Random Firework");
    }
}