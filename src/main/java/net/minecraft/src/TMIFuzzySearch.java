package net.minecraft.src;

import java.util.*;

import net.minecraft.item.ItemStack;

public class TMIFuzzySearch {
    private List<ItemStack> baseItems;
    private String lastQuery;
    private Deque<List<TMISearchResult>> resultStack = new LinkedList();

    public TMIFuzzySearch(List<ItemStack> p_i12_1_) {
        this.baseItems = p_i12_1_;
    }

    public List<ItemStack> query(String paramString) {
        if (paramString == null || paramString.equals(""))
            return this.baseItems;
        if (this.lastQuery != null && this.lastQuery.startsWith(paramString)) {
            for (byte b = 0; b < this.lastQuery.length() - paramString.length(); b++)
                this.resultStack.pollLast();
            return getResults();
        }
        int i = 0;
        if (this.lastQuery == null || !paramString.startsWith(this.lastQuery)) {
            while (this.resultStack.pollLast() != null) ;
        } else {
            i = this.lastQuery.length();
        }
        for (int j = i; j < paramString.length(); j++)
            pushQuery(paramString.charAt(j));
        return getResults();
    }

    public List<ItemStack> getResults() {
        {
            ArrayList var1 = new ArrayList();
            List var2 = (List)this.resultStack.peekLast();

            if (var2 == null)
            {
                return null;
            }
            else
            {
                Iterator var3 = var2.iterator();

                while (((Iterator<?>) var3).hasNext())
                {
                    TMISearchResult var4 = (TMISearchResult)var3.next();
                    var1.add(var4.stack);
                }

                return var1;
            }
        }
    }

    private void pushQuery(char p_pushQuery_1_)
    {
        ArrayList var2 = new ArrayList();
        List var3 = (List)this.resultStack.peekLast();
        Iterator var4;
        TMISearchResult var6;

        if (var3 == null)
        {
            var4 = this.baseItems.iterator();

            while (var4.hasNext())
            {
                ItemStack var5 = (ItemStack)var4.next();
                var6 = TMISearchResult.scan(var5, p_pushQuery_1_);

                if (var6 != null)
                {
                    var2.add(var6);
                }
            }
        }
        else
        {
            var4 = var3.iterator();

            while (var4.hasNext())
            {
                TMISearchResult var7 = (TMISearchResult)var4.next();
                var6 = var7.scan(p_pushQuery_1_);

                if (var6 != null)
                {
                    var2.add(var6);
                }
            }
        }

        Collections.sort(var2);
        this.resultStack.addLast(var2);
    }
}