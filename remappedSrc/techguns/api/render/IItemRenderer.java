package techguns.api.render;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

/**
 * <3 <3 <3  1.7.10  <3 <3 <3
 */
public interface IItemRenderer {

	public void renderItem(LivingEntity elb, ModelTransformation.Mode transform, MatrixStack matrices, ItemStack stack, boolean leftHanded, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model);
	
}