package techguns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import techguns.api.ICamoChangeable;

public class TGCamos implements ITGInitializer {

	protected static HashMap<Item, WeaponCamoList> weapon_camos = new HashMap<>();
	
	protected static final Identifier DEFAULT = new TGIdentifier("default");
	
	@Override
	public void init() {
		registerWeaponCamo(TGuns.M4, DEFAULT, new TGIdentifier("textures/guns/m4texture.png"));
		registerWeaponCamo(TGuns.M4, new TGIdentifier("woodland"), new TGIdentifier("textures/guns/m4texture_1.png"));
		registerWeaponCamo(TGuns.M4, new TGIdentifier("desert"), new TGIdentifier("textures/guns/m4texture_2.png"));
		registerWeaponCamo(TGuns.M4, new TGIdentifier("snow"), new TGIdentifier("textures/guns/m4texture_3.png"));
		registerWeaponCamo(TGuns.M4, new TGIdentifier("hazard"), new TGIdentifier("textures/guns/m4texture_4.png"));
		
		registerWeaponCamo(TGuns.ROCKET_LAUNCHER, DEFAULT, new TGIdentifier("textures/guns/rocketlauncher.png"));
		registerWeaponCamo(TGuns.ROCKET_LAUNCHER, new TGIdentifier("wasteland"), new TGIdentifier("textures/guns/rocketlauncher_1.png"));
				
		registerWeaponCamo(TGuns.AK47, DEFAULT, new TGIdentifier("textures/guns/ak47texture.png"));
		registerWeaponCamo(TGuns.AK47, new TGIdentifier("blue"), new TGIdentifier("textures/guns/ak47texture_1.png"));
			
		registerWeaponCamo(TGuns.AUG, DEFAULT, new TGIdentifier("textures/guns/augtexture.png"));
		registerWeaponCamo(TGuns.AUG, new TGIdentifier("black"), new TGIdentifier("textures/guns/augtexture_1.png"));
		registerWeaponCamo(TGuns.AUG, new TGIdentifier("woodland"), new TGIdentifier("textures/guns/augtexture_2.png"));
		registerWeaponCamo(TGuns.AUG, new TGIdentifier("desert"), new TGIdentifier("textures/guns/augtexture_3.png"));
		registerWeaponCamo(TGuns.AUG, new TGIdentifier("nether"), new TGIdentifier("textures/guns/augtexture_4.png"));
				
		registerWeaponCamo(TGuns.BOLTACTION, DEFAULT, new TGIdentifier("textures/guns/boltactionrifle.png"));
		registerWeaponCamo(TGuns.BOLTACTION, new TGIdentifier("woodland"), new TGIdentifier("textures/guns/boltactionrifle_1.png"));
		registerWeaponCamo(TGuns.BOLTACTION, new TGIdentifier("night"), new TGIdentifier("textures/guns/boltactionrifle_2.png"));
		
		registerWeaponCamo(TGuns.VECTOR, DEFAULT, new TGIdentifier("textures/guns/vector_texture.png"));
		registerWeaponCamo(TGuns.VECTOR, new TGIdentifier("black"), new TGIdentifier("textures/guns/vector_texture_1.png"));
		
		registerWeaponCamo(TGuns.SCAR, DEFAULT, new TGIdentifier("textures/guns/scar_texture.png"));
		registerWeaponCamo(TGuns.SCAR, new TGIdentifier("black"), new TGIdentifier("textures/guns/scar_texture_1.png"));
		
		FabricItemGroupBuilder.create(
				new TGIdentifier("techguns_camos"))
				.icon(() -> new ItemStack(TGuns.M4))
				.appendItems(stacks -> {
					addAllCamoItems(stacks);
				})
				.build();
	}
	
	
	protected void addAllCamoItems(List<ItemStack> list) {
		for (Item it : weapon_camos.keySet()) {
			
			ItemStack gun = new ItemStack(it);
			gun.getItem().onCraft(gun, null, null);
			
			WeaponCamoList l = weapon_camos.get(it);
			for (Identifier entry : l.camos.keySet()) {
				ItemStack gun_to_add = gun.copy();
				gun_to_add.getTag().putString("camo", entry.toString());
				
				list.add(gun_to_add);
			}
		}
	}
	
	public static int getCamoCount(Item it) {
		if (it instanceof ICamoChangeable) {
			WeaponCamoList list = weapon_camos.getOrDefault(it, null);
			if (list!=null) {
				return list.camo_names.size();
			}
		}
		return 0;
	}
	

	public static boolean hasCamos(Item it) {
		return weapon_camos.containsKey(it);
	}
	
	/**
	 * Get camo name by index for an item, or null if invalid
	 * @param it
	 * @param index
	 * @return
	 */
	@Nullable
	public static Identifier getCamoByIndex(Item it, int index) {
		WeaponCamoList list = weapon_camos.getOrDefault(it, null);
		if (list !=null) {
			return list.getCamoWithIndex(index);
		}
		return null;
	}
	
   /**
    * Return the camo entry for this key, or null
    * @param it
    * @param key
    * @return
    */
	@Nullable
	public static WeaponCamoEntry getCamoEntry(Item it, Identifier key) {
		if (weapon_camos.containsKey(it)) {
			WeaponCamoList camos = weapon_camos.get(it);
			return camos.camos.getOrDefault(key, null);
		}
		return null;
	}
	
	public static void registerWeaponCamo(Item item, Identifier key, Identifier... textures) {
		if (!weapon_camos.containsKey(item)) {
			weapon_camos.put(item, new WeaponCamoList());
		}
		WeaponCamoList camos = weapon_camos.get(item);
		camos.add(key, textures);
	}
	
	public static class WeaponCamoList {
		public HashMap<Identifier, WeaponCamoEntry> camos;
		public ArrayList<Identifier> camo_names;
		
		public WeaponCamoList() {
			this.camos = new HashMap<>();
			this.camo_names = new ArrayList<>();
		}
		
		public void add(Identifier name, Identifier... textures) {
			this.camo_names.add(name);
			this.camos.put(name, new WeaponCamoEntry(this.camo_names.size()-1, textures));
		}
		
		public Identifier getCamoWithIndex(int index) {
			if (!this.camo_names.isEmpty()) {
				while (index > this.camo_names.size()-1) {
					index -= this.camo_names.size();
				}
				while (index < 0) {
					index += this.camo_names.size();
				}
				return this.camo_names.get(index);								
			}
			return null;
		}
	}
	
	public static class WeaponCamoEntry {
		public List<Identifier> textures;
		public int camoindex;
		
		public WeaponCamoEntry(int camoindex, Identifier... textures) {
			super();
			this.textures = new ArrayList<Identifier>(textures.length);
			this.textures.addAll(Arrays.asList(textures));
			this.camoindex = camoindex;
		}
	}
}
