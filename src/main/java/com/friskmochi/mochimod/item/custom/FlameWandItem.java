package com.friskmochi.mochimod.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

import static com.friskmochi.mochimod.tag.ModItemTags.WAND_PROJECTILES;


public class FlameWandItem extends BowItem {

    public FlameWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.mochisymphony.flame_wand.tooltip.line1"));
        tooltip.add(Text.translatable("item.mochisymphony.flame_wand.tooltip.line2"));
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return (stack) -> stack.isIn(WAND_PROJECTILES);
    }


    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            ItemStack itemStack = player.getProjectileType(stack);
            if (!itemStack.isEmpty()) {
                int useTime = this.getMaxUseTime(stack, user) - remainingUseTicks;
                float pullProgress = getPullProgress(useTime);
                if (pullProgress < 0.1F) {
                    return;
                }
                if (!world.isClient) {
                    // 获取玩家视线方向向量
                    Vec3d look = player.getRotationVec(1.0F);
                    // 根据拉弓进度计算火焰弹发射速度
                    Vec3d velocity = look.multiply(pullProgress * 3.0F);
                    // 创建火焰弹实体：构造函数参数为 (World, double x, double y, double z, Vec3d velocity)
                    SmallFireballEntity fireball = new SmallFireballEntity(
                            world,
                            player.getX(),
                            player.getEyeY(),
                            player.getZ(),
                            velocity
                    );
                    world.spawnEntity(fireball);
                }
                // 播放火焰弹发射音效
                world.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.ITEM_FIRECHARGE_USE,
                        SoundCategory.PLAYERS,
                        1.0F,
                        1.0F
                );
                // 更新玩家使用该物品的统计数据
                player.incrementStat(Stats.USED.getOrCreateStat(this));

                // 消耗一个FLAME_CRYSTALLINE
                itemStack.decrement(1);
            }
        }
    }
}