package net.minecraft.src;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class TMISearchResult<T extends Comparable<T>>
implements Comparable {
    public ItemStack stack;
    protected String normalizedName;
    protected int internalDistance;
    protected int firstPosition;
    protected int lastPosition;
    protected static Pattern diacritics = Pattern.compile((String)"\\p{InCombiningDiacriticalMarks}+");
    protected static Pattern styles = Pattern.compile((String)"\u00a7.");

    public static TMISearchResult scan(ItemStack p_scan_0_, char p_scan_1_) {
        if (p_scan_0_ == null) {
            return null;
        }
        List<String> list = p_scan_0_.getTooltip((PlayerEntity)MinecraftClient.getInstance().player, true);
        if (list != null && list.size() != 0) {
            String s = TMISearchResult.clean((String)list.get(0));
            int i = s.indexOf((int)(p_scan_1_ = Character.toLowerCase((char)p_scan_1_)));
            if (i == -1) {
                return null;
            }
            TMISearchResult tmisearchresult = new TMISearchResult();
            tmisearchresult.stack = p_scan_0_;
            tmisearchresult.firstPosition = tmisearchresult.lastPosition = i;
            tmisearchresult.internalDistance = 0;
            tmisearchresult.normalizedName = s;
            return tmisearchresult;
        }
        return null;
    }

    public TMISearchResult scan(char p_scan_1_) {
        int i = this.normalizedName.indexOf((int)(p_scan_1_ = Character.toLowerCase((char)p_scan_1_)), this.lastPosition + 1);
        if (i == -1) {
            return null;
        }
        TMISearchResult<T> tmisearchresult = new TMISearchResult<T>();
        tmisearchresult.stack = this.stack;
        tmisearchresult.normalizedName = this.normalizedName;
        tmisearchresult.firstPosition = this.firstPosition;
        tmisearchresult.lastPosition = i;
        tmisearchresult.internalDistance = this.internalDistance + i - this.lastPosition;
        return tmisearchresult;
    }

    public int compareTo(TMISearchResult p_compareTo_1_) {
        return this.internalDistance > p_compareTo_1_.internalDistance ? 1 : (this.internalDistance < p_compareTo_1_.internalDistance ? -1 : (this.firstPosition > p_compareTo_1_.firstPosition ? 1 : (this.firstPosition < p_compareTo_1_.firstPosition ? -1 : 0)));
    }

    public static String clean(String p_clean_0_) {
        p_clean_0_ = TMISearchResult.deaccent(p_clean_0_);
        p_clean_0_ = p_clean_0_.toLowerCase();
        p_clean_0_ = styles.matcher((CharSequence)p_clean_0_).replaceAll("");
        return p_clean_0_;
    }

    public static String deaccent(String p_deaccent_0_) {
        try {
            Class.forName((String)"java.text.Normalizer");
        }
        catch (ClassNotFoundException var21) {
            return p_deaccent_0_;
        }
        String s = Normalizer.normalize((CharSequence)p_deaccent_0_, (Normalizer.Form)Normalizer.Form.NFD);
        return diacritics.matcher((CharSequence)s).replaceAll("");
    }

    public int compareTo(Object p_compareWith_1_) {
        return this.compareTo((TMISearchResult)p_compareWith_1_);
    }
}