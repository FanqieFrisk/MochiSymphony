package com.friskmochi.mochimod.entity.custom;

import com.friskmochi.mochimod.entity.ModEntities;
import com.friskmochi.mochimod.item.ModItems;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RainElfEntity extends AnimalEntity {
    public static final AnimationState idleAnimation = new AnimationState();
    public int idleAnimationTimeout = 0;

    public RainElfEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
    private void setupAnimation() {
        if (idleAnimationTimeout <=0) {
            idleAnimationTimeout = this.random.nextInt(40) + 80;
            idleAnimation.start(this.age);
        } else {
            idleAnimationTimeout--;
        }
    }
//不想做挂机动画。

    @Override
    public void tick() {
        super.tick();
        setupAnimation();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0,new SwimGoal(this));
        this.goalSelector.add(1,new AnimalMateGoal(this,1.0D));
        this.goalSelector.add(2,new TemptGoal(this,1.25D, Ingredient.ofItems(ModItems.RAIN_CRYSTAL),false));
        this.goalSelector.add(3,new FollowParentGoal(this,1.25D));
        this.goalSelector.add(4,new WanderAroundGoal(this,1.0D));
        this.goalSelector.add(5,new LookAtEntityGoal(this, PlayerEntity.class,3.0f));
        this.goalSelector.add(6,new LookAroundGoal(this));
    }
    public static DefaultAttributeContainer.Builder createRainElfAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH,10)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.3F)
                .add(EntityAttributes.GENERIC_ARMOR,4)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,3);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ModItems.RAIN_CRYSTAL);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.RAIN_ELF.create(world);
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f,1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f,0.2f);
    }
}
