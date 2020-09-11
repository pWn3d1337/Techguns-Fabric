package techguns.items;

import net.minecraft.item.Item;
import techguns.TGItems;

public class GenericItem extends Item {

	public GenericItem(Settings settings) {
		super(settings.group(TGItems.ITEM_GROUP_TECHGUNS));
	}

}
