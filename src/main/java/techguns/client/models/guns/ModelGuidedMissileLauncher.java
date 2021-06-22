package techguns.client.models.guns;


import techguns.client.models.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import techguns.api.entity.ITGExtendedPlayer;
import techguns.api.guns.IGenericGun;
import techguns.client.models.ModelMultipart;
import techguns.client.render.TGRenderHelper;
import techguns.items.guns.GenericGun;

public class ModelGuidedMissileLauncher extends ModelMultipart {
	// fields
	public ModelPart Barrel01;
	public ModelPart Barrel02;
	public ModelPart End01;
	public ModelPart End02;
	public ModelPart End03;
	public ModelPart Optics02;
	public ModelPart Optics03;
	public ModelPart Optics04;
	public ModelPart Optics05;
	public ModelPart Optics06;
	public ModelPart Optics07;
	public ModelPart Holo2;
	public ModelPart Optics01;
	public ModelPart Holo2_1;
	public ModelPart Front02;
	public ModelPart Front03;
	public ModelPart Front01;
	public ModelPart Grip01;
	public ModelPart Grip02;
	public ModelPart Grip03;
	public ModelPart Grip04;
	public ModelPart Center01;
	public ModelPart Center02;
	public ModelPart Alpha01;
	public ModelPart Alpha02;
	public ModelPart Alpha03;
	public ModelPart Alpha04;
	public ModelPart Extra01;
	public ModelPart Extra02;
	public ModelPart Extra03;
	public ModelPart Extra04;
	public ModelPart Extra05;
	public ModelPart Extra06;

	
	
	@Override
	public RenderLayer getLayerForPart(IGenericGun gun, ItemStack stack, Identifier texture, int part) {
		if (part==0) {
			return this.getLayer(texture);
		} else {
			return RenderLayer.getEntityCutout(texture);
		}
	}

	public ModelGuidedMissileLauncher() {
		super(RenderLayer::getEntitySolid);
		
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.Optics07 = new ModelPart(this, 68, 9);
		this.Optics07.setPivot(23.0F, -2.0F, 4.0F);
		this.Optics07.addCuboid(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.End02 = new ModelPart(this, 94, 4);
		this.End02.setPivot(-27.0F, -0.5F, -3.5F);
		this.End02.addCuboid(0.0F, 0.0F, 0.0F, 2, 6, 6, 0.0F);
		this.End03 = new ModelPart(this, 107, 0);
		this.End03.setPivot(-26.9F, -1.0F, -2.0F);
		this.End03.addCuboid(0.0F, 0.0F, 0.0F, 2, 7, 3, 0.0F);
		this.Optics03 = new ModelPart(this, 35, 17);
		this.Optics03.setPivot(17.0F, -1.0F, -1.5F);
		this.Optics03.addCuboid(0.0F, 0.0F, 0.0F, 8, 1, 2, 0.0F);
		this.Alpha01 = new ModelPart(this, 0, 57);
		this.Alpha01.setPivot(16.0F, -0.5F, 2.5F);
		this.Alpha01.addCuboid(0.0F, 0.0F, 0.0F, 10, 6, 0, 0.0F);
		this.Extra01 = new ModelPart(this, 45, 57);
		this.Extra01.setPivot(37.5F, -1.0F, -3.5F);
		this.Extra01.addCuboid(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.Center02 = new ModelPart(this, 0, 38);
		this.Center02.setPivot(26.0F, -0.5F, -3.5F);
		this.Center02.addCuboid(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
		this.Front03 = new ModelPart(this, 0, 0);
		this.Front03.setPivot(26.9F, -0.5F, -2.0F);
		this.Front03.addCuboid(0.0F, 0.0F, 0.0F, 12, 6, 3, 0.0F);
		this.Center01 = new ModelPart(this, 0, 38);
		this.Center01.setPivot(15.0F, -0.5F, -3.5F);
		this.Center01.addCuboid(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
		this.Optics05 = new ModelPart(this, 61, 11);
		this.Optics05.setPivot(23.0F, 3.5F, 3.0F);
		this.Optics05.addCuboid(0.0F, -2.0F, 0.0F, 1, 2, 3, 0.0F);
		this.setRotation(Optics05, 0.5235987755982988F, 0.0F, 0.0F);
		this.Front02 = new ModelPart(this, 0, 10);
		this.Front02.setPivot(27.0F, 0.0F, -3.0F);
		this.Front02.addCuboid(0.0F, 0.0F, 0.0F, 12, 5, 5, 0.0F);
		this.End01 = new ModelPart(this, 110, 5);
		this.End01.setPivot(-26.9F, 1.0F, -4.0F);
		this.End01.addCuboid(0.0F, 0.0F, 0.0F, 2, 3, 7, 0.0F);
		this.Holo2 = new ModelPart(this, 78, 0);
		this.Holo2.setPivot(23.5F, -3.0F, 5.0F);
		this.Holo2.addCuboid(0.0F, 0.0F, 0.0F, 0, 8, 8, 0.0F);
		this.Optics01 = new ModelPart(this, 32, 10);
		this.Optics01.setPivot(18.0F, -3.0F, -0.5F);
		this.Optics01.addCuboid(0.0F, -1.0F, -1.0F, 7, 2, 2, 0.0F);
		this.setRotation(Optics01, 0.7853981633974483F, 0.0F, 0.0F);
		this.Grip04 = new ModelPart(this, 60, 44);
		this.Grip04.setPivot(28.0F, 5.5F, -2.0F);
		this.Grip04.addCuboid(0.0F, 0.0F, 0.0F, 8, 5, 3, 0.0F);
		this.Alpha04 = new ModelPart(this, 0, 57);
		this.Alpha04.setPivot(16.0F, -0.5F, -3.5F);
		this.Alpha04.addCuboid(0.0F, 0.0F, 0.0F, 10, 6, 0, 0.0F);
		this.Extra06 = new ModelPart(this, 45, 57);
		this.Extra06.setPivot(27.5F, -1.0F, 1.5F);
		this.Extra06.addCuboid(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.Optics04 = new ModelPart(this, 51, 10);
		this.Optics04.setPivot(20.0F, 1.5F, 2.0F);
		this.Optics04.addCuboid(0.0F, 0.0F, 0.0F, 5, 2, 1, 0.0F);
		this.Grip01 = new ModelPart(this, 38, 38);
		this.Grip01.setPivot(16.0F, 5.5F, -1.5F);
		this.Grip01.addCuboid(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
		this.setRotation(Grip01, 0.0F, 0.0F, 0.3490658503988659F);
		this.Front01 = new ModelPart(this, 31, 0);
		this.Front01.setPivot(26.9F, 1.0F, -3.5F);
		this.Front01.addCuboid(0.0F, 0.0F, 0.0F, 12, 3, 6, 0.0F);
		this.Grip02 = new ModelPart(this, 47, 38);
		this.Grip02.setPivot(16.0F, 5.0F, -2.0F);
		this.Grip02.addCuboid(0.0F, 0.0F, 0.0F, 12, 2, 3, 0.0F);
		this.Extra03 = new ModelPart(this, 45, 57);
		this.Extra03.setPivot(27.5F, -1.0F, -3.5F);
		this.Extra03.addCuboid(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.Extra05 = new ModelPart(this, 45, 61);
		this.Extra05.setPivot(27.5F, -2.0F, 1.5F);
		this.Extra05.addCuboid(0.0F, 0.0F, 0.0F, 11, 1, 1, 0.0F);
		this.Grip03 = new ModelPart(this, 43, 44);
		this.Grip03.setPivot(24.0F, 5.5F, -2.5F);
		this.Grip03.addCuboid(0.0F, 0.0F, 0.0F, 4, 5, 4, 0.0F);
		this.Alpha03 = new ModelPart(this, -6, 51);
		this.Alpha03.setPivot(16.0F, -0.5F, -3.5F);
		this.Alpha03.addCuboid(0.0F, 0.0F, 0.0F, 10, 0, 6, 0.0F);
		this.Extra02 = new ModelPart(this, 45, 61);
		this.Extra02.setPivot(27.5F, -2.0F, -3.5F);
		this.Extra02.addCuboid(0.0F, 0.0F, 0.0F, 11, 1, 1, 0.0F);
		this.Barrel01 = new ModelPart(this, 0, 21);
		this.Barrel01.setPivot(-25.0F, 1.0F, -3.0F);
		this.Barrel01.addCuboid(0.0F, 0.0F, 0.0F, 51, 3, 5, 0.0F);
		this.Optics02 = new ModelPart(this, 35, 14);
		this.Optics02.setPivot(19.0F, -2.0F, -1.5F);
		this.Optics02.addCuboid(0.0F, 0.0F, 0.0F, 5, 1, 2, 0.0F);
		this.Holo2_1 = new ModelPart(this, 78, -8);
		this.Holo2_1.setPivot(23.5F, -3.0F, 5.0F);
		this.Holo2_1.addCuboid(0.0F, 0.0F, 0.0F, 0, 8, 8, 0.0F);
		this.Alpha02 = new ModelPart(this, -6, 51);
		this.Alpha02.setPivot(16.0F, 5.5F, -3.5F);
		this.Alpha02.addCuboid(0.0F, 0.0F, 0.0F, 10, 0, 6, 0.0F);
		this.Barrel02 = new ModelPart(this, 0, 29);
		this.Barrel02.setPivot(-25.0F, 0.0F, -2.0F);
		this.Barrel02.addCuboid(0.0F, 0.0F, 0.0F, 51, 5, 3, 0.0F);
		this.Optics06 = new ModelPart(this, 67, 14);
		this.Optics06.setPivot(23.0F, 1.0F, 5.0F);
		this.Optics06.addCuboid(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
		this.Extra04 = new ModelPart(this, 45, 57);
		this.Extra04.setPivot(37.5F, -1.0F, 1.5F);
		this.Extra04.addCuboid(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
	}

	@Override
	public void render(Entity entityIn, MatrixStack matrices, VertexConsumer vertices, int ammoLeft,
			float reloadProgress, Mode transformType, int part, float fireProgress, float chargeProgress, int light,
			int overlay) {
		if (part==0) {
		this.Optics07.render(matrices, vertices, light, overlay);
		this.End02.render(matrices, vertices, light, overlay);
		this.End03.render(matrices, vertices, light, overlay);
		this.Optics03.render(matrices, vertices, light, overlay);
		this.Alpha01.render(matrices, vertices, light, overlay);
		this.Extra01.render(matrices, vertices, light, overlay);
		this.Center02.render(matrices, vertices, light, overlay);
		this.Front03.render(matrices, vertices, light, overlay);
		this.Center01.render(matrices, vertices, light, overlay);
		this.Optics05.render(matrices, vertices, light, overlay);
		this.Front02.render(matrices, vertices, light, overlay);
		this.End01.render(matrices, vertices, light, overlay);

		this.Optics01.render(matrices, vertices, light, overlay);
		this.Grip04.render(matrices, vertices, light, overlay);
		this.Alpha04.render(matrices, vertices, light, overlay);
		this.Extra06.render(matrices, vertices, light, overlay);
		this.Optics04.render(matrices, vertices, light, overlay);
		this.Grip01.render(matrices, vertices, light, overlay);
		this.Front01.render(matrices, vertices, light, overlay);
		this.Grip02.render(matrices, vertices, light, overlay);
		this.Extra03.render(matrices, vertices, light, overlay);
		this.Extra05.render(matrices, vertices, light, overlay);
		this.Grip03.render(matrices, vertices, light, overlay);
		this.Alpha03.render(matrices, vertices, light, overlay);
		this.Extra02.render(matrices, vertices, light, overlay);
		this.Barrel01.render(matrices, vertices, light, overlay);
		this.Optics02.render(matrices, vertices, light, overlay);

		this.Alpha02.render(matrices, vertices, light, overlay);
		this.Barrel02.render(matrices, vertices, light, overlay);
		this.Optics06.render(matrices, vertices, light, overlay);
		this.Extra04.render(matrices, vertices, light, overlay);

		} else if(part==1) {
			
			matrices.push();
			
			matrices.translate(this.Holo2.pivotX*scale, this.Holo2.pivotY*scale, this.Holo2.pivotZ*scale);
			/*matrices.translate(this.Holo2.pivotX * scale, this.Holo2.pivotY * scale,
					this.Holo2.pivotZ * scale);*/
			matrices.scale(0.5F, 0.5F, 0.5F);
			matrices.translate(-this.Holo2.pivotX*scale, -this.Holo2.pivotY*scale, -this.Holo2.pivotZ*scale);
			/*matrices.translate(-this.Holo2.pivotX * scale, -this.Holo2.pivotY * scale,
					-this.Holo2.pivotZ * scale);*/
			boolean locked = false;
			if (transformType == Mode.FIRST_PERSON_LEFT_HAND || transformType == Mode.FIRST_PERSON_RIGHT_HAND ) {
				if (entityIn != null && entityIn instanceof PlayerEntity) {
					PlayerEntity player = (PlayerEntity) entityIn;
					ITGExtendedPlayer tge = (ITGExtendedPlayer) player;
					if (tge != null && player.getActiveItem() != null && player.getActiveItem().getItem() instanceof GenericGun) {
						GenericGun gun = (GenericGun)player.getActiveItem().getItem();
						if (tge.getLockOnTicks() >= gun.getLockOnTicks()) {
							locked = true;
						}
					}
				}
			}
			if (locked) {
				this.Holo2_1.render(matrices, vertices, bright_light, overlay);
			}else {
				this.Holo2.render(matrices, vertices, bright_light, overlay);
			}
			matrices.pop();
		}
	}

}
