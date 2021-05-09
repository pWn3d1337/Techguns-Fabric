package techguns.blocks.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import techguns.inventory.ScreenHandlerFactory;

public class TGScreenBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    protected final ScreenHandlerFactory screenHandler;

    public TGScreenBlockEntity(BlockEntityType<?> type, ScreenHandlerFactory screenHandler) {
        super(type);
        this.screenHandler = screenHandler;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return this.screenHandler.create(syncId, playerInventory, null);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }
}
