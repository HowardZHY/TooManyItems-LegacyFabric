package net.minecraft.src;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class TMITickEntity extends Entity {
    public static long timeout = 0L;
    public static boolean quietErrors = false;
    public static boolean infiniteStack = false;
    public static boolean preventRain = false;
    private ItemStack previousStack = null;
    private int previousSlot = -1;
    private int previousSize = -1;

    protected TMITickEntity(World p_i30_1_) {
        super(p_i30_1_);
    }

    @Override
    protected void initDataTracker() {}

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbtcompound) {}

    @Override
    protected void writeCustomDataToNbt(NbtCompound tagCompound) {}

    public static TMITickEntity create() {
        for (Entity entity : MinecraftClient.getInstance().world.entities) {
            if (!(entity instanceof TMITickEntity)) continue;
            return (TMITickEntity)entity;
        }
        TMITickEntity tmitickentity = new TMITickEntity(MinecraftClient.getInstance().world);
        MinecraftClient.getInstance().world.entities.add(tmitickentity);
        return tmitickentity;
    }

    public void doInfiniteStack() {
        if (MinecraftClient.getInstance().currentScreen != null) {
            this.previousStack = null;
        } else {
            PlayerInventory inventoryplayer = TMIGame.getPlayerServer().inventory;
            int i = inventoryplayer.selectedSlot;
            if (i >= 0 && i <= 8) {
                ItemStack itemstack = inventoryplayer.getMainHandStack();
                if (this.previousStack != null && this.previousSlot == i) {
                    if (itemstack == null || itemstack.count < this.previousSize) {
                        this.previousStack.count = this.previousSize;
                        inventoryplayer.main[i] = this.previousStack;
                        MinecraftClient.getInstance().player.inventory.main[i] = this.previousStack;
                    }
                } else {
                    this.previousStack = itemstack;
                    this.previousSlot = i;
                    this.previousSize = itemstack != null ? itemstack.count : -1;
                }
            } else {
                this.previousStack = null;
            }
        }
    }

    public void tick() {
        long i = System.currentTimeMillis();
        if (i >= timeout) {
            timeout = i + 100L;
            try {
                if (infiniteStack && MinecraftClient.getInstance().isInSingleplayer()) {
                    this.doInfiniteStack();
                }
                if (preventRain && TMIGame.isRaining()) {
                    TMIGame.setRaining(false);
                }
            }
            catch (Throwable throwable) {
                if (!quietErrors)
                {
                    TMIDebug.reportException(throwable);
                    quietErrors = true;
                }
            }
        }
    }

}