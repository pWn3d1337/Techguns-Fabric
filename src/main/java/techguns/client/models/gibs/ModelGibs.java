package techguns.client.models.gibs;


import java.util.function.Function;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Deprecated
public abstract class ModelGibs extends Model{
	
	public ModelGibs(Function<Identifier, RenderLayer> layerFactory) {
		super(layerFactory);
	}

	public abstract void render(MatrixStack matrices, VertexConsumer vertices, int light, int part);
	
	public abstract int getNumGibs();
}