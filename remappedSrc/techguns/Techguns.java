package techguns;

import java.util.ArrayList;
import java.util.Arrays;

import net.fabricmc.api.ModInitializer;
import techguns.items.guns.ammo.AmmoTypes;

public class Techguns implements ModInitializer {
	public static final String MODID = "techguns";
		
	public static final AmmoTypes ammos = new AmmoTypes();
	public static final TGItems items = new TGItems();
	public static final TGSounds sounds = new TGSounds();
	public static final TGEntities entities = new TGEntities();
	public static final TGuns guns = new TGuns();
	protected ArrayList<ITGInitializer> initializers = new ArrayList<>(Arrays.asList(
	    	sounds,
	    	items,
			ammos,
	    	entities,
	    	guns
	    ));
	    
	
	@Override
	public void onInitialize() {
		for (ITGInitializer init : initializers) {
			init.init();
		}
		TGPacketsC2S.initialize();
		initializers.clear();
	}

}
