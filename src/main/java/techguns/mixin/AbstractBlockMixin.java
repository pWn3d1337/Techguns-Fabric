package techguns.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.util.BlockUtil;

import java.util.List;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Inject(at = @At(value="INVOKE"), method = "calcBlockBreakingDelta", cancellable = true)
    public void OnCalcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> callback){
        ItemStack itemStack = player.getMainHandStack();
        if(!itemStack.isEmpty() && itemStack.getItem() instanceof GenericGunMeleeCharge){

            GenericGunMeleeCharge gun = (GenericGunMeleeCharge)itemStack.getItem();
            int miningradius = gun.getMiningRadius(player, itemStack);
            if(miningradius > 0){

                BlockHitResult blockHitResult = gun.getMiningTarget(player, world);
                if(blockHitResult !=null){

                    Direction sideHit = blockHitResult.getSide();
                    List<BlockPos> blocks = BlockUtil.getBlockPlaneAroundAxisForMining(world, player, blockHitResult.getBlockPos(), sideHit.getAxis(), miningradius, false, gun, itemStack);

                    float hardest = state.getHardness(world, pos);
                    for (BlockPos p: blocks) {
                        BlockState s = world.getBlockState(p);
                        float h = s.getHardness(world, pos); //player.getBlockBreakingSpeed(s);
                        if (h > hardest) {
                            hardest = h;
                        }
                    }
                    int i = player.isUsingEffectiveTool(state) ? 30 : 100;
                    callback.setReturnValue(player.getBlockBreakingSpeed(state) / hardest / (float)i);

                    callback.cancel();
                }
            }
        }
    }


}
