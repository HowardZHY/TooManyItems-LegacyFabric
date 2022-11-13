package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.input.Keyboard;

public class TMIConfigFile {
    public static final String filename = "TooManyItems.txt";
    public static final int numSaves = 7;
    private static Map<String, String> settings = new LinkedHashMap();

    public static File file() {
        return new File(MinecraftClient.getInstance().runDirectory, filename);
    }

    public static boolean read() {
        try {
            String s;
            File file1 = TMIConfigFile.file();
            if (!file1.exists()) {
                return false;
            }
            BufferedReader bufferedreader = new BufferedReader((Reader)new FileReader(file1));
            while ((s = bufferedreader.readLine()) != null) {
                String[] astring = s.split(":", 2);
                if (astring.length <= 1) continue;
                settings.put(astring[0], astring[1]);
            }
            bufferedreader.close();
            return true;
        }
        catch (Throwable throwable) {
            TMIDebug.reportException(throwable);
            return false;
        }
    }

    public static void write() {
        try {
            File file1 = TMIConfigFile.file();
            PrintWriter printwriter = new PrintWriter((Writer)new FileWriter(file1));
            for (String s : settings.keySet()) {
                printwriter.println(s + ":" + (String)settings.get((Object)s));
            }
            printwriter.close();
        }
        catch (Throwable throwable) {
            TMIDebug.reportException(throwable);
        }
    }

    public static int getHotkey() {
        String s = (String)settings.get((Object)"key");
        boolean flag = false;
        int i = Keyboard.getKeyIndex((String)s.toUpperCase());
        if (i == 0) {
            i = 24;
        }
        return i;
    }

    public static void set(String p_set_0_, String p_set_1_) {
        settings.put(p_set_0_, p_set_1_);
        TMIConfigFile.write();
    }

    public static boolean getBooleanSetting(String p_getBooleanSetting_0_) {
        return Boolean.parseBoolean((String)((String)settings.get((Object)p_getBooleanSetting_0_)));
    }

    public static boolean isEnabled() {
        return TMIConfigFile.getBooleanSetting("enable");
    }

    public static void toggleEnabled() {
        TMIConfigFile.set("enable", Boolean.toString((!TMIConfigFile.getBooleanSetting("enable") ? 1 : 0) != 0));
    }

    public static void setEnabled(boolean p_setEnabled_0_) {
        TMIConfigFile.set("enable", Boolean.toString((boolean)p_setEnabled_0_));
    }

    public static String getSaveName(int p_getSaveName_0_) {
        String s = (String)settings.get((Object)("save-name" + (p_getSaveName_0_ + 1)));
        return s != null ? s : "" + (p_getSaveName_0_ + 1);
    }

    public static void setSaveName(int p_setSaveName_0_, String p_setSaveName_1_) {
        TMIConfigFile.set("save-name" + (p_setSaveName_0_ + 1), p_setSaveName_1_);
    }

    static {
        settings.put("enable", "true");
        settings.put("give-command", "/give {0} {1} {2} {3}");
        settings.put("give-command-num-id", "false");
        settings.put("key", "o");
        for (int i = 0; i < 7; ++i) {
            settings.put(("save-name" + (i + 1)), ("" + (i + 1)));
        }
    }
}