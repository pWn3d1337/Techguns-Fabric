package techguns.client.particle;

import java.util.Comparator;
import java.util.Iterator;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import techguns.TGConfig;
import techguns.client.particle.ParticleList.ParticleListIterator;


public class TGParticleManager {
	
    public static double interpPosX;
    public static double interpPosY;
    public static double interpPosZ;

	protected ParticleList<TGParticleSystem> list_systems = new ParticleList<>();
	protected ParticleList<ITGParticle> list = new ParticleList<>();
	protected ParticleList<ITGParticle> list_nosort = new ParticleList<>();
	protected ComparatorParticleDepth compare = new ComparatorParticleDepth();
	
	public void addEffect(ITGParticle effect)
    {
        if (effect == null) return;
        if(effect instanceof TGParticleSystem) {
        	list_systems.add((TGParticleSystem) effect);
        } else {
        	if (effect.doNotSort()) {
        		list_nosort.add(effect);
        	} else {
        		list.add(effect);
        	}
        }
    }
	
	public void tickParticles() {
		MinecraftClient mc = MinecraftClient.getInstance();
		if(mc.isPaused()) return;
		
		Entity viewEnt = mc.getCameraEntity();
	
		Iterator<TGParticleSystem> sysit = list_systems.iterator();
		while(sysit.hasNext()) {
			TGParticleSystem p = sysit.next();
			
			p.updateTick();
			if(p.shouldRemove()) {
				sysit.remove();
			}
		}
		
		ParticleListIterator<ITGParticle> it = list.iterator();
		while(it.hasNext()) {
			ITGParticle p = it.next();
			
			p.updateTick();
			if(p.shouldRemove()) {
				it.remove();
			} else {
				if(viewEnt!=null) {
					p.setDepth(this.distanceToPlane(viewEnt, p.getPos()));
				}
			}
		}
		
		Iterator<ITGParticle> it2 = list_nosort.iterator();
		while(it2.hasNext()) {
			ITGParticle p = it2.next();
			
			p.updateTick();
			if(p.shouldRemove()) {
				it2.remove();
			}
		}
		//list.debugPrintList();
		
		if(TGConfig.cl_sortPassesPerTick>0) {
			this.doSorting();
		}
	}

	public void doSorting() {
		this.list.doBubbleSort(TGConfig.cl_sortPassesPerTick, compare);
	}
	
	/**
	 * 
	 * @param entityIn renderViewEntity
	 * @param matrix4f 
	 * @param camera 
	 * @param matrices 
	 * @param partialTick
	 */
	public void renderParticles(VertexConsumerProvider.Immediate vertexConsumerProvider, Entity entityIn, float partialTicks, MatrixStack matrices, Camera camera, Matrix4f matrix4f)
    {
        /*float f1 = MathHelper.cos(entityIn.yaw * 0.017453292F);
        float f2 = MathHelper.sin(entityIn.yaw * 0.017453292F);
        float f3 = -f2 * MathHelper.sin(entityIn.pitch * 0.017453292F);
        float f4 = f1 * MathHelper.sin(entityIn.pitch * 0.017453292F);
        float f5 = MathHelper.cos(entityIn.pitch * 0.017453292F);
        
        interpPosX = entityIn.lastRenderX + (entityIn.getX() - entityIn.lastRenderX) * (double)partialTicks;
        interpPosY = entityIn.lastRenderY + (entityIn.getY() - entityIn.lastRenderY) * (double)partialTicks;
        interpPosZ = entityIn.lastRenderZ + (entityIn.getZ() - entityIn.lastRenderZ) * (double)partialTicks;
        */		
		float f1 = MathHelper.cos(camera.getYaw() * 0.017453292F);
        float f2 = MathHelper.sin(camera.getYaw() * 0.017453292F);
        float f3 = -f2 * MathHelper.sin(camera.getPitch() * 0.017453292F);
        float f4 = f1 * MathHelper.sin(camera.getPitch() * 0.017453292F);
        float f5 = MathHelper.cos(camera.getPitch() * 0.017453292F);
        
        interpPosX = entityIn.lastRenderX + (entityIn.getX() - entityIn.lastRenderX) * (double)partialTicks;
        interpPosY = entityIn.lastRenderY + (entityIn.getY() - entityIn.lastRenderY) * (double)partialTicks;
        interpPosZ = entityIn.lastRenderZ + (entityIn.getZ() - entityIn.lastRenderZ) * (double)partialTicks;

        Matrix4f m = matrices.peek().getModel().copy();

        this.list.forEach(p -> {	
        	p.doRender(vertexConsumerProvider, entityIn, partialTicks, f1, f5, f2, f3, f4, m, camera);
        });
        
        this.list_nosort.forEach(p -> {	
        	p.doRender(vertexConsumerProvider, entityIn, partialTicks, f1, f5, f2, f3, f4, m, camera);
        });
    }	
	
	public double distanceToPlane(Entity viewEntity, Vec3d pos) {
		//Formula from: http://geomalgorithms.com/a04-_planes.html
		Vec3d n = viewEntity.getRotationVector();
		double dot1 = -n.dotProduct(pos.subtract(viewEntity.getPos()));
		double dot2 = n.dotProduct(n);
		double f = dot1/dot2;
		
		Vec3d pos2 = pos.add(n.multiply(f));
		return pos.squaredDistanceTo(pos2);
	}
	
	public static class ComparatorParticleDepth implements Comparator<ITGParticle> {

		@Override
		public int compare(ITGParticle p1, ITGParticle p2) {
			if(p1.doNotSort() && p2.doNotSort()) {
				return 0;
			}
				double dist1=p1.getDepth();
				double dist2=p2.getDepth();

				if(dist1<dist2) {
					return 1;
				} else if(dist1>dist2) {
					return -1;
				} else {
					return 0;
				}
		}
		

	}
}
