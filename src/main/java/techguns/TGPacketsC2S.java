package techguns;

import java.util.function.Supplier;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import techguns.packets.PacketCraftAmmoBench;
import techguns.packets.TGBasePacket;
import techguns.packets.c2s.*;

public class TGPacketsC2S {

	public static final Identifier SHOOT_GUN = new TGIdentifier("shoot_gun");
	public static final Identifier SHOOT_GUN_TARGET = new TGIdentifier("shoot_gun_target");
	public static final Identifier KEYBIND_PRESS = new TGIdentifier("keybind_press");
	public static final Identifier MINING_UPDATE = new TGIdentifier("mining_update");
	public static final Identifier CLIENT_SWING_RECOIL = new TGIdentifier("client_swing_recoil");
	public static final Identifier CRAFT_AMMO_BENCH = new TGIdentifier("craft_ammo_bench");

	public static void initialize() {
		registerPacket(SHOOT_GUN, PacketShootGun::new);
		registerPacket(SHOOT_GUN_TARGET, PacketShootGunTarget::new);
		registerPacket(KEYBIND_PRESS, PacketTGKeybindPress::new);
		registerPacket(MINING_UPDATE, PacketTGToolMiningUpdate::new);
		registerPacket(CLIENT_SWING_RECOIL, PacketClientSwingRecoil::new);
		registerPacket(CRAFT_AMMO_BENCH, PacketCraftAmmoBench::new);
	}
	
	public static void registerPacket(Identifier id, Supplier<TGBasePacket> ctor) {
		ServerPlayNetworking.registerGlobalReceiver(id, (MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) -> {
			TGBasePacket packet = ctor.get();
			packet.unpack(buf);
			server.execute(() ->{
				packet.handle(player);
			});
		});
	}
	
	public static void sendToServer(TGBasePacket packet) {
        PacketByteBuf buf = PacketByteBufs.create();
        packet.pack(buf);
		ClientPlayNetworking.send(packet.getID(), buf);
	}
}
