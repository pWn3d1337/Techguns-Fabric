package techguns.util;

//import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
//import net.fabricmc.fabric.api.tool.attribute.v1.ToolManager;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.mixin.ShovelItemAccessor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BlockUtil {

    public static Iterable<BlockPos> getAllInBox(BlockPos from, BlockPos to){
        return BlockPos.iterate(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()), Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
    }

    public static List<BlockPos> getBlockPlaneAroundAxisForMining(BlockView world, PlayerEntity ply, BlockPos center, Direction.Axis axis, int radius, boolean includeCenter, @Nullable GenericGunMeleeCharge miningtool, ItemStack stack){
        List<BlockPos> entries = new LinkedList<>();

        Iterable<BlockPos> blocks;
        switch(axis) {
            case X:
                blocks = getAllInBox(center.add(0, -radius, -radius), center.add(0, radius, radius));
                break;
            case Y:
                blocks = getAllInBox(center.add(-radius,0, -radius), center.add(radius,0, radius));
                break;
            case Z:
            default:
                blocks = getAllInBox(center.add(-radius, -radius,0), center.add(radius, radius,0));
                break;
        }

        Iterator<BlockPos> iter = blocks.iterator();
        while(iter.hasNext()) {
            BlockPos b = iter.next();
            if(includeCenter || !b.equals(center)) {
                if(world.getBlockEntity(b)==null) {
                    if(miningtool==null || stack.isEmpty() || checkMiningLevels(world, b, ply, miningtool, stack)) {
                        entries.add(new BlockPos(b));
                    }
                }
            }
        }
        return entries;
    }

    protected static boolean checkMiningLevels(BlockView world, BlockPos p, PlayerEntity ply, GenericGunMeleeCharge tool, ItemStack stack){
        BlockState state = world.getBlockState(p);
        if(state.getHardness(world, p) < 0F) {
            return false;
        }

        if(stack.isSuitableFor(state)) {
            //TODO FIXME 1.18.2: dynamic tool stuff
            return true;
        }
        //if(ToolManager.handleIsEffectiveOn(state, stack, ply)){
        //    return true;
        //}

        //DO a special fix for bugged shovel checks
        //TODO 1.17 no longer needed?
        /*if (ShovelItemAccessor.getEFFECTIVE_BLOCKS().contains(state.getBlock())) {
            if (FabricToolTags.SHOVELS.contains(tool) && tool.getMiningLevel(FabricToolTags.SHOVELS, state, stack, ply) > 0) {
               return true;
            }
        }*/

        return false;
    }

    /**
     * Return VoxelShape, NORTH is assumed original position
     * @param x0
     * @param y0
     * @param z0
     * @param x1
     * @param y1
     * @param z1
     * @param direction
     * @return
     */
    public static VoxelShape getHorizonzalRotatedVoxelShape(double x0, double y0, double z0, double x1, double y1, double z1, Direction direction)
    {
       switch (direction){
           case SOUTH:
               return VoxelShapes.cuboid(x0, y0, 1.0-z1, x1, y1, 1.0-z0);
           case EAST:
               return VoxelShapes.cuboid(1.0-z1, y0, x0, 1.0-z0, y1, x1);
           case WEST:
               return VoxelShapes.cuboid(z0, y0, 1.0-x1, z1, y1, 1.0-x0);
           case NORTH:
           default:
               return VoxelShapes.cuboid(x0, y0, z0, x1, y1, z1);
       }
    }

    /**
     * Return VoxelShape, NORTH is assumed original position
     * @param b
     * @param direction
     * @return
     */
    public static VoxelShape getHorizonzalRotatedVoxelShape(Box b, Direction direction)
    {
        return getHorizonzalRotatedVoxelShape(b.minX, b.minY, b.minZ, b.maxX, b.maxY, b.maxZ, direction);
    }
}
