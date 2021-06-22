package techguns.client.render.entities;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import techguns.TGIdentifier;
import techguns.client.render.TGRenderHelper;
import techguns.entities.projectiles.StoneBulletProjectile;

public class RenderStoneBulletProjectile extends RenderTextureProjectile<StoneBulletProjectile>{

	protected static final Identifier texture =  new TGIdentifier("textures/entity/handgunbullet.png");
	
	public RenderStoneBulletProjectile(EntityRendererFactory.Context ctx) {
		super(ctx);
    	textureLoc = texture;
    	scale=1.0f;
    	baseSize=0.1f;
	}

	@Override
	protected RenderLayer getRenderLayer(StoneBulletProjectile entity) {
		return TGRenderHelper.getProjectileCutout(texture);
	}

}
