package net.minecraft.src;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.effect.StatusEffect;

public class TMIEffectField
extends TMINumField {
    public StatusEffect effect;

    public TMIEffectField(StatusEffect p_i9_1_) {
        super(I18n.translate((String)p_i9_1_.getTranslationKey(), (Object[])new Object[0]));
        this.effect = p_i9_1_;
    }
}