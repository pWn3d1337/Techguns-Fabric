package techguns.inventory;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.api.ICamoChangeable;

import java.util.List;

public class CamoBenchScreenHandler extends TGBaseScreenHandler {
    protected final ScreenHandlerContext context;
    protected final Property selectedRecipe;
    protected final World world;
    protected List<StonecuttingRecipe> availableRecipes;
    protected ItemStack inputStack;
    protected long lastTakeTime;
    protected final Slot inputSlot;
    protected final Slot outputSlot;
    protected Runnable contentsChangedListener;
    public final Inventory input;
    protected final CraftingResultInventory output;

    public CamoBenchScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public CamoBenchScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
        super(TGBlocks.CAMO_BENCH_SCREEN_HANDLER, syncId);

        this.selectedRecipe = Property.create();
        this.availableRecipes = Lists.newArrayList();
        this.inputStack = ItemStack.EMPTY;
        this.contentsChangedListener = () -> {
        };
        this.input = new SimpleInventory(1) {
            public void markDirty() {
                super.markDirty();
                CamoBenchScreenHandler.this.onContentChanged(this);
                CamoBenchScreenHandler.this.contentsChangedListener.run();
            }
        };
        this.output = new CraftingResultInventory();
        this.context = context;
        this.world = playerInventory.player.world;
        this.inputSlot = this.addSlot(new Slot(this.input, 0, 20, 33));
        this.outputSlot = this.addSlot(new Slot(this.output, 1, 143, 33) {
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
                stack.onCraft(player.world, player, stack.getCount());
                CamoBenchScreenHandler.this.output.unlockLastRecipe(player);
                ItemStack itemStack = CamoBenchScreenHandler.this.inputSlot.takeStack(1);
                if (!itemStack.isEmpty()) {
                    CamoBenchScreenHandler.this.populateResult();
                }

                context.run((world, blockPos) -> {
                    long l = world.getTime();
                    if (CamoBenchScreenHandler.this.lastTakeTime != l) {
                        world.playSound((PlayerEntity)null, blockPos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        CamoBenchScreenHandler.this.lastTakeTime = l;
                    }

                });
                return super.onTakeItem(player, stack);
            }
        });

        //The player inventory
        addSlotGrid(playerInventory, 9, 9, 3, 8, 84);
        //The player Hotbar
        addSlotGrid(playerInventory, 0, 9, 1, 8, 142);

        this.addProperty(this.selectedRecipe);

    }

    protected boolean selctionWithinBounds(int i) {
        return i >= 0 && i < this.availableRecipes.size();
    }

    protected void populateResult() {
        boolean hasCamoChangeableItem = this.inputSlot.hasStack() && this.inputSlot.getStack().getItem() instanceof ICamoChangeable;
        if (hasCamoChangeableItem && this.selctionWithinBounds(this.selectedRecipe.get())) {
            /*StonecuttingRecipe stonecuttingRecipe = (StonecuttingRecipe)this.availableRecipes.get(this.selectedRecipe.get());
            this.output.setLastRecipe(stonecuttingRecipe);
            this.outputSlot.setStack(stonecuttingRecipe.craft(this.input));*/



        } else {
            this.outputSlot.setStack(ItemStack.EMPTY);
        }

        this.sendContentUpdates();
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (this.selctionWithinBounds(id)) {
            this.selectedRecipe.set(id);
            this.populateResult();
        }

        return true;
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        ItemStack itemStack = this.inputSlot.getStack();
        if (itemStack.getItem() != this.inputStack.getItem()) {
            this.inputStack = itemStack.copy();
            this.updateInput(inventory, itemStack);
        }
    }

    protected void updateInput(Inventory input, ItemStack stack) {
        this.availableRecipes.clear();
        this.selectedRecipe.set(-1);
        this.outputSlot.setStack(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            this.availableRecipes = this.world.getRecipeManager().getAllMatches(RecipeType.STONECUTTING, input, this.world);
        }

    }

    @Override
    public ScreenHandlerType<?> getType() {
        return TGBlocks.CAMO_BENCH_SCREEN_HANDLER;
    }

    @Environment(EnvType.CLIENT)
    public void setContentsChangedListener(Runnable runnable) {
        this.contentsChangedListener = runnable;
    }

    @Environment(EnvType.CLIENT)
    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    @Environment(EnvType.CLIENT)
    public List<StonecuttingRecipe> getAvailableRecipes() {
        return this.availableRecipes;
    }

    @Environment(EnvType.CLIENT)
    public int getAvailableRecipeCount() {
        return this.availableRecipes.size();
    }

    @Environment(EnvType.CLIENT)
    public boolean canCraft() {
        return this.inputSlot.hasStack() && !this.availableRecipes.isEmpty();
    }

    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (index == 1) {
                item.onCraft(itemStack2, player.world, player);
                if (!this.insertItem(itemStack2, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onStackChanged(itemStack2, itemStack);
            } else if (index == 0) {
                if (!this.insertItem(itemStack2, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.world.getRecipeManager().getFirstMatch(RecipeType.STONECUTTING, new SimpleInventory(new ItemStack[]{itemStack2}), this.world).isPresent()) {
                if (!this.insertItem(itemStack2, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 2 && index < 29) {
                if (!this.insertItem(itemStack2, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 29 && index < 38 && !this.insertItem(itemStack2, 2, 29, false)) {
                return ItemStack.EMPTY;
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

    public void close(PlayerEntity player) {
        super.close(player);
        this.output.removeStack(1);
        this.context.run((world, blockPos) -> {
            this.dropInventory(player, player.world, this.input);
        });
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
