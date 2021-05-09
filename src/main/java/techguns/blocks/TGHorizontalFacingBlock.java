package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;

/**
 * Block that is rotateable in 4 cardinal directions
 */
public class TGHorizontalFacingBlock extends HorizontalFacingBlock {

    public TGHorizontalFacingBlock(Settings settings) {
        super(settings);
        setDefaultState(getTGBlockDefaultState());
    }

    /**
     * Override this in subclasses and add properties to default state
     * @return
     */
    protected BlockState getTGBlockDefaultState(){
        return getStateManager().getDefaultState().with(FACING, Direction.NORTH);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }
}
