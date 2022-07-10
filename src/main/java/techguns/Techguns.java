package techguns;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import techguns.items.guns.ammo.AmmoTypes;

import java.util.ArrayList;
import java.util.Arrays;

public class Techguns implements ModInitializer {
	public static final String MODID = "techguns";
		
	public static final AmmoTypes ammos = new AmmoTypes();
	public static final TGItems items = new TGItems();
	public static final TGSounds sounds = new TGSounds();
	public static final TGEntities entities = new TGEntities();
	public static final TGuns guns = new TGuns();
	public static final TGCamos camos = new TGCamos();
	public static final TGEvents events = new TGEvents();
	public static final TGBlocks blocks = new TGBlocks();
	public static final TGRecipes recipes = new TGRecipes();
	protected ArrayList<ITGInitializer> initializers = new ArrayList<>(Arrays.asList(
	    	sounds,
			entities,
	    	items,
			ammos,
	    	guns,
	    	blocks,
	    	camos,
	    	events,
			recipes
	    ));

	@Override
	public void onInitialize() {
		//Register & get config
		AutoConfig.register(TGConfig.class, JanksonConfigSerializer::new);
		TGConfig.INSTANCE = AutoConfig.getConfigHolder(TGConfig.class).getConfig();

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

			Class<?> PacketHandlerClass = net.minecraft.network.NetworkState.class.getDeclaredClasses()[1];
			//Class<?> PacketHandlerClass = Class.forName("net.minecraft.network.NetworkState$PacketHandler");
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
