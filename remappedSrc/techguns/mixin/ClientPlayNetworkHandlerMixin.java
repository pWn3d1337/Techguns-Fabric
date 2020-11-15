package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import techguns.TGEntities;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.GuidedMissileProjectile;
import techguns.entities.projectiles.RocketProjectile;

/**
 * Based on https://gist.github.com/matjojo/011bbd340a71f258e25806bf1c82229c
 */
@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
	
	@Shadow
	private ClientWorld world;

	@Inject(
			method = "onEntitySpawn(Lnet/minecraft/network/packet/s2c/play/EntitySpawnS2CPacket;)V",
			at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/network/packet/s2c/play/EntitySpawnS2CPacket;getEntityTypeId()Lnet/minecraft/entity/EntityType;"),
			cancellable = true,
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void onEntitySpawn(EntitySpawnS2CPacket packet, CallbackInfo callback, double x, double y, double z, EntityType<?> type) {
		Entity entity = null;
		
		if (type == TGEntities.GENERIC_PROJECTILE) {
			entity = new GenericProjectile(TGEntities.GENERIC_PROJECTILE, world);
		} else if (type == TGEntities.GUIDED_MISSILE) {
			entity = new GuidedMissileProjectile(TGEntities.GUIDED_MISSILE, world);
		} else if (type == TGEntities.ROCKET_PROJECTILE) {
			entity = new RocketProjectile(TGEntities.ROCKET_PROJECTILE, world);
		}
		if (entity != null) {
			int i = packet.getId();
			entity.updateTrackedPosition(x, y, z);
			entity.refreshPositionAfterTeleport(x, y, z);
			entity.pitch = (float) (packet.getPitch() * 360) / 256.0F;
			entity.yaw = (float) (packet.getYaw() * 360) / 256.0F;
			entity.setEntityId(i);
			entity.setUuid(packet.getUuid());
			this.world.addEntity(i, entity);

			callback.cancel(); // cancel stops the rest of the method to run (so no spawning code from mc runs)
		}
	}

}