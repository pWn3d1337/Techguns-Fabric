package techguns.client.render.entities;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import techguns.TGIdentifier;
import techguns.entities.projectiles.BioGunProjectile;

public class RenderBioGunProjectile extends RenderTextureProjectile<BioGunProjectile>{

	protected static final Identifier textureLoc = new TGIdentifier("textures/entity/bioblob.png");
	
	public RenderBioGunProjectile(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	@Override
	public Identifier getTexture(BioGunProjectile entity) {
		return textureLoc;
	}

	@Override
	protected float getScale(BioGunProjectile entity) {
		float scale = 1.0f;
		switch (entity.level) {
		case 1:
			scale = 1.25f;
			break;
		case 2:
			scale = 2.5f;
			break;
		case 3:
			scale = 4.0f;
			break;
		}
		return this.baseSize * scale;
	}	

}
