package com.friskmochi.mochimod.item;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.block.ModBlocks;
import com.friskmochi.mochimod.potion.ModPotions;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup MOCHI_BLOCKS_GROUP = Registry.register(Registries.ITEM_GROUP,Identifier.of(MochiMod.MOD_ID,"mochi_blocks_group"),
        ItemGroup.create(null,-1).displayName(Text.translatable("itemGroup.mochi_blocks_group"))
                .icon(() -> new ItemStack(ModBlocks.RAIN_BLOCK))
                .entries((displayContext, entries) -> {
                    entries.add(ModBlocks.RAIN_BLOCK);
                    entries.add(ModBlocks.CHISELED_POLISHED_RAIN_BLOCK);

                    entries.add(ModBlocks.RAIN_CRYSTAL_ORE);
                    entries.add(ModBlocks.ELECTRIC_EXTRACT_ORE);
                    entries.add(ModBlocks.HERBA_NUGGET_ORE);
                    entries.add(ModBlocks.FLAME_CRYSTALLINE_ORE);
                    entries.add(ModBlocks.ABYSS_DIP_ORE);

                    entries.add(ModBlocks.HERBA_WOOD);
                    entries.add(ModBlocks.HERBA_LOG);
                    entries.add(ModBlocks.STRIPPED_HERBA_WOOD);
                    entries.add(ModBlocks.STRIPPED_HERBA_LOG);

                }).build());

    public static final ItemGroup MOCHI_NATURE_GROUP = Registry.register(Registries.ITEM_GROUP,Identifier.of(MochiMod.MOD_ID,"mochi_nature_group"),
            ItemGroup.create(null,-1).displayName(Text.translatable("itemGroup.mochi_nature_group"))
                .icon(() -> new ItemStack(ModBlocks.HERBA_SAPLING))
                .entries((displayContext, entries) -> {
                    entries.add(ModBlocks.RAIN_FLOWER);

                    entries.add(ModBlocks.HERBA_SAPLING);
                    entries.add(ModBlocks.HERBA_LEAVES);
                    entries.add(ModBlocks.HERBA_WOOD);
                    entries.add(ModBlocks.HERBA_LOG);
                    entries.add(ModBlocks.STRIPPED_HERBA_WOOD);
                    entries.add(ModBlocks.STRIPPED_HERBA_LOG);

                    entries.add(ModItems.RAIN_ELF_SPAWN_EGG);

                    }).build());


    public static final ItemGroup MOCHI_EQUIPMENTS_GROUP = Registry.register(Registries.ITEM_GROUP,Identifier.of(MochiMod.MOD_ID,"mochi_equipments_group"),
        ItemGroup.create(null,-1).displayName(Text.translatable("itemGroup.mochi_equipments_group"))
                .icon(() -> new ItemStack(ModItems.RAIN_SWORD))
                .entries((displayContext, entries) -> {
                    entries.add(ModItems.RAIN_SWORD);
                    entries.add(ModItems.RAIN_SHOVEL);
                    entries.add(ModItems.RAIN_PICKAXE);
                    entries.add(ModItems.RAIN_AXE);
                    entries.add(ModItems.RAIN_HOE);
                    entries.add(ModItems.RAIN_HELMET);
                    entries.add(ModItems.RAIN_CHESTPLATE);
                    entries.add(ModItems.RAIN_LEGGINGS);
                    entries.add(ModItems.RAIN_BOOTS);
                    entries.add(ModItems.ORE_TOME);
                    entries.add(ModItems.ELEMENT_TOME);
                    entries.add(ModItems.FLAME_WAND);
                    entries.add(ModItems.MUSIC_DISC_QRRS);
                    entries.add(ModItems.MUSIC_DISC_AIR);
                    entries.add(ModItems.MUSIC_DISC_UR);

//                    entries.add(ModItems.ELECTRIC_SHIELD);

                }).build());

    public static final ItemGroup MOCHI_MATERIALS_GROUP = Registry.register(Registries.ITEM_GROUP,Identifier.of(MochiMod.MOD_ID,"mochi_materials_group"),
            ItemGroup.create(null,-1).displayName(Text.translatable("itemGroup.mochi_materials_group"))
                .icon(() -> new ItemStack(ModItems.ABYSS_INGOT))
                .entries((displayContext, entries) -> {
                    entries.add(ModItems.RAIN_CRYSTAL);
                    entries.add(ModItems.RAIN_INGOT);

                    entries.add(ModItems.ELECTRIC_EXTRACT);
                    entries.add(ModItems.ELECTRIC_INGOT);

                    entries.add(ModItems.HERBA_NUGGET);
                    entries.add(ModItems.HERBA_INGOT);

                    entries.add(ModItems.FLAME_CRYSTALLINE);
                    entries.add(ModItems.FLAME_INGOT);

                    entries.add(ModItems.ECLIPSE_ETHER);
                    entries.add(ModItems.TENEBRAE_ETHER);
                    entries.add(ModItems.ABYSS_DIP);
                    entries.add(ModItems.ABYSS_INGOT);

                    entries.add(ModItems.WEATHERFORGED_OMNISTRUCT_INGOT);

                }).build());

    public static final ItemGroup MOCHI_FAD_GROUP = Registry.register(Registries.ITEM_GROUP,Identifier.of(MochiMod.MOD_ID,"mochi_fad_group"),
            ItemGroup.create(null,-1).displayName(Text.translatable("itemGroup.mochi_fad_group"))
                .icon(() -> new ItemStack(ModItems.HAWTHORN))
                .entries((displayContext, entries) -> {
                    entries.add(ModItems.MOCHI);
                    entries.add(ModItems.STRAWBERRY);
                    entries.add(ModItems.HAWTHORN);
                    entries.add(ModItems.CANDIED_HAWTHORN);
                    entries.add(ModItems.TANGHULU);
//                    entries.add(ModPotions.LUCKY_GLOW_POTION);

                }).build());

    public static void registerModItemGroups() {
        MochiMod.LOGGER.info("Registering Item Groups");
    }
}
