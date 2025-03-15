// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.friskmochi.mochimod.entity.client;

import com.friskmochi.mochimod.entity.animation.RainElfAnimation;
import com.friskmochi.mochimod.entity.custom.RainElfEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class RainElfModel<RE extends RainElfEntity> extends SinglePartEntityModel<RE> {
    private final ModelPart rain_elf;

    private final ModelPart body;

    public RainElfModel(ModelPart root) {
        this.rain_elf = root.getChild("rain_elf");
        this.body = rain_elf.getChild("body");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData tiger = modelPartData.addChild("rain_elf", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData hand = tiger.addChild("hand", ModelPartBuilder.create().uv(0, 14).cuboid(4.0F, -7.0F, 4.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 14).cuboid(3.0F, -13.0F, -6.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 14).cuboid(-6.0F, -9.0F, 5.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 14).cuboid(-7.0F, -8.0F, -5.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = tiger.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -11.0F, -3.0F, 7.0F, 7.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.rain_elf;
    }

    @Override
    public void setAngles(RE entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
//        this.setHeadAngles(headYaw, headPitch);

        this.animateMovement(RainElfAnimation.SPIN, limbAngle, limbDistance,2f,2.5f);

    }

//    private void setHeadAngles(float headYaw, float headPitch) {
//        headYaw = MathHelper.clamp(headYaw,-30.0f,30.0f);
//        headPitch = MathHelper.clamp(headPitch,-25.0f,45.0f);
//        this.head.yaw = headYaw * 0.014753292f;
//        this.head.pitch = headPitch * 0.014753292f;
//    }
//    当然，目前因为并没有head，所以就先注释掉啦

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        rain_elf.render(matrices, vertices, light, overlay, color);
    }
}