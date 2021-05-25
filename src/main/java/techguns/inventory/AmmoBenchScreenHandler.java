package techguns.inventory;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import techguns.TGBlocks;
import techguns.TGItems;
import techguns.TGRecipes;
import techguns.api.ICamoChangeable;
import techguns.inventory.slots.SlotTagItem;
import techguns.recipes.AmmoBenchRecipe;

public class AmmoBenchScreenHandler extends StoneCutterStyleScreenHandler<AmmoBenchRecipe> {

    protected ItemStack inputStack1;
    protected ItemStack inputStack2;

    protected Slot inputSlot1 = null;
    protected Slot inputSlot2 = null;

    public AmmoBenchScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public AmmoBenchScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(TGBlocks.AMMO_BENCH_SCREEN_HANDLER, syncId, playerInventory, context);

        this.inputStack1 = ItemStack.EMPTY;
        this.inputStack2 = ItemStack.EMPTY;

        this.input = new SimpleInventory(3) {
            public void markDirty() {
                super.markDirty();
                AmmoBenchScreenHandler.this.onContentChanged(this);
                AmmoBenchScreenHandler.this.contentsChangedListener.run();
            }
        };
        this.output = new CraftingResultInventory();

        //Input Slots
        this.inputSlot = this.addSlot(new SlotTagItem(TGItems.TAG_BULLET_CORE, this.input, 0, 20, 15));
        this.inputSlot1 = this.addSlot(new SlotTagItem(TGItems.TAG_BULLET_CASING, this.input, 1, 20, 34));
        this.inputSlot2 = this.addSlot(new SlotTagItem(TGItems.TAG_BULLET_POWDER, this.input, 2, 20, 53));

        this.outputSlot = this.addSlot(new Slot(this.output, 1, 143, 33) {
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
                stack.onCraft(player.world, player, stack.getCount());

                if (!AmmoBenchScreenHandler.this.inputSlot.getStack().isEmpty() &&
                        !AmmoBenchScreenHandler.this.inputSlot1.getStack().isEmpty() &&
                        !AmmoBenchScreenHandler.this.inputSlot2.getStack().isEmpty()){

                    AmmoBenchScreenHandler.this.inputSlot.takeStack(1);
                    AmmoBenchScreenHandler.this.inputSlot1.takeStack(1);
                    AmmoBenchScreenHandler.this.inputSlot2.takeStack(1);

                    AmmoBenchScreenHandler.this.populateResult();
                }

                context.run((world, blockPos) -> {
                    long l = world.getTime();
                    if (AmmoBenchScreenHandler.this.lastTakeTime != l) {
                        world.playSound((PlayerEntity)null, blockPos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        AmmoBenchScreenHandler.this.lastTakeTime = l;
                    }

                });
                return super.onTakeItem(player, stack);
            }
        });

        this.addPlayerInventorySlots(playerInventory);
    }

    @Override
    protected void populateResult() {
        if (!this.availableRecipes.isEmpty() && this.selctionWithinBounds(this.selectedRecipe.get())) {
            AmmoBenchRecipe recipe = this.availableRecipes.get(this.selectedRecipe.get());
            this.output.setLastRecipe(recipe);
            this.outputSlot.setStack(recipe.craft(this.input));
        } else {
            this.outputSlot.setStack(ItemStack.EMPTY);
        }
        this.sendContentUpdates();
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        ItemStack itemStack = this.inputSlot.getStack();
        ItemStack itemStack1 = this.inputSlot1.getStack();
        ItemStack itemStack2 = this.inputSlot2.getStack();

        if (itemStack.getItem() != this.inputStack.getItem() ||
                itemStack1.getItem() != this.inputStack1.getItem() ||
                itemStack2.getItem() != this.inputStack2.getItem()) {

            this.inputStack = itemStack.copy();
            this.inputStack1 = itemStack1.copy();
            this.inputStack2 = itemStack2.copy();
            this.updateInput(inventory, itemStack, itemStack1, itemStack2);
        }

    }

    protected void updateInput(Inventory input, ItemStack stack, ItemStack stack1, ItemStack stack2) {
        this.availableRecipes.clear();
        this.selectedRecipe.set(-1);
        this.outputSlot.setStack(ItemStack.EMPTY);
        //Check if all input slots are filled
        if (!stack.isEmpty() && !stack1.isEmpty() && !stack2.isEmpty()) {
            this.availableRecipes = this.world.getRecipeManager().getAllMatches(TGRecipes.AMMOBENCH_RECIPE_TYPE, input, this.world);
        }
    }

    @Environment(EnvType.CLIENT)
    public boolean canCraft() {
        return !this.availableRecipes.isEmpty() &&
                this.inputSlot.hasStack() &&
                this.inputSlot1.hasStack() &&
                this.inputSlot2.hasStack();
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.output.removeStack(0);
        this.context.run((world, blockPos) -> {
            this.dropInventory(player, player.world, this.input);
        });
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        final int OUTPUT_SLOT=3;
        final int PLAYER_INV_START = OUTPUT_SLOT+1;
        final int PLAYER_INV_END = PLAYER_INV_START+27;
        final int INV_END = PLAYER_INV_END+9;

        int x = INV_END;

        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();

            if (index == OUTPUT_SLOT) {
                item.onCraft(itemStack2, player.world, player);
                if (!this.insertItem(itemStack2, PLAYER_INV_START, INV_END, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onStackChanged(itemStack2, itemStack);
            } else if (index >= 0 && index < OUTPUT_SLOT) {
                if (!this.insertItem(itemStack2, PLAYER_INV_START, INV_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (TGItems.TAG_BULLET_CORE.contains(itemStack2.getItem())){
                if (!this.insertItem(itemStack2, 0, 1, false)){
                    return ItemStack.EMPTY;
                }
            } else if (TGItems.TAG_BULLET_CASING.contains(itemStack2.getItem())){
                if (!this.insertItem(itemStack2, 1, 2, false)){
                    return ItemStack.EMPTY;
                }
            } else if (TGItems.TAG_BULLET_POWDER.contains(itemStack2.getItem())){
                if (!this.insertItem(itemStack2, 2, 3, false)){
                    return ItemStack.EMPTY;
                }
            } else {
                if (index >= PLAYER_INV_START && index < PLAYER_INV_END) {
                    if (!this.insertItem(itemStack2, PLAYER_INV_END, INV_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= PLAYER_INV_END && index < INV_END && !this.insertItem(itemStack2, PLAYER_INV_START, PLAYER_INV_END, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            }

            slot.markDirty();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
            this.sendContentUpdates();
        }

        return itemStack;
    }

}
