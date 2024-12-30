package net.bogismok.thedirtystuff.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import static net.bogismok.thedirtystuff.Config.cigaretteEffect;
import static net.bogismok.thedirtystuff.Config.cigaretteEffectLevel;
import static net.bogismok.thedirtystuff.Config.cigaretteEffectDuration;

public class CigaretteItem extends SmokeItem {
    public final int singleUseDuration() {
        return 16;
    }
    public final int smokeAmplifier() {
        return 2;
    }
    public final Holder<MobEffect> effect() {
        return cigaretteEffect;
    }
    public final int effectAmplifier() {
        return cigaretteEffectLevel;
    }
    public int effectDuration() {
        return cigaretteEffectDuration;
    }

    public CigaretteItem(Properties pProperties) {
        super(pProperties);
    }
}