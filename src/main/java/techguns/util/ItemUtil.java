package techguns.util;

import net.minecraft.item.ItemStack;

public class ItemUtil {

	public static boolean isItemEqual(ItemStack item1, ItemStack item2){
		if(item1.isEmpty() && item2.isEmpty()){
			return true;
		} else if (item1.isEmpty() || item2.isEmpty()){
			return false;
		} else {
			return item1.getItem() == item2.getItem() && item1.getDamage() == item2.getDamage();
		}
	}
	
}
