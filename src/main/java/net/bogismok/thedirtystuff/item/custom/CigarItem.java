package net.bogismok.thedirtystuff.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import static net.bogismok.thedirtystuff.Config.cigarEffect;
import static net.bogismok.thedirtystuff.Config.cigarEffectLevel;
import static net.bogismok.thedirtystuff.Config.cigarEffectDuration;

public class CigarItem extends SmokeItem {
    public final int singleUseDuration() {
        return 16;
    }
    public final int smokeAmplifier() {
        return 3;
    }
    public final Holder<MobEffect> effect() {
        return cigarEffect;
    }
    public final int effectAmplifier() {
        return cigarEffectLevel;
    }
    public int effectDuration() {
        return cigarEffectDuration;
    }

    public CigarItem(Properties pProperties) {
        super(pProperties);
    }
}