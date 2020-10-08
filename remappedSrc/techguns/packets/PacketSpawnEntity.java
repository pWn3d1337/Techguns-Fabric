package techguns.packets;

import java.io.IOException;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.client.sound.MovingMinecartSoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.entity.vehicle.CommandBlockMinecartEntity;
import net.minecraft.entity.vehicle.FurnaceMinecartEntity;
import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.entity.vehicle.SpawnerMinecartEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import techguns.TGEntities;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;

public class PacketSpawnEntity extends TGBasePacket implements Packet<T>{

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
	}

	public PacketSpawnEntity(Entity entity) {
		this(entity, 0, new CompoundTag());
	}

	public PacketSpawnEntity(Entity entity, int entityData, CompoundTag additionalData) {
		this(entity.getEntityId(), entity.getUuid(), entity.getX(), entity.getY(), entity.getZ(), entity.pitch,
				entity.yaw, entity.getType(), entityData, entity.getVelocity(), additionalData);
	}

	/*public PacketSpawnEntity(Entity entity, EntityType<?> entityType, int data, BlockPos pos) {
		this(entity.getEntityId(), entity.getUuid(), (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(),
				entity.pitch, entity.yaw, entityType, data, entity.getVelocity());
	}*/
	

	public PacketSpawnEntity(PacketByteBuf buf) {
		super(buf);
	}

	@Override
	public void pack(PacketByteBuf buf) {
		buf.writeVarInt(this.id);
		buf.writeUuid(this.uuid);
		buf.writeVarInt(Registry.ENTITY_TYPE.getRawId(this.entityTypeId));
		buf.writeDouble(this.x);
		buf.writeDouble(this.y);
		buf.writeDouble(this.z);
		buf.writeByte(this.pitch);
		buf.writeByte(this.yaw);
		buf.writeInt(this.entityData);
		buf.writeShort(this.velocityX);
		buf.writeShort(this.velocityY);
		buf.writeShort(this.velocityZ);
		buf.writeCompoundTag(this.additionalData);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void unpack(PacketByteBuf buf) {
		this.id = buf.readVarInt();
		this.uuid = buf.readUuid();
		this.entityTypeId = (EntityType) Registry.ENTITY_TYPE.get(buf.readVarInt());
		this.x = buf.readDouble();
		this.y = buf.readDouble();
		this.z = buf.readDouble();
		this.pitch = buf.readByte();
		this.yaw = buf.readByte();
		this.entityData = buf.readInt();
		this.velocityX = buf.readShort();
		this.velocityY = buf.readShort();
		this.velocityZ = buf.readShort();
		this.additionalData = buf.readCompoundTag();
	}

	@Override
	public void handle(PlayerEntity player) {
	      Entity ent=null;
	      if(this.entityTypeId == TGEntities.GENERIC_PROJECTILE) {
	    	  int id = additionalData.getInt("shooter");
	    	  Entity entity = player.world.getEntityById(id);
	    	  LivingEntity shooter = null;
	    	  if(entity != null && entity instanceof LivingEntity) {
	    		  shooter = (LivingEntity) entity;
	    	  }
	    	  
	    	  ent = new GenericProjectile(player.world, shooter, 0, speed, TTL, 0, 0, 0, 0, 0, false, EnumBulletFirePos.CENTER);
	      }
	      

	     
	      if (ent != null) {
	         ent.updateTrackedPosition(this.x, this.y, this.z);
	         ent.refreshPositionAfterTeleport(this.x, this.y, this.z);
	         ent.pitch = (float)(this.pitch * 360) / 256.0F;
	         ent.yaw = (float)(this.yaw * 360) / 256.0F;
	         ent.setEntityId(this.id);
	         ent.setUuid(this.uuid);
	         ((ClientWorld)player.world).addEntity(this.id, ent);
	      }
	}

	@Override
	public Identifier getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void read(PacketByteBuf buf) throws IOException {
		this.unpack(buf);
	}

	@Override
	public void write(PacketByteBuf buf) throws IOException {
		this.pack(buf);
	}

	@Override
	public void apply(T listener) {
		// TODO Auto-generated method stub
		
	}

	
}
