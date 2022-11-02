package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.itemgroup.ItemGroup;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRuleManager;
import net.minecraft.world.level.LevelInfo;

public class TMIGame {
    public static final int MODE_SURVIVAL = 0;
    public static final int MODE_CREATIVE = 1;
    public static final int MODE_ADVENTURE = 2;
    public static final int MODE_SPECTATOR = 3;
    public static final int TIME_SUNRISE = 0;
    public static final int TIME_NOON = 6000;
    public static final int TIME_MOONRISE = 13000;
    public static final int TIME_MIDNIGHT = 18000;

    public static PlayerEntity getPlayerClient() {
        return MinecraftClient.getInstance().player;
    }

    public static PlayerEntity getPlayerServer() {
        return MinecraftClient.getInstance().getServer().getPlayerManager().getPlayer(MinecraftClient.getInstance().player.getTranslationKey());
    }

    public static void chat(String p_chat_0_) {
        MinecraftClient.getInstance().player.sendChatMessage(p_chat_0_);
    }

    public static boolean isPlayerAlive() {
        return !MinecraftClient.getInstance().player.removed;
    }

    public static void fillPlayerHealth() {
        if (TMIGame.isPlayerAlive()) {
            TMIGame.getPlayerServer().setHealth(20.0f);
        }
    }

    public static void fillPlayerHunger() {
        if (TMIGame.isPlayerAlive()) {
            TMIGame.getPlayerServer().getHungerManager().setFoodLevel(20);
            TMIGame.getPlayerServer().getHungerManager().setSaturationLevelClient(5.0f);
        }
    }

    public static void removePlayerEffects() {
        TMIGame.getPlayerServer().clearStatusEffects();
    }

    public static ItemStack[] getInventoryForPlayer(PlayerEntity p_getInventoryForPlayer_0_) {
        return p_getInventoryForPlayer_0_.inventory.main;
    }

    public static ItemStack[] getArmorForPlayer(PlayerEntity p_getArmorForPlayer_0_) {
        return p_getArmorForPlayer_0_.inventory.armor;
    }

    public static ItemStack getHeldItem() {
        return MinecraftClient.getInstance().player.inventory.getCursorStack();
    }

    public static void setHeldItem(ItemStack p_setHeldItem_0_) {
        TMIGame.getPlayerServer().inventory.setCursorStack(p_setHeldItem_0_);
        TMIGame.getPlayerClient().inventory.setCursorStack(p_setHeldItem_0_);
    }

    public static void giveStack(ItemStack p_giveStack_0_) {
        if (p_giveStack_0_ == null) {
            System.out.println("[TMI] Tried to give null item");
        } else {
            if (TMIGame.stackName(p_giveStack_0_).contains((CharSequence)"Random Firework")) {
                p_giveStack_0_ = TMISpawnerBuilder.randomFireworkSpawner(1);
            }
            if (TMIGame.isMultiplayer()) {
                String s = "";
                if (p_giveStack_0_.getNbt() != null) {
                    s = p_giveStack_0_.getNbt().toString();
                }
                String s1 = String.format("/give %s %s %d %d %s", new Object[]{MinecraftClient.getInstance().player.getTranslationKey(), Item.REGISTRY.getIdentifier((p_giveStack_0_.getItem())), p_giveStack_0_.count, p_giveStack_0_.getDamage(), s});
                s1 = s1.replaceAll("\u00a7.", "");
                System.out.println(s1);
                if (s1.length() > 100) {
                    MinecraftClient.getInstance().inGameHud.getChatHud().addMessage((Text)new LiteralText("[TMI] give command exceeds length limit - blame Mojang"));
                    return;
                }
                TMIGame.chat(s1);
            } else {
                TMIGame.getPlayerServer().inventory.insertStack(p_giveStack_0_.copy());
                MinecraftClient.getInstance().player.inventory.insertStack(p_giveStack_0_.copy());
            }
        }
    }

    public static ItemStack copyStack(ItemStack p_copyStack_0_) {
        return p_copyStack_0_ == null ? null : p_copyStack_0_.copy();
    }

    public static void deleteInventory() {
        if (TMIGame.isMultiplayer()) {
            TMIGame.chat("/clear");
        } else {
            ItemStack[] aitemstack = TMIGame.getInventoryForPlayer(TMIGame.getPlayerServer());
            ItemStack[] aitemstack1 = TMIGame.getInventoryForPlayer(TMIGame.getPlayerClient());
            for (int i = 0; i < aitemstack.length; ++i) {
                aitemstack[i] = null;
                aitemstack1[i] = null;
            }
            ItemStack[] aitemstack3 = TMIGame.getArmorForPlayer(TMIGame.getPlayerServer());
            ItemStack[] aitemstack2 = TMIGame.getArmorForPlayer(TMIGame.getPlayerClient());
            for (int j = 0; j < aitemstack3.length; ++j) {
                aitemstack3[j] = null;
                aitemstack2[j] = null;
            }
        }
    }

    public static boolean isMultiplayer() {
        return !MinecraftClient.getInstance().isInSingleplayer();
    }

    public static boolean isModloader() {
        try {
            Class.forName((String)"ModLoader");
            return true;
        }
        catch (ClassNotFoundException var1) {
            return false;
        }
    }

    public static boolean isForge() {
        return false;
    }

    public static List<ItemStack> gameItems() {
        ArrayList arraylist = new ArrayList();
        for (Item item : Item.REGISTRY) {
            item.appendItemStacks(item, (ItemGroup)null, arraylist);
        }
        return arraylist;
    }

    public static List<ItemStack> allItems() {
        List<ItemStack> list = TMIGame.gameItems();
        list.addAll(TMIExtraItems.items());
        return list;
    }

    public static int getGameMode() {
        return MinecraftClient.getInstance().interactionManager.method_9667().getGameModeId();
    }

    public static void setGameMode(int p_setGameMode_0_) {
        if (TMIGame.isMultiplayer()) {
            TMIGame.chat("/gamemode " + p_setGameMode_0_);
        } else {
            TMIGame.getPlayerServer().method_3170(LevelInfo.method_3754((int)p_setGameMode_0_));
        }
    }

    public static boolean isRaining() {
        return MinecraftClient.getInstance().world.getLevelProperties().isRaining();
    }

    public static void setRaining(boolean p_setRaining_0_) {
        if (TMIGame.isMultiplayer()) {
            TMIGame.chat("/weather " + (p_setRaining_0_ ? "rain" : "clear"));
        } else {
            MinecraftClient.getInstance().getServer().worlds[0].getLevelProperties().setRaining(p_setRaining_0_);
            MinecraftClient.getInstance().getServer().worlds[0].getLevelProperties().setRainTime(p_setRaining_0_ ? 18000 : 180000);
        }
    }

    public static void toggleRaining() {
        TMIGame.setRaining(!TMIGame.isRaining());
    }

    public static long getTime() {
        return MinecraftClient.getInstance().world.getLevelProperties().getTime();
    }

    public static void setTime(long p_setTime_0_) {
        if (TMIGame.isMultiplayer()) {
            TMIGame.chat("/time set " + p_setTime_0_);
        } else {
            MinecraftClient.getInstance().getServer().worlds[0].setTimeOfDay(p_setTime_0_);
        }
    }

    public static int getDifficulty() {
        return MinecraftClient.getInstance().world.getLevelProperties().getDifficulty().getId();
    }

    public static void setDifficulty(int p_setDifficulty_0_) {
        if (TMIGame.isMultiplayer()) {
            TMIGame.chat("/difficulty " + p_setDifficulty_0_);
        } else {
            MinecraftClient.getInstance().getServer().setDifficulty(Difficulty.byOrdinal((int)p_setDifficulty_0_));
        }
    }

    public static void incrementDifficulty() {
        TMIGame.setDifficulty(TMIGame.getDifficulty() + 1);
    }

    public static String getDifficultyName() {
        return I18n.translate(MinecraftClient.getInstance().world.getLevelProperties().getDifficulty().getName(), (Object[])new Object[0]);
    }

    public static void setCheats(boolean p_setCheats_0_) {
        MinecraftClient.getInstance().getServer().worlds[0].getLevelProperties().setCheats(p_setCheats_0_);
    }

    public static boolean getCheats() {
        return MinecraftClient.getInstance().getServer().worlds[0].getLevelProperties().areCheatsEnabled();
    }

    public static boolean getKeepInventory() {
        return TMIGame.isMultiplayer() ? false : MinecraftClient.getInstance().getServer().worlds[0].getLevelProperties().getGamerules().getBoolean("keepInventory");
    }

    public static void setKeepInventory(boolean p_setKeepInventory_0_) {
        if (TMIGame.isMultiplayer()) {
            TMIGame.chat("/gamerule keepInventory " + (p_setKeepInventory_0_ ? "true" : "false"));
        } else {
            MinecraftClient.getInstance().getServer().worlds[0].getLevelProperties().getGamerules().addGameRule("keepInventory", p_setKeepInventory_0_ ? "true" : "false", GameRuleManager.VariableType.BOOLEAN);
        }
    }

    public static String stackName(ItemStack p_stackName_0_) {
        List<String> list = p_stackName_0_.getTooltip((PlayerEntity)MinecraftClient.getInstance().player, false);
        return list != null && list.size() != 0 ? (String)list.get(0) : "Unnamed";
    }
}