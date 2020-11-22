package techguns.api.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;

/**
 * Fired when disconect() is called in MinecraftClient
 *
 */
public interface ClientDisconnectEvent {

	Event<ClientDisconnectEvent> EVENT = EventFactory.createArrayBacked(ClientDisconnectEvent.class, (listeners) -> (client) -> {
		for (ClientDisconnectEvent listener : listeners) {
			listener.onDisconnect(client);
		}
	});
	
	void onDisconnect(MinecraftClient client);
}
