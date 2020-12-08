package techguns.api.entity;

import techguns.deatheffects.EntityDeathUtils.DeathType;

public interface ITGLivingEntity {
	
	public void setDeathType(DeathType deathType);
	public DeathType getDeathType();
}
