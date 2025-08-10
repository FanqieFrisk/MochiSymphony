package com.friskmochi.mochimod.client.block.screen;

import com.friskmochi.mochimod.MochiMod;
import com.friskmochi.mochimod.block.screenhandler.AlchemyTableScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;


public class AlchemyTableScreen extends HandledScreen<AlchemyTableScreenHandler> {
    private static final Identifier GUI = Identifier.of(MochiMod.MOD_ID,"textures/gui/alchemy_table.png");
    public AlchemyTableScreen(AlchemyTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(GUI,x,y,0,0,backgroundWidth,backgroundHeight);
        context.drawTexture(GUI,x + 111,y + 44,176,10,(int) (handler.getProgressRate() * 22),15);
    }//画背景和进度条

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xC0101010); // 半透明黑色背景 // 背景
        super.render(context, mouseX, mouseY, delta); // 这行会处理物品渲染和 tooltip 检查
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }//画物品详细信息


}
