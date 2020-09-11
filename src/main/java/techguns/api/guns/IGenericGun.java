package techguns.api.guns;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;


public interface IGenericGun {

	public boolean isShootWithLeftClick();

	public boolean isSemiAuto();

	@Environment(EnvType.CLIENT)
	public boolean isZooming();

	public void shootGunPrimary(ItemStack stack, World world, PlayerEntity player, boolean zooming, Hand hand, Entity target);

	public int getAmmoLeft(ItemStack stack);
	
	public GunHandType getGunHandType();

	public boolean isHoldZoom();

	public float getZoomMult();

	public default boolean canCharge() {return false;};

	public boolean isAimed(LivingEntity ply, ItemStack stack);
	//TODO add
	//public Identifier getCurrentTexture(ItemStack stack);

	@Environment(EnvType.CLIENT)
	public ArmPose getArmPose();

	public String getCurrentAmmoVariantKey(ItemStack stack);
}
