package techguns.util;

import net.minecraft.entity.Entity;

/**
 * Used for Sounds and ParticleSystems, which will stop when the condition is not met.
 */
public enum EntityCondition {
	NONE(0),
	CHARGING_WEAPON(1),
	ENTITY_ALIVE(2);
	
	public byte id = 0;
	private EntityCondition(int id) {
		this.id = (byte)id;
	}
	
	public static EntityCondition fromByte(byte id) {
		for (EntityCondition e : EntityCondition.values()) {
			if (e.id == id) return e;
		}
		return NONE;
	}
	
	public boolean evaluate(Entity entity) {
		switch (this) {
		case CHARGING_WEAPON:
			//TODO Charging Weapons
			/*if (entity instanceof PlayerEntity) {
				TGExtendedPlayer txp = TGExtendedPlayer.get((EntityPlayer)entity);
				return txp.isChargingWeapon();
			}*/
			return false;
		case ENTITY_ALIVE:
			return entity.isAlive();
		case NONE:
		default: //If no condition is set, never stop a sound/particle.
			return true;
		}
	}
}
