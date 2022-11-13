package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.effect.StatusEffect;

public class TMICustomPotionPanel
extends TMIArea {
    private TMIButton prevColor = new TMIButton(null, "<");
    private TMIButton nextColor = new TMIButton(null, ">");
    private TMIItemButton potionButton = new TMIItemButton(null);
    private TMIItemButton splashButton = new TMIItemButton(null);
    private TMITextField nameField = new TMITextField();
    private TMINumField durField = new TMINumField("Seconds:", 40);
    private List<TMIEffectField> effectList = new ArrayList();
    private int page = 0;
    private int fieldsMargin = 56;
    private int color = 0;
    public static final int DRINKABLE = 8192;
    public static final int SPLASH = 16384;

    public TMICustomPotionPanel() {
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
        for (int i = 0; i < StatusEffect.STATUS_EFFECTS.length; ++i) {
            if (StatusEffect.STATUS_EFFECTS[i] == null) continue;
            TMIEffectField tMIEffectField = new TMIEffectField(StatusEffect.STATUS_EFFECTS[i]);
            tMIEffectField.addEventListener(this);
            this.effectList.add(tMIEffectField);
        }
        this.recreateItem();
    }

    private void recreateItem() {
        TMIStackBuilder tMIStackBuilder = new TMIStackBuilder("potion");
        if (!this.nameField.value().equals((Object)"")) {
            tMIStackBuilder.name(this.nameField.value());
        }
        for (TMIEffectField tMIEffectField : this.effectList) {
            int n = tMIEffectField.intValue();
            int n2 = this.durField.intValue();
            if (n <= 0 || n2 <= 0) continue;
            tMIStackBuilder.effect(tMIEffectField.effect, n - 1, n2 * 20);
        }
        this.potionButton.stack = tMIStackBuilder.meta(0x2000 | this.color).stack();
        this.splashButton.stack = tMIStackBuilder.meta(0x4000 | this.color).stack();
    }

    public void layoutComponent() {
        this.fixPage();
        int n = this.x + (this.width - 32 - this.prevColor.width * 2 - 6) / 2;
        this.prevColor.setPosition(n, this.y + 6);
        this.potionButton.setPosition(n + this.prevColor.width + 2, this.y + 4);
        this.splashButton.setPosition(this.potionButton.x + 16 + 2, this.y + 4);
        this.nextColor.setPosition(this.splashButton.x + 16 + 2, this.y + 6);
        this.nameField.setSize(this.width - 6, 12);
        this.nameField.setPosition(this.x + 2, this.y + 4 + 16 + 4);
        this.durField.setSize(this.width - 4, 12);
        this.durField.setPosition(this.x + 2, this.nameField.y + 14 + 2);
        int n2 = this.y + this.fieldsMargin;
        for (TMIArea tMIArea : this.children) {
            if (!(tMIArea instanceof TMIEffectField)) continue;
            tMIArea.setSize(this.width - 4, 14);
            tMIArea.setPosition(this.x + 2, n2);
            n2 += 16;
        }
    }

    private void fixPage() {
        int n;
        this.removeChildrenOfType(TMIEffectField.class);
        int n2 = (this.height - this.fieldsMargin - 4) / 16;
        int n3 = (int)Math.ceil((double)((float)this.effectList.size() / (float)n2));
        while (this.page < 0) {
            this.page += n3;
        }
        this.page %= n3;
        for (int i = n = n2 * this.page; i < n + n2 && i < this.effectList.size(); ++i) {
            this.addChild((TMIArea)this.effectList.get(i));
        }
    }

    public void mouseEvent(TMIEvent tMIEvent) {
        if (tMIEvent.type == 2) {
            this.page -= tMIEvent.wheel;
            this.layoutComponent();
            tMIEvent.cancel();
        } else if (tMIEvent.mouseButton == 0) {
            if (tMIEvent.target == this.nextColor) {
                this.color = (this.color + 1) % 15;
                this.recreateItem();
            } else if (tMIEvent.target == this.prevColor) {
                --this.color;
                if (this.color < 0) {
                    this.color = 15;
                }
                this.recreateItem();
            }
        }
    }

    public void controlEvent(TMIEvent tMIEvent) {
        if (tMIEvent.type == 3) {
            this.recreateItem();
        }
    }

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
                        if (!(tMIArea instanceof TMIEffectField)) continue;
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