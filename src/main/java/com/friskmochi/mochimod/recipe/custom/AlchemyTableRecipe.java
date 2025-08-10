package com.friskmochi.mochimod.recipe.custom;

import com.friskmochi.mochimod.recipe.ModRecipeSerializer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class AlchemyTableRecipe implements Recipe<SingleStackRecipeInput> {

    private final Ingredient ingredient;
    private final ItemStack result;
    private final int craftTime;

    public AlchemyTableRecipe(Ingredient ingredient, ItemStack result, int craftTime) {
        this.ingredient = ingredient;
        this.result = result;
        this.craftTime = craftTime;
    }

    @Override
    public boolean matches(SingleStackRecipeInput input, World world) {
        return ingredient.test(input.item());
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.ALCHEMY_TABLE;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeType.ALCHEMY_TABLE;
    }

    public int getCraftTime(){
        return this.craftTime;
    }

    public static class AlchemyTableRecipeSerializer implements RecipeSerializer<AlchemyTableRecipe>{

        private final PacketCodec<RegistryByteBuf, AlchemyTableRecipe> packetCodec = PacketCodec.ofStatic(this::write, this::read);
        private final MapCodec<AlchemyTableRecipe> codec = RecordCodecBuilder.mapCodec(
                i -> i.group(
                        Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(r -> r.ingredient),
                        ItemStack.VALIDATED_UNCOUNTED_CODEC.fieldOf("result").forGetter(r -> r.result),
                        Codec.INT.fieldOf("craft_time").orElse(200).forGetter(r -> r.craftTime)
                ).apply(i, AlchemyTableRecipe::new)
        );

        private AlchemyTableRecipe read(RegistryByteBuf buf) {
            Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
            int time = buf.readVarInt();
            return new AlchemyTableRecipe(ingredient,itemStack,time);
        }

        private void write(RegistryByteBuf buf, AlchemyTableRecipe recipe) {
            Ingredient.PACKET_CODEC.encode(buf, recipe.ingredient);
            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
            buf.writeVarInt(recipe.craftTime);
        }

        @Override
        public MapCodec<AlchemyTableRecipe> codec() {
            return codec;
        }

        @Override
        public PacketCodec<RegistryByteBuf, AlchemyTableRecipe> packetCodec() {
            return packetCodec;
        }
    }

}
