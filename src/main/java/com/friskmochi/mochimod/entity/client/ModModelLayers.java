package com.friskmochi.mochimod.entity.client;

import com.friskmochi.mochimod.MochiMod;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer RAIN_ELF =
            new EntityModelLayer(Identifier.of(MochiMod.MOD_ID, "rain_elf"),"main");
}
