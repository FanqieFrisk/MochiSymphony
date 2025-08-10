package com.friskmochi.mochimod.item.custom;

import com.friskmochi.mochimod.item.ModItems;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class HerbaWandItem extends Item {

    public HerbaWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.mochisymphony.herba_wand.tooltip.line1"));
        tooltip.add(Text.translatable("item.mochisymphony.herba_wand.tooltip.line2"));
        tooltip.add(Text.translatable("item.mochisymphony.herba_wand.tooltip.line3"));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack wandStack = user.getStackInHand(hand);

        boolean isCreative = user.isCreative();
        ItemStack costItem = findHerbaNugget(user);

        if (!isCreative && (costItem == null || costItem.isEmpty())) {
            user.sendMessage(Text.translatable("item.mochisymphony.herba_wand.no_nugget"), true);
            return TypedActionResult.fail(wandStack);
        }

        if (!world.isClient && !user.getItemCooldownManager().isCoolingDown(this)) {
            user.getItemCooldownManager().set(this, 20 * 100); // 100 秒冷却

            if (!isCreative) {
                costItem.decrement(1); // 消耗
            }

            // 范围内玩家治愈
            List<PlayerEntity> players = world.getEntitiesByClass(
                    PlayerEntity.class,
                    new Box(user.getBlockPos()).expand(5),
                    p -> true
            );

            for (PlayerEntity p : players) {
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 20 * 50, 9));
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20 * 30, 2));
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 20, 1));
            }

            // 音效
            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    SoundEvents.ENTITY_ALLAY_AMBIENT_WITH_ITEM, SoundCategory.PLAYERS,
                    1.0f, 1.2f);

            // 🌿加强粒子：环绕藤蔓、治愈光、自然叶子✨
            if (world instanceof ServerWorld serverWorld) {
                for (int i = 0; i < 80; i++) {
                    double angle = Math.toRadians((360.0 / 80) * i);
                    double radius = 2.5;
                    double px = user.getX() + Math.cos(angle) * radius;
                    double py = user.getY() + 0.6 + 0.3 * Math.sin(i * 0.3); // 浮动
                    double pz = user.getZ() + Math.sin(angle) * radius;

                    serverWorld.spawnParticles(ParticleTypes.HAPPY_VILLAGER,
                            px, py, pz, 3, 0.1, 0.2, 0.1, 0.005);

                    serverWorld.spawnParticles(ParticleTypes.FALLING_SPORE_BLOSSOM,
                            px, py + 0.3, pz, 2, 0.1, 0.1, 0.1, 0.01);

                    serverWorld.spawnParticles(ParticleTypes.GLOW,
                            px, py, pz, 1, 0.0, 0.0, 0.0, 0.01);
                }

                // 内圈向上漂浮的微光
                for (int i = 0; i < 30; i++) {
                    double x = user.getX() + world.random.nextGaussian() * 1.2;
                    double y = user.getY() + 0.5 + world.random.nextDouble();
                    double z = user.getZ() + world.random.nextGaussian() * 1.2;

                    serverWorld.spawnParticles(ParticleTypes.SMALL_FLAME, x, y, z, 1, 0, 0.05, 0, 0.01);
                    serverWorld.spawnParticles(ParticleTypes.END_ROD, x, y, z, 1, 0, 0.03, 0, 0.005);
                }
            }
        }

        return TypedActionResult.success(wandStack, world.isClient());
    }

    private ItemStack findHerbaNugget(PlayerEntity player) {
        if (player.getOffHandStack().isOf(ModItems.HERBA_NUGGET)) {
            return player.getOffHandStack();
        }
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isOf(ModItems.HERBA_NUGGET)) {
                return stack;
            }
        }
        return null;
    }
}
