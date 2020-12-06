package techguns.client.modelloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.Resource;
import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import techguns.Techguns;

public class TGObjLoader implements ModelResourceProvider, Function<ResourceManager, ModelResourceProvider> {
	public static final TGObjLoader INSTANCE = new TGObjLoader();

	protected final HashMap<ModelIdentifier, ModelLoadParameters> manuallyLoadedModels = new HashMap<>();

	public Set<ModelIdentifier> getManuallyLoadedModels() {
		return manuallyLoadedModels.keySet();
	}

	public void registerManualModel(ModelIdentifier identifier){
		this.manuallyLoadedModels.put(identifier, new ModelLoadParameters(true, false));
	}

	public void registerManualModel(Identifier identifier){
		this.manuallyLoadedModels.put(new ModelIdentifier(identifier, null), new ModelLoadParameters(true, false));
	}

	public void registerManualModel(Identifier identifier, boolean flip_v){
		this.manuallyLoadedModels.put(new ModelIdentifier(identifier, null), new ModelLoadParameters(true, flip_v));
	}

	protected static final ModelLoadParameters DEFAULT_PARAMS = new ModelLoadParameters(false,false);

	protected static class ModelLoadParameters{
		boolean flip_v;
		boolean custom_texture;

		public ModelLoadParameters(boolean custom_texture, boolean flip_v){
			this.custom_texture=custom_texture;
			this.flip_v = flip_v;
		}
	}

	@Override
	public ModelResourceProvider apply(ResourceManager t) {
		return this;
	}

	@Override
	public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context)
			throws ModelProviderException {

		//only load techguns .obj models
		if (resourceId.getNamespace().equals(Techguns.MODID) && resourceId.getPath().endsWith(".obj")){
			ResourceManager resourceManager = MinecraftClient.getInstance().getResourceManager();

			try {
				Identifier modelId = new Identifier(resourceId.getNamespace(), "models/"+resourceId.getPath());
				Resource r = resourceManager.getResource(modelId);
				BufferedReader br = new BufferedReader(new InputStreamReader(r.getInputStream()));

				ModelLoadParameters params = this.manuallyLoadedModels.getOrDefault(resourceId, DEFAULT_PARAMS);

				return TGObjModel.parseFromFile(br, modelId, resourceManager, params);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

}
