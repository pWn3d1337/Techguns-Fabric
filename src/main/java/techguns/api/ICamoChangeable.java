package techguns.api;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import techguns.TGCamos;
import techguns.TGCamos.CamoEntry;

public interface ICamoChangeable {
	public int getCamoCount();

	public static boolean setCamo(ItemStack stack, Identifier camo){
		CamoEntry entry = TGCamos.getCamoEntry(stack.getItem(), camo);
		if (entry!=null) {
			NbtCompound tags = stack.getNbt();
			if (tags == null) {
				tags = new NbtCompound();
				stack.setNbt(tags);
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
		NbtCompound tags = item.getNbt();
		if (tags== null){
			tags = new NbtCompound();
			item.setNbt(tags);
		}
		String camoID=null;
		if (tags.contains("camo")){
			camoID=tags.getString("camo");
		}
		
		CamoEntry entry = TGCamos.getCamoEntry(item.getItem(), new Identifier(camoID));
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
		NbtCompound tags = item.getNbt();
		int camoID=0;
		if (tags!=null && tags.contains("camo")){
			String id = tags.getString("camo");
			CamoEntry entry = TGCamos.getCamoEntry(item.getItem(), new Identifier(id));
			if (entry!=null) {
				camoID = entry.camoindex;
			}
		}
		return camoID;
	};
	
	public default List<Identifier> getCurrentCamoTextures(ItemStack item) {
		NbtCompound tags = item.getNbt();
		if (tags != null){

			String camo=tags.getString("camo");
			if (camo != "") {
				CamoEntry entry = TGCamos.getCamoEntry(item.getItem(), new Identifier(camo));
				if (entry!=null) {
					return entry.textures;
				}
			}
		}
		return null;
	}
	
	public default String getCurrentCamoName(ItemStack item) {
		NbtCompound tags = item.getNbt();
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
