package com.friskmochi.mochimod.item.custom;

import com.friskmochi.mochimod.item.ModArmorMaterials;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import java.util.Map;

public class ModRainArmorItem extends ArmorItem {
    private static final Map<ArmorMaterial, StatusEffectInstance> MAP =
            (new ImmutableMap.Builder<ArmorMaterial, StatusEffectInstance>())
                    .put(ModArmorMaterials.RAIN.value(), new StatusEffectInstance(StatusEffects.CONDUIT_POWER,200,1,
                            false,false,true)).build();

    public ModRainArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient() && entity instanceof PlayerEntity player && hasFullSuitOfArmor(player)) {
            evaluateArmorEffects(player);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void evaluateArmorEffects(PlayerEntity player) {
        for (Map.Entry<ArmorMaterial, StatusEffectInstance> entry : MAP.entrySet()) {
            ArmorMaterial material = entry.getKey();
            StatusEffectInstance effect = entry.getValue();

            if (hasCorrectArmorSet(player, material)) {
                addStatusEffectForMaterial(player, effect);
            }
        }
    }

    private void addStatusEffectForMaterial(PlayerEntity player, StatusEffectInstance effect) {
        boolean hasEffect = player.hasStatusEffect(effect.getEffectType());

        if (!hasEffect) {
            player.addStatusEffect(new StatusEffectInstance(effect));
        }
    }

    private boolean hasCorrectArmorSet(PlayerEntity player, ArmorMaterial material) {
        for (ItemStack stack : player.getInventory().armor) {
            if (!(stack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem helmet = (ArmorItem) player.getInventory().getArmorStack(3).getItem();
        ArmorItem chestplate = (ArmorItem) player.getInventory().getArmorStack(2).getItem();
        ArmorItem leggings = (ArmorItem) player.getInventory().getArmorStack(1).getItem();
        ArmorItem boots = (ArmorItem) player.getInventory().getArmorStack(0).getItem();

        return helmet.getMaterial().value() == material &&
                chestplate.getMaterial().value() == material &&
                leggings.getMaterial().value() == material &&
                boots.getMaterial().value() == material;
    }

    private boolean hasFullSuitOfArmor(PlayerEntity player) {
        ItemStack helmet = player.getInventory().getArmorStack(3);
        ItemStack chestplate = player.getInventory().getArmorStack(2);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack boots = player.getInventory().getArmorStack(0);

        return !helmet.isEmpty() && !chestplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
    }
}
