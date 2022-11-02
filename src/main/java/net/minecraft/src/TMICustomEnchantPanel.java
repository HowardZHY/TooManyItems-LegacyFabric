package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.CommonI18n;
import net.minecraft.util.registry.SimpleRegistry;

public class TMICustomEnchantPanel
extends TMIArea {
    private TMIItemButton itemButton = new TMIItemButton(new TMIStackBuilder("diamond_pickaxe").stack(), true);
    private TMITextField nameField = new TMITextField();
    private List<TMIEnchantField> enchantFields = new ArrayList();
    private int page = 0;
    private int fieldsMargin = 44;

    public Enchantment enchantment;


    public TMICustomEnchantPanel() {
        this.addChild(this.itemButton);
        this.addChild(this.nameField);
        this.nameField.placeholder = "Name...";
        this.nameField.addEventListener(this);
        this.itemButton.addEventListener(this);
        this.updateFromItem();
    }

    private void updateFromItem() {
        this.blurFocused();
        this.enchantFields.clear();
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage((Text)new LiteralText("Enchant and Potion Panel aren't working for this version"));
        NbtCompound nbtcompound = new TMIStackBuilder(this.itemButton.stack).tag();
        HashMap hashmap = new HashMap();
        if (nbtcompound.contains("ench")) {
            NbtList nbtlist = nbtcompound.getList("ench", 10);
            for (int i = 0; i < nbtlist.size(); ++i) {
                try {
                    NbtCompound nbtcompound1 = (NbtCompound)nbtlist.get(i);
                    hashmap.put(Integer.valueOf(nbtcompound1.getInt("id")), Integer.valueOf(nbtcompound1.getShort("lvl")));
                }
                catch (Throwable var6) {
                    ;
                }
            }
        }
        //for (Enchantment enchantment : this.enchantmentsForItem(this.itemButton.stack)) {
            //TMIEnchantField tmienchantfield = new TMIEnchantField(enchantment, this.itemButton.stack);
            //tmienchantfield.addEventListener(this);
            //this.enchantFields.add(tmienchantfield);

            //if (hashmap.containsKey(Integer.valueOf(String.valueOf(enchantment)))) {
            //    tmienchantfield.setValue("" + hashmap.get(Integer.valueOf(String.valueOf(enchantment))));
            //}
        //}
    }

    private void recreateItem() {
        TMIStackBuilder tmistackbuilder = new TMIStackBuilder(this.itemButton.stack).clearEnch().clearName();
        for (TMIEnchantField tmienchantfield : this.enchantFields) {
            int i = tmienchantfield.intValue();
            if (i <= 0) continue;
            tmistackbuilder.ench(tmienchantfield.enchantment, i);
        }
        if (!this.nameField.value().equals("")) {
            tmistackbuilder.name(this.nameField.value());
        }
        this.itemButton.stack = tmistackbuilder.stack();
        this.layoutComponent();
    }

    private void fixPage() {
        int k;
        this.removeChildrenOfType(TMIEnchantField.class);
        int i = (this.height - this.fieldsMargin - 4) / 16;
        int j = (int)Math.ceil((double)(this.enchantFields.size() / i));
        while (this.page < 0) {
            this.page += j;
        }
        this.page %= j;
        for (int m = k = i * this.page; m < k + i && m < this.enchantFields.size(); ++m) {
            this.addChild((TMIArea)this.enchantFields.get(m));
        }
    }

    @Override
    public void layoutComponent() {
        this.fixPage();
        this.itemButton.setPosition(this.x + this.width / 2 - 8, this.y + 4);
        this.nameField.setSize(this.width - 4, 12);
        this.nameField.setPosition(this.x + 2, this.y + 4 + 16 + 4);
        int i = this.y + this.fieldsMargin;
        for (TMIArea tmiarea : this.children) {
            if (!(tmiarea instanceof TMIEnchantField)) continue;
            tmiarea.setSize(this.width - 4, 14);
            tmiarea.setPosition(this.x + 2, i);
            i += 16;
        }
    }

    private List<Enchantment> enchantmentsForItem(final ItemStack p_enchantmentsForItem_1_) {
        List list = Arrays.asList((Object[])new SimpleRegistry[]{Enchantment.REGISTRY});
        Collections.sort((List)list, (Comparator)new Comparator(){

            public int compare(Enchantment p_compare_1_, Enchantment p_compare_2_) {
                boolean flag1;
                boolean flag = p_compare_1_.target.isCompatible(p_enchantmentsForItem_1_.getItem());
                return flag == (flag1 = p_compare_2_.target.isCompatible(p_enchantmentsForItem_1_.getItem())) ? CommonI18n.translate((String)p_compare_1_.getTranslationKey()).compareTo(CommonI18n.translate((String)p_compare_2_.getTranslationKey())) : (flag1 ? 1 : -1);
            }

            public int compare(Object p_compare_1_, Object p_compare_2_) {
                return this.compare((Enchantment)p_compare_1_, (Enchantment)p_compare_2_);
            }
        });
        return list;
    }

    @Override
    public void mouseEvent(TMIEvent p_mouseEvent_1_) {
        if (p_mouseEvent_1_.type == 2) {
            this.page -= p_mouseEvent_1_.wheel;
            this.layoutComponent();
            p_mouseEvent_1_.cancel();
        }
    }

    @Override
    public void controlEvent(TMIEvent p_controlEvent_1_) {
        if (p_controlEvent_1_.type == 4 && p_controlEvent_1_.target == this.itemButton) {
            this.updateFromItem();
            this.layoutComponent();
        } else if (p_controlEvent_1_.type == 3) {
            this.recreateItem();
        }
    }

    @Override
    public void keyboardEvent(TMIEvent p_keyboardEvent_1_) {
        if (p_keyboardEvent_1_.keyCode == 15) {
            if (this.getFocused() == null) {
                this.nameField.focus();
            } else {
                int i = this.children.indexOf((Object)this.getFocused());
                if (i == -1) {
                    this.nameField.focus();
                } else if (i + 1 == this.children.size()) {
                    ++this.page;
                    this.layoutComponent();
                    for (TMIArea tmiarea : this.children) {
                        if (!(tmiarea instanceof TMIEnchantField)) continue;
                        tmiarea.focus();
                        break;
                    }
                } else {
                    ((TMIArea)this.children.get(i + 1)).focus();
                }
            }
            p_keyboardEvent_1_.cancel();
        }
    }
}