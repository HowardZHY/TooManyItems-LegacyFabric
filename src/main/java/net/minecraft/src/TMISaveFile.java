package net.minecraft.src;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.PositionTracker;

public class TMISaveFile {
    public static final String filename = "TMI.nbt";
    public static final int INVENTORY_SIZE = 44;
    public static final List<ItemStack> favorites = new ArrayList();
    private static ItemStack[][] states = new ItemStack[7][44];
    public static boolean[] statesSaved = new boolean[7];

    public static File file() {
        return new File(MinecraftClient.getInstance().runDirectory, filename);
    }

    public static boolean read() {
        try {
            File file1 = TMISaveFile.file();
            if (!file1.exists()) {
                return false;
            }
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));
            NbtList nbttaglist = new NbtList();
            nbttaglist.read((DataInput)datainputstream, 1, new PositionTracker(0x200000L));
            int i = nbttaglist.size();
            if (i == 0) {
                return false;
            }
            favorites.clear();
            NbtList nbttaglist1 = (NbtList)nbttaglist.get(0);
            for (int j = 0; j < nbttaglist1.size(); ++j) {
                ItemStack itemstack = new ItemStack((Item)null);
                itemstack.writeNbt((NbtCompound)nbttaglist1.get(j));
                favorites.add(itemstack);
            }
            for (int l = 0; l < i - 1 && l < states.length; ++l) {
                NbtList nbttaglist2 = (NbtList)nbttaglist.get(l + 1);
                if (nbttaglist2.size() == 0) continue;
                ItemStack[] aitemstack = states[l];
                for (int k = 0; k < nbttaglist2.size(); ++k) {
                    NbtCompound nbttagcompound = nbttaglist2.getCompound(k);
                    if (nbttagcompound.contains("id")) {
                        aitemstack[k] = new ItemStack((Item)null);
                        aitemstack[k].writeNbt(nbttagcompound);
                        continue;
                    }
                    aitemstack[k] = null;
                }
                TMISaveFile.statesSaved[l] = true;
            }
            return true;
        }
        catch (Throwable throwable) {
            TMIDebug.reportException(throwable);
            return false;
        }
    }

    public static void write() {
        try {
            NbtList nbttaglist = new NbtList();
            NbtList nbttaglist1 = new NbtList();
            for (ItemStack itemstack : favorites) {
                NbtCompound nbttagcompound = new NbtCompound();
                if (itemstack != null) {
                    itemstack.toNbt(nbttagcompound);
                }
                nbttaglist1.add((NbtElement)nbttagcompound);
            }
            nbttaglist.add((NbtElement)nbttaglist1);
            for (int j = 0; j < states.length; ++j) {
                if (!statesSaved[j]) {
                    nbttaglist.add((NbtElement)new NbtList());
                    continue;
                }
                ItemStack[] aitemstack = states[j];
                NbtList nbttaglist2 = new NbtList();
                for (int i = 0; i < aitemstack.length; ++i) {
                    NbtCompound nbttagcompound1 = new NbtCompound();
                    if (aitemstack[i] != null) {
                        aitemstack[i].toNbt(nbttagcompound1);
                    }
                    nbttaglist2.add((NbtElement)nbttagcompound1);
                }
                nbttaglist.add((NbtElement)nbttaglist2);
            }
            DataOutputStream dataoutputstream = new DataOutputStream((OutputStream)new FileOutputStream(TMISaveFile.file()));
            nbttaglist.write((DataOutput)dataoutputstream);
        }
        catch (Throwable throwable) {
            TMIDebug.reportException(throwable);
        }
    }

    public static void addFavorite(ItemStack p_addFavorite_0_) {
        favorites.add(p_addFavorite_0_);
        TMISaveFile.write();
    }

    public static void removeFavorite(ItemStack p_removeFavorite_0_) {
        favorites.remove(p_removeFavorite_0_);
        TMISaveFile.write();
    }

    public static void saveState(int p_saveState_0_) {
        ItemStack[] aitemstack = MinecraftClient.getInstance().player.inventory.main;
        ItemStack[] aitemstack1 = MinecraftClient.getInstance().player.inventory.armor;
        for (int i = 0; i < 4; ++i) {
            TMISaveFile.states[p_saveState_0_][i + 4] = TMIGame.copyStack(aitemstack1[i]);
        }
        for (int j = 0; j < 27; ++j) {
            TMISaveFile.states[p_saveState_0_][j + 8] = TMIGame.copyStack(aitemstack[j + 9]);
        }
        for (int k = 0; k < 9; ++k) {
            TMISaveFile.states[p_saveState_0_][k + 8 + 27] = TMIGame.copyStack(aitemstack[k]);
        }
        TMISaveFile.statesSaved[p_saveState_0_] = true;
        TMISaveFile.write();
    }

    public static void loadState(int p_loadState_0_) {
        if (statesSaved[p_loadState_0_]) {
            ItemStack[] aitemstack = MinecraftClient.getInstance().player.inventory.main;
            ItemStack[] aitemstack1 = MinecraftClient.getInstance().player.inventory.armor;
            ItemStack[] aitemstack2 = TMIGame.getPlayerServer().inventory.main;
            ItemStack[] aitemstack3 = TMIGame.getPlayerServer().inventory.armor;
            for (int i = 0; i < 4; ++i) {
                aitemstack3[i] = TMIGame.copyStack(states[p_loadState_0_][i + 4]);
                aitemstack1[i] = TMIGame.copyStack(states[p_loadState_0_][i + 4]);
            }
            for (int j = 0; j < 27; ++j) {
                aitemstack2[j + 9] = TMIGame.copyStack(states[p_loadState_0_][j + 8]);
                aitemstack[j + 9] = TMIGame.copyStack(states[p_loadState_0_][j + 8]);
            }
            for (int k = 0; k < 9; ++k) {
                aitemstack2[k] = TMIGame.copyStack(states[p_loadState_0_][k + 8 + 27]);
                aitemstack[k] = TMIGame.copyStack(states[p_loadState_0_][k + 8 + 27]);
            }
        }
    }

    public static void clearState(int p_clearState_0_) {
        TMISaveFile.statesSaved[p_clearState_0_] = false;
        TMISaveFile.write();
    }
}