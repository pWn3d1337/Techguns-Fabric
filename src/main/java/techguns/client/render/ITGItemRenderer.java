package techguns.client.render;

import net.minecraft.item.ItemStack;

/**
 * indicates that this item should use 1.7 style render hook
 */
public interface ITGItemRenderer {
	/**
	 * Return if the passed ItemStack should use the renderhack
	 */
	default boolean shouldUseRenderHack(ItemStack stack){return true;};
	
	default boolean isModelBase(ItemStack stack){return true;};
}
