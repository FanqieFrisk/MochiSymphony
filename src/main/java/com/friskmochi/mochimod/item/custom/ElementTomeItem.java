package com.friskmochi.mochimod.item.custom;

import com.friskmochi.mochimod.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ElementTomeItem extends Item {
    public ElementTomeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();

        if (!world.isClient()) {
            boolean foundBlock = false;
            if (!Screen.hasShiftDown()) {
                // 实现模糊搜索
                for (int i = 0; i <= pos.getY() + 64; i++) {
                    for (int dx = -5; dx <= 5; dx++) {
                        for (int dz = -5; dz <= 5; dz++) {
                            BlockPos pos1 = pos.down(i).add(dx, 0, dz);

                            BlockState blockState = world.getBlockState(pos1);
                            String name = blockState.getBlock().getName().getString();

                            if (isRightBlock(blockState)) {
                                player.sendMessage(Text.of("【§e模糊搜索§r】成功在11*11的范围内发现 §d" + name + "§r！"), true);
                                foundBlock = true;
                                world.playSound(
                                        null, // null = 所有附近玩家都能听见
                                        pos1, // 声音来源位置
                                        SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,
                                        SoundCategory.PLAYERS,
                                        1.0f, // 音量
                                        1.0f  // 音调
                                );


                                break;
                            }
                        }
                    }
                }
                if (!foundBlock) {
                    player.sendMessage(Text.of("【§e模糊搜索§r】在11*11的范围内未发现元素矿！"), true);
                }
            } else {
                // 实现精确搜索
                for (int i = 0; i <= pos.getY() + 64; i++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dz = -1; dz <= 1; dz++) {
                            BlockPos pos1 = pos.down(i).add(dx, 0, dz);
                            BlockState blockState = world.getBlockState(pos1);
                            String name = blockState.getBlock().getName().getString();

                            if (isRightBlock(blockState)) {
                                player.sendMessage(Text.of("【§a精确搜索§r】成功在3*3的范围内发现 §d" + name + "§r！"), true);
                                world.playSound(
                                        null, // null = 所有附近玩家都能听见
                                        pos1, // 声音来源位置
                                        SoundEvents.ENTITY_ENDER_EYE_DEATH,
                                        SoundCategory.PLAYERS,
                                        1.0f, // 音量
                                        1.0f
                                );

                                foundBlock = true;
                                break;
                            }
                        }
                        if (foundBlock) break;
                    }
                    if (foundBlock) break;
                }

                if (!foundBlock) {
                    player.sendMessage(Text.of("【§a精确搜索§r】在3*3的范围内未发现元素矿！"), true);
                }
            }
            context.getStack().damage(1, player, EquipmentSlot.MAINHAND);
            return ActionResult.SUCCESS;
        }
        return super.useOnBlock(context);
    }

    private boolean isRightBlock(BlockState blockState) {
        if (blockState.isIn(ModBlockTags.ELEMENTS)) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.mochisymphony.element_tome.shift_tooltip"));
        } else {
            tooltip.add(Text.translatable("item.mochisymphony.element_tome.tooltip"));
        }
    }
}