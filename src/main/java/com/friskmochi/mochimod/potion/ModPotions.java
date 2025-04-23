package com.friskmochi.mochimod.potion;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.effect.ModStatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModPotions {

    public static final Potion LUCKY_GLOW_POTION = registerPotions("lucky_glow_potion", new Potion(
            new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(ModStatusEffects.LUCKY_GLOW), 3600)));


    private static Potion registerPotions(String name, Potion potion) {
        return Registry.register(Registries.POTION, Identifier.of(MochiMod.MOD_ID, name), potion);
    }

    public static void registerModPotions(){
        MochiMod.LOGGER.info("Registering Potions");
    }

}
