package techguns.client.render;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.item.Item;
import techguns.api.render.IItemRenderer;
import net.minecraft.util.Identifier;

public class TGRenderRegistries {
	protected static HashMap<Item, IItemRenderer> renderRegistry = new HashMap<>();
	
	protected static HashMap<Item, WeaponCamoList> weapon_camos;
	
	public static void registerItemRenderer(Item item, IItemRenderer renderer) {
		renderRegistry.put(item, renderer);
	};
	
	public static IItemRenderer getRendererForItem(Item item) {
		return renderRegistry.get(item);
	}
	
	
	public static void registerWeaponCamo(Item item, Identifier key, Identifier texture) {
		if (!weapon_camos.containsKey(item)) {
			weapon_camos.put(item, new WeaponCamoList());
		}
		WeaponCamoList camos = weapon_camos.get(item);
		camos.add(key, texture);
	}
	
	public static class WeaponCamoList {
		public HashMap<Identifier, WeaponCamoEntry> camos;
		public ArrayList<Identifier> camo_names;
		
		public WeaponCamoList() {
			this.camos = new HashMap<>();
			this.camo_names = new ArrayList<>();
		}
		
		public void add(Identifier name, Identifier texture) {
			this.camo_names.add(name);
			this.camos.put(name, new WeaponCamoEntry(texture, this.camo_names.size()-1));
		}
	}
	
	public static class WeaponCamoEntry {
		public Identifier texture;
		public int camoindex;
		
		public WeaponCamoEntry(Identifier texture, int camoindex) {
			super();
			this.texture = texture;
			this.camoindex = camoindex;
		}
	}
}
