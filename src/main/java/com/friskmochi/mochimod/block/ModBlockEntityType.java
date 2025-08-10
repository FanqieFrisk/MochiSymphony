package com.friskmochi.mochimod.block;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.block.entity.AlchemyTableBlockEntity;
import com.mojang.datafixers.types.Type;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class ModBlockEntityType {
    public static final BlockEntityType<AlchemyTableBlockEntity> ALCHEMY_TABLE_BLOCK_ENTITY = create("alchemy_table",
            BlockEntityType.Builder.create(AlchemyTableBlockEntity::new,ModBlocks.ALCHEMY_TABLE));

    private static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType.Builder<T> builder) {

        Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
        return (BlockEntityType) Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MochiMod.MOD_ID,id), builder.build(type));
    }
    public static void init() {

    }
}
