package com.friskmochi.mochimod.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.random.Random;


public class LingaGuliGuliStatusEffect extends StatusEffect {

    private static final int BASE_INTERVAL = 2;  // 每 2 tick 触发基础逻辑

    public LingaGuliGuliStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xFF00FF); // 粉
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration > 0 && (duration + 1) % BASE_INTERVAL == 0;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!(entity instanceof PlayerEntity player)) return true;

        if (player.getWorld().isClient()) return true; // 客户端跳过逻辑
        Random rand = player.getRandom();

        //自动跳跃
        if (player.isOnGround()) {
            player.addVelocity(0, 0.6 + rand.nextDouble() * 0.3, 0);
            player.velocityModified = true;
        }

        //左右手物品互换
        ItemStack main = player.getMainHandStack();
        ItemStack off = player.getOffHandStack();
        player.setStackInHand(Hand.MAIN_HAND, off);
        player.setStackInHand(Hand.OFF_HAND, main);

        return true;
    }
}
