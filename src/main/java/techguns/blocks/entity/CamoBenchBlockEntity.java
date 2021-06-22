package techguns.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import techguns.TGBlocks;
import techguns.inventory.ScreenHandlerFactory;

public class CamoBenchBlockEntity extends TGScreenBlockEntity {
    public CamoBenchBlockEntity(BlockPos pos, BlockState state, ScreenHandlerFactory screenHandler) {
        super(null/*TGBlocks.CAMO_BENCH_BLOCK_ENTITY*/,pos, state, screenHandler);
    }




}
