package techguns.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import techguns.TGPacketsS2C;
import techguns.client.ClientProxy;
import techguns.packets.PacketPlaySound;
import techguns.sounds.TGSoundCategory;

public class SoundUtil {

	/**
	 * Plays a moving Sound on the specified Entity, in front of the entity.
	 */
	public static void playSoundOnEntityGunPosition(World world, Entity entity, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving, boolean playOnOwnPlayer, TGSoundCategory category, EntityCondition condition) {
		if (!world.isClient) {
			TGPacketsS2C.sendToAllAroundEntity(new PacketPlaySound(soundname, entity, volume, pitch, repeat, moving, true, playOnOwnPlayer, category), entity, 100.0);
		}else {
			MinecraftClient mc = MinecraftClient.getInstance();
			if(playOnOwnPlayer || mc.player != entity) {
				ClientProxy.get().playSoundOnEntity(entity, soundname, volume, pitch, false, moving,true, category, condition);
			}
		}
	}
	
	/**
	 * Plays a moving Sound on the specified Entity, in front of the entity.
	 */
	public static void playSoundOnEntityGunPosition(World world, Entity entity, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving, boolean playOnOwnPlayer, TGSoundCategory category) {
		playSoundOnEntityGunPosition(world, entity, soundname, volume, pitch, repeat, moving, playOnOwnPlayer, category, EntityCondition.NONE);
	}
	
	/**
	 * Plays a moving Sound on the specified Entity, in front of the entity.
	 */
	public static void playSoundOnEntityGunPosition(World world, Entity entity, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving,  TGSoundCategory category) {
		playSoundOnEntityGunPosition(world, entity, soundname, volume, pitch, repeat, moving, false, category);
	}
	
	/**
	 * Like playSoundOnEntityGunPosition() but does a check for last played own reload.
	 */
	public static void playReloadSoundOnEntity(World world, Entity entity, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving,  TGSoundCategory category) {
		if (!world.isClient) {
			TGPacketsS2C.sendToAllAroundEntity(new PacketPlaySound(soundname, entity, volume, pitch, repeat, moving, true, category), entity, 100.0);
		}else {
			ClientProxy cp = ClientProxy.get();
			if (cp.lastReloadsoundPlayed - System.currentTimeMillis()<-500){
				cp.lastReloadsoundPlayed = System.currentTimeMillis();
				cp.playSoundOnEntity(entity, soundname, volume, pitch, repeat, moving,true, category, EntityCondition.NONE);
			}
		}
	}
	
	
	/**
	 * Plays a moving Sound on the specified Entity
	 */
	/*public static void playSoundOnEntity(World world, Entity entity, SoundEvent soundname, float volume, float pitch, boolean repeat,  TGSoundCategory category) {
		if (!world.isRemote) {
		TGPackets.network.sendToAllAround(new PacketPlaySound(soundname, entity, volume, pitch, repeat, true, category),
				new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 100.0f));
		}else {
			Techguns.proxy.playSoundOnEntity(entity, soundname, volume, pitch, repeat, true, false,category);
			//Minecraft.getMinecraft().getSoundHandler().playSound(new TGSound(soundname,entity, volume, pitch, repeat));
		}
	}*/
	
	/**
	 * Plays a non-moving Sound at the specified Entity's position
	 */
	/*public static void playSoundAtEntityPos(World world, Entity entity, SoundEvent soundname, float volume, float pitch, boolean repeat, TGSoundCategory category) {
		if (!world.isRemote) {
		TGPackets.network.sendToAllAround(new PacketPlaySound(soundname, entity, volume, pitch, repeat, false, category),
				new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 100.0f));
		}else {
			//Minecraft.getMinecraft().getSoundHandler().playSound(new TGSound(soundname, (float)entity.posX, (float)entity.posY, (float)entity.posZ, volume, pitch, repeat));
			//Minecraft.getMinecraft().getSoundHandler().playSound(new TGSound(soundname,entity, volume, pitch, repeat, false));
			Techguns.proxy.playSoundOnEntity(entity, soundname, volume, pitch, repeat, false, false,category);
		}
	}*/
	
}
