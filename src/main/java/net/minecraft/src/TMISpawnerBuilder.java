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

    public TMISpawnerBuilder(String p_i20_1_) {
        super("mob_spawner");
        this.entityID(p_i20_1_);
    }

    public TMISpawnerBuilder(TMISpawnerBuilder p_i21_1_) {
        super(p_i21_1_.stack());
    }

    public NbtCompound spawnData() {
        return TMISpawnerBuilder.getTagWithCreate(this.blockEntity(), "SpawnData");
    }

    public NbtList spawnPotentials() {
        return TMISpawnerBuilder.getTagListWithCreate(this.blockEntity(), "SpawnPotentials");
    }

    public short attr(String p_attr_1_) {
        return this.blockEntity().getShort(p_attr_1_);
    }

    public TMISpawnerBuilder attr(String p_attr_1_, int p_attr_2_) {
        this.blockEntity().putShort(p_attr_1_, (short)p_attr_2_);
        return this;
    }

    public String entityID() {
        return this.blockEntity().getString("EntityId");
    }

    public TMISpawnerBuilder entityID(String p_entityID_1_) {
        this.blockEntity().putString("EntityId", p_entityID_1_);
        return this;
    }

    public TMISpawnerBuilder weight(int p_weight_1_) {
        this.bePotential();
        this.spawnPotentials().getCompound(0).putInt("Weight", p_weight_1_);
        return this;
    }

    public TMISpawnerBuilder addPotential(int p_addPotential_1_, TMISpawnerBuilder p_addPotential_2_) {
        this.bePotential();
        this.addPotential(p_addPotential_1_, p_addPotential_2_, false);
        return this;
    }

    public TMISpawnerBuilder data(String p_data_1_, boolean p_data_2_) {
        this.spawnData().putBoolean(p_data_1_, p_data_2_);
        return this;
    }

    public TMISpawnerBuilder data(String p_data_1_, byte p_data_2_) {
        this.spawnData().putByte(p_data_1_, p_data_2_);
        return this;
    }

    public TMISpawnerBuilder data(String p_data_1_, int p_data_2_) {
        this.spawnData().putInt(p_data_1_, p_data_2_);
        return this;
    }

    public TMISpawnerBuilder data(String p_data_1_, NbtElement p_data_2_) {
        this.spawnData().put(p_data_1_, p_data_2_);
        return this;
    }

    public TMISpawnerBuilder data(String p_data_1_, String p_data_2_) {
        this.spawnData().putString(p_data_1_, p_data_2_);
        return this;
    }

    public TMISpawnerBuilder equipment(ItemStack ... p_equipment_1_) {
        NbtList nbttaglist = TMISpawnerBuilder.getTagListWithCreate(this.spawnData(), "Equipment");
        for (int i = 0; i < 5; ++i) {
            if (i < p_equipment_1_.length && p_equipment_1_[i] != null) {
                NbtCompound nbttagcompound = new NbtCompound();
                p_equipment_1_[i].toNbt(nbttagcompound);
                nbttaglist.add((NbtElement)nbttagcompound);
                continue;
            }
            nbttaglist.add((NbtElement)new NbtCompound());
        }
        return this;
    }

    public static ItemStack randomFireworkSpawner(int p_randomFireworkSpawner_0_) {
        TMIStackBuilder tmispawnerbuilder = null;
        for (int i = 0; i < 10; ++i) {
            TMISpawnerBuilder tmispawnerbuilder1 = new TMISpawnerBuilder("FireworksRocketEntity").attr("MinSpawnDelay", 20).attr("MaxSpawnDelay", 20).attr("SpawnCount", 1).attr("MaxNearbyEntities", 5).attr("RequiredPlayerRange", 120).attr("SpawnRange", 2);
            tmispawnerbuilder1.data("FireworksItem", (NbtElement)TMIFireworkBuilder.randomFirework().stack().toNbt(new NbtCompound()));
            tmispawnerbuilder1.data("LifeTime", random.nextInt(15) + random.nextInt(15) + 20);
            if (i == 0) {
                tmispawnerbuilder = tmispawnerbuilder1;
                continue;
            }
            ((TMISpawnerBuilder)tmispawnerbuilder).addPotential(1, tmispawnerbuilder1);
        }
        return tmispawnerbuilder.name("TMI Random Firework Spawner").lore("Every one is different!").amount(p_randomFireworkSpawner_0_).stack();
    }

    private void addPotential(int p_addPotential_1_, TMISpawnerBuilder p_addPotential_2_, boolean p_addPotential_3_) {
        if (!p_addPotential_3_) {
            this.bePotential();
        }
        NbtCompound nbttagcompound = new NbtCompound();
        nbttagcompound.putInt("Weight", p_addPotential_1_);
        nbttagcompound.putString("Type", p_addPotential_2_.blockEntity().getString("EntityId"));
        nbttagcompound.put("Properties", (NbtElement)p_addPotential_2_.spawnData().copy());
        this.spawnPotentials().add((NbtElement)nbttagcompound);
    }

    private void bePotential() {
        NbtList nbttaglist = this.spawnPotentials();
        if (nbttaglist.size() == 0) {
            this.addPotential(1, this, true);
        }
    }
}