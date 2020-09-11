package techguns.api.guns;

import java.util.Iterator;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class GunManager {

	/**
	 * Return if the current off hand weapon can be used depending on dual wield system (1hand,2hand weapons)
	 * @param mainHand
	 * @param offHand
	 * @param ent
	 * @return
	 */
	public static boolean canUseOffhand(LivingEntity ent) {
		Iterable<ItemStack> items = ent.getItemsHand();
		ItemStack mh = ItemStack.EMPTY;
		ItemStack oh = ItemStack.EMPTY;
		if (items !=null) {
			Iterator<ItemStack> it= items.iterator();
			if (it.hasNext()) {
				mh = it.next();
				if (it.hasNext()) {
					oh = it.next();
				}
			}
		}
		return canUseOffhand(mh, oh, ent);
	}
	
	/**
	 * Return if the current off hand weapon can be used depending on dual wield system (1hand,2hand weapons)
	 * @param mainHand
	 * @param offHand
	 * @param ent
	 * @return
	 */
	public static boolean canUseOffhand(ItemStack mainHand, ItemStack offHand, LivingEntity ent) {
		if (!offHand.isEmpty()&& offHand.getItem() instanceof IGenericGun) {
			IGenericGun gun = (IGenericGun) offHand.getItem();
			if (gun.getGunHandType()==GunHandType.TWO_HANDED) {
				return false; //Don't allow two handed gun in off hand
			}
		}
		if (!mainHand.isEmpty()&& mainHand.getItem() instanceof IGenericGun) {
			IGenericGun gun = (IGenericGun) mainHand.getItem();
			if (gun.getGunHandType()==GunHandType.TWO_HANDED) {
				return false; //Don't allow 1handed gun in offhand, when mainhand has 2h gun
			}
		}
		return true;
	}
	
}
