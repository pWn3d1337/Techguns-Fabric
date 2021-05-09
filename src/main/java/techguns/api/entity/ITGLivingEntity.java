package techguns.api.entity;

import net.minecraft.entity.EntityPose;
import techguns.deatheffects.EntityDeathUtils.DeathType;

public interface ITGLivingEntity {
	
	public void setDeathType(DeathType deathType);
	public DeathType getDeathType();

	/**
	 * Vanilla version is client only
	 */
	public float getEyeHeight_ServerSide(EntityPose pose);
}
