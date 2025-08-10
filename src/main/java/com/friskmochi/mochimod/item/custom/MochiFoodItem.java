package com.friskmochi.mochimod.item.custom;


import com.friskmochi.mochimod.effect.ModStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MochiFoodItem extends Item {
    public MochiFoodItem(Settings settings) {
        super(settings);
    }
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(ModStatusEffects.MOCHI_SKIN, 600, 0, false, false, true));
        }
        return super.finishUsing(stack, world, user);
    }
}
