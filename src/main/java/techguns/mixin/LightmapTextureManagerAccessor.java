package techguns.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.texture.NativeImage;

@Mixin(LightmapTextureManager.class)
public interface LightmapTextureManagerAccessor{

	@Accessor(value="image")
    public NativeImage getImage();

}
