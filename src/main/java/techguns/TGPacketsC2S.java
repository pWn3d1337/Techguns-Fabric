package techguns;

import java.util.function.Function;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.impl.networking.ServerSidePacketRegistryImpl;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import techguns.packets.TGBasePacket;
import techguns.packets.c2s.PacketShootGun;
import techguns.packets.c2s.PacketTGKeybindPress;

public class TGPacketsC2S {

	public static final Identifier SHOOT_GUN = new TGIdentifier("shoot_gun");
	public static final Identifier KEYBIND_PRESS = new TGIdentifier("keybind_press");

	public static void initialize() {
		registerPacket(SHOOT_GUN, PacketShootGun::new);
		registerPacket(KEYBIND_PRESS, PacketTGKeybindPress::new);
	}
	
	public static void registerPacket(Identifier id, Function<PacketByteBuf, TGBasePacket> ctor) {
		ServerSidePacketRegistryImpl.INSTANCE.register(id, ((context, buf) -> {
			TGBasePacket packet = ctor.apply(buf);
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
