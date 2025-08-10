package com.friskmochi.mochimod.item;

import com.friskmochi.mochimod.effect.ModStatusEffects;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.data.client.Models;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;

public class ModFoodComponents {

    public static final FoodComponent MOCHI = new FoodComponent.Builder().nutrition(5).saturationModifier(0.5f)
            .statusEffect(new StatusEffectInstance(StatusEffects.LUCK,600),0.1f)
            .statusEffect(new StatusEffectInstance(ModStatusEffects.MOCHI_SKIN,600),1.0f).build();
    public static final FoodComponent STRAWBERRY = new FoodComponent.Builder().nutrition(4).saturationModifier(0.5f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,100),0.5F).build();
    public static final FoodComponent HAWTHORN = new FoodComponent.Builder().nutrition(2).saturationModifier(0.2f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE,600),0.3f).build();
    public static final FoodComponent CANDIED_HAWTHORN = new FoodComponent.Builder().nutrition(4).saturationModifier(0.4f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE,800),0.5F).build();
    public static final FoodComponent TANGHULU = new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE,1800),0.9f).usingConvertsTo(Items.STICK).alwaysEdible().build();


}
