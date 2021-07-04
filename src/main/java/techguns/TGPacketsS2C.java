package techguns;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.packets.GunFiredMessage;
import techguns.packets.PacketEntityAdditionalSpawnData;
import techguns.packets.PacketEntityDeathType;
import techguns.packets.PacketGunImpactFX;
import techguns.packets.PacketPlaySound;
import techguns.packets.PacketShowKeybindConfirmedMessage;
import techguns.packets.PacketSpawnParticle;
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
	public static final Identifier SPAWN_PARTICLE = new TGIdentifier("spawn_particle");
	public static final Identifier KEYBIND_CONFIRMED_MESSAGE = new TGIdentifier("keybind_confirmed_message");
	public static final Identifier PROJECTILE_ADDITIONAL_SPAWNDATA = new TGIdentifier("pojectile_spawndata");
	public static final Identifier ENTITY_DEATH_TYPE = new TGIdentifier("entity_death_type");
		
	public static void initialize() {
		registerPacket(GUN_FIRED, GunFiredMessage::new);
		registerPacket(RELOAD_STARTED, ReloadStartedMessage::new);
		registerPacket(SWAP_WEAPON, PacketSwapWeapon::new);
		registerPacket(PLAY_SOUND, PacketPlaySound::new);
		registerPacket(GUN_IMPACT_FX, PacketGunImpactFX::new);
		registerPacket(SPAWN_PARTICLE_ON_ENTITY, PacketSpawnParticleOnEntity::new);
		registerPacket(SPAWN_PARTICLE, PacketSpawnParticle::new);
		registerPacket(KEYBIND_CONFIRMED_MESSAGE, PacketShowKeybindConfirmedMessage::new);
		registerPacket(PROJECTILE_ADDITIONAL_SPAWNDATA, PacketEntityAdditionalSpawnData::new);
		registerPacket(ENTITY_DEATH_TYPE, PacketEntityDeathType::new);
	}
	
	public static void registerPacket(Identifier id, Supplier<TGBasePacket> ctor) {
		ClientSidePacketRegistryImpl.INSTANCE.register(id, ((context, buf) -> {
			TGBasePacket packet = ctor.get();
			packet.unpack(buf);
			context.getTaskQueue().execute(() -> {
				packet.handle(context);
			});
        }));
	}

	public static void sentToAllTrackingPos(TGBasePacket packet, World world, BlockPos pos) {
		//TODO 1.17 migrate to non-deprecated API
		Stream<PlayerEntity> watchingPlayers = PlayerStream.watching(world, pos);
		sendTo(packet, watchingPlayers);
	}
	
	public static void sendToAllTracking(TGBasePacket packet, Entity tracked_ent, boolean sendToTrackedEnt) {
		Stream<PlayerEntity> watchingPlayers = PlayerStream.watching(tracked_ent);

		List<PlayerEntity> players = watchingPlayers.collect(Collectors.toList());

		if (tracked_ent instanceof PlayerEntity) {
			boolean addTracked=sendToTrackedEnt;

			ListIterator<PlayerEntity> iter = players.listIterator();
			while(iter.hasNext()) {
				PlayerEntity ply = iter.next();
				if (ply==tracked_ent) {
					if (sendToTrackedEnt) {
						addTracked=false;
					} else {
						iter.remove();
					}
				}
			}
			if (addTracked) {
				players.add((PlayerEntity) tracked_ent);
			}
		}
		sendTo(packet, players.stream());
	}
	
	public static void sendToAllAroundEntity(TGBasePacket packet, Entity ent, double radius) {
		sendToAllAround(packet, ent.world, ent.getPos(), radius);
	}
	
	public static void sendToAllAround(TGBasePacket packet, World world, Vec3d pos, double radius) {
        Stream<PlayerEntity> watchingPlayers = PlayerStream.around(world, pos, radius);
        sendTo(packet, watchingPlayers);
	}
	
	public static void sendTo(TGBasePacket packet, PlayerEntity player) {
		sendTo(packet, Stream.of(player));
	}
	
	public static void sendTo(TGBasePacket packet, Stream<PlayerEntity> players) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        packet.pack(buf);

        players.forEach(player ->
                ServerSidePacketRegistry.INSTANCE.sendToPlayer(player,packet.getID(),buf));
	}
}
