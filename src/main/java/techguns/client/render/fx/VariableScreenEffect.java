package techguns.client.render.fx;

import joptsimple.util.KeyValuePair;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

import java.util.AbstractMap;
import java.util.HashMap;


public class VariableScreenEffect implements IScreenEffect{

    public HashMap<String, IScreenEffect> effects = new HashMap<>();
    public IScreenEffect defaultEffect = null;

    public VariableScreenEffect(AbstractMap.SimpleEntry<String, IScreenEffect>... entries) {
        for (AbstractMap.SimpleEntry<String, IScreenEffect> entry : entries) {
            effects.put(entry.getKey(), entry.getValue());
        }
    }

    public VariableScreenEffect setDefaultEffect(IScreenEffect effect) {
        this.defaultEffect = effect;
        return this;
    }

    public void doRender(MatrixStack matrices, VertexConsumerProvider verticesProvider, float progress, float offsetX, float offsetY, float offsetZ, float scale, float rot_x, float rot_y, float rot_z, boolean is3p, String ammoVariant) {
        if (effects.containsKey(ammoVariant)) {
            effects.get(ammoVariant).doRender(matrices, verticesProvider, progress, offsetX, offsetY, offsetZ, scale, rot_x, rot_y, rot_z, is3p, ammoVariant);
        }else if (this.defaultEffect != null) {
            defaultEffect.doRender(matrices, verticesProvider, progress, offsetX, offsetY, offsetZ, scale, rot_x, rot_y, rot_z, is3p, ammoVariant);
        }
    }
}
