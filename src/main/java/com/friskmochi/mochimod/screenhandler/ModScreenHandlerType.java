package com.friskmochi.mochimod.screenhandler;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.block.screenhandler.AlchemyTableScreenHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlerType {
    public static final ScreenHandlerType<AlchemyTableScreenHandler> ALCHEMY_TABLE_SCREEN_HANDLER = register("alchemy_table",
            AlchemyTableScreenHandler::new);
    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MochiMod.MOD_ID,id), new ScreenHandlerType(factory, FeatureFlags.VANILLA_FEATURES));
    }
    public static void init(){

    }

}
