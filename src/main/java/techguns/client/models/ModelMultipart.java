package techguns.client.models;

import java.util.function.Function;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;

import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import techguns.api.guns.IGenericGun;

public abstract class ModelMultipart extends Model {

	protected int textureWidth = 64;
	protected int textureHeight = 64;

	protected static final double scale = 0.0625;
	
	protected static final int bright_light = 15728880; //240,240
	
	public ModelMultipart(Function<Identifier, RenderLayer> layerFactory) {
		super(layerFactory);
	}

	
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
			float blue, float alpha) {
		render(null, matrices, vertices, 0, 0f, ModelTransformationMode.NONE, 0, 0f, 0f, light, overlay);
	}

	public abstract void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
								float reloadProgress, ModelTransformationMode transformType, int part, float fireProgress, float chargeProgress, int light, int overlay);

	
	public RenderLayer getLayerForPart(IGenericGun gun, ItemStack stack, Identifier texture, int part) {
		return this.getLayer(texture);
	}
	

	protected void setRotation(techguns.client.models.ModelPart model, float x, float y, float z) {
		model.pitch = x;
		model.yaw = y;
		model.roll = z;
	}
	
}
