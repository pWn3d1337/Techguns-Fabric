package techguns.items;

import net.minecraft.item.ItemStack;
import techguns.client.render.ITGItemRenderer;

public class GenericItemRenderHack extends GenericItem implements ITGItemRenderer {
	boolean useRenderHack;
	
	
	public GenericItemRenderHack(Settings settings) {
		super(settings);
		useRenderHack=true;
	}

	public GenericItemRenderHack(Settings settings, boolean useRenderHack) {
		super(settings);
		this.useRenderHack = useRenderHack;
	}

	@Override
	public boolean shouldUseRenderHack(ItemStack stack) {
		return useRenderHack;
	}

	
}
