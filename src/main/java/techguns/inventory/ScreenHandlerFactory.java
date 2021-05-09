package techguns.inventory;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ScreenHandlerFactory {
    ScreenHandler create(int syncId, PlayerInventory playerInventory, @Nullable Inventory inventory);
}