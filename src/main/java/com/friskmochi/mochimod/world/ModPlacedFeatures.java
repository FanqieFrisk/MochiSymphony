package com.friskmochi.mochimod.world;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> HERBA_TREE_PLACED_KEY = of("herba_tree_placed");
    public static final RegistryKey<PlacedFeature> RAIN_CRYSTAL_ORE_PLACED_KEY = of("rain_crystal_ore_placed");
    public static final RegistryKey<PlacedFeature> FLAME_CRYSTALLINE_ORE_PLACED_KEY = of("flame_crystalline_ore_placed");
    public static final RegistryKey<PlacedFeature> ABYSS_DIP_ORE_PLACED_KEY = of("abyss_dip_ore_placed");

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        register(featureRegisterable, HERBA_TREE_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.HERBA_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(2,0.1f,2),
                        ModBlocks.HERBA_SAPLING));

        register(featureRegisterable, RAIN_CRYSTAL_ORE_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.RAIN_CRYSTAL_ORE_KEY),
                ModOrePlacements.modifiersWithCount(5,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
        register(featureRegisterable, FLAME_CRYSTALLINE_ORE_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.FLAME_CRYSTALLINE_ORE_KEY),
                ModOrePlacements.modifiersWithCount(5,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
        register(featureRegisterable, ABYSS_DIP_ORE_PLACED_KEY, registryEntryLookup.getOrThrow(ModConfiguredFeatures.ABYSS_DIP_ORE_KEY),
                ModOrePlacements.modifiersWithCount(3,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
    }

    public static RegistryKey<PlacedFeature> of (String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE,Identifier.of(MochiMod.MOD_ID, id));
    }

    public static void register(
            Registerable<PlacedFeature> featureRegisterable,
            RegistryKey<PlacedFeature> key,
            RegistryEntry<ConfiguredFeature<?, ?>> feature,
            List<PlacementModifier> modifiers
    ) {
        featureRegisterable.register(key, new PlacedFeature(feature, List.copyOf(modifiers)));
    }

    public static void register(
            Registerable<PlacedFeature> featureRegisterable,
            RegistryKey<PlacedFeature> key,
            RegistryEntry<ConfiguredFeature<?, ?>> feature,
            PlacementModifier... modifiers
    ) {
        register(featureRegisterable, key, feature, List.of(modifiers));
    }

}
