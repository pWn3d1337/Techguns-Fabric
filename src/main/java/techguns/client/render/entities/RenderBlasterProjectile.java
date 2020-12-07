package techguns.client.render.entities;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.util.Identifier;
import techguns.TGIdentifier;
import techguns.entities.projectiles.GenericProjectile;

public class RenderBlasterProjectile extends GenericProjectileRenderer {
    private static final Identifier textureLoc = new TGIdentifier("textures/fx/laser3.png");

    public RenderBlasterProjectile(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public Identifier getTexture(GenericProjectile entity) {
        return textureLoc;
    }
}
