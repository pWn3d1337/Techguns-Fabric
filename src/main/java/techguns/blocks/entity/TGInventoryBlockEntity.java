package techguns.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import techguns.inventory.BaseInventory;
import techguns.inventory.ScreenHandlerFactory;

public class TGInventoryBlockEntity extends TGScreenBlockEntity implements BaseInventory {
    protected final DefaultedList<ItemStack> inventory;

    public TGInventoryBlockEntity(BlockEntityType type, BlockPos pos, BlockState state, int inventorySize, ScreenHandlerFactory screenHandler) {
        super(type, pos, state, screenHandler);
        this.inventory = DefaultedList.ofSize(inventorySize, ItemStack.EMPTY);
    }

    @Override
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        Inventories.readNbt(tag, inventory);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        Inventories.writeNbt(tag, inventory);
        return super.writeNbt(tag);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return this.screenHandler.create(syncId, playerInventory, this);
    }
}
