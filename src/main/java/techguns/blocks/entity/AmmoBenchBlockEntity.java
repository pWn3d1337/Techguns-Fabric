package techguns.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;
import techguns.TGBlocks;
import techguns.inventory.AmmoBenchScreenHandler;

public class AmmoBenchBlockEntity extends TGInventoryBlockEntity {

    /**
     * Has 1 extra size for output
     */
    public static final int INVENTORY_SIZE = 3;

    public AmmoBenchBlockEntity() {
        super(null/*TGBlocks.AMMO_BENCH_BLOCK_ENTITY*/, new BlockPos(0,0,0), Blocks.AIR.getDefaultState(), INVENTORY_SIZE, null); //Override CreateMenu directly
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null; //new AmmoBenchScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(this.world, this.pos), this);
    }
}
