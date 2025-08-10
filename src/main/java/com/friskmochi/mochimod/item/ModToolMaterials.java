package com.friskmochi.mochimod.item;

import com.google.common.base.Suppliers;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial {
    RAIN(BlockTags.INCORRECT_FOR_IRON_TOOL, 1500, 7.0f, 2.5f, 22, () -> Ingredient.ofItems(ModItems.RAIN_INGOT)),
    ELECTRIC(BlockTags.INCORRECT_FOR_IRON_TOOL, 800, 9.0f, 3.5f, 15, () -> Ingredient.ofItems(ModItems.ELECTRIC_INGOT)),
    HERBA(BlockTags.INCORRECT_FOR_IRON_TOOL, 2000, 5.0f, 1.5f, 30, () -> Ingredient.ofItems(ModItems.HERBA_INGOT)),
    FLAME(BlockTags.INCORRECT_FOR_IRON_TOOL, 1600, 7.5f, 2.5f, 25, () -> Ingredient.ofItems(ModItems.FLAME_INGOT)),
    ABYSS(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2300, 9.5f, 4.0f, 77, () -> Ingredient.ofItems(ModItems.ABYSS_INGOT)),
    HAWTHORN(BlockTags.INCORRECT_FOR_IRON_TOOL,300,9.0f,2.0f,100, () -> Ingredient.ofItems(ModItems.HAWTHORN));

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterials(TagKey<Block> inverseTag, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.inverseTag = inverseTag;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
