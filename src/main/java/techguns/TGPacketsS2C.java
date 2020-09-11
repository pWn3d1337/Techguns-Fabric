package techguns;

import java.util.function.Function;
import java.util.stream.Stream;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.fabricmc.fabric.impl.networking.ClientSidePacketRegistryImpl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.packets.GunFiredMessage;
import techguns.packets.PacketGunImpactFX;
import techguns.packets.PacketPlaySound;
import techguns.packets.PacketSpawnParticleOnEntity;
import techguns.packets.PacketSwapWeapon;
import techguns.packets.ReloadStartedMessage;
import techguns.packets.TGBasePacket;

public class TGPacketsS2C {

	public static final Identifier GUN_FIRED = new TGIdentifier("gun_fired");
	public static final Identifier RELOAD_STARTED = new TGIdentifier("reload_started");
	public static final Identifier SWAP_WEAPON = new TGIdentifier("swap_weapon");
	public static final Identifier PLAY_SOUND = new TGIdentifier("play_sound");
	public static final Identifier GUN_IMPACT_FX = new TGIdentifier("gun_impact_fx");
	public static final Identifier SPAWN_PARTICLE_ON_ENTITY = new TGIdentifier("spawn_particle_on_entity");
		
	public static void initialize() {
		registerPacket(GUN_FIRED, GunFiredMessage::new);
		registerPacket(RELOAD_STARTED, ReloadStartedMessage::new);
		registerPacket(SWAP_WEAPON, PacketSwapWeapon::new);
		registerPacket(PLAY_SOUND, PacketPlaySound::new);
		registerPacket(GUN_IMPACT_FX, PacketGunImpactFX::new);
		registerPacket(SPAWN_PARTICLE_ON_ENTITY, PacketSpawnParticleOnEntity::new);
	}
	
	public static void registerPacket(Identifier id, Function<PacketByteBuf, TGBasePacket> ctor) {
		ClientSidePacketRegistryImpl.INSTANCE.register(id, ((context, buf) -> {
			TGBasePacket packet = ctor.apply(buf);
			context.getTaskQueue().execute(() -> {
				packet.handle(context);
			});
        }));
	}

	public static void sendToAllTracking(TGBasePacket packet, Entity tracked_ent) {
		Stream<PlayerEntity> watchingPlayers = PlayerStream.watching(tracked_ent);
		sendTo(packet, watchingPlayers);
	}
	
	public static void sendToAllAroundEntity(TGBasePacket packet, Entity ent, double radius) {
		sendToAllAround(packet, ent.world, ent.getPos(), radius);
	}
	
	public static void sendToAllAround(TGBasePacket packet, World world, Vec3d pos, double radius) {
        Stream<PlayerEntity> watchingPlayers = PlayerStream.around(world, pos, radius);
        sendTo(packet, watchingPlayers);
	}
	
	public static void sendTo(TGBasePacket packet, Stream<PlayerEntity> players) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        packet.pack(buf);

        players.forEach(player ->
                ServerSidePacketRegistry.INSTANCE.sendToPlayer(player,packet.getID(),buf));
	}
}
