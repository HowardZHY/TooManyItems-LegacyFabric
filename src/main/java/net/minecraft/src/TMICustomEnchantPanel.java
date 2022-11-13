package net.minecraft.src;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.CommonI18n;

public class TMICustomEnchantPanel
extends TMIArea {
    private TMIItemButton itemButton = new TMIItemButton(new TMIStackBuilder("diamond_pickaxe").stack(), true);
    private TMITextField nameField = new TMITextField();
    private List<TMIEnchantField> enchantFields = new ArrayList();
    private int page = 0;
    private int fieldsMargin = 44;

    public TMICustomEnchantPanel() {
        this.addChild(this.itemButton);
        this.addChild(this.nameField);
        this.nameField.placeholder = "Name...";
        this.nameField.addEventListener(this);
        this.itemButton.addEventListener(this);
        this.updateFromItem();
    }

    private void updateFromItem()
    {
        this.blurFocused();
        this.enchantFields.clear();
        NbtCompound nbtcompound = (new TMIStackBuilder(this.itemButton.stack)).tag();
        HashMap hashmap = new HashMap();

        if (nbtcompound.contains("ench"))
        {
            NbtList nbtlist = nbtcompound.getList("ench", 10);

            for (int i = 0; i < nbtlist.size(); ++i)
            {
                try
                {
                    NbtCompound nbtcompound1 = (NbtCompound)nbtlist.get(i);
                    hashmap.put((int) nbtcompound1.getShort("id"), (int) nbtcompound1.getShort("lvl"));
                }
                catch (Throwable t)
                {
                    TMIDebug.reportException(t);
                }
            }
        }
        for (Enchantment enchantment : this.enchantmentsForItem(this.itemButton.stack))
        {
            TMIEnchantField tmienchantfield = new TMIEnchantField(enchantment, this.itemButton.stack);
            tmienchantfield.addEventListener(this);
            this.enchantFields.add(tmienchantfield);

            if (hashmap.containsKey(enchantment.id))
            {
                tmienchantfield.setValue("" + hashmap.get(enchantment.id));
            }
        }
    }

    private void recreateItem() {
        TMIStackBuilder tMIStackBuilder = new TMIStackBuilder(this.itemButton.stack).clearEnch().clearName();
        for (TMIEnchantField tMIEnchantField : this.enchantFields) {
            int n = tMIEnchantField.intValue();
            if (n <= 0) continue;
            tMIStackBuilder.ench(tMIEnchantField.ench, n);
        }
        if (!this.nameField.value().equals((Object)"")) {
            tMIStackBuilder.name(this.nameField.value());
        }
        this.itemButton.stack = tMIStackBuilder.stack();
        this.layoutComponent();
    }

    private void fixPage() {
        int n;
        this.removeChildrenOfType(TMIEnchantField.class);
        int n2 = (this.height - this.fieldsMargin - 4) / 16;
        int n3 = (int)Math.ceil((double)((float)this.enchantFields.size() / (float)n2));
        while (this.page < 0) {
            this.page += n3;
        }
        this.page %= n3;
        for (int i = n = n2 * this.page; i < n + n2 && i < this.enchantFields.size(); ++i) {
            this.addChild((TMIArea)this.enchantFields.get(i));
        }
    }

    @Override
    public void layoutComponent() {
        this.fixPage();
        this.itemButton.setPosition(this.x + this.width / 2 - 8, this.y + 4);
        this.nameField.setSize(this.width - 4, 12);
        this.nameField.setPosition(this.x + 2, this.y + 4 + 16 + 4);
        int n = this.y + this.fieldsMargin;
        for (TMIArea tMIArea : this.children) {
            if (!(tMIArea instanceof TMIEnchantField)) continue;
            tMIArea.setSize(this.width - 4, 14);
            tMIArea.setPosition(this.x + 2, n);
            n += 16;
        }
    }

    private List<Enchantment> enchantmentsForItem(final ItemStack p_enchantmentsForItem_1_)
    {
        List<Enchantment> list = Arrays.asList(Enchantment.ALL_ENCHANTMENTS);
        Collections.sort(list, new Comparator<Enchantment>()
        {
            public int compare(Enchantment p_compare_1_, Enchantment p_compare_2_)
            {
                boolean flag = p_compare_1_.target.isCompatible(p_enchantmentsForItem_1_.getItem());
                boolean flag1 = p_compare_2_.target.isCompatible(p_enchantmentsForItem_1_.getItem());
                return flag == flag1 ? CommonI18n.translate(p_compare_1_.getTranslationKey()).compareTo(CommonI18n.translate(p_compare_2_.getTranslationKey())) : (flag1 ? 1 : -1);
            }
        });
        return list;
    }

    @Override
    public void mouseEvent(TMIEvent tMIEvent) {
        if (tMIEvent.type == 2) {
            this.page -= tMIEvent.wheel;
            this.layoutComponent();
            tMIEvent.cancel();
        }
    }

    @Override
    public void controlEvent(TMIEvent tMIEvent) {
        if (tMIEvent.type == 4 && tMIEvent.target == this.itemButton) {
            this.updateFromItem();
            this.layoutComponent();
        } else if (tMIEvent.type == 3) {
            this.recreateItem();
        }
    }

    @Override
    public void keyboardEvent(TMIEvent tMIEvent) {
        if (tMIEvent.keyCode == 15) {
            if (this.getFocused() == null) {
                this.nameField.focus();
            } else {
                int n = this.children.indexOf((Object)this.getFocused());
                if (n == -1) {
                    this.nameField.focus();
                } else if (n + 1 == this.children.size()) {
                    ++this.page;
                    this.layoutComponent();
                    for (TMIArea tMIArea : this.children) {
                        if (!(tMIArea instanceof TMIEnchantField)) continue;
                        tMIArea.focus();
                        break;
                    }
                } else {
                    ((TMIArea)this.children.get(n + 1)).focus();
                }
            }
            tMIEvent.cancel();
        }
    }
}