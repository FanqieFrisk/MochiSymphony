package com.friskmochi.mochimod.item;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.effect.ModStatusEffects;
import com.friskmochi.mochimod.effect.custom.LuckyGlowStatusEffect;
import com.friskmochi.mochimod.entity.ModEntities;
import com.friskmochi.mochimod.item.custom.*;
import com.friskmochi.mochimod.potion.ModPotions;
import com.friskmochi.mochimod.sound.ModJukeboxSongs;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static net.minecraft.item.Items.register;

public class ModItems {
    public static final Item GROUP_ICON = registerItems("group_icon", new Item(new Item.Settings()));

    public static final Item RAIN_CRYSTAL = registerItems("rain_crystal", new Item(new Item.Settings()));
    public static final Item RAIN_INGOT = registerItems("rain_ingot", new Item(new Item.Settings()));

    public static final Item ELECTRIC_EXTRACT = registerItems("electric_extract",new Item(new Item.Settings()));
    public static final Item ELECTRIC_INGOT = registerItems("electric_ingot",new Item(new Item.Settings()));

    public static final Item HERBA_NUGGET = registerItems("herba_nugget",new Item(new Item.Settings()));
    public static final Item HERBA_INGOT = registerItems("herba_ingot",new Item(new Item.Settings()));

    public static final Item FLAME_CRYSTALLINE = registerItems("flame_crystalline",new Item(new Item.Settings()));
    public static final Item FLAME_INGOT = registerItems("flame_ingot",new Item(new Item.Settings()));

    public static final Item ECLIPSE_ETHER = registerItems("eclipse_ether",new Item(new Item.Settings()));
    public static final Item TENEBRAE_ETHER = registerItems("tenebrae_ether",new Item(new Item.Settings()));
    public static final Item ABYSS_DIP = registerItems("abyss_dip",new Item(new Item.Settings()));
    public static final Item ABYSS_INGOT = registerItems("abyss_ingot",new Item(new Item.Settings()));

    public static final Item WEATHERFORGED_OMNISTRUCT_INGOT = registerItems("weatherforged_omnistruct_ingot",
            new WOIngot(new Item.Settings().rarity(Rarity.UNCOMMON)));

    public static final Item MUSIC_DISC_QRRS = registerItems("music_disc_qrrs",
            new Item(new Item.Settings().maxCount(1).rarity(Rarity.EPIC).jukeboxPlayable(ModJukeboxSongs.QRRS)));
    public static final Item MUSIC_DISC_AIR = registerItems("music_disc_air",
            new Item(new Item.Settings().maxCount(1).rarity(Rarity.EPIC).jukeboxPlayable(ModJukeboxSongs.AIR)));
    public static final Item MUSIC_DISC_UR = registerItems("music_disc_ur",
            new Item(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).jukeboxPlayable(ModJukeboxSongs.UR)));


    public static final Item RAIN_SWORD = registerItems("rain_sword",
            new SwordItem(ModToolMaterials.RAIN,new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers
                    (ModToolMaterials.RAIN,8,1f))));
    public static final Item RAIN_AXE = registerItems("rain_axe",
            new AxeItem(ModToolMaterials.RAIN,new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers
                    (ModToolMaterials.RAIN,10,-3.0f))));
    public static final Item RAIN_PICKAXE = registerItems("rain_pickaxe",
            new PickaxeItem(ModToolMaterials.RAIN,new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers
                    (ModToolMaterials.RAIN,5,-1.0f))));
    public static final Item RAIN_SHOVEL = registerItems("rain_shovel",
            new ShovelItem(ModToolMaterials.RAIN,new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers
                    (ModToolMaterials.RAIN,4,-1.0f))));
    public static final Item RAIN_HOE = registerItems("rain_hoe",
            new HoeItem(ModToolMaterials.RAIN,new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers
                    (ModToolMaterials.RAIN,3,-1.0f))));

    public static final Item FLAME_WAND = registerItems("flame_wand",
            new FlameWandItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));

//    public static final Item ELECTRIC_SHIELD = registerItems("electric_shield",
//            new ShieldItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE)));

    public static final Item RAIN_HELMET = registerItems("rain_helmet",
            new ModRainArmorItem(ModArmorMaterials.RAIN, ArmorItem.Type.HELMET,
                    new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(35))));
    public static final Item RAIN_CHESTPLATE = registerItems("rain_chestplate",
            new ModRainArmorItem(ModArmorMaterials.RAIN, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(35))));
    public static final Item RAIN_LEGGINGS = registerItems("rain_leggings",
            new ModRainArmorItem(ModArmorMaterials.RAIN, ArmorItem.Type.LEGGINGS,
                    new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(35))));
    public static final Item RAIN_BOOTS = registerItems("rain_boots",
            new ModRainArmorItem(ModArmorMaterials.RAIN, ArmorItem.Type.BOOTS,
                    new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(35))));

    public static final Item RAIN_ELF_SPAWN_EGG = registerItems("rain_elf_spawn_egg",
            new SpawnEggItem(ModEntities.RAIN_ELF,0x00FFE5,0x004989, new Item.Settings()));

    public static final Item MOCHI = registerItems("mochi",new Item(new Item.Settings().food(ModFoodComponents.MOCHI)));
    public static final Item STRAWBERRY = registerItems("strawberry",new Item(new Item.Settings().food(ModFoodComponents.STRAWBERRY)));
    public static final Item HAWTHORN = registerItems("hawthorn",new Item(new Item.Settings().food(ModFoodComponents.HAWTHORN)));
    public static final Item CANDIED_HAWTHORN = registerItems("candied_hawthorn",new Item(new Item.Settings().food(ModFoodComponents.CANDIED_HAWTHORN)));
    public static final Item TANGHULU = registerItems("tanghulu", new TanghuluItem(new Item.Settings()
            .attributeModifiers(SwordItem.createAttributeModifiers
                    (ModToolMaterials.HAWTHORN,3,1f))
            .food(ModFoodComponents.TANGHULU)));

    public static final Item ELEMENT_TOME = registerItems("element_tome",new ElementTomeItem(new Item.Settings()
            .maxDamage(7777).maxCount(1).rarity(Rarity.UNCOMMON).fireproof()));
    public static final Item ORE_TOME = registerItems("ore_tome",new OreTomeItem(new Item.Settings()
            .maxDamage(7777).maxCount(1).rarity(Rarity.EPIC).fireproof()));

//    public static final Item LUCKY_GLOW_POTION_ITEM = registerItems("lucky_glow_potion",
//            new PotionItem(new Item.Settings().maxCount(1)));

    private static Item registerItems(String id,Item item){
        return Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), Identifier.of(MochiMod.MOD_ID,id)),item);
    }

    private static void addItemToLG(FabricItemGroupEntries fabricItemGroupEntries) {
    }


    public static void registerModItems(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemToLG);
        MochiMod.LOGGER.info("Registering Items");
    }


}


