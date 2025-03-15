package com.friskmochi.mochimod;

import com.friskmochi.mochimod.block.ModBlocks;
import com.friskmochi.mochimod.entity.ModEntities;
import com.friskmochi.mochimod.entity.custom.RainElfEntity;
import com.friskmochi.mochimod.item.ModItemGroups;
import com.friskmochi.mochimod.item.ModItems;
import com.friskmochi.mochimod.sound.ModJukeboxSongs;
import com.friskmochi.mochimod.sound.ModSoundEvents;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MochiMod implements ModInitializer {
	public static final String MOD_ID = "mochisymphony";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		ModBlocks.registerModBlocks();
		ModSoundEvents.registerModSoundEvents();



		StrippableBlockRegistry.register(ModBlocks.HERBA_LOG, ModBlocks.STRIPPED_HERBA_LOG);
		StrippableBlockRegistry.register(ModBlocks.HERBA_WOOD, ModBlocks.STRIPPED_HERBA_WOOD);


		FabricDefaultAttributeRegistry.register(ModEntities.RAIN_ELF, RainElfEntity.createRainElfAttributes());




		LOGGER.info("Hello Fabric world!");
		ModItems.registerModItems();
	}
}