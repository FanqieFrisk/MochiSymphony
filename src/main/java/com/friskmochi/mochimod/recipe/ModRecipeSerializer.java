package com.friskmochi.mochimod.recipe;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.recipe.custom.AlchemyTableRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipeSerializer {

    public static final RecipeSerializer<AlchemyTableRecipe> ALCHEMY_TABLE = register("alchemy_table", new AlchemyTableRecipe.AlchemyTableRecipeSerializer());

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer){
        return Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(MochiMod.MOD_ID, id), serializer);
    }
    public static void init(){

    }
}
