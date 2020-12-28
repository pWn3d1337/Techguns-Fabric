package techguns.packets;

import java.io.IOException;
import java.util.UUID;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.Vec3d;
import techguns.TGEntities;
import techguns.entities.projectiles.*;

public class PacketSpawnEntity extends EntitySpawnS2CPacket implements Packet<ClientPlayPacketListener>{

	private CompoundTag additionalData;
	
	public PacketSpawnEntity(int id, UUID uuid, double x, double y, double z, float pitch, float yaw,
			EntityType<?> entityTypeId, int entityData, Vec3d velocity, CompoundTag additionalData) {
		super(id, uuid, x, y, z, pitch, yaw, entityTypeId, entityData, velocity);
		this.additionalData = additionalData;
	}

	public PacketSpawnEntity(Entity entity) {
		this(entity, 0, new CompoundTag());
	}

	public PacketSpawnEntity(Entity entity, int entityData, CompoundTag additionalData) {
		this(entity.getEntityId(), entity.getUuid(), entity.getX(), entity.getY(), entity.getZ(), entity.pitch,
				entity.yaw, entity.getType(), entityData, entity.getVelocity(), additionalData);
	}
	
	public PacketSpawnEntity() {
		super();
	}
	
	@Override
	public void read(PacketByteBuf buf) throws IOException {
		super.read(buf);
		this.additionalData = buf.readCompoundTag();
	}

	@Override
	public void write(PacketByteBuf buf) throws IOException {
		super.write(buf);
		buf.writeCompoundTag(this.additionalData);
	}

	@Override
	public void apply(ClientPlayPacketListener listener) {
		MinecraftClient mc = MinecraftClient.getInstance();
		NetworkThreadUtils.forceMainThread(this, listener, mc);
		Entity ent = null;
		Entity entity = mc.player.world.getEntityById(this.getEntityData());
		LivingEntity shooter = null;
		if (entity != null && entity instanceof LivingEntity) {
			shooter = (LivingEntity) entity;
		}
		
		if (this.getEntityTypeId() == TGEntities.GENERIC_PROJECTILE) {
			ent = new GenericProjectile(TGEntities.GENERIC_PROJECTILE, mc.player.world, shooter,additionalData);
			
		} else if(this.getEntityTypeId() == TGEntities.ROCKET_PROJECTILE) {
			ent = new RocketProjectile(TGEntities.ROCKET_PROJECTILE, mc.player.world,shooter, additionalData);
			
		} else if(this.getEntityTypeId() == TGEntities.GUIDED_MISSILE) {
			ent = new GuidedMissileProjectile(TGEntities.GUIDED_MISSILE, mc.player.world,shooter, additionalData);
			
		} else if(this.getEntityTypeId() == TGEntities.BIOGUN_PROJECTILE) {
			ent = new BioGunProjectile(TGEntities.BIOGUN_PROJECTILE, mc.player.world,shooter, additionalData);
			
		} else if(this.getEntityTypeId() == TGEntities.STONEBULLET_PROJECTILE) {
			ent = new StoneBulletProjectile(TGEntities.STONEBULLET_PROJECTILE, mc.player.world,shooter, additionalData);
		
		} else if(this.getEntityTypeId() == TGEntities.TFG_PROJECTILE) {
			ent = new TFGProjectile(TGEntities.TFG_PROJECTILE, mc.player.world,shooter, additionalData);
		
		} else if(this.getEntityTypeId() == TGEntities.GENERIC_BEAM_PROJECTILE) {
			ent = new GenericBeamProjectile(TGEntities.GENERIC_BEAM_PROJECTILE, mc.player.world,shooter, additionalData);

		} else if(this.getEntityTypeId() == TGEntities.GRENADE_PROJECTILE){
			ent = new GrenadeProjectile(TGEntities.GRENADE_PROJECTILE, mc.player.world, shooter, additionalData);

		} else if (this.getEntityTypeId() == TGEntities.FLAMETHROWER_PROJECTILE){
			ent = new FlamethrowerProjectile(TGEntities.FLAMETHROWER_PROJECTILE, mc.player.world, shooter, additionalData);
		}
		else if (this.getEntityTypeId() == TGEntities.GENERIC_FX_PROJECTILE){
			ent = new GenericProjectileFX(TGEntities.GENERIC_FX_PROJECTILE, mc.player.world, shooter, additionalData);
		}
		else if (this.getEntityTypeId() == TGEntities.FLYING_GIBS){
			ent = new FlyingGibs(TGEntities.FLYING_GIBS, mc.player.world);

		} else if (this.getEntityTypeId() == TGEntities.SONIC_SHOTGUN_PROJECTILE){
			ent = new SonicShotgunProjectile(TGEntities.SONIC_SHOTGUN_PROJECTILE, mc.player.world, shooter, additionalData);
		}

		if (ent != null) {
			ent.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
			ent.refreshPositionAfterTeleport(this.getX(), this.getY(), this.getZ());
			ent.pitch = (float) (this.getPitch() * 360) / 256.0F;
			ent.yaw = (float) (this.getYaw() * 360) / 256.0F;
			ent.setEntityId(this.getId());
			ent.setUuid(this.getUuid());
			((ClientWorld) mc.player.world).addEntity(this.getId(), ent);
		}
	}

	
}
