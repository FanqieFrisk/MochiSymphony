package com.friskmochi.mochimod.sound;

import com.friskmochi.mochimod.MochiMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {

    public static final RegistryEntry.Reference<SoundEvent> MUSIC_DISC_QRRS = registerReference("music_disc.qrrs");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_DISC_UR = registerReference("music_disc.ur");
    public static final RegistryEntry.Reference<SoundEvent> MUSIC_DISC_AIR = registerReference("music_disc.air");

    private static SoundEvent register(String name) {
        Identifier id = Identifier.of(MochiMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    private static RegistryEntry.Reference<SoundEvent> registerReference(String name) {
        Identifier id = Identifier.of(MochiMod.MOD_ID, name);
        return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSoundEvents() {

    }
}