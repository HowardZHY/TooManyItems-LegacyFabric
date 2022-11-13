package net.minecraft.src;

import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

class TMISpawnerBuilder
extends TMIStackBuilder {
    public static final Random random = new Random();

    public TMISpawnerBuilder() {
        super("mob_spawner");
    }

    public TMISpawnerBuilder(String string) {
        super("mob_spawner");
        this.entityID(string);
    }

    public TMISpawnerBuilder(TMISpawnerBuilder tMISpawnerBuilder) {
        super(tMISpawnerBuilder.stack());
    }

    public NbtCompound spawnData() {
        return TMISpawnerBuilder.getTagWithCreate(this.blockEntity(), "SpawnData");
    }

    public NbtList spawnPotentials() {
        return TMISpawnerBuilder.getTagListWithCreate(this.blockEntity(), "SpawnPotentials");
    }

    public short attr(String string) {
        return this.blockEntity().getShort(string);
    }

    public TMISpawnerBuilder attr(String string, int n) {
        this.blockEntity().putShort(string, (short)n);
        return this;
    }

    public String entityID() {
        return this.blockEntity().getString("EntityId");
    }

    public TMISpawnerBuilder entityID(String string) {
        this.blockEntity().putString("EntityId", string);
        return this;
    }

    public TMISpawnerBuilder weight(int n) {
        this.bePotential();
        this.spawnPotentials().getCompound(0).putInt("Weight", n);
        return this;
    }

    public TMISpawnerBuilder addPotential(int n, TMISpawnerBuilder tMISpawnerBuilder) {
        this.bePotential();
        this.addPotential(n, tMISpawnerBuilder, false);
        return this;
    }

    public TMISpawnerBuilder data(String string, boolean bl) {
        this.spawnData().putBoolean(string, bl);
        return this;
    }

    public TMISpawnerBuilder data(String string, byte by) {
        this.spawnData().putByte(string, by);
        return this;
    }

    public TMISpawnerBuilder data(String string, int n) {
        this.spawnData().putInt(string, n);
        return this;
    }

    public TMISpawnerBuilder data(String string, NbtElement nbtElement) {
        this.spawnData().put(string, nbtElement);
        return this;
    }

    public TMISpawnerBuilder data(String string, String string2) {
        this.spawnData().putString(string, string2);
        return this;
    }

    public TMISpawnerBuilder equipment(ItemStack ... itemStackArray) {
        NbtList nbtList = TMISpawnerBuilder.getTagListWithCreate(this.spawnData(), "Equipment");
        for (int i = 0; i < 5; ++i) {
            if (i < itemStackArray.length && itemStackArray[i] != null) {
                NbtCompound nbtCompound = new NbtCompound();
                itemStackArray[i].toNbt(nbtCompound);
                nbtList.add((NbtElement)nbtCompound);
                continue;
            }
            nbtList.add((NbtElement)new NbtCompound());
        }
        return this;
    }

    public static ItemStack randomFireworkSpawner(int n) {
        TMIStackBuilder tMIStackBuilder = null;
        for (int i = 0; i < 10; ++i) {
            TMISpawnerBuilder tMISpawnerBuilder = new TMISpawnerBuilder("FireworksRocketEntity").attr("MinSpawnDelay", 20).attr("MaxSpawnDelay", 20).attr("SpawnCount", 1).attr("MaxNearbyEntities", 5).attr("RequiredPlayerRange", 120).attr("SpawnRange", 2);
            tMISpawnerBuilder.data("FireworksItem", (NbtElement)TMIFireworkBuilder.randomFirework().stack().toNbt(new NbtCompound()));
            tMISpawnerBuilder.data("LifeTime", random.nextInt(15) + random.nextInt(15) + 20);
            if (i == 0) {
                tMIStackBuilder = tMISpawnerBuilder;
                continue;
            }
            ((TMISpawnerBuilder)tMIStackBuilder).addPotential(1, tMISpawnerBuilder);
        }
        return tMIStackBuilder.name("TMI Random Firework Spawner").lore("Every one is different!").amount(n).stack();
    }

    private void addPotential(int n, TMISpawnerBuilder tMISpawnerBuilder, boolean bl) {
        if (!bl) {
            this.bePotential();
        }
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putInt("Weight", n);
        nbtCompound.putString("Type", tMISpawnerBuilder.blockEntity().getString("EntityId"));
        nbtCompound.put("Properties", tMISpawnerBuilder.spawnData().copy());
        this.spawnPotentials().add((NbtElement)nbtCompound);
    }

    private void bePotential() {
        NbtList nbtList = this.spawnPotentials();
        if (nbtList.size() == 0) {
            this.addPotential(1, this, true);
        }
    }
}