package com.friskmochi.mochimod.recipe.custom;

import com.friskmochi.mochimod.MochiMod;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModRecipeType {
    public static final RecipeType<AlchemyTableRecipe> ALCHEMY_TABLE = register("alchemy_table");
    public static <T extends Recipe<?>> RecipeType<T> register(String id){
        return Registry.register(Registries.RECIPE_TYPE, Identifier.of(MochiMod.MOD_ID, id), new RecipeType<T>() {
            @Override
            public String toString() {
                return id;
            }
        });
    }
    public static void init(){

    }
}
