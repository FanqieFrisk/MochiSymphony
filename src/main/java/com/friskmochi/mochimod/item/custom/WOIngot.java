package com.friskmochi.mochimod.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class WOIngot extends Item {


    public WOIngot(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.mochisymphony.weatherforged_omnistruct_ingot.tooltip_line1"));
        tooltip.add(Text.translatable("item.mochisymphony.weatherforged_omnistruct_ingot.tooltip_line2"));
    }
}
