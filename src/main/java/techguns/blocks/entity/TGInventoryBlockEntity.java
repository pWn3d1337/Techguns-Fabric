package techguns.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;
import techguns.inventory.BaseInventory;
import techguns.inventory.ScreenHandlerFactory;

public class TGInventoryBlockEntity extends TGScreenBlockEntity implements BaseInventory {
    protected final DefaultedList<ItemStack> inventory;

    public TGInventoryBlockEntity(BlockEntityType type, int inventorySize, ScreenHandlerFactory screenHandler) {
        super(type, screenHandler);
        this.inventory = DefaultedList.ofSize(inventorySize, ItemStack.EMPTY);
    }

    @Override
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag, inventory);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag, inventory);
        return super.toTag(tag);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return this.screenHandler.create(syncId, playerInventory, this);
    }
}
