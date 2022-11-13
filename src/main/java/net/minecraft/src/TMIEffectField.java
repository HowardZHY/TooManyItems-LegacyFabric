package net.minecraft.src;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.effect.StatusEffect;

public class TMIEffectField
extends TMINumField {
    public StatusEffect effect;

    public TMIEffectField(StatusEffect statusEffect) {
        super(I18n.translate((String)statusEffect.getTranslationKey(), (Object[])new Object[0]));
        this.effect = statusEffect;
    }
}