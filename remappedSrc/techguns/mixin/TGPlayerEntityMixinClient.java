package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import techguns.TGPacketsC2S;
import techguns.api.client.entity.ITGExtendedPlayerClient;
import techguns.api.entity.ITGExtendedPlayer;
import techguns.api.guns.IGenericGun;
import techguns.client.ClientProxy;
import techguns.packets.c2s.PacketShootGun;

@Mixin(PlayerEntity.class)
public abstract class TGPlayerEntityMixinClient extends LivingEntity implements ITGExtendedPlayerClient {

	protected TGPlayerEntityMixinClient(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Inject(at = @At(value = "RETURN"), method="tick", cancellable = false)
	public void tick(CallbackInfo info) {
		if(this.world.isClient) {
			this.clientPlayerTick();
		}
	}	
	
	public void clientPlayerTick() {
		ClientProxy cp = ClientProxy.get();
		
		PlayerEntity ply = (PlayerEntity)(Object)this;
		ITGExtendedPlayer props = (ITGExtendedPlayer)this;
		
		// ONLY DO FOR OWN PLAYER!
		if (ply == cp.getPlayerClient()) {
			//TGExtendedPlayer props = TGExtendedPlayer.get(cp.getPlayerClient());
			
			if (MinecraftClient.getInstance().isWindowFocused() && !ply.isSpectator()) {
				ItemStack stack = ply.getMainHandStack();
				ItemStack stackOff = ply.getOffHandStack();
				
				if (!stack.isEmpty() && stack.getItem() instanceof IGenericGun && ((IGenericGun) stack.getItem()).isShootWithLeftClick()) {
					if (cp.keyFirePressedMainhand) {
						// event.player.swingItem();
						
						IGenericGun gun = (IGenericGun) stack.getItem();
						if (props.getFireDelay(Hand.MAIN_HAND) <= 0) {
							//TODO genericguncharge
							/*if (gun instanceof GenericGunCharge && ((GenericGunCharge)gun).getLockOnTicks() > 0 && props.lockOnEntity != null && props.lockOnTicks > ((GenericGunCharge)gun).getLockOnTicks()) {
								TGPackets.network.sendToServer(new PacketShootGunTarget(gun.isZooming(),EnumHand.MAIN_HAND, props.lockOnEntity));
								gun.shootGunPrimary(stack, event.player.world, event.player, gun.isZooming(), EnumHand.MAIN_HAND, props.lockOnEntity);
							}else {*/
								TGPacketsC2S.sendToServer(new PacketShootGun(gun.isZooming(),Hand.MAIN_HAND));
								gun.shootGunPrimary(stack, ply.world, ply, gun.isZooming(), Hand.MAIN_HAND, null);
							//}
							
						}
						if (gun.isSemiAuto()) {
							cp.keyFirePressedMainhand = false;
						}
					}
	
				} else {
					cp.keyFirePressedMainhand = false;
				}
				
				if (!stackOff.isEmpty() && stackOff.getItem() instanceof IGenericGun && ((IGenericGun) stackOff.getItem()).isShootWithLeftClick()) {
					if (cp.keyFirePressedOffhand) {
						// event.player.swingItem();
						IGenericGun gun = (IGenericGun) stackOff.getItem();
						if (props.getFireDelay(Hand.OFF_HAND) <= 0) {

							TGPacketsC2S.sendToServer(new PacketShootGun(gun.isZooming(),Hand.OFF_HAND));
							gun.shootGunPrimary(stackOff, ply.world,ply, gun.isZooming(), Hand.OFF_HAND, null);
						}
						if (gun.isSemiAuto()) {
							cp.keyFirePressedOffhand = false;
						}
					}

				} else {
					cp.keyFirePressedOffhand = false;
				}
				
				//TODO GenericGunCharge
				//Reset lock if out of ammo
				/*if (!stack.isEmpty() && stack.getItem() instanceof GenericGunCharge && ((GenericGunCharge) stack.getItem()).getLockOnTicks() > 0) {
					//System.out.println("RMB: "+cp.keyFirePressedOffhand);
					if (((GenericGunCharge)stack.getItem()).getAmmoLeft(stack) <= 0 && props.lockOnEntity != null) {
						props.lockOnEntity = null;
						props.lockOnTicks = -1;
						//System.out.println("reset lock.");
					}
				}*/
				
			} else {
				cp.keyFirePressedMainhand = false;
				cp.keyFirePressedOffhand = false;
			}
		}
	}
}
