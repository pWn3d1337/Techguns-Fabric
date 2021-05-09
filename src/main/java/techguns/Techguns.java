package techguns;

import java.util.ArrayList;
import java.util.Arrays;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;
import techguns.items.guns.ammo.AmmoTypes;
import techguns.recipes.*;

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
	protected ArrayList<ITGInitializer> initializers = new ArrayList<>(Arrays.asList(
	    	sounds,
	    	items,
			ammos,
	    	entities,
	    	guns,
	    	blocks,
	    	camos,
	    	events
	    ));
	    
	public static SpecialRecipeSerializer<AmmoChangeRecipe> AMMO_CHANGE_SERIALIZER;

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

		NBTShapedRecipe.SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new TGIdentifier("crafting_shaped_nbt"), new NBTShapedRecipe.Serializer());

		TransferAmmoRecipe.SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new TGIdentifier("transfer_ammo"), new TransferAmmoRecipe.Serializer());

		MiningHeadUpgradeRecipe.SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new TGIdentifier("mininghead_upgrade"), new MiningHeadUpgradeRecipe.Serializer());

		AMMO_CHANGE_SERIALIZER = (SpecialRecipeSerializer<AmmoChangeRecipe>)Registry.register(Registry.RECIPE_SERIALIZER, new TGIdentifier("ammo_change_recipe"), new SpecialRecipeSerializer<AmmoChangeRecipe>(AmmoChangeRecipe::new));

		if (Recipewriter.WRITE_RECIPES) {
			Recipewriter.generateItemRecipes();
		}

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
