package com.friskmochi.mochimod.block.screenhandler;

import com.friskmochi.mochimod.item.ModItems;
import com.friskmochi.mochimod.screenhandler.ModScreenHandlerType;
import com.friskmochi.mochimod.tag.ModItemTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class AlchemyTableScreenHandler extends ScreenHandler{
    private Inventory inventory;
    private PropertyDelegate delegate;
    public AlchemyTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(4), new ArrayPropertyDelegate(2));
    }

    public AlchemyTableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlerType.ALCHEMY_TABLE_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.delegate = delegate;

        //添加渊墨玉槽，使其只能放入燃料
        this.addSlot(new Slot(inventory, 0, 10, 18) {
            @Override
            public boolean canInsert(ItemStack stack) {
                // 定义可放入物品
                return stack.isOf(ModItems.ABYSS_DIP);
            }
        });
        //添加合成槽（左）
        this.addSlot(new Slot(inventory, 1,36,43));
        //添加合成槽（右）
        this.addSlot(new Slot(inventory, 2,85,43){
            @Override
            public boolean canInsert(ItemStack stack) {
                // 定义可放入物品（额其实这条的作用是不让放东西，属于投机取巧的小技巧哦）
                return stack.isOf(ModItems.BASIC_WAND);
            }
        });
        //添加产物槽
        this.addSlot(new Slot(inventory, 3,143,43) {
            @Override
            public boolean canInsert(ItemStack stack) {
                // 定义可放入物品（额其实这条的作用是不让放东西，属于投机取巧的小技巧哦）
                return stack.isOf(Items.AIR);
            }
        });

        //添加背包
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory,j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        //添加快捷键
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.addProperties(delegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot invSlot = this.slots.get(slot);
        if (invSlot != null && invSlot.hasStack()) {
            ItemStack originalStack = invSlot.getStack();
            newStack = originalStack.copy();
            if (slot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                invSlot.setStack(ItemStack.EMPTY);
            } else {
                invSlot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    public float getProgressRate(){
        float total = delegate.get(1);
        if(total != 0) {
            return delegate.get(0) / total;
        }
        return 0;
    }

}
