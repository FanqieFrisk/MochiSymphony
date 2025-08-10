package com.friskmochi.mochimod.block.entity;

import com.friskmochi.mochimod.block.ModBlockEntityType;
import com.friskmochi.mochimod.block.custom.AlchemyTableBlock;
import com.friskmochi.mochimod.block.screenhandler.AlchemyTableScreenHandler;
import com.friskmochi.mochimod.item.ModItems;
import com.friskmochi.mochimod.recipe.custom.AlchemyTableRecipe;
import com.friskmochi.mochimod.recipe.custom.ModRecipeType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AlchemyTableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, Inventory {

    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4,ItemStack.EMPTY);

    private int craftProgress;
    private int craftProgressTotal;
    private final RecipeManager.MatchGetter<SingleStackRecipeInput, AlchemyTableRecipe> matchGetter;

    private final PropertyDelegate delegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index){
                case 0 ->AlchemyTableBlockEntity.this.craftProgress;
                case 1 ->AlchemyTableBlockEntity.this.craftProgressTotal;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> AlchemyTableBlockEntity.this.craftProgress = value;
                case 1 -> AlchemyTableBlockEntity.this.craftProgressTotal = value;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("craftProgress", craftProgress);
        nbt.putInt("craftProgressTotal", craftProgressTotal);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        craftProgress = nbt.getInt("CraftProgress");
        craftProgressTotal = nbt.getInt("CraftProgressTotal");
    }

    public AlchemyTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.ALCHEMY_TABLE_BLOCK_ENTITY, pos, state);
        this.matchGetter = RecipeManager.createCachedMatchGetter(ModRecipeType.ALCHEMY_TABLE);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.alchemy_table_block.text");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new AlchemyTableScreenHandler(syncId, playerInventory, this, this.delegate);
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : inventory) {
            if(itemStack.isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.inventory, slot, amount);
        if(!itemStack.isEmpty()) {
            this.markDirty();
        }
        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        stack.capCount(this.getMaxCount(stack));
        if(slot == 1){
                craftProgress = 0;
                craftProgressTotal = 5 * 20;
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this,player);
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    private void tick(World world, BlockPos blockPos, BlockState blockstate) {
        ItemStack abyss_stack = this.inventory.get(0);
        ItemStack craftStack = this.inventory.get(1);
        ItemStack craftStack2 = this.inventory.get(2);
        ItemStack resultStack = this.inventory.get(3);

        boolean isCraftingNow = false; // 默认不亮

        RecipeEntry<AlchemyTableRecipe> entry = matchGetter.getFirstMatch(new SingleStackRecipeInput(craftStack), world).orElse(null);

        if(entry != null &&
                resultStack.isEmpty() &&
                !craftStack.isEmpty() && craftStack.getCount() >= 1 &&
                !craftStack2.isEmpty() && craftStack2.getCount() >= 1 &&
                !abyss_stack.isEmpty() && abyss_stack.getCount() >= 1) {

            isCraftingNow = true; // 开始工作

            craftProgressTotal = entry.value().getCraftTime();
            craftProgress++;

            if (craftProgress >= craftProgressTotal) {
                abyss_stack.decrement(1);
                craftStack.decrement(1);
                craftStack2.decrement(1);

                if (resultStack.isEmpty()) {
                    this.setStack(3, entry.value().craft(null, null));
                } else if (resultStack.isOf(entry.value().getResult(null).getItem())) {
                    resultStack.increment(1);
                }

                craftProgress = 0;
                markDirty();
            }

        } else {
            // 没在工作 → 清除进度（可选）
            if (craftProgress > 0) {
                craftProgress = 0;
                markDirty();
            }
        }

        // 🪄 【关键】每 tick 都更新 LIT 状态
        boolean currentlyLit = blockstate.get(AlchemyTableBlock.LIT);
        if (currentlyLit != isCraftingNow) {
            world.setBlockState(blockPos, blockstate.with(AlchemyTableBlock.LIT, isCraftingNow), Block.NOTIFY_ALL);
        }
    }


    public static void tick(World world, BlockPos blockPos, BlockState blockstate, AlchemyTableBlockEntity entity) {
        entity.tick(world, blockPos, blockstate);
    }


}
