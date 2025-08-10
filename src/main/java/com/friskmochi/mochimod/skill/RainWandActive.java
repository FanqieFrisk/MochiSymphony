package com.friskmochi.mochimod.skill;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RainWandActive {
    private static final List<RainWandActive> ACTIVE = new LinkedList<>();

    public static void add(ServerWorld world, PlayerEntity player, float pullPower) {
        ACTIVE.add(new RainWandActive(world, player, pullPower));
    }

    public static void onServerTick(MinecraftServer server) {
        Iterator<RainWandActive> it = ACTIVE.iterator();
        while (it.hasNext()) {
            RainWandActive r = it.next();
            if (!r.tick()) {
                it.remove();
            }
        }
    }

    private final ServerWorld world;
    private final PlayerEntity player;
    private final int maxSegments;
    private int age = 0;

    private RainWandActive(ServerWorld world, PlayerEntity player, float pullPower) {
        this.world = world;
        this.player = player;
        this.maxSegments = Math.max(1, (int)(pullPower * 6));
    }

    //判断持续时间
    public boolean tick() {
        age++;
        if (age > 160) return false; // 8秒结束

        if (age % 10 == 1) { // 每0.5秒触发
            doPulse();
        }
        return true;
    }

    private void doPulse() {
        float seconds = age / 20f;
        float damage = seconds <= 3 ? 15f : (seconds <= 5 ? 10f : 5f);

        for (int i = 1; i <= maxSegments; i++) {
            Vec3d start = player.getEyePos();
            Vec3d dir = player.getRotationVec(1.0F).normalize();
            Vec3d pos = start.add(dir.multiply(i * 2.5));

            world.spawnParticles(ParticleTypes.SPLASH, pos.x, pos.y, pos.z, 10, 1,0.5,1,0.1);
            world.spawnParticles(ParticleTypes.SONIC_BOOM, pos.x, pos.y+1, pos.z, 1, 0,0,0,0);

            if (i == 1) {
                world.playSound(null, pos.x, pos.y, pos.z,
                        SoundEvents.ENTITY_DOLPHIN_SPLASH,
                        SoundCategory.PLAYERS, 0.6f, 1.0f);
            }

            List<Entity> targets = world.getOtherEntities(player, Box.of(pos, 2.5,2.5,2.5),
                    e -> e instanceof LivingEntity && !e.isTeammate(player));

            for (Entity t : targets) {
                t.damage(world.getDamageSources().magic(), damage);
            }
        }
    }
}
