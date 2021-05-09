package techguns.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.util.BlockUtil;

import java.util.List;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Shadow
    public ServerWorld world;

    @Inject(method="tryBreakBlock", at=@At(value = "INVOKE", target="Lnet/minecraft/block/Block;onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)V"), cancellable=false)
    private void onBlockBreak(BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        ItemStack stack = player.getMainHandStack();
        if(!stack.isEmpty() && stack.getItem() instanceof GenericGunMeleeCharge){
            GenericGunMeleeCharge gun = (GenericGunMeleeCharge) stack.getItem();
            int miningradius = gun.getMiningRadius(this.player, stack);
            if(miningradius > 0) {

                BlockHitResult blockHitResult = gun.getMiningTarget(this.player, this.world);
                if (blockHitResult != null) {

                    Direction sideHit = blockHitResult.getSide();
                    List<BlockPos> blocks = BlockUtil.getBlockPlaneAroundAxisForMining(world, player, blockHitResult.getBlockPos(), sideHit.getAxis(), miningradius, false, gun, stack);
                    for (BlockPos p: blocks) {
                        BlockEntity blockEntity = this.world.getBlockEntity(p);
                        BlockState blockState = this.world.getBlockState(p);
                        Block block = blockState.getBlock();

                        block.onBreak(this.world, p, blockState, this.player);
                        boolean bl = this.world.removeBlock(p, false);
                        if (bl) {
                            block.onBroken(this.world, p, blockState);
                            if (!player.isCreative()) {
                                block.afterBreak(this.world, this.player, p, blockState, blockEntity, stack);
                            }
                        }

                    }

                }
            }
        }
    }

}
