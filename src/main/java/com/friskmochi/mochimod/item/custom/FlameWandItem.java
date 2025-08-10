package com.friskmochi.mochimod.item.custom;

import com.friskmochi.mochimod.item.ModItems;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class FlameWandItem extends BowItem {

    public FlameWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.mochisymphony.flame_wand.tooltip.line1"));
        tooltip.add(Text.translatable("item.mochisymphony.flame_wand.tooltip.line2"));
        tooltip.add(Text.translatable("item.mochisymphony.flame_wand.tooltip.line3"));
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return stack -> stack.getItem() == ModItems.FLAME_CRYSTALLINE;
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
            player.getItemCooldownManager().set(this, 60); // 冷却 3 秒

            castInfernoRay(world, player, pullProgress);
        }

        // 爆发音效
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1.2f, 0.9f);

        player.incrementStat(Stats.USED.getOrCreateStat(this));
        ammo.decrement(1);
    }

    private void castInfernoRay(World world, PlayerEntity player, float power) {
        Vec3d start = player.getEyePos();
        Vec3d direction = player.getRotationVec(1.0F).normalize();
        double segmentLength = 5.0;
        int maxSegments = (int) (power * 6); // 更远！30 格
        Random random = new Random();

        for (int i = 1; i <= maxSegments; i++) {
            Vec3d pos = start.add(direction.multiply(i * segmentLength));

            if (world instanceof ServerWorld server) {
                // 主火焰射线
                server.spawnParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z,
                        40, 1.0, 0.8, 1.0, 0.02);
                server.spawnParticles(ParticleTypes.SMOKE, pos.x, pos.y, pos.z,
                        20, 0.7, 0.5, 0.7, 0.01);
                server.spawnParticles(ParticleTypes.LAVA, pos.x, pos.y, pos.z,
                        15, 0.3, 0.3, 0.3, 0.001);
                server.spawnParticles(ParticleTypes.EXPLOSION, pos.x, pos.y, pos.z,
                        3, 0, 0, 0, 0);

                // 熔岩滴溅
                server.spawnParticles(ParticleTypes.DRIPPING_LAVA, pos.x, pos.y + 1, pos.z,
                        8, 0.2, 0.2, 0.2, 0.01);
                server.spawnParticles(ParticleTypes.FALLING_LAVA, pos.x, pos.y + 1, pos.z,
                        5, 0.2, 0.2, 0.2, 0.005);
                server.spawnParticles(ParticleTypes.LANDING_LAVA, pos.x, pos.y, pos.z,
                        5, 0.2, 0.2, 0.2, 0.005);
            }

            world.playSound(null, pos.x, pos.y, pos.z,
                    SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS,
                    0.6f, 1.1f + i * 0.04f);

            // 命中判断
            List<Entity> targets = world.getOtherEntities(player,
                    Box.of(pos, 3.5, 3.5, 3.5),
                    entity -> entity instanceof LivingEntity && !entity.isTeammate(player));

            for (Entity target : targets) {
                target.damage(world.getDamageSources().magic(), 13.0f);
                target.setOnFireFor(5);
                target.addVelocity(direction.x * 0.35, 0.15, direction.z * 0.35);
                target.velocityModified = true;
            }

            // 地面点燃特效
            if (world instanceof ServerWorld server) {
                for (int j = 0; j < 4; j++) {
                    double fx = pos.x + random.nextDouble() * 2 - 1;
                    double fz = pos.z + random.nextDouble() * 2 - 1;
                    BlockPos ground = new BlockPos((int) fx, (int) (pos.y - 1), (int) fz);
                    server.spawnParticles(ParticleTypes.FLAME, fx, pos.y, fz,
                            10, 0.2, 0.1, 0.2, 0.02);
                }
            }
        }
    }
}
