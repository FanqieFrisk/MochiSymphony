package com.friskmochi.mochimod.item.custom;

import com.friskmochi.mochimod.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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

public class ElectricWandItem extends BowItem {

    public ElectricWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.mochisymphony.electric_wand.tooltip.line1"));
        tooltip.add(Text.translatable("item.mochisymphony.electric_wand.tooltip.line2"));
        tooltip.add(Text.translatable("item.mochisymphony.electric_wand.tooltip.line3"));
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return stack -> stack.getItem() == ModItems.ELECTRIC_EXTRACT;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return;
        ItemStack ammo = player.getProjectileType(stack);
        if (ammo.isEmpty()) return;

        int useTime = this.getMaxUseTime(stack, user) - remainingUseTicks;
        float pullProgress = getPullProgress(useTime);
        if (pullProgress < 0.1F) return;

        if (!world.isClient) {
            if (player.getItemCooldownManager().isCoolingDown(this)) return;
            player.getItemCooldownManager().set(this, 20 * 5); // 5秒冷却

            castElectricShock(world, player, pullProgress);
        }

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.PLAYERS, 1.0F, 1.0F);

        player.incrementStat(Stats.USED.getOrCreateStat(this));
        ammo.decrement(1);
    }

    private void castElectricShock(World world, PlayerEntity player, float power) {
        Vec3d center = player.getPos().add(0, player.getStandingEyeHeight() * 0.5, 0);
        double radius = 3.0 + power * 7.0; // 范围3~10格
        float damage = 12.0F + power * 8.0F; // 12~20点伤害
        int stunDuration = (int)(40 + power * 40); // 2~4秒眩晕，40ticks = 2秒

        // 粒子特效 & 音效
        if (world instanceof ServerWorld serverWorld) {
            // 中心爆裂粒子
            serverWorld.spawnParticles(ParticleTypes.FLASH,
                    center.x, center.y, center.z,
                    1, 0, 0, 0, 0);

            // 电弧 & 火花环绕
            for (int i = 0; i < 160; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double dist = Math.random() * radius;
                double height = (Math.random() - 0.5) * 2.0;

                double px = center.x + Math.cos(angle) * dist;
                double py = center.y + height;
                double pz = center.z + Math.sin(angle) * dist;

                serverWorld.spawnParticles(ParticleTypes.ELECTRIC_SPARK, px, py, pz,
                        2, 0.1, 0.1, 0.1, 0.01);
                serverWorld.spawnParticles(ParticleTypes.CRIT, px, py, pz,
                        1, 0.05, 0.05, 0.05, 0.02);
                serverWorld.spawnParticles(ParticleTypes.WITCH, px, py, pz,
                        1, 0.2, 0.1, 0.2, 0.01);
            }

            // 圆环粒子：仿雷鸣电圈
            for (double yOffset = -1; yOffset <= 1; yOffset += 0.5) {
                for (int j = 0; j < 30; j++) {
                    double theta = j * (2 * Math.PI / 30);
                    double px = center.x + Math.cos(theta) * radius;
                    double pz = center.z + Math.sin(theta) * radius;
                    double py = center.y + yOffset;

                    serverWorld.spawnParticles(ParticleTypes.END_ROD, px, py, pz,
                            1, 0.05, 0.05, 0.05, 0.001);
                }
            }
        }


        world.playSound(null, center.x, center.y, center.z,
                SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.PLAYERS,
                1.2F, 1.0F);

        // 对范围内敌人造成伤害和眩晕效果
        List<Entity> targets = world.getOtherEntities(player,
                Box.of(center, radius, radius, radius),
                e -> e instanceof LivingEntity && !e.isTeammate(player));

        for (Entity target : targets) {
            LivingEntity living = (LivingEntity) target;
            living.damage(world.getDamageSources().magic(), damage);
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, stunDuration, 1));
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, stunDuration, 1));
        }
    }
}
