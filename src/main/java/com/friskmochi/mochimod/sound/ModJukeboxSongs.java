package com.friskmochi.mochimod.sound;

import com.friskmochi.mochimod.MochiMod;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public interface ModJukeboxSongs {

    RegistryKey<JukeboxSong> QRRS = of("qrrs");
    RegistryKey<JukeboxSong> AIR = of("air");
    RegistryKey<JukeboxSong> UR = of("ur");

    private static RegistryKey<JukeboxSong> of(String id) {
        return RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(MochiMod.MOD_ID, id));
    }
    private static void register(
            Registerable<JukeboxSong> registry, RegistryKey<JukeboxSong> key, RegistryEntry.Reference<SoundEvent> soundEvent, int lengthInSeconds, int comparatorOutput
    ) {
        registry.register(
                key, new JukeboxSong(soundEvent, Text.translatable(Util.createTranslationKey("jukebox_song", key.getValue())), (float)lengthInSeconds, comparatorOutput)
        );
    }

    static void bootstrap(Registerable<JukeboxSong> registry) {
        register(registry, QRRS, ModSoundEvents.MUSIC_DISC_QRRS, 109, 7);
        register(registry, AIR, ModSoundEvents.MUSIC_DISC_AIR, 197, 7);
        register(registry, UR, ModSoundEvents.MUSIC_DISC_UR, 147, 9);
    }
}
