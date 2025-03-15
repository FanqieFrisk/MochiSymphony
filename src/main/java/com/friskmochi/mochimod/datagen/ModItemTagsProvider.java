package com.friskmochi.mochimod.datagen;

import com.friskmochi.mochimod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends FabricTagProvider.ItemTagProvider {


    public ModItemTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
//        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
//                .add(ModItems.RAIN_HELMET)
//                .add(ModItems.RAIN_CHESTPLATE)
//                .add(ModItems.RAIN_LEGGINGS)
//                .add(ModItems.RAIN_BOOTS);
    }
}