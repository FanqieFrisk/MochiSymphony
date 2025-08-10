package com.friskmochi.mochimod.item.custom;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RainSwordItem extends SwordItem {

    public RainSwordItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, net.minecraft.entity.Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (!(entity instanceof PlayerEntity)) return;

        PlayerEntity player = (PlayerEntity) entity;

        // 必须主手持有才加效果
        if (!player.getMainHandStack().equals(stack)) return;

        // 雨天并且玩家在雨中
        if (world.isRaining() && world.isSkyVisible(player.getBlockPos())) {
            // 添加速度 I 和跳跃提升 I 效果（不断刷新）
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 0, true, false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 10, 0, true, false));

        }
    }
}
