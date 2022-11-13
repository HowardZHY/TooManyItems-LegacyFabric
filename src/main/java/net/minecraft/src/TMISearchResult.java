package net.minecraft.src;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class TMISearchResult
implements Comparable<TMISearchResult> {
    public ItemStack stack;
    protected String normalizedName;
    protected int internalDistance;
    protected int firstPosition;
    protected int lastPosition;
    protected static Pattern diacritics = Pattern.compile((String)"\\p{InCombiningDiacriticalMarks}+");
    protected static Pattern styles = Pattern.compile((String)"\u00a7.");

    protected TMISearchResult() {
    }

    public static TMISearchResult scan(ItemStack itemStack, char c) {
        if (itemStack == null) {
            return null;
        }
        List list = itemStack.getTooltip((PlayerEntity)MinecraftClient.getInstance().player, true);
        if (list == null || list.size() == 0) {
            return null;
        }
        String string = TMISearchResult.clean((String)list.get(0));
        int n = string.indexOf((int)(c = Character.toLowerCase((char)c)));
        if (n == -1) {
            return null;
        }
        TMISearchResult tMISearchResult = new TMISearchResult();
        tMISearchResult.stack = itemStack;
        tMISearchResult.firstPosition = tMISearchResult.lastPosition = n;
        tMISearchResult.internalDistance = 0;
        tMISearchResult.normalizedName = string;
        return tMISearchResult;
    }

    public TMISearchResult scan(char c) {
        int n = this.normalizedName.indexOf((int)(c = Character.toLowerCase((char)c)), this.lastPosition + 1);
        if (n == -1) {
            return null;
        }
        TMISearchResult tMISearchResult = new TMISearchResult();
        tMISearchResult.stack = this.stack;
        tMISearchResult.normalizedName = this.normalizedName;
        tMISearchResult.firstPosition = this.firstPosition;
        tMISearchResult.lastPosition = n;
        tMISearchResult.internalDistance = this.internalDistance + n - this.lastPosition;
        return tMISearchResult;
    }

    public int compareTo(TMISearchResult tMISearchResult) {
        return this.internalDistance > tMISearchResult.internalDistance ? 1 : (this.internalDistance < tMISearchResult.internalDistance ? -1 : (this.firstPosition > tMISearchResult.firstPosition ? 1 : (this.firstPosition < tMISearchResult.firstPosition ? -1 : 0)));
    }

    public static String clean(String string) {
        string = TMISearchResult.deaccent(string);
        string = string.toLowerCase();
        string = styles.matcher((CharSequence)string).replaceAll("");
        return string;
    }

    public static String deaccent(String string) {
        try {
            Class.forName((String)"java.text.Normalizer");
        }
        catch (ClassNotFoundException classNotFoundException) {
            return string;
        }
        String string2 = Normalizer.normalize((CharSequence)string, (Normalizer.Form)Normalizer.Form.NFD);
        return diacritics.matcher((CharSequence)string2).replaceAll("");
    }
}