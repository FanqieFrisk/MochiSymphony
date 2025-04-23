package com.friskmochi.mochimod.effect.custom;

import com.friskmochi.mochimod.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class LuckyGlowStatusEffect extends StatusEffect {

    private static final int EFFECT_INTERVAL = 200; // 每 200 tick = 10 秒

    public LuckyGlowStatusEffect() {
        super(
                StatusEffectCategory.BENEFICIAL, // 药水效果是有益的
                0x2CBAA8);
    }

    // 每 10 秒检查一次
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration > 0 && (duration + 5) % EFFECT_INTERVAL == 0;
    }

    // 应用效果时执行的逻辑
    // 应用效果时执行的逻辑
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) { // 使用Java16模式匹配语法
            // 只允许服务端执行实际逻辑
            if (player.getWorld().isClient()) {
                return true; // 客户端直接返回，不执行后续操作
            }

            // ---- 以下原有逻辑保持不变 ----
            double rewardChance = 0.20 + amplifier * 0.05;
            rewardChance = Math.min(rewardChance, 1.0);

            double randomValue = Math.random();
            if (randomValue <= rewardChance) {
                giveRandomReward(player);
            } else {
                player.sendMessage(Text.of("【§e幸运之光§r】：很遗憾，这次§c没有§r获得奖励。"), false);
            }
        }
        return true;
    }

    // 给玩家一个随机奖励
    private void giveRandomReward(PlayerEntity player) {
        int rewardType = (int) (Math.random() * 6);

        switch (rewardType) {
            case 0:
                player.giveItemStack(new ItemStack(ModItems.RAIN_INGOT, 1)); // 1 个雨锭
                player.sendMessage(Text.of("【§e幸运之光§r】：你获得了 1 个§b雨锭§r！"), false);
                break;
            case 1:
                player.giveItemStack(new ItemStack(ModItems.FLAME_INGOT, 1)); // 1 个焰锭
                player.sendMessage(Text.of("【§e幸运之光§r】：你获得了 1 个§c焰锭§r！"), false);
                break;
            case 2:
                player.giveItemStack(new ItemStack(ModItems.HERBA_INGOT, 1)); // 1 个芸锭
                player.sendMessage(Text.of("【§e幸运之光§r】：你获得了 1 个§a芸锭§r！"), false);
                break;
            case 3:
                player.giveItemStack(new ItemStack(ModItems.ELECTRIC_INGOT, 1)); // 1 个雷锭
                player.sendMessage(Text.of("【§e幸运之光§r】：你获得了 1 个§d雷锭§r！"), false);
                break;
            case 4:
                player.giveItemStack(new ItemStack(ModItems.ECLIPSE_ETHER, 1)); // 1 个雷锭
                player.sendMessage(Text.of("【§e幸运之光§r】：你获得了 1 个§7渊以太§r！"), false);
                break;
            case 5:
                player.giveItemStack(new ItemStack(ModItems.TENEBRAE_ETHER, 1)); // 1 个雷锭
                player.sendMessage(Text.of("【§e幸运之光§r】：你获得了 1 个§7墨以太§r！"), false);
                break;
            default:
                break;
        }
    }
}
