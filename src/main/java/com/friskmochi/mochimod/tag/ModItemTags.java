package com.friskmochi.mochimod.tag;

import com.friskmochi.mochimod.MochiMod;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;


public class ModItemTags {
    public static final TagKey<Item> WAND_PROJECTILES = of("wand_projectiles");
    public static final TagKey<Item> ELEMENTS = of("elements");

    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(MochiMod.MOD_ID, id));
    }
    public static void registerModItemTags() {
        MochiMod.LOGGER.info("Registering Block Tags");
    }
}
