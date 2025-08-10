package com.friskmochi.mochimod.mixin.client;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.effect.ModStatusEffects;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {

    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "getSkinTextures", at = @At("RETURN"), cancellable = true)
    private void onGetSkinTextures(CallbackInfoReturnable<SkinTextures> cir) {
        if (this.hasStatusEffect(ModStatusEffects.MOCHI_SKIN)) {
            // 1. 获取原方法准备返回的皮肤对象，这是确定玩家模型的关键
            SkinTextures originalSkin = cir.getReturnValue();
            SkinTextures.Model model = originalSkin.model();

            // 2. 根据玩家的原始模型，选择正确的皮肤Identifier
            Identifier skinId;
            if (model == SkinTextures.Model.SLIM) {
                // 如果玩家是Slim模型，使用slim版本的皮肤
                skinId = Identifier.of(MochiMod.MOD_ID, "textures/skin/mochi_slim.png");
            } else {
                // 否则（即Classic模型），使用classic版本的皮肤
                skinId = Identifier.of(MochiMod.MOD_ID, "textures/skin/mochi_classic.png");
            }

            // 3. 使用选择好的皮肤ID和【原始模型】来创建新的皮肤对象
            SkinTextures customSkin = new SkinTextures(skinId, null, null, null, model, true);

            // 4. 设置新的皮肤对象为最终返回值
            cir.setReturnValue(customSkin);
        }
    }
}
