package techguns.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public abstract class TGBaseScreenHandler extends ScreenHandler {

    protected TGBaseScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
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
}
