package techguns.inventory;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import techguns.TGBlocks;
import techguns.inventory.slots.PlayerArmorSlot;

public class CamoBenchScreenHandlerOld extends ScreenHandler {
    protected final Inventory inventory;

    public CamoBenchScreenHandlerOld(int syncId, PlayerInventory playerInventory){
        this(syncId, playerInventory, new SimpleInventory(1));//CamoBenchBlockEntity.INVENTORY_SIZE));
    }

    protected void addSlotGrid(Inventory inv, int idStart, int numX, int numY, int startX, int startY) {
        addSlotGrid(inv, idStart, numX, numY, startX, startY, 18, 18);
    }

    protected void addSlotGrid(Inventory inv, int idStart, int numX, int numY, int startX, int startY, int spacingX, int spacingY) {
        for (int y = 0; y < numY; ++y) {
            for (int x = 0; x < numX; ++x) {
                int slotId = idStart + x + y * numX;
                this.addSlot(new Slot(inv, slotId, startX + x * spacingX, startY + y * spacingY));
            }
        }
    }

    public CamoBenchScreenHandlerOld(int syncId, PlayerInventory playerInventory, Inventory inventory){
        super(TGBlocks.CAMO_BENCH_SCREEN_HANDLER, syncId);
        checkSize(inventory, 1);//CamoBenchBlockEntity.INVENTORY_SIZE);
        this.inventory = inventory;

        inventory.onOpen(playerInventory.player);

        //Camo Bench inventory
        this.addSlot(new Slot(inventory, 0, 17, 18));

        //player Armor Slots
        EquipmentSlot[] slots = {EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD};
        for (int i=0; i<4; i++){
            int j=3-i;
            this.addSlot(new PlayerArmorSlot(playerInventory, playerInventory.main.size()+i, 99+18*j, 18, slots[i]));
        }
        //The player inventory
        addSlotGrid(playerInventory, 9, 9, 3, 8, 84);
        //The player Hotbar
        addSlotGrid(playerInventory, 0, 9, 1, 8, 142);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }
}
