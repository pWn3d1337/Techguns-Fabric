package techguns.api.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public interface ClientGameJoinEvent {
	Event<ClientGameJoinEvent> EVENT = EventFactory.createArrayBacked(ClientGameJoinEvent.class, (listeners) -> (client) -> {
		for (ClientGameJoinEvent listener : listeners) {
			listener.onGameJoin(client);
		}
	});
	
	void onGameJoin(MinecraftClient client);
}
