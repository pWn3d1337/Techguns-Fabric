package techguns.api;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import techguns.TGCamos;
import techguns.TGCamos.WeaponCamoEntry;
import techguns.Techguns;

public interface ICamoChangeable {
	public int getCamoCount();

	public static boolean setCamo(ItemStack stack, Identifier camo){
		WeaponCamoEntry entry = TGCamos.getCamoEntry(stack.getItem(), camo);
		if (entry!=null) {
			CompoundTag tags = stack.getTag();
			if (tags == null) {
				tags = new CompoundTag();
				stack.setTag(tags);
			}
			tags.putString("camo", camo.toString());
			return true;
		}
		return false;
	}

	public default int switchCamo(ItemStack item){
		return this.switchCamo(item, false);
	}
	
	public default int switchCamo(ItemStack item,boolean back){
		CompoundTag tags = item.getTag();
		if (tags== null){
			tags = new CompoundTag();
			item.setTag(tags);
		}
		String camoID=null;
		if (tags.contains("camo")){
			camoID=tags.getString("camo");
		}
		
		WeaponCamoEntry entry = TGCamos.getCamoEntry(item.getItem(), new Identifier(camoID));
		int id = entry.camoindex;
		
		if (entry != null) {
		
			//ICamoChangeable it = (ICamoChangeable) item.getItem();
			
			if(back){
				id--;
			} else {
				id++;
			}
			
			Identifier nextCamo = TGCamos.getCamoByIndex(item.getItem(), id);
			
			if (nextCamo != null) {
				tags.putString("camo", nextCamo.toString());
				return id;
			} else {
				return 0;
			}
			
		} else {
			tags.remove("camo");
			return 0;
		}
	}
	
	public default int getCurrentCamoIndex(ItemStack item) {
		CompoundTag tags = item.getTag();
		int camoID=0;
		if (tags!=null && tags.contains("camo")){
			String id = tags.getString("camo");
			WeaponCamoEntry entry = TGCamos.getCamoEntry(item.getItem(), new Identifier(id));
			if (entry!=null) {
				camoID = entry.camoindex;
			}
		}
		return camoID;
	};
	
	public default List<Identifier> getCurrentCamoTextures(ItemStack item) {
		CompoundTag tags = item.getTag();
		if (tags != null){

			String camo=tags.getString("camo");
			if (camo != "") {
				WeaponCamoEntry entry = TGCamos.getCamoEntry(item.getItem(), new Identifier(camo));
				if (entry!=null) {
					return entry.textures;
				}
			}
		}
		return null;
	}
	
	public default String getCurrentCamoName(ItemStack item) {
		CompoundTag tags = item.getTag();
		if (tags != null){

			String camo=tags.getString("camo");
			if (camo != "") {
				return camo;
			}
		}
		return TGCamos.DEFAULT.toString();
	}
	
	public default int getFirstItemCamoDamageValue() {
		return 0;
	}
	
	public default boolean addBlockCamoChangeRecipes() {
		return true;
	}
}
