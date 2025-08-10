package com.friskmochi.mochimod.entity;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.entity.custom.RainElfEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<RainElfEntity> RAIN_ELF = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(MochiMod.MOD_ID, "rain_elf"),
            EntityType.Builder.create(RainElfEntity::new, SpawnGroup.CREATURE).dimensions(1f,1f).build());

        public static void registerEntities() {
            System.out.println("Registering custom entities for MochiMod~");
        }
    }
