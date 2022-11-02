package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public class TMIExtraItems {
    public static List<ItemStack> items() {
        String[] astring;
        ArrayList arraylist = new ArrayList();
        ItemStack itemstack = new TMIStackBuilder("bow").stack();
        ItemStack itemstack1 = new TMIStackBuilder("stone_sword").stack();
        arraylist.add((Object)new TMIFireworkBuilder().flight(1).explosion(3, new int[]{4312372}, new int[]{15435844}, true, false).firework().name("Creepy Sparkler").stack());
        arraylist.add((Object)new TMIFireworkBuilder().flight(4).explosion(2, new int[]{6719955}, null, false, false).firework().name("Star").stack());
        arraylist.add((Object)new TMIFireworkBuilder().explosion(1, new int[]{11743532}, null, false, false).firework().name("Big Red").stack());
        arraylist.add((Object)new TMIFireworkBuilder().explosion(1, new int[]{11743532, 0xF0F0F0, 2437522}, new int[]{0xF0F0F0}, true, true).firework().name("Old Glory").stack());
        for (String s : astring = new String[]{"FallingSand", "Boat", "Creeper", "Skeleton", "Spider", "Giant", "Zombie", "Slime", "Ghast", "PigZombie", "Enderman", "CaveSpider", "Silverfish", "Blaze", "LavaSlime", "EnderDragon", "WitherBoss", "Bat", "Witch", "Endermite", "Guardian", "Pig", "Sheep", "Cow", "Chicken", "Squid", "Wolf", "MushroomCow", "SnowMan", "Ozelot", "VillagerGolem", "Rabbit"}) {
            arraylist.add((Object)new TMISpawnerBuilder(s).name(s + " Spawner").stack());
        }
        arraylist.add((Object)new TMISpawnerBuilder("Creeper").data("powered", true).name("Charged Creeper Spawner").stack());
        arraylist.add((Object)new TMISpawnerBuilder("Pig").data("Saddle", true).name("Saddled Pig Spawner").stack());
        arraylist.add((Object)new TMISpawnerBuilder("Skeleton").data("SkeletonType", (byte)1).equipment(itemstack1).name("Wither Skeleton Spawner").stack());
        arraylist.add((Object)new TMISpawnerBuilder("Zombie").data("IsVillager", true).name("Zombie Villager Spawner").stack());
        arraylist.add((Object)new TMISpawnerBuilder("Zombie").data("IsBaby", true).name("Zombie Baby Spawner").stack());
        arraylist.add((Object)new TMISpawnerBuilder("Zombie").data("IsVillager", true).data("IsBaby", true).name("Zombie Baby Villager Spawner").stack());
        NbtCompound nbttagcompound = new NbtCompound();
        nbttagcompound.putString("id", "Spider");
        arraylist.add((Object)new TMISpawnerBuilder("Skeleton").data("Riding", (NbtElement)nbttagcompound).equipment(itemstack).name("Spider Jockey Spawner").stack());
        arraylist.add((Object)new TMISpawnerBuilder("Skeleton").data("SkeletonType", (byte)1).data("Riding", (NbtElement)nbttagcompound).equipment(itemstack).name("Wither Jockey Spawner").stack());
        String[] astring1 = new String[]{"Farmer", "Librarian", "Priest", "Blacksmith", "Butcher", "Generic"};
        for (int j = 0; j < astring1.length; ++j) {
            arraylist.add((Object)new TMISpawnerBuilder("Villager").data("Profession", j).name("Village " + astring1[j] + " Spawner").stack());
        }
        String[] astring2 = new String[]{"Horse", "Donkey", "Mule", "Zombie Horse", "Skeleton Horse"};
        for (int i = 0; i < astring2.length; ++i) {
            arraylist.add((Object)new TMISpawnerBuilder("EntityHorse").data("Age", 6000).data("Type", i).name(astring2[i] + " Spawner").stack());
        }
        arraylist.add((Object)new TMISpawnerBuilder("FallingSand").data("Block", "torch").data("Time", (byte)2).data("DropItem", false).data("HurtEntities", false).attr("MinSpawnDelay", 15).attr("MaxSpawnDelay", 15).attr("SpawnCount", 10).attr("MaxNearbyEntities", 15).attr("RequiredPlayerRange", 16).attr("SpawnRange", 136).name("TMI Wide-Area Torch Spawner").lore("It's full of torches!").stack());
        arraylist.add((Object)TMISpawnerBuilder.randomFireworkSpawner(1));
        return arraylist;
    }
}