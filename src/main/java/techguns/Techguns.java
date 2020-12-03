package techguns;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

import net.fabricmc.api.ModInitializer;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.NetworkState;
import techguns.items.guns.ammo.AmmoTypes;
import techguns.mixin.NetworkStateMixin;
import techguns.packets.PacketSpawnEntity;

public class Techguns implements ModInitializer {
	public static final String MODID = "techguns";
		
	public static final AmmoTypes ammos = new AmmoTypes();
	public static final TGItems items = new TGItems();
	public static final TGSounds sounds = new TGSounds();
	public static final TGEntities entities = new TGEntities();
	public static final TGuns guns = new TGuns();
	public static final TGCamos camos = new TGCamos();
	public static final TGEvents events = new TGEvents();
	protected ArrayList<ITGInitializer> initializers = new ArrayList<>(Arrays.asList(
	    	sounds,
	    	items,
			ammos,
	    	entities,
	    	guns,
	    	camos,
	    	events
	    ));
	    
	
	@Override
	public void onInitialize() {
		for (ITGInitializer init : initializers) {
			init.init();
		}
		TGPacketsC2S.initialize();
		initializers.clear();
		initializers=null;
		
		/*NetworkStateMixin play = (NetworkStateMixin) (Object)NetworkState.PLAY;
		Map map = play.getPacketHandlers();
		
		Map handlerStateMap = play.getHANDLER_STATE_MAP();		
		try {
			Class<?> PacketHandlerClass = Class.forName("net.minecraft.network.NetworkState$PacketHandler"); 
			Object handler = map.get(NetworkSide.CLIENTBOUND);
			
			Method m_register = PacketHandlerClass.getDeclaredMethod("register", Class.class, Supplier.class);
			Supplier<PacketSpawnEntity> supplier = PacketSpawnEntity::new;
	
			m_register.invoke(handler, PacketSpawnEntity.class, supplier);
			handlerStateMap.put(PacketSpawnEntity.class, NetworkState.PLAY);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}*/
	}

}
