package com.friskmochi.mochimod.entity.client;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.entity.custom.RainElfEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RainElfRenderer extends MobEntityRenderer<RainElfEntity, RainElfModel<RainElfEntity>> {
    public static final Identifier TEXTURE = Identifier.of(MochiMod.MOD_ID,"textures/entity/rain_elf/rain_elf.png");

    public RainElfRenderer(EntityRendererFactory.Context context) {
        super(context, new RainElfModel<>(context.getPart(ModModelLayers.RAIN_ELF)),0.5f);
    }

    @Override
    public Identifier getTexture(RainElfEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(RainElfEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (livingEntity.isBaby()) {
            matrixStack.scale(0.5F,0.5F,0.5F);
        } else {
            matrixStack.scale(1.0F,1.0F,1.0F);
        }
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
