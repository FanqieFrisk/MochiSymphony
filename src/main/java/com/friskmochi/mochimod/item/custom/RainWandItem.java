package com.friskmochi.mochimod.item.custom;

import com.friskmochi.mochimod.item.ModItems;
import com.friskmochi.mochimod.skill.RainWandActive;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class RainWandItem extends BowItem {

    public RainWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.mochisymphony.rain_wand.tooltip.line1"));
        tooltip.add(Text.translatable("item.mochisymphony.rain_wand.tooltip.line2"));
        tooltip.add(Text.translatable("item.mochisymphony.rain_wand.tooltip.line3"));
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return stack -> stack.getItem() == ModItems.RAIN_CRYSTAL;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            ItemStack itemStack = player.getProjectileType(stack);
            if (!itemStack.isEmpty()) {
                int useTime = this.getMaxUseTime(stack, user) - remainingUseTicks;
                float pullProgress = getPullProgress(useTime);
                if (pullProgress < 0.1F) {
                    return;
                }

                if (!world.isClient) {
                    // 冷却检查
                    if (player.getItemCooldownManager().isCoolingDown(this)) {
                        return;
                    }
                    player.getItemCooldownManager().set(this, 20 * 30); // 30秒冷却

                    // 初始水柱粒子轰炸效果
                    initialWaterBlast((ServerWorld) world, player, pullProgress);

                    // 启动持续攻击
                    RainWandActive.add((ServerWorld) world, player, pullProgress);
                }

                // 播放初始音效
                world.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.ITEM_BUCKET_FILL,
                        SoundCategory.PLAYERS,
                        1.0F,
                        1.0F
                );

                player.incrementStat(Stats.USED.getOrCreateStat(this));
                itemStack.decrement(1);
            }
        }
    }

    // 初始水柱攻击特效（只显示粒子，不伤害）
    private void initialWaterBlast(ServerWorld world, PlayerEntity player, float pullPower) {
        Vec3d start = player.getEyePos();
        Vec3d direction = player.getRotationVec(1.0F).normalize();
        double segmentLength = 2.5;
        int maxSegments = (int)(pullPower * 6);

        for (int i = 1; i <= maxSegments; i++) {
            Vec3d pos = start.add(direction.multiply(i * segmentLength));

            world.spawnParticles(ParticleTypes.SPLASH, pos.x, pos.y, pos.z, 60, 1.5, 1.0, 1.5, 0.2);
            world.spawnParticles(ParticleTypes.BUBBLE_POP, pos.x, pos.y, pos.z, 40, 1.0, 1.0, 1.0, 0.1);
            world.spawnParticles(ParticleTypes.FALLING_WATER, pos.x, pos.y + 1.0, pos.z, 20, 0.8, 0.3, 0.8, 0.01);
            world.spawnParticles(ParticleTypes.BUBBLE, pos.x, pos.y, pos.z, 15, 0.6, 0.6, 0.6, 0.02);

            // 初始轰击音效
            world.playSound(null, pos.x, pos.y, pos.z,
                    SoundEvents.ENTITY_DOLPHIN_SPLASH,
                    SoundCategory.PLAYERS, 0.6f, 1.1f + i * 0.05f);
        }
    }
}
