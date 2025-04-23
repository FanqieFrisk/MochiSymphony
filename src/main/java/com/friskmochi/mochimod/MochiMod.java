package com.friskmochi.mochimod;

import com.friskmochi.mochimod.block.ModBlocks;
import com.friskmochi.mochimod.effect.ModStatusEffects;
import com.friskmochi.mochimod.entity.ModEntities;
import com.friskmochi.mochimod.entity.custom.RainElfEntity;
import com.friskmochi.mochimod.item.ModItemGroups;
import com.friskmochi.mochimod.item.ModItems;
import com.friskmochi.mochimod.potion.ModPotions;
import com.friskmochi.mochimod.sound.ModSoundEvents;
import com.friskmochi.mochimod.tag.ModBlockTags;
import com.friskmochi.mochimod.tag.ModItemTags;
import com.friskmochi.mochimod.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MochiMod implements ModInitializer {
	public static final String MOD_ID = "mochisymphony";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerModItemGroups();
		ModSoundEvents.registerModSoundEvents();
		ModWorldGeneration.registerWorldGenerations();
		ModItemTags.registerModItemTags();
		ModBlockTags.registerModBlockTags();
		ModStatusEffects.registerModStatusEffects();
		ModPotions.registerModPotions();


		StrippableBlockRegistry.register(ModBlocks.HERBA_LOG, ModBlocks.STRIPPED_HERBA_LOG);
		StrippableBlockRegistry.register(ModBlocks.HERBA_WOOD, ModBlocks.STRIPPED_HERBA_WOOD);


		FabricDefaultAttributeRegistry.register(ModEntities.RAIN_ELF, RainElfEntity.createRainElfAttributes());



		LOGGER.info("Hello Fabric world!");
	}
}