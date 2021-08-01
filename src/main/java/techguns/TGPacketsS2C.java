package techguns;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.networking.ClientSidePacketRegistryImpl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.packets.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

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
		if (!world.isClient) {
			Collection<ServerPlayerEntity> watchingPlayers = PlayerLookup.tracking((ServerWorld) world, pos);

			sendTo(packet, watchingPlayers);
		}
	}

	public static void sendToAllTracking(TGBasePacket packet, Entity tracked_ent, boolean sendToTrackedEnt) {
		// .tracking returns an unmodifiable collection, so pass it to a list
		List<ServerPlayerEntity> watchingPlayers = new LinkedList<>(PlayerLookup.tracking(tracked_ent));

		if (!sendToTrackedEnt && watchingPlayers.contains(tracked_ent)){
			watchingPlayers.remove(tracked_ent);
		}
		if (sendToTrackedEnt && tracked_ent instanceof ServerPlayerEntity ply && !watchingPlayers.contains(tracked_ent)){
			watchingPlayers.add(ply);
		}

		sendTo(packet, watchingPlayers);
	}

	public static void sendToAllAroundEntity(TGBasePacket packet, Entity ent, double radius) {
		sendToAllAround(packet, ent.world, ent.getPos(), radius);
	}
	
	public static void sendToAllAround(TGBasePacket packet, World world, Vec3d pos, double radius) {
		if (!world.isClient) {
			Collection<ServerPlayerEntity> watchingPlayers = PlayerLookup.around((ServerWorld) world, pos, radius);
			sendTo(packet, watchingPlayers);
		}
	}
	
	public static void sendTo(TGBasePacket packet, PlayerEntity player) {
		if (player instanceof ServerPlayerEntity serverPlayer) {
			sendTo(packet, List.of(serverPlayer));
		}
	}
	
	public static void sendTo(TGBasePacket packet, Collection<ServerPlayerEntity> players) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        packet.pack(buf);

        players.forEach(player ->
				ServerPlayNetworking.send(player,packet.getID(),buf));
	}
}
