package techguns.packets;

import java.nio.charset.StandardCharsets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import techguns.TGPacketsS2C;
import techguns.client.ClientProxy;
import techguns.util.EntityCondition;
import techguns.util.TGLogger;

public class PacketSpawnParticleOnEntity extends TGBasePacket {

	protected String name;
	protected int entityID;
	
	protected float offsetX = 0.0f;
	protected float offsetY = 0.0f;
	protected float offsetZ = 0.0f;
	
	//float scale = 1.0f;
	
	protected boolean attachToHead = false;
	
	protected EntityCondition condition;
	
	public PacketSpawnParticleOnEntity() {
	}

	public PacketSpawnParticleOnEntity(String name, Entity ent) {
		this(name, ent, 0.0f, 0.0f, 0.0f, false, EntityCondition.NONE);
	}
	
	public PacketSpawnParticleOnEntity(String name, Entity ent, float offsetX, float offsetY, float offsetZ, boolean attachToHead) {
		this(name, ent, offsetX, offsetY, offsetZ, attachToHead, EntityCondition.NONE);
	}
	
	public PacketSpawnParticleOnEntity(String name, Entity ent, float offsetX, float offsetY, float offsetZ, boolean attachToHead, EntityCondition condition) {
		super();
		this.name=name;
		this.entityID=ent.getEntityId();
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.attachToHead = attachToHead;
		this.condition = condition;
	}

	@Override
	public void unpack(PacketByteBuf buf) {
		
		System.out.println("SpawnParticleOnEntity_Unpack");
		
		int len = buf.readShort();
		this.name = buf.readCharSequence(len, StandardCharsets.UTF_8).toString();
		
		this.entityID=buf.readInt();
		
		this.offsetX=buf.readFloat();
		this.offsetY=buf.readFloat();
		this.offsetZ=buf.readFloat();
		
		this.attachToHead = buf.readBoolean();
		
		this.condition = EntityCondition.fromByte(buf.readByte());
	}

	@Override
	public void pack(PacketByteBuf buf) {
		CharSequence cs = this.name;
		buf.writeShort(cs.length());
		buf.writeCharSequence(name, StandardCharsets.UTF_8);
		
		buf.writeInt(entityID);
		
		buf.writeFloat(this.offsetX);
		buf.writeFloat(this.offsetY);
		buf.writeFloat(this.offsetZ);
		
		buf.writeBoolean(this.attachToHead);
		
		if (condition == null) {
			buf.writeByte(EntityCondition.NONE.id);
		}
		else {
			buf.writeByte(condition.id);
		}
	}
	
	@Override
	public void handle(PlayerEntity player) {
		Entity ent = player.world.getEntityById(this.entityID);
		System.out.println("SpawnParticleOnEntity");
		if (ent!=null){
			ClientProxy.get().createFXOnEntityWithOffset(this.name, ent, this.offsetX, this.offsetY, this.offsetZ, this.attachToHead, this.condition);
		} else {
			TGLogger.logger_client.warning("Got Packet for FX "+this.name+" on Entity, but ent was null");
		}
	}

	@Override
	public Identifier getID() {
		return TGPacketsS2C.SPAWN_PARTICLE_ON_ENTITY;
	}

}
