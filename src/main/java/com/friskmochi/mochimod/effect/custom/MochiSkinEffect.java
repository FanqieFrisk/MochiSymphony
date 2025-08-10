package com.friskmochi.mochimod.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class MochiSkinEffect extends StatusEffect {
    public MochiSkinEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xFFC0CB); // 中性效果，颜色为粉色
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        World world = entity.getWorld();

        if (world instanceof ServerWorld serverWorld) {
            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0f, 1.1f);

            for (int i = 0; i < 360; i += 20) { // 每 20 度生成一个粒子点
                double angle = Math.toRadians(i);
                double radius = 1.2;
                double px = entity.getX() + Math.cos(angle) * radius;
                double pz = entity.getZ() + Math.sin(angle) * radius;

                serverWorld.spawnParticles(ParticleTypes.END_ROD, px, entity.getY() + 0.2, pz, 1, 0, 0, 0, 0.01);
            }

            final Vector3f pinkColor = new Vector3f(1.0f, 0.75f, 0.79f);
            DustParticleEffect pinkDust = new DustParticleEffect(pinkColor, 1.0f);

            for (int i = 0; i < 40; i++) {
                double height = i * 0.08; // 总上升高度
                double angle = i * 24;   // 螺旋的紧密程度
                double radius = 0.9;     // 螺旋的半径

                double px = entity.getX() + Math.cos(Math.toRadians(angle)) * radius;
                double py = entity.getY() + height;
                double pz = entity.getZ() + Math.sin(Math.toRadians(angle)) * radius;

                serverWorld.spawnParticles(ParticleTypes.HEART, px, py + 0.5, pz, 1, 0, 0, 0, 0);
                serverWorld.spawnParticles(pinkDust, px, py, pz, 1, 0, 0, 0, 0);
            }
        }

        super.onApplied(entity, amplifier);
    }
}
