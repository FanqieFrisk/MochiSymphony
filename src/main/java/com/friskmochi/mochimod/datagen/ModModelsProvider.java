package com.friskmochi.mochimod.datagen;

import com.friskmochi.mochimod.block.ModBlocks;
import com.friskmochi.mochimod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;

public class ModModelsProvider extends FabricModelProvider {
    public ModModelsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
//        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.HERBA_LEAVES);
//          blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_HERBA_LOG).log(ModBlocks.STRIPPED_HERBA_LOG).wood(ModBlocks.STRIPPED_HERBA_WOOD);
//
//          blockStateModelGenerator.registerTintableCross(ModBlocks.HERBA_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
//        itemModelGenerator.register(ModItems.RAIN_PICKAXE, Models.GENERATED);
//        itemModelGenerator.register(ModItems.RAIN_SHOVEL, Models.GENERATED);
//        itemModelGenerator.register(ModItems.RAIN_HOE, Models.GENERATED);
//        itemModelGenerator.register(ModItems.RAIN_AXE, Models.GENERATED);
//        itemModelGenerator.registerArmor((ArmorItem) ModItems.RAIN_HELMET);
//        itemModelGenerator.registerArmor((ArmorItem) ModItems.RAIN_CHESTPLATE);
//        itemModelGenerator.registerArmor((ArmorItem) ModItems.RAIN_LEGGINGS);
//        itemModelGenerator.registerArmor((ArmorItem) ModItems.RAIN_BOOTS);
//        itemModelGenerator.register(ModItems.CANDIED_HAWTHORN, Models.GENERATED);
//        itemModelGenerator.register(ModItems.HAWTHORN, Models.GENERATED);
//        itemModelGenerator.register(ModItems.TANGHULU, Models.GENERATED);


    }
}
