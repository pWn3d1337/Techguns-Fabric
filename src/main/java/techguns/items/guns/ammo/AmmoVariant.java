package techguns.items.guns.ammo;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AmmoVariant {

	protected String key;
	protected ItemStack[] ammo;
	protected ItemStack[] bullet;
	
	public AmmoVariant(ItemStack[] ammo, ItemStack[] bullet) {
		this(AmmoTypes.TYPE_DEFAULT,ammo,bullet);
	}
	
	public AmmoVariant(String key, ItemStack[] ammo, ItemStack[] bullet) {
		super();
		this.key = key;
		this.bullet = bullet;
		this.ammo = ammo;
	}

	/**
	 * check if this variant uses the passes stack as ammo
	 */
	public boolean isAmmoForThisVariant(ItemStack stack){
		return ammo.length==1 && ammo[0].isItemEqual(stack);
	}

	public String getKey() {
		return key;
	}
}
