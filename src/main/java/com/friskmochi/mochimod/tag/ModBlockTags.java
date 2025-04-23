package com.friskmochi.mochimod.tag;

import com.friskmochi.mochimod.MochiMod;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {
    public static final TagKey<Block> ELEMENTS = of("elements");
    public static final TagKey<Block> ORES = of("ores");
    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(MochiMod.MOD_ID, id));
    }
    public static void registerModBlockTags() {
        MochiMod.LOGGER.info("Registering Block Tags");
    }
}