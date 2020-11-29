package techguns.items.guns.ammo;

import techguns.items.guns.ammo.DamageModifier;

public class DamageModifier {

	public static final DamageModifier DEFAULT_MODIFIER = new DamageModifier();
	
	public static final DamageModifier INCENDIARY_BULLET_MODIFIER = new DamageModifier().setDmg(1.1f, 0f);
	
	public static final DamageModifier ROCKET_HV_MODIFIER = new DamageModifier().setRadius(0.75f, 0f).setRange(0.75f, 0f).setVelocity(2.0f, 0f);
	
	public static final DamageModifier ROCKET_NUKE_MODIFIER = new DamageModifier().setDmg(5f, 0f).setRadius(5f, 0f).setRange(5f, 0f);
	
	protected float dmgMul=1.0f;
	protected float dmgAdd=0f;
	
	protected float radiusMul=1.0f;
	protected float radiusAdd=0f;
	
	protected float rangeMul=1.0f;
	protected float rangeAdd=0f;
	
	protected float velocityMul=1.0f;
	protected float velocityAdd=0f;
	
	public DamageModifier setDmg(float mul, float add) {
		this.dmgMul=mul;
		this.dmgAdd=add;
		return this;
	}
	
	public DamageModifier setRadius(float mul, float add) {
		this.radiusMul=mul;
		this.radiusAdd=add;
		return this;
	}
	
	public DamageModifier setRange(float mul, float add) {
		this.rangeMul=mul;
		this.rangeAdd=add;
		return this;
	}
	
	public DamageModifier setVelocity(float mul, float add) {
		this.velocityMul=mul;
		this.velocityAdd=add;
		return this;
	}
	
	public float getDamage(float dmg) {
		return dmg*dmgMul + dmgAdd;
	}
	
	public float getRadius(float rad) {
		return rad*radiusMul+radiusAdd;
	}

	public float getRange(float r) {
		return r*rangeMul+rangeAdd;
	}
	
	public int getTTL(int ttl) {
		return Math.round(ttl*rangeMul +rangeAdd);
	}

	public float getVelocity(float velocity) {
		return velocity*velocityMul+velocityAdd;
	}
	
	public float getDmgMul() {
		return dmgMul;
	}

	public float getDmgAdd() {
		return dmgAdd;
	}

	public float getRadiusMul() {
		return radiusMul;
	}

	public float getRadiusAdd() {
		return radiusAdd;
	}

	public float getRangeMul() {
		return rangeMul;
	}

	public float getRangeAdd() {
		return rangeAdd;
	}

	public float getVelocityMul() {
		return velocityMul;
	}

	public float getVelocityAdd() {
		return velocityAdd;
	}
}
