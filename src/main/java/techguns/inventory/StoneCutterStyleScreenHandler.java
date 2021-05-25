package techguns.inventory;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class StoneCutterStyleScreenHandler<T> extends TGBaseScreenHandler {
    protected final ScreenHandlerContext context;
    protected final Property selectedRecipe;
    protected final World world;
    protected List<T> availableRecipes;
    protected ItemStack inputStack;
    protected long lastTakeTime;
    protected Slot inputSlot = null;
    protected Slot outputSlot = null;
    protected Runnable contentsChangedListener;
    public Inventory input = null;
    protected CraftingResultInventory output = null;

    public StoneCutterStyleScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
        super(type, syncId);

        this.selectedRecipe = Property.create();
        this.availableRecipes = new ArrayList<T>();
        this.inputStack = ItemStack.EMPTY;

        this.contentsChangedListener = () -> {
        };

        this.context = context;
        this.world = playerInventory.player.world;

        this.addProperty(selectedRecipe);
    }

    protected boolean selctionWithinBounds(int i) {
        return i >= 0 && i < this.availableRecipes.size();
    }

    protected abstract void populateResult();

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (this.selctionWithinBounds(id)) {
            this.selectedRecipe.set(id);
            this.populateResult();
        }

        return true;
    }

    public abstract void onContentChanged(Inventory inventory);

    @Environment(EnvType.CLIENT)
    public void setContentsChangedListener(Runnable runnable) {
        this.contentsChangedListener = runnable;
    }

    @Environment(EnvType.CLIENT)
    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    @Environment(EnvType.CLIENT)
    public List<T> getAvailableRecipes() {
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

}
