package techguns;

import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.impl.networking.ServerSidePacketRegistryImpl;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import techguns.packets.TGBasePacket;
import techguns.packets.c2s.*;

public class TGPacketsC2S {

	public static final Identifier SHOOT_GUN = new TGIdentifier("shoot_gun");
	public static final Identifier SHOOT_GUN_TARGET = new TGIdentifier("shoot_gun_target");
	public static final Identifier KEYBIND_PRESS = new TGIdentifier("keybind_press");
	public static final Identifier MINING_UPDATE = new TGIdentifier("mining_update");
	public static final Identifier CLIENT_SWING_RECOIL = new TGIdentifier("client_swing_recoil");

	public static void initialize() {
		registerPacket(SHOOT_GUN, PacketShootGun::new);
		registerPacket(SHOOT_GUN_TARGET, PacketShootGunTarget::new);
		registerPacket(KEYBIND_PRESS, PacketTGKeybindPress::new);
		registerPacket(MINING_UPDATE, PacketTGToolMiningUpdate::new);
		registerPacket(CLIENT_SWING_RECOIL, PacketClientSwingRecoil::new);
	}
	
	public static void registerPacket(Identifier id, Supplier<TGBasePacket> ctor) {
		ServerSidePacketRegistryImpl.INSTANCE.register(id, ((context, buf) -> {
			TGBasePacket packet = ctor.get();
			packet.unpack(buf);
			context.getTaskQueue().execute(() -> {
				packet.handle(context);
			});
        }));
	}
	
	public static void sendToServer(TGBasePacket packet) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        packet.pack(buf);
        ClientSidePacketRegistry.INSTANCE.sendToServer(packet.getID(), buf);
	}
}
