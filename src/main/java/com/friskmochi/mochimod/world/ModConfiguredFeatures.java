package com.friskmochi.mochimod.world;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> HERBA_TREE_KEY = of("herba_tree");

    public static final RegistryKey<ConfiguredFeature<?, ?>> RAIN_CRYSTAL_ORE_KEY = of("rain_crystal_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLAME_CRYSTALLINE_ORE_KEY = of("flame_crystalline_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ABYSS_DIP_ORE_KEY = of("abyss_dip_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        register(featureRegisterable, HERBA_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.HERBA_LOG),
                new StraightTrunkPlacer(3,0,2),
                BlockStateProvider.of(ModBlocks.HERBA_LEAVES),
                new BlobFoliagePlacer(ConstantIntProvider.create(4), ConstantIntProvider.create(2), 2),
                new TwoLayersFeatureSize(1,0,2)
        ).build());

        RuleTest stoneReplace = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest netherReplace = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest endReplace = new BlockMatchRuleTest(Blocks.END_STONE);

        List<OreFeatureConfig.Target> overWorldTargets = List.of(
                OreFeatureConfig.createTarget(stoneReplace, ModBlocks.RAIN_CRYSTAL_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> netherTargets = List.of(
                OreFeatureConfig.createTarget(netherReplace, ModBlocks.FLAME_CRYSTALLINE_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> endTargets = List.of(
                OreFeatureConfig.createTarget(endReplace, ModBlocks.ABYSS_DIP_ORE.getDefaultState()));

        register(featureRegisterable,RAIN_CRYSTAL_ORE_KEY, Feature.ORE, new OreFeatureConfig(overWorldTargets,6));
        register(featureRegisterable,FLAME_CRYSTALLINE_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherTargets,6));
        register(featureRegisterable,ABYSS_DIP_ORE_KEY, Feature.ORE, new OreFeatureConfig(endTargets,3));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> of (String id){
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MochiMod.MOD_ID, id));
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> void register(
            Registerable<ConfiguredFeature<?, ?>> registerable, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC config
    ) {
        registerable.register(key, new ConfiguredFeature<FC, F>(feature, config));
    }

}
