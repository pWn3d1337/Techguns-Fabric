package techguns.client.modelloader;

import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class TGObjLoader implements ModelResourceProvider, Function<ResourceManager, ModelResourceProvider> {

	@Override
	public ModelResourceProvider apply(ResourceManager t) {
		return this;
	}

	@Override
	public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context)
			throws ModelProviderException {
		// TODO Auto-generated method stub
		return null;
	}

}
