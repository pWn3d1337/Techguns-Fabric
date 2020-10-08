package techguns.packets;

import java.util.BitSet;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import techguns.TGPacketsS2C;
import techguns.TGSounds;
import techguns.client.ClientProxy;
import techguns.util.DataUtil;

public class PacketGunImpactFX extends TGBasePacket {
	short soundType;
	double loc_x;
	double loc_y;
	double loc_z;
	float pitch;
	float yaw;
	byte flags1;
	
	boolean incendiary=false;
	
	public PacketGunImpactFX(PacketByteBuf buf) {
		super(buf);
	}

	public PacketGunImpactFX(short soundType, double loc_x, double loc_y, double loc_z, float pitch, float yaw) {
		this.soundType = soundType;
		this.loc_x = loc_x;
		this.loc_y = loc_y;
		this.loc_z = loc_z;
		this.pitch = pitch;
		this.yaw = yaw;
		
	}

	public PacketGunImpactFX(short soundType, double loc_x, double loc_y, double loc_z, float pitch, float yaw, boolean inc) {
		this.soundType = soundType;
		this.loc_x = loc_x;
		this.loc_y = loc_y;
		this.loc_z = loc_z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.incendiary=inc;
	}
	


	@Override
	public void unpack(PacketByteBuf buf) {
		this.soundType=buf.readShort();
		this.loc_x=buf.readDouble();
		this.loc_y=buf.readDouble();
		this.loc_z=buf.readDouble();
		this.pitch=buf.readFloat();
		this.yaw = buf.readFloat();
		byte flags1 = buf.readByte();
		BitSet bs = DataUtil.uncompress(flags1);
		this.incendiary = bs.get(0);
	}

	@Override
	public void pack(PacketByteBuf buf) {
		buf.writeShort(soundType);
		buf.writeDouble(loc_x);
		buf.writeDouble(loc_y);
		buf.writeDouble(loc_z);
		buf.writeFloat(pitch);
		buf.writeFloat(yaw);
		byte flags1=DataUtil.compress(incendiary);
		buf.writeByte(flags1);
	}

	@Override
	public void handle(PlayerEntity ply) {
		double x,y,z;
		x=this.loc_x;
		y=this.loc_y;
		z=this.loc_z;
		World world = ply.world;
		boolean distdelay = true;
		
		ClientProxy cp = ClientProxy.get();
		
		if(this.incendiary) {
			cp.createFX("Impact_IncendiaryBullet", world, x, y, z, 0.0D, 0.0D, 0.0D, this.pitch, this.yaw);
		}
		
		if(this.soundType==0) { //stone
			world.playSound(x, y, z, TGSounds.BULLET_IMPACT_STONE, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
			cp.createFX("Impact_BulletRock", world, x, y, z, 0.0D, 0.0D, 0.0D, this.pitch, this.yaw);
			
		} else if(this.soundType==1) { //wood
			world.playSound(x, y, z, TGSounds.BULLET_IMPACT_WOOD, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
			cp.createFX("Impact_BulletWood", world, x, y, z, 0.0D, 0.0D, 0.0D, this.pitch, this.yaw);
			
		} else if(this.soundType==2) { //glass
			world.playSound(x, y, z, TGSounds.BULLET_IMPACT_GLASS, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
			cp.createFX("Impact_BulletGlass", world, x, y, z, 0.0D, 0.0D, 0.0D, this.pitch, this.yaw);
			
		} else if(this.soundType==3) { //metal
			world.playSound(x, y, z, TGSounds.BULLET_IMPACT_METAL, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
			cp.createFX("Impact_BulletMetal", world, x, y, z, 0.0D, 0.0D, 0.0D, this.pitch, this.yaw);
			
		} else if(this.soundType==4) { //dirt
			world.playSound(x, y, z, TGSounds.BULLET_IMPACT_DIRT, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
	    	cp.createFX("Impact_BulletDirt", world, x, y, z, 0.0D, 0.0D, 0.0D, this.pitch, this.yaw);
	    	
		} else if(this.soundType==5) { //dirt, only sound
    		world.playSound(x, y, z, TGSounds.BULLET_IMPACT_DIRT, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
	    	
		} else if (this.soundType==-1){ //default
			world.playSound(x, y, z, TGSounds.BULLET_IMPACT_DIRT, SoundCategory.AMBIENT, 1.0f, 1.0f, distdelay);
	    	cp.createFX("Impact_BulletDefault", world, x, y, z, 0.0D, 0.0D, 0.0D, this.pitch, this.yaw);
			//this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D);
		}		

	}

	@Override
	public Identifier getID() {
		return TGPacketsS2C.GUN_IMPACT_FX;
	}

}
