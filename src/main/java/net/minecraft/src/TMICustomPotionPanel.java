package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class TMICustomPotionPanel
extends TMIArea {
    public static final StatusEffect[] potionTypes = new StatusEffect[30];
    private TMIButton prevColor = new TMIButton(null, "<");
    private TMIButton nextColor = new TMIButton(null, ">");
    private TMIItemButton potionButton = new TMIItemButton(null);
    private TMIItemButton splashButton = new TMIItemButton(null);
    private TMITextField nameField = new TMITextField();
    private TMINumField durField = new TMINumField("Seconds:", 40);
    private List<TMIEffectField> effectList = new ArrayList();
    private int page = 0;
    private int fieldsMargin = 56;
    private static int color = 0;
    public static final int DRINKABLE = 8192;
    public static final int SPLASH = 16384;

    public TMICustomPotionPanel() {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage((Text)new LiteralText("Enchant and Potion Panel aren't working for this version"));
        this.addChild(this.prevColor);
        this.addChild(this.nextColor);
        this.addChild(this.potionButton);
        this.addChild(this.splashButton);
        this.addChild(this.nameField);
        this.addChild(this.durField);
        this.nameField.addEventListener(this);
        this.durField.addEventListener(this);
        this.nameField.placeholder = "Name...";
        this.durField.setValue("60");
        for (int i = 0; i < potionTypes.length; ++i) {
            if (this.potionTypes[i] == null) continue;
            TMIEffectField tmieffectfield = new TMIEffectField(this.potionTypes[i]);
            tmieffectfield.addEventListener(this);
            this.effectList.add(tmieffectfield);
        }
        this.recreateItem();
    }

    private void recreateItem() {
        TMIStackBuilder tmistackbuilder = new TMIStackBuilder("potion");
        if (!this.nameField.value().equals((Object)"")) {
            tmistackbuilder.name(this.nameField.value());
        }
        for (TMIEffectField tmieffectfield : this.effectList) {
            int i = tmieffectfield.intValue();
            int j = this.durField.intValue();
            if (i <= 0 || j <= 0) continue;
            tmistackbuilder.effect(tmieffectfield.effect, i - 1, j * 20);
        }
        this.potionButton.stack = tmistackbuilder.meta(Items.POTION.getMeta(color)).stack();
        this.splashButton.stack = tmistackbuilder.meta(Items.SPLASH_POTION.getMeta(color)).stack();
    }

    @Override
    public void layoutComponent() {
        this.fixPage();
        int i = this.x + (this.width - 32 - this.prevColor.width * 2 - 6) / 2;
        this.prevColor.setPosition(i, this.y + 6);
        this.potionButton.setPosition(i + this.prevColor.width + 2, this.y + 4);
        this.splashButton.setPosition(this.potionButton.x + 16 + 2, this.y + 4);
        this.nextColor.setPosition(this.splashButton.x + 16 + 2, this.y + 6);
        this.nameField.setSize(this.width - 6, 12);
        this.nameField.setPosition(this.x + 2, this.y + 4 + 16 + 4);
        this.durField.setSize(this.width - 4, 12);
        this.durField.setPosition(this.x + 2, this.nameField.y + 14 + 2);
        int j = this.y + this.fieldsMargin;
        for (TMIArea tmiarea : this.children) {
            if (!(tmiarea instanceof TMIEffectField)) continue;
            tmiarea.setSize(this.width - 4, 14);
            tmiarea.setPosition(this.x + 2, j);
            j += 16;
        }
    }

    private void fixPage() {
        int k;
        this.removeChildrenOfType(TMIEffectField.class);
        int i = (this.height - this.fieldsMargin - 4) / 16;
        int j = (int)Math.ceil((double)(this.effectList.size() / i));
        while (this.page < 0) {
            this.page += j;
        }
        this.page %= j;
        for (int m = k = i * this.page; m < k + i && m < this.effectList.size(); ++m) {
            this.addChild((TMIArea)this.effectList.get(m));
        }
    }

    @Override
    public void mouseEvent(TMIEvent p_mouseEvent_1_) {
        if (p_mouseEvent_1_.type == 2) {
            this.page -= p_mouseEvent_1_.wheel;
            this.layoutComponent();
            p_mouseEvent_1_.cancel();
        } else if (p_mouseEvent_1_.mouseButton == 0) {
            if (p_mouseEvent_1_.target == this.nextColor) {
                color = (color + 1) % 15;
                this.recreateItem();
            } else if (p_mouseEvent_1_.target == this.prevColor) {
                if (--color < 0) {
                    color = 15;
                }
                this.recreateItem();
            }
        }
    }

    @Override
    public void controlEvent(TMIEvent p_controlEvent_1_) {
        if (p_controlEvent_1_.type == 3) {
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
                        if (!(tmiarea instanceof TMIEffectField)) continue;
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