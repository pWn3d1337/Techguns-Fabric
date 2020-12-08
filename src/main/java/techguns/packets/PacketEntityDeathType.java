package techguns.packets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import techguns.TGConfig;
import techguns.TGPacketsS2C;
import techguns.client.ClientProxy;
import techguns.client.deatheffects.DeathEffectHandler;
import techguns.deatheffects.EntityDeathUtils.DeathType;

public class PacketEntityDeathType extends TGBasePacket{

	int entityId;
	DeathType deathtype;
	float motionX;
	float motionY;
	float motionZ;
	
	
	public PacketEntityDeathType() {
	}
	
	public PacketEntityDeathType(LivingEntity entity, DeathType deathtype) {
		this(entity.getEntityId(), deathtype, (float)entity.getVelocity().x, (float)entity.getVelocity().y, (float)entity.getVelocity().z);
	}

	public PacketEntityDeathType(int entityId, DeathType deathtype, float motionX, float motionY, float motionZ) {
		this.entityId = entityId;
		this.deathtype = deathtype;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}
	

	@Override
	public void pack(PacketByteBuf buf) {
		buf.writeInt(entityId);
		buf.writeByte((byte)deathtype.ordinal());
		buf.writeFloat(motionX);
		buf.writeFloat(motionY);
		buf.writeFloat(motionZ);
	}

	@Override
	public void unpack(PacketByteBuf buf) {
		this.entityId=buf.readInt();
		byte dt = buf.readByte();
		if (dt > 0 && dt < DeathType.values().length) {
			this.deathtype = DeathType.values()[dt];
		}
		this.motionX = buf.readFloat();
		this.motionY = buf.readFloat();
		this.motionZ = buf.readFloat();
	}

	@Override
	public void handle(PlayerEntity player) {
		if (TGConfig.cl_enableDeathFX) {
			LivingEntity entity = (LivingEntity) player.world.getEntityById(this.entityId);
			if (this.deathtype != DeathType.GORE || (this.deathtype==DeathType.GORE && TGConfig.cl_enableDeathFX_Gore)){			
				if (entity != null) {
					entity.setVelocity(motionX, motionY, motionZ);
					DeathEffectHandler.setEntityDeathType(entity, this.deathtype);
					DeathEffectHandler.createDeathEffect(entity, this.deathtype, this.motionX, this.motionY, this.motionZ);
				}
			}	
		}
	}

	@Override
	public Identifier getID() {
		return TGPacketsS2C.ENTITY_DEATH_TYPE;
	}

}
