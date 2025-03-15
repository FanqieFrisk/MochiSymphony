package com.friskmochi.mochimod.world.tree;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.world.ModConfiguredFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class ModTreeGenerator {
    public static final SaplingGenerator HERBA_TREE = new SaplingGenerator(
            MochiMod.MOD_ID + ":herba_tree",
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.HERBA_TREE_KEY),
            Optional.empty()
    );
}
