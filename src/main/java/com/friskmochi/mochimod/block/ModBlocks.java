package com.friskmochi.mochimod.block;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.world.tree.ModTreeGenerator;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {
    public static final Block RAIN_CRYSTAL_ORE = register("rain_crystal_ore",new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_ORE)));
    public static final Block ABYSS_DIP_ORE = register("abyss_dip_ore",new Block(AbstractBlock.Settings.copy(Blocks.ANCIENT_DEBRIS)));
    public static final Block ELECTRIC_EXTRACT_ORE = register("electric_extract_ore",new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_ORE)));
    public static final Block FLAME_CRYSTALLINE_ORE = register("flame_crystalline_ore",new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_ORE)));
    public static final Block HERBA_NUGGET_ORE = register("herba_nugget_ore",new Block(AbstractBlock.Settings.copy(Blocks.DIAMOND_ORE)));

    public static final Block RAIN_BLOCK = register("rain_block",new Block(AbstractBlock.Settings.create().strength(3.0F,3.0f)));
    public static final Block CHISELED_POLISHED_RAIN_BLOCK = register("chiseled_polished_rain_block",new Block(AbstractBlock.Settings.create().strength(3.0F,3.0f)));

    public static final Block HERBA_LEAVES = register("herba_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.AZALEA_LEAVES)));

    public static final Block HERBA_LOG = register("herba_log",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)));
    public static final Block HERBA_WOOD = register("herba_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD)));

    public static final Block STRIPPED_HERBA_LOG = register("stripped_herba_log",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final Block STRIPPED_HERBA_WOOD = register("stripped_herba_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD)));

    public static final Block RAIN_FLOWER = register("rain_flower",
            new FlowerBlock(StatusEffects.WATER_BREATHING,30,AbstractBlock.Settings.copy(Blocks.DANDELION).nonOpaque()));
    public static final Block POTTED_RAIN_FLOWER = Registry.register(Registries.BLOCK,Identifier.of(MochiMod.MOD_ID,"potted_rain_flower"),
            new FlowerPotBlock(RAIN_FLOWER,AbstractBlock.Settings.copy(Blocks.POTTED_DANDELION)));

    public static final Block HERBA_SAPLING = register("herba_sapling",
            new SaplingBlock(ModTreeGenerator.HERBA_TREE, AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)));

    public static void registerBlockItems(String id,Block block){
        Item item = Registry.register(Registries.ITEM,Identifier.of(MochiMod.MOD_ID,id),new BlockItem(block,new Item.Settings()));
        if (item instanceof BlockItem){
            ((BlockItem)item).appendBlocks(Item.BLOCK_ITEMS,item);
        }
    }
    public static Block register(String id,Block block) {
        registerBlockItems(id,block);
        return Registry.register(Registries.BLOCK, Identifier.of(MochiMod.MOD_ID,id),block);
    }
    public static void registerModBlocks() {
        MochiMod.LOGGER.info("Registering Blocks");
    }
}
