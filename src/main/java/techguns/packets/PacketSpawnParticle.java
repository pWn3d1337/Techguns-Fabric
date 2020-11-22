package techguns.packets;

import java.nio.charset.StandardCharsets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import techguns.TGPacketsS2C;
import techguns.client.ClientProxy;

public class PacketSpawnParticle extends TGBasePacket {

	protected String name;
	
	protected double posX;
	protected double posY;
	protected double posZ;
	
	protected double motionX=0.0D;
	protected double motionY=0.0D;
	protected double motionZ=0.0D;
	
	protected float scale = 1.0f;
	
	public PacketSpawnParticle() {
		super();
	}	
	
	public PacketSpawnParticle(String name, double posX, double posY, double posZ) {
		super();
		this.name=name;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}
	
	public PacketSpawnParticle(String name, double posX, double posY, double posZ, float scale) {
		this(name, posX, posY, posZ);
		this.scale = scale;
	}

	public PacketSpawnParticle(String name, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
		super();
		this.name=name;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}
	
	public PacketSpawnParticle(String name, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float scale) {
		this(name, posX, posY, posZ, motionX, motionY, motionZ);
		this.scale = scale;
	}

	@Override
	public void unpack(PacketByteBuf buf) {
		int len = buf.readShort();
		this.name =  buf.readCharSequence(len, StandardCharsets.UTF_8).toString();
		
		this.posX=buf.readDouble();
		this.posY=buf.readDouble();
		this.posZ=buf.readDouble();
		
		this.motionX=buf.readDouble();
		this.motionY=buf.readDouble();
		this.motionZ=buf.readDouble();
		
		this.scale = buf.readFloat();
	}

	@Override
	public void pack(PacketByteBuf buf) {
		CharSequence cs = this.name;
		buf.writeShort(cs.length());
		buf.writeCharSequence(name, StandardCharsets.UTF_8);
		
		buf.writeDouble(posX);
		buf.writeDouble(posY);
		buf.writeDouble(posZ);
		
		buf.writeDouble(motionX);
		buf.writeDouble(motionY);
		buf.writeDouble(motionZ);
		
		buf.writeFloat(scale);
	}
	

	@Override
	public void handle(PlayerEntity player) {
		ClientProxy.get().createFX(name, player.world, posX, posY, posZ, motionX, motionY, motionZ, scale);
	}

	@Override
	public Identifier getID() {
		return TGPacketsS2C.SPAWN_PARTICLE;
	}

}
