package com.friskmochi.mochimod;

import com.friskmochi.mochimod.entity.ModEntities;
import com.friskmochi.mochimod.entity.client.ModModelLayers;
import com.friskmochi.mochimod.entity.client.RainElfModel;
import com.friskmochi.mochimod.entity.client.RainElfRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class MochiModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.RAIN_ELF, RainElfModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.RAIN_ELF, RainElfRenderer::new);

    }
}