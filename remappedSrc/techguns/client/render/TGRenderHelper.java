package techguns.client.render;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import techguns.client.render.fx.IScreenEffect.RenderType;

//import net.minecraft.client.render.OpenGlHelper;

public class TGRenderHelper {
	
	protected static float lastBrightnessX=0;
	protected static float lastBrightnessY=0;
	
	protected static int lastBlendFuncSrc=0;
	protected static int lastBlendFuncDest=0;

	protected static final RenderPhase.Transparency LIGHTNING_TRANSPARENCY = new RenderPhase.Transparency("techguns_lightning_transparency", () -> {
	      RenderSystem.enableBlend();
	      RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE);
	   }, () -> {
	      RenderSystem.disableBlend();
	      RenderSystem.defaultBlendFunc();
	   });
	
	protected static final RenderPhase.Target PARTICLES_TARGET = new RenderPhase.Target("tg_particles_target", () -> {
        if (MinecraftClient.isFabulousGraphicsOrBetter()) {
           MinecraftClient mc = MinecraftClient.getInstance();
           mc.worldRenderer.getParticlesFramebuffer().beginWrite(false);
        }

     }, () -> {
        if (MinecraftClient.isFabulousGraphicsOrBetter()) {
           MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
        }

     });
	protected static final RenderPhase.WriteMaskState ALL_MASK = new RenderPhase.WriteMaskState(true, true);
	protected static final RenderPhase.WriteMaskState COLOR_MASK = new RenderPhase.WriteMaskState(true, false);
	protected static final RenderPhase.ShadeModel SMOOTH_SHADE_MODEL = new RenderPhase.ShadeModel(true);
	
	protected static final RenderPhase.Cull DISABLE_CULLING = new RenderPhase.Cull(false);
	
	public static RenderLayer get_fx_renderlayer(Identifier texture) {
		return RenderLayer.of("techguns_fx", VertexFormats.POSITION_TEXTURE_COLOR, 7, 256, false, true, RenderLayer.MultiPhaseParameters.builder().texture(new RenderPhase.Texture(texture, false, false)).writeMaskState(COLOR_MASK).transparency(LIGHTNING_TRANSPARENCY).target(PARTICLES_TARGET).shadeModel(SMOOTH_SHADE_MODEL).cull(DISABLE_CULLING).build(false));
	}
	
	public static RenderLayer get_fx_particlelayer(Identifier texture) {
		return RenderLayer.of("techguns_particle", VertexFormats.POSITION_TEXTURE_COLOR_LIGHT, 7, 256, false, true, RenderLayer.MultiPhaseParameters.builder().texture(new RenderPhase.Texture(texture, false, false)).writeMaskState(COLOR_MASK).transparency(LIGHTNING_TRANSPARENCY).target(PARTICLES_TARGET).shadeModel(SMOOTH_SHADE_MODEL).cull(DISABLE_CULLING).build(false));
	}
	
	//TODO OpenGLHelper
	public static void enableFXLighting()
    {
    	//lastBrightnessX= OpenGlHelper.lastBrightnessX;
		//lastBrightnessY= OpenGlHelper.lastBrightnessY;

		GlStateManager.disableLighting();
		//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
    }
	
    public static void disableFXLighting()
    {
    	GlStateManager.enableLighting();
    	//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
    }
    
    /*public static void enableFluidGlow(int luminosity) {
    	lastBrightnessX= OpenGlHelper.lastBrightnessX;
		lastBrightnessY= OpenGlHelper.lastBrightnessY;
		
		float newLightX = Math.min((luminosity/15.0f)*240.0f + lastBrightnessX, 240.0f);
		float newLightY = Math.min((luminosity/15.0f)*240.0f + lastBrightnessY, 240.0f);
		
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, newLightX, newLightY);
    }
    
    public static void disableFluidGlow() {
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
    }*/
	
    /**
     * This includes FXLighting!
     */
    public static void enableBlendMode(RenderType type) {
    	if (type != RenderType.SOLID) {
    		RenderSystem.enableBlend();
    	}
        if (type == RenderType.ALPHA) {
        	lastBlendFuncSrc = GlStateManager.getInteger(GL11.GL_BLEND_SRC);
			lastBlendFuncDest = GlStateManager.getInteger(GL11.GL_BLEND_DST);
			RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        	
        } else if (type == RenderType.ADDITIVE || type==RenderType.NO_Z_TEST) {
        	lastBlendFuncSrc = GlStateManager.getInteger(GL11.GL_BLEND_SRC);
			lastBlendFuncDest = GlStateManager.getInteger(GL11.GL_BLEND_DST);
			RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE);
        }      
        if (type==RenderType.NO_Z_TEST){
        	RenderSystem.depthMask(false);
        	RenderSystem.disableDepthTest();
        }
        
        if (type != RenderType.ALPHA_SHADED) TGRenderHelper.enableFXLighting();
	}
	
    /**
     * This includes FXLighting!
     */
	public static void disableBlendMode(RenderType type) {
		if (type != RenderType.ALPHA_SHADED) TGRenderHelper.disableFXLighting();
		if (type != RenderType.SOLID) {
			RenderSystem.disableBlend();
    	}
		if (type == RenderType.ALPHA || type == RenderType.ADDITIVE || type==RenderType.NO_Z_TEST) {
			//GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			RenderSystem.blendFunc(lastBlendFuncSrc, lastBlendFuncDest);
        }
        if (type==RenderType.NO_Z_TEST){
        	RenderSystem.depthMask(true);
        	RenderSystem.enableDepthTest();
        }

	}
	
}
