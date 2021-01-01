package techguns.client.render.entities;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.util.Identifier;
import techguns.TGIdentifier;
import techguns.entities.projectiles.GenericBeamProjectile;

public class RenderTeslaProjectile extends RenderGenericBeamProjectile {

    public static final Identifier TEXTURE = new TGIdentifier("textures/fx/laser_blue.png");

    public RenderTeslaProjectile(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public Identifier getTexture(GenericBeamProjectile entity) {
        return TEXTURE;
    }

}
