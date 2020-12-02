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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import techguns.TGEntities;
import techguns.entities.projectiles.BioGunProjectile;
import techguns.entities.projectiles.GenericBeamProjectile;
import techguns.entities.projectiles.GenericProjectile;
import techguns.entities.projectiles.GuidedMissileProjectile;
import techguns.entities.projectiles.RocketProjectile;
import techguns.entities.projectiles.TFGProjectile;
import techguns.entities.projectiles.StoneBulletProjectile;

public class PacketSpawnEntity implements Packet<ClientPlayPacketListener>{

	//default spawn packet fields
	private int id;
	private UUID uuid;
	private double x;
	private double y;
	private double z;
	private int velocityX;
	private int velocityY;
	private int velocityZ;
	private int pitch;
	private int yaw;
	private EntityType<?> entityTypeId;
	private int entityData;
	private CompoundTag additionalData;
	
	public PacketSpawnEntity(int id, UUID uuid, double x, double y, double z, float pitch, float yaw,
			EntityType<?> entityTypeId, int entityData, Vec3d velocity, CompoundTag additionalData) {
		this.id = id;
		this.uuid = uuid;
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = MathHelper.floor(pitch * 256.0F / 360.0F);
		this.yaw = MathHelper.floor(yaw * 256.0F / 360.0F);
		this.entityTypeId = entityTypeId;
		this.entityData = entityData;
		this.velocityX = (int) (MathHelper.clamp(velocity.x, -3.9D, 3.9D) * 8000.0D);
		this.velocityY = (int) (MathHelper.clamp(velocity.y, -3.9D, 3.9D) * 8000.0D);
		this.velocityZ = (int) (MathHelper.clamp(velocity.z, -3.9D, 3.9D) * 8000.0D);
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
	
	@SuppressWarnings("rawtypes")
	@Override
	public void read(PacketByteBuf buf) throws IOException {
		this.id = buf.readVarInt();
		this.uuid = buf.readUuid();
		this.entityTypeId = (EntityType) Registry.ENTITY_TYPE.get(buf.readVarInt());
		this.x = buf.readDouble();
		this.y = buf.readDouble();
		this.z = buf.readDouble();
		this.pitch = buf.readByte();
		this.yaw = buf.readByte();
		this.entityData = buf.readInt();
		this.velocityX = buf.readInt();
		this.velocityY = buf.readInt();
		this.velocityZ = buf.readInt();
		this.additionalData = buf.readCompoundTag();
	}

	@Override
	public void write(PacketByteBuf buf) throws IOException {
		buf.writeVarInt(this.id);
		buf.writeUuid(this.uuid);
		buf.writeVarInt(Registry.ENTITY_TYPE.getRawId(this.entityTypeId));
		buf.writeDouble(this.x);
		buf.writeDouble(this.y);
		buf.writeDouble(this.z);
		buf.writeByte(this.pitch);
		buf.writeByte(this.yaw);
		buf.writeInt(this.entityData);
		buf.writeInt(this.velocityX);
		buf.writeInt(this.velocityY);
		buf.writeInt(this.velocityZ);
		buf.writeCompoundTag(this.additionalData);
	}

	@Override
	public void apply(ClientPlayPacketListener listener) {
		MinecraftClient mc = MinecraftClient.getInstance();
		NetworkThreadUtils.forceMainThread(this, listener, mc);
		Entity ent = null;
		Entity entity = mc.player.world.getEntityById(this.entityData);
		LivingEntity shooter = null;
		if (entity != null && entity instanceof LivingEntity) {
			shooter = (LivingEntity) entity;
		}
		
		if (this.entityTypeId == TGEntities.GENERIC_PROJECTILE) {
			ent = new GenericProjectile(TGEntities.GENERIC_PROJECTILE, mc.player.world, shooter,additionalData);
			
		} else if(this.entityTypeId == TGEntities.ROCKET_PROJECTILE) {
			ent = new RocketProjectile(TGEntities.ROCKET_PROJECTILE, mc.player.world,shooter, additionalData);
			
		} else if(this.entityTypeId == TGEntities.GUIDED_MISSILE) {
			ent = new GuidedMissileProjectile(TGEntities.GUIDED_MISSILE, mc.player.world,shooter, additionalData);
			
		} else if(this.entityTypeId == TGEntities.BIOGUN_PROJECTILE) {
			ent = new BioGunProjectile(TGEntities.BIOGUN_PROJECTILE, mc.player.world,shooter, additionalData);
			
		} else if(this.entityTypeId == TGEntities.STONEBULLET_PROJECTILE) {
			ent = new StoneBulletProjectile(TGEntities.STONEBULLET_PROJECTILE, mc.player.world,shooter, additionalData);
		
		} else if(this.entityTypeId == TGEntities.TFG_PROJECTILE) {
			ent = new TFGProjectile(TGEntities.TFG_PROJECTILE, mc.player.world,shooter, additionalData);
		
		} else if(this.entityTypeId == TGEntities.GENERIC_BEAM_PROJECTILE) {
			ent = new GenericBeamProjectile(TGEntities.GENERIC_BEAM_PROJECTILE, mc.player.world,shooter, additionalData);
		}

		if (ent != null) {
			ent.updateTrackedPosition(this.x, this.y, this.z);
			ent.refreshPositionAfterTeleport(this.x, this.y, this.z);
			ent.pitch = (float) (this.pitch * 360) / 256.0F;
			ent.yaw = (float) (this.yaw * 360) / 256.0F;
			ent.setEntityId(this.id);
			ent.setUuid(this.uuid);
			((ClientWorld) mc.player.world).addEntity(this.id, ent);
		}
	}

	
}
