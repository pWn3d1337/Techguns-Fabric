package techguns.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import techguns.TGEntities;
import techguns.api.client.ClientDisconnectEvent;
import techguns.api.client.ClientGameJoinEvent;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.GuidedMissileProjectile;
import techguns.entities.projectiles.RocketProjectile;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
	
	@Shadow
	private MinecraftClient client;

	@Shadow
	private ClientWorld world;

	@Inject(method = "onEntitySpawn", at = @At("TAIL"), cancellable = false)
	private void onEntitySpawn(EntitySpawnS2CPacket packet, CallbackInfo info){
		Entity ent =null;

		Entity entity = this.world.getEntityById(packet.getEntityData());
		LivingEntity shooter = null;
		if (entity != null && entity instanceof LivingEntity) {
			shooter = (LivingEntity) entity;
		}

		EntityType<?> type = packet.getEntityTypeId();
		if(TGEntities.ENTITY_SPAWN_PACKET_MAP.containsKey(type)){
			ent = TGEntities.ENTITY_SPAWN_PACKET_MAP.get(type).create(type, this.world, shooter);
		}

		if (ent !=null) {
			int i = packet.getId();
			double x = packet.getX();
			double y = packet.getY();
			double z = packet.getZ();
			ent.updateTrackedPosition(x, y, z);
			ent.refreshPositionAfterTeleport(x, y, z);
			ent.pitch = (float) (packet.getPitch() * 360) / 256.0F;
			ent.yaw = (float) (packet.getYaw() * 360) / 256.0F;
			ent.setEntityId(i);
			ent.setUuid(packet.getUuid());
			this.world.addEntity(i, ent);
		}
	}

	/*@Shadow
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
	}*/
	
	@Inject(method = "onGameJoin", at = @At(value="RETURN"), cancellable = false )
	private void onGameJoin(CallbackInfo callback) {
		ClientGameJoinEvent.EVENT.invoker().onGameJoin(this.client);
	}
}