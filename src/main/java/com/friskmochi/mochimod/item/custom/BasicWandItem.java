package com.friskmochi.mochimod.item.custom;

import com.friskmochi.mochimod.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

public class BasicWandItem extends BowItem {

    public BasicWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.mochisymphony.basic_wand.tooltip.line1"));
        tooltip.add(Text.translatable("item.mochisymphony.basic_wand.tooltip.line2"));
        tooltip.add(Text.translatable("item.mochisymphony.basic_wand.tooltip.line3"));
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return stack -> stack.getItem() == Items.GOLD_INGOT;
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
            player.getItemCooldownManager().set(this, 20 * 3); // 3秒冷却

            castBasicMagic((ServerWorld) world, player, pullProgress);
        }

        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.2F);

        player.incrementStat(Stats.USED.getOrCreateStat(this));
        ammo.decrement(1);
    }

    private void castBasicMagic(ServerWorld world, PlayerEntity player, float pullPower) {
        Vec3d start = player.getEyePos();
        Vec3d dir = player.getRotationVec(1.0F).normalize();
        double step = 1.0;
        int maxSteps = 12;

        for (int i = 1; i <= maxSteps; i++) {
            Vec3d pos = start.add(dir.multiply(i * step));

            world.spawnParticles(ParticleTypes.GLOW, pos.x, pos.y, pos.z, 5, 0.2, 0.2, 0.2, 0.01);

            List<Entity> targets = world.getOtherEntities(player,
                    Box.of(pos, 1.2, 1.2, 1.2),
                    e -> e instanceof LivingEntity && !e.isTeammate(player));

            if (!targets.isEmpty()) {
                LivingEntity target = (LivingEntity) targets.get(0);
                target.damage(world.getDamageSources().magic(), 8.0f);
                target.addVelocity(dir.x * 0.1, 0.1, dir.z * 0.1);
                target.velocityModified = true;
                break;
            }
        }
    }
}
