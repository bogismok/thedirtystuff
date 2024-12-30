package net.bogismok.thedirtystuff.item.custom;

import net.bogismok.thedirtystuff.init.ModDataComponentTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import net.minecraft.network.chat.Component;

import static net.bogismok.thedirtystuff.Config.igniters;

public abstract class SmokeItem extends Item {
    public abstract int singleUseDuration();
    public abstract int smokeAmplifier();
    public abstract Holder<MobEffect> effect();
    public abstract int effectAmplifier();
    public abstract int effectDuration();

    public int smokeCombo = 0;

    public SmokeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pLivingEntity) {
        int maxDamageToTicks = pStack.getMaxDamage() * singleUseDuration();
        int damageToTicks = pStack.getDamageValue() * singleUseDuration();
        return maxDamageToTicks - damageToTicks;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.TOOT_HORN;
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (!pLevel.isClientSide && smokeCombo != 0 && pStack.get(ModDataComponentTypes.LIT.get())) {
            smokeParticles(pLivingEntity, pLevel, smokeCombo * smokeAmplifier());
            smokeSound(pLevel, pLivingEntity);
            smokeEffect(pLivingEntity, smokeCombo);

            smokeCombo = 0;
        }
    }

    @Override
    public SoundEvent getBreakingSound() {
        return SoundEvents.CANDLE_EXTINGUISH;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide && pStack.get(ModDataComponentTypes.LIT.get())) {
            smokeCombo += 1;

            smokeParticles(pLivingEntity, pLevel, smokeCombo * smokeAmplifier());
            smokeSound(pLevel, pLivingEntity);
            smokeEffect(pLivingEntity, smokeCombo);

            pStack.consume(1, pLivingEntity);

            smokeCombo = 0;
        }
        return pStack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        for (Item igniter : igniters) {
            if ((stack.get(ModDataComponentTypes.LIT.get()) == null || Boolean.FALSE.equals(stack.get(ModDataComponentTypes.LIT.get()))) && pPlayer.getOffhandItem().is(igniter)) {
                stack.set(ModDataComponentTypes.LIT.get(), true);
                pLevel.playSound(
                        null,
                        pPlayer.getX(),
                        pPlayer.getY(),
                        pPlayer.getZ(),
                        SoundEvents.FLINTANDSTEEL_USE,
                        SoundSource.PLAYERS,
                        0.5F,
                        1.0F
                );
                ItemStack stack_ = pPlayer.getOffhandItem();
                if (stack_.isStackable()){
                    stack_.consume(1, pPlayer);
                } else {
                    stack_.hurtAndBreak(1, pPlayer, pPlayer.getEquipmentSlotForItem(stack_));
                }
            }
        }
        if (stack.get(ModDataComponentTypes.LIT.get()) == null || Boolean.FALSE.equals(stack.get(ModDataComponentTypes.LIT.get()))) {
            pPlayer.displayClientMessage(Component.literal("You need to hold a flint and steel in your off hand to light it"), true);
            return InteractionResultHolder.fail(stack);
        }
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (pRemainingUseDuration <= (getUseDuration(pStack, pLivingEntity) - singleUseDuration())
            && (pRemainingUseDuration % singleUseDuration()) == 0
            && !pLevel.isClientSide
            && getUseDuration(pStack, pLivingEntity) > singleUseDuration()
            && pStack.get(ModDataComponentTypes.LIT.get())) {
            smokeCombo += 1;

            InteractionHand pHand = pLivingEntity.getUsedItemHand();
            smokeDurabilityUse(pLivingEntity, pHand, pStack, 1);

            smokeSound(pLevel, pLivingEntity);
        }
    }

    public static void smokeDurabilityUse(LivingEntity pLivingEntity, InteractionHand pHand, ItemStack pStack, int damageDealt) {
        pStack.hurtAndBreak(damageDealt, pLivingEntity, LivingEntity.getSlotForHand(pHand));
    }

    public static void smokeParticles(LivingEntity pLivingEntity, Level pLevel, int amplifier){
        double xPos = pLivingEntity.getX() + 0.4 * Math.sin(pLivingEntity.getYRot() / ((-180) / Math.PI)) * Math.cos(pLivingEntity.getXRot() / ((-180) / Math.PI));
        double yPos = pLivingEntity.getY() + 0.4 * Math.sin(pLivingEntity.getXRot() / ((-180) / Math.PI));
        double zPos = pLivingEntity.getZ() + 0.4 * Math.cos(pLivingEntity.getYRot() / ((-180) / Math.PI)) * Math.cos(pLivingEntity.getXRot() / ((-180) / Math.PI));
        if(pLevel instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, xPos, (yPos + pLivingEntity.getBbHeight()), zPos, amplifier, 0.001 * amplifier, 0.002 * amplifier, 0.001 * amplifier, 0.01);
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

    public void smokeEffect (LivingEntity pLivingEntity, int smokeCombo) {
        if(pLivingEntity.hasEffect(effect()) && effect() != null) {
            for (MobEffectInstance mobEffects : pLivingEntity.getActiveEffects()) {
                if (mobEffects.is(effect())){
                    int duration = mobEffects.getDuration();
                    pLivingEntity.addEffect(new MobEffectInstance(effect(), duration + effectDuration() * smokeCombo, effectAmplifier()));
                }
            }
        } else {
            pLivingEntity.addEffect(new MobEffectInstance(effect(), effectDuration() * smokeCombo, effectAmplifier()));
        }
    }
}