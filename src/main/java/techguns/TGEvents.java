package techguns;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import techguns.api.guns.GunHandType;
import techguns.api.guns.IGenericGun;

public class TGEvents implements ITGInitializer {

	@Override
	public void init() {
		
		//Block offhand use with 2handed guns
		UseItemCallback.EVENT.register((PlayerEntity player, World world, Hand hand) -> {
			ItemStack stack = player.getStackInHand(hand);

			if (isSpectator(player)) {
				return TypedActionResult.pass(stack);
			} else if (player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
				return TypedActionResult.pass(stack);
			}
			
			if (!allowOffhandUse(player, hand)) {
				return TypedActionResult.fail(stack);
			}
			
			return TypedActionResult.pass(stack);
		});
		
		UseBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockHitResult hitRes) -> {
			if(isSpectator(player)) return ActionResult.SUCCESS;
			if(!allowOffhandUse(player, hand)) {
				return ActionResult.FAIL;
			}
			return ActionResult.PASS;
		});
		
		UseEntityCallback.EVENT.register((PlayerEntity player, World world, Hand hand,Entity entity, EntityHitResult hitRes) -> {
			if(isSpectator(player)) return ActionResult.SUCCESS;
			if(!allowOffhandUse(player, hand)) {
				return ActionResult.FAIL;
			}			
			return ActionResult.PASS;
		});
	}


	protected boolean isSpectator(PlayerEntity player) {
		GameMode mode = null;
		if (player.world.isClient) {
			net.minecraft.client.MinecraftClient mc = net.minecraft.client.MinecraftClient.getInstance();
			if(player == mc.player && mc.interactionManager != null) {
				mode =  mc.interactionManager.getCurrentGameMode();
			}
		} else {
			net.minecraft.server.network.ServerPlayerEntity ply = (net.minecraft.server.network.ServerPlayerEntity) player;
			mode = ply.interactionManager.getGameMode();
		}
			
		return (mode != null && mode == GameMode.SPECTATOR);
	}
	
	
	protected static boolean allowOffhandUse(PlayerEntity player, Hand hand) {
		if (hand == Hand.MAIN_HAND) return true;
		if(!player.getMainHandStack().isEmpty() && player.getMainHandStack().getItem() instanceof IGenericGun) {
			IGenericGun g = (IGenericGun) player.getMainHandStack().getItem();
			if(g.getGunHandType()==GunHandType.TWO_HANDED) {
				return false;
			}
		}
		if(!player.getOffHandStack().isEmpty() && player.getOffHandStack().getItem() instanceof IGenericGun) {
			IGenericGun g = (IGenericGun) player.getOffHandStack().getItem();
			if(g.getGunHandType()==GunHandType.TWO_HANDED) {
				return false;
			}
		}
		return true;
	}
}
