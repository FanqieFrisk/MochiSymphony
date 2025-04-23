package com.friskmochi.mochimod.effect;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.effect.custom.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {

    public static final StatusEffect EXP_ABSORPTION = registerStatusEffects("exp_absorption", new ExpAbsorptionStatusEffect());
    public static final StatusEffect LUCKY_GLOW = registerStatusEffects("lucky_glow", new LuckyGlowStatusEffect());

    private static StatusEffect registerStatusEffects(String id, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, Identifier.of(MochiMod.MOD_ID, id), statusEffect);
    }

    public static void registerModStatusEffects() {
        MochiMod.LOGGER.info("Registering Status Effects");
    }
}
