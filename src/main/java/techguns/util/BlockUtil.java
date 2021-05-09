package techguns.util;

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.fabricmc.fabric.api.tool.attribute.v1.ToolManager;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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
        if(ToolManager.handleIsEffectiveOn(state, stack, ply)){
            return true;
        }

        //DO a special fix for bugged shovel checks
        if (ShovelItemAccessor.getEFFECTIVE_BLOCKS().contains(state.getBlock())) {
            if (FabricToolTags.SHOVELS.contains(tool) && tool.getMiningLevel(FabricToolTags.SHOVELS, state, stack, ply) > 0) {
               return true;
            }
        }

        return false;
    }

}
