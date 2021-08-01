package techguns.blocks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import techguns.inventory.AmmoBenchScreenHandler;
import techguns.util.BlockUtil;

import java.util.function.Function;

public class AmmoBenchBlock extends TGHorizontalFacingBlock {

    private static final Text TITLE = new TranslatableText("techguns.container.ammobench");

    private static final VoxelShape[] BOUNDING_SHAPES = Util.make(() -> {
        final float SCALE = 0.0625F; // 1/16
        Box box1 = new Box(1 * SCALE, 0.0D, 1 * SCALE, 15 * SCALE, 8 * SCALE, 15 * SCALE);
        Box box2 = new Box(0.0D, 8*SCALE, 0.0D, 1.0D, 10*SCALE, 1.0D);
        Box box3 = new Box(1 * SCALE, 10 * SCALE, 14 * SCALE, 15 * SCALE, 1.0D, 1.0D);

        Function<Direction, VoxelShape> shape_for_dir = (Direction dir) -> {
            VoxelShape cube1 = BlockUtil.getHorizonzalRotatedVoxelShape(box1, dir);
            VoxelShape cube2 = BlockUtil.getHorizonzalRotatedVoxelShape(box2, dir);
            VoxelShape cube3 = BlockUtil.getHorizonzalRotatedVoxelShape(box3, dir);
            return VoxelShapes.combine(cube3, VoxelShapes.combine(cube1, cube2, BooleanBiFunction.OR), BooleanBiFunction.OR);
        };

        return new VoxelShape[]{
                shape_for_dir.apply(Direction.SOUTH),
                shape_for_dir.apply(Direction.WEST),
                shape_for_dir.apply(Direction.NORTH),
                shape_for_dir.apply(Direction.EAST)
        };
    });
    public AmmoBenchBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            //player.incrementStat(Stats.INTERACT_WITH_STONECUTTER);
            return ActionResult.CONSUME;
        }
    }

    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> {
            return new AmmoBenchScreenHandler(i, playerInventory, ScreenHandlerContext.create(world, pos));
        }, TITLE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
       Direction direction = state.get(FACING);
       return BOUNDING_SHAPES[direction.getHorizontal()];
    }
}
