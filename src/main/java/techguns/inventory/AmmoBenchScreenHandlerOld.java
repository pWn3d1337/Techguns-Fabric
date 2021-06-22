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
import net.minecraft.tag.Tag;
import techguns.TGBlocks;
import techguns.TGItems;
import techguns.TGPacketsC2S;
import techguns.TGRecipes;
import techguns.blocks.entity.AmmoBenchBlockEntity;
import techguns.inventory.slots.SlotTagItem;
import techguns.packets.PacketCraftAmmoBench;
import techguns.recipes.AmmoBenchRecipe;

public class AmmoBenchScreenHandlerOld extends StoneCutterStyleScreenHandler<AmmoBenchRecipe> {

    protected final Inventory inventory;

    public AmmoBenchScreenHandlerOld(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY, new SimpleInventory(AmmoBenchBlockEntity.INVENTORY_SIZE));
    }

    protected class AmmoBenchInputSlot extends SlotTagItem {

        public AmmoBenchInputSlot(Tag<Item> tag, Inventory inventory, int index, int x, int y) {
            super(tag, inventory, index, x, y);
        }

        @Override
        public void markDirty() {
            super.markDirty();
            AmmoBenchScreenHandlerOld.this.onContentChanged(AmmoBenchScreenHandlerOld.this.inventory);
            AmmoBenchScreenHandlerOld.this.contentsChangedListener.run();
        }
    }

    public AmmoBenchScreenHandlerOld(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, Inventory blockEntityInventory) {
        super(TGBlocks.AMMO_BENCH_SCREEN_HANDLER, syncId, playerInventory, context);
        this.inventory = blockEntityInventory;

        checkSize(this.inventory, AmmoBenchBlockEntity.INVENTORY_SIZE);//CamoBenchBlockEntity.INVENTORY_SIZE);

        inventory.onOpen(playerInventory.player);

        this.output = new CraftingResultInventory();

        //Camo Bench inventory
        this.addSlot(new AmmoBenchInputSlot(TGItems.TAG_BULLET_CORE, inventory, 0, 20, 15));
        this.addSlot(new AmmoBenchInputSlot(TGItems.TAG_BULLET_CASING, inventory, 1, 20, 34));
        this.addSlot(new AmmoBenchInputSlot(TGItems.TAG_BULLET_POWDER, inventory, 2, 20, 53));

        this.outputSlot = this.addSlot(new Slot(this.output, 0, 143, 33) {
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                stack.onCraft(player.world, player, stack.getCount());

                System.out.println("Taking From Slot");

                for (int i = 0; i< AmmoBenchBlockEntity.INVENTORY_SIZE; i++){
                    AmmoBenchScreenHandlerOld.this.inventory.getStack(i).decrement(1);
                }

                boolean empty=false;
                for (int i = 0; i< AmmoBenchBlockEntity.INVENTORY_SIZE; i++){
                    if (AmmoBenchScreenHandlerOld.this.inventory.getStack(i).isEmpty()){
                        empty=true;
                    }
                }
                if (!empty) {
                    AmmoBenchScreenHandlerOld.this.populateResult();
                }

                context.run((world, blockPos) -> {
                    long l = world.getTime();
                    if (AmmoBenchScreenHandlerOld.this.lastTakeTime != l) {
                        world.playSound((PlayerEntity)null, blockPos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        AmmoBenchScreenHandlerOld.this.lastTakeTime = l;
                    }
                    if (world.isClient) {
                        TGPacketsC2S.sendToServer(new PacketCraftAmmoBench(stack, blockPos));
                    }
                });
                super.onTakeItem(player, stack);
            }
        });

        addPlayerInventorySlots(playerInventory);
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.updateInput(inventory, ItemStack.EMPTY); //Itemstack parameter is not used here
    }

    @Override
    public void populateResult() {
        if (!this.availableRecipes.isEmpty() && this.selctionWithinBounds(this.selectedRecipe.get())) {
            AmmoBenchRecipe recipe = this.availableRecipes.get(this.selectedRecipe.get());
            this.output.setLastRecipe(recipe);
            this.outputSlot.setStack(recipe.craft(this.input));
        } else {
            this.outputSlot.setStack(ItemStack.EMPTY);
        }

        this.sendContentUpdates();
    }

    protected void updateInput(Inventory input, ItemStack stack) {
        this.availableRecipes.clear();
        this.selectedRecipe.set(-1);
        this.outputSlot.setStack(ItemStack.EMPTY);
        //Check if all input slots are filled
        for (int i = 0; i< AmmoBenchBlockEntity.INVENTORY_SIZE; i++){
            if (input.getStack(i).isEmpty()){
                return;
            }
        }
        this.availableRecipes = this.world.getRecipeManager().getAllMatches(TGRecipes.AMMOBENCH_RECIPE_TYPE, input, this.world);
    }

    @Environment(EnvType.CLIENT)
    public boolean canCraft() {
        for(int i = 0; i<AmmoBenchBlockEntity.INVENTORY_SIZE; i++){
            if (this.inventory.getStack(i).isEmpty()) {
                return false;
            }
        }
        return !this.availableRecipes.isEmpty();
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
