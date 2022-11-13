package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.LevelInfo.GameMode;
import net.minecraft.world.GameRuleManager.VariableType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.world.Difficulty;

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
        return MinecraftServer.getServer().getPlayerManager().getPlayer(MinecraftClient.getInstance().player.getTranslationKey());
    }

    public static void chat(String string) {
        MinecraftClient.getInstance().player.sendChatMessage(string);
    }

    public static boolean isPlayerAlive() {
        return !MinecraftClient.getInstance().player.removed;
    }

    public static void fillPlayerHealth() {
        if (TMIGame.isPlayerAlive()) {
            TMIGame.getPlayerServer().setHealth(100.0f);
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

    public static ItemStack[] getInventoryForPlayer(PlayerEntity playerEntity) {
        return playerEntity.inventory.main;
    }

    public static ItemStack[] getArmorForPlayer(PlayerEntity playerEntity) {
        return playerEntity.inventory.armor;
    }

    public static ItemStack getHeldItem() {
        return MinecraftClient.getInstance().player.inventory.getCursorStack();
    }

    public static void setHeldItem(ItemStack itemStack) {
        TMIGame.getPlayerServer().inventory.setCursorStack(itemStack);
        TMIGame.getPlayerClient().inventory.setCursorStack(itemStack);
    }

    public static void giveStack(ItemStack itemStack) {
        if (itemStack == null) {
            System.out.println("[TMI] Tried to give null item");
            return;
        }
        if (TMIGame.stackName(itemStack).contains((CharSequence)"Random Firework")) {
            itemStack = TMISpawnerBuilder.randomFireworkSpawner(1);
        }
        if (TMIGame.isMultiplayer()) {
            String string = "";
            if (itemStack.getNbt() != null) {
                string = itemStack.getNbt().toString();
            }
            String string2 = String.format((String)"/give %s %s %d %d %s", (Object[])new Object[]{MinecraftClient.getInstance().player.getTranslationKey(), Item.REGISTRY.getIdentifier(itemStack.getItem()), itemStack.count, itemStack.getDamage(), string});
            string2 = string2.replaceAll("\u00a7.", "");
            System.out.println(string2);
            if (string2.length() > 100) {
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage((Text)new LiteralText("[TMI] give command exceeds length limit - blame Mojang"));
                return;
            }
            TMIGame.chat(string2);
        } else {
            TMIGame.getPlayerServer().inventory.insertStack(itemStack.copy());
            MinecraftClient.getInstance().player.inventory.insertStack(itemStack.copy());
        }
    }

    public static ItemStack copyStack(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        return itemStack.copy();
    }

    public static void deleteInventory() {
        if (TMIGame.isMultiplayer()) {
            TMIGame.chat("/clear");
        } else {
            ItemStack[] itemStackArray = TMIGame.getInventoryForPlayer(TMIGame.getPlayerServer());
            ItemStack[] itemStackArray2 = TMIGame.getInventoryForPlayer(TMIGame.getPlayerClient());
            for (int i = 0; i < itemStackArray.length; ++i) {
                itemStackArray[i] = null;
                itemStackArray2[i] = null;
            }
            ItemStack[] itemStackArray3 = TMIGame.getArmorForPlayer(TMIGame.getPlayerServer());
            ItemStack[] itemStackArray4 = TMIGame.getArmorForPlayer(TMIGame.getPlayerClient());
            for (int i = 0; i < itemStackArray3.length; ++i) {
                itemStackArray3[i] = null;
                itemStackArray4[i] = null;
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
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    public static boolean isForge() {
        try {
            Class.forName((String)"net.minecraftforge.common.MinecraftForge");
            return true;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    public static List<ItemStack> gameItems() {
        ArrayList arrayList = new ArrayList();
        for (Item item : Item.REGISTRY) {
            item.appendItemStacks(item, null, (List)arrayList);
        }
        return arrayList;
    }

    public static List<ItemStack> allItems() {
        List<ItemStack> list = TMIGame.gameItems();
        list.addAll(TMIExtraItems.items());
        return list;
    }

    public static int getGameMode() {
        return MinecraftClient.getInstance().interactionManager.getCurrentGameMode().getId();
    }

    public static void setGameMode(int n) {
        if (TMIGame.isMultiplayer()) {
            TMIGame.chat("/gamemode " + n);
        } else {
            TMIGame.getPlayerServer().setGameMode(GameMode.byId((int)n));
        }
    }

    public static boolean isRaining() {
        return MinecraftClient.getInstance().world.getLevelProperties().isRaining();
    }

    public static void setRaining(boolean bl) {
        if (TMIGame.isMultiplayer()) {
            TMIGame.chat("/weather " + (bl ? "rain" : "clear"));
        } else {
            MinecraftServer.getServer().worlds[0].getLevelProperties().setRaining(bl);
            MinecraftServer.getServer().worlds[0].getLevelProperties().setRainTime(bl ? 18000 : 180000);
        }
    }

    public static void toggleRaining() {
        TMIGame.setRaining(!TMIGame.isRaining());
    }

    public static long getTime() {
        return MinecraftClient.getInstance().world.getLevelProperties().getTime();
    }

    public static void setTime(long l) {
        if (TMIGame.isMultiplayer()) {
            TMIGame.chat("/time set " + l);
        } else {
            MinecraftServer.getServer().worlds[0].setTimeOfDay(l);
        }
    }

    public static int getDifficulty() {
        return MinecraftClient.getInstance().world.getLevelProperties().getDifficulty().getId();
    }

    public static void setDifficulty(int n) {
        if (TMIGame.isMultiplayer()) {
            TMIGame.chat("/difficulty " + n);
        } else {
            MinecraftServer.getServer().setDifficulty(Difficulty.byOrdinal((int)n));
        }
    }

    public static void incrementDifficulty() {
        TMIGame.setDifficulty(TMIGame.getDifficulty() + 1);
    }

    public static String getDifficultyName() {
        return I18n.translate((String)MinecraftClient.getInstance().world.getLevelProperties().getDifficulty().getName(), (Object[])new Object[0]);
    }

    public static void setCheats(boolean bl) {
        MinecraftServer.getServer().worlds[0].getLevelProperties().setCheats(bl);
    }

    public static boolean getCheats() {
        return MinecraftServer.getServer().worlds[0].getLevelProperties().areCheatsEnabled();
    }

    public static boolean getKeepInventory() {
        if (TMIGame.isMultiplayer()) {
            return false;
        }
        return MinecraftServer.getServer().worlds[0].getLevelProperties().getGamerules().getBoolean("keepInventory");
    }

    public static void setKeepInventory(boolean bl) {
        if (TMIGame.isMultiplayer()) {
            TMIGame.chat("/gamerule keepInventory " + (bl ? "true" : "false"));
        } else {
            MinecraftServer.getServer().worlds[0].getLevelProperties().getGamerules().addGameRule("keepInventory", bl ? "true" : "false", VariableType.BOOLEAN);
        }
    }

    public static String stackName(ItemStack itemStack) {
        List list = itemStack.getTooltip((PlayerEntity)MinecraftClient.getInstance().player, false);
        if (list == null || list.size() == 0) {
            return "Unnamed";
        }
        return (String)list.get(0);
    }
}