package net.bogismok.thedirtystuff.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class CigaretteItem extends Item {
    public static final int singleUseDuration = 32;
    int smokeCombo = 0;

    public CigaretteItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pLivingEntity) {
        int damageToTicks = pStack.getDamageValue() * singleUseDuration;
        return 384 - damageToTicks;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.TOOT_HORN;
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (!pLevel.isClientSide && smokeCombo != 0) {
            int amplifier = smokeCombo * 2;
            smokePraticles(pLivingEntity, pLevel, amplifier);
            smokeSound(pLevel, pLivingEntity);
            for (MobEffectInstance mobEffects : pLivingEntity.getActiveEffects()) {
                int duration = mobEffects.getDuration();
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, duration + 100 * smokeCombo, 2));
            }
            smokeCombo = 0;
        }
    }

    @Override
    public SoundEvent getBreakingSound() {
        return SoundEvents.CANDLE_EXTINGUISH;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide) {
            smokeCombo += 1;
            InteractionHand pHand = pLivingEntity.getUsedItemHand();
            int amplifier = smokeCombo * 2;
            smokeDurabilityUse(pLivingEntity, pHand, pStack, 1);
            smokePraticles(pLivingEntity, pLevel, amplifier);
            smokeSound(pLevel, pLivingEntity);
            for (MobEffectInstance mobEffects : pLivingEntity.getActiveEffects()) {
                int duration = mobEffects.getDuration();
                pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, duration + 100 * smokeCombo, 2));
            }
            smokeCombo = 0;
        }
        return pStack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (pRemainingUseDuration <= (getUseDuration(pStack, pLivingEntity) - singleUseDuration) && (pRemainingUseDuration % singleUseDuration) == 0 && !pLevel.isClientSide && getUseDuration(pStack, pLivingEntity) > singleUseDuration) {
            smokeCombo += 1;
            InteractionHand pHand = pLivingEntity.getUsedItemHand();
            smokeDurabilityUse(pLivingEntity, pHand, pStack, 1);
            smokeSound(pLevel, pLivingEntity);
        }
    }

    public static void smokeDurabilityUse(LivingEntity pLivingEntity, InteractionHand pHand, ItemStack pStack, int damageDealt) {
        pStack.hurtAndBreak(damageDealt, pLivingEntity, LivingEntity.getSlotForHand(pHand));
    }

    public static void smokePraticles(LivingEntity pLivingEntity, Level pLevel, int amplifier){
        double xpos = pLivingEntity.getX() + 0.4 * Math.sin(pLivingEntity.getYRot() / ((-180) / Math.PI)) * Math.cos(pLivingEntity.getXRot() / ((-180) / Math.PI));
        double ypos = pLivingEntity.getY() + 0.4 * Math.sin(pLivingEntity.getXRot() / ((-180) / Math.PI));
        double zpos = pLivingEntity.getZ() + 0.4 * Math.cos(pLivingEntity.getYRot() / ((-180) / Math.PI)) * Math.cos(pLivingEntity.getXRot() / ((-180) / Math.PI));
        if(pLevel instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, xpos, (ypos + pLivingEntity.getBbHeight()), zpos, amplifier, 0.001 * amplifier, 0.002 * amplifier, 0.001 * amplifier, 0.01);
        }
    }
    public static void smokeSound(Level pLevel, LivingEntity pLivingEntity) {
        pLevel.playSound(
                null,
                pLivingEntity.getX(),
                pLivingEntity.getY(),
                pLivingEntity.getZ(),
                SoundEvents.CANDLE_AMBIENT,
                SoundSource.PLAYERS,
                2.0F,
                0.5F
        );
    }
}