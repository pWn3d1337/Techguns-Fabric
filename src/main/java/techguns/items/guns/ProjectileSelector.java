package techguns.items.guns;

import java.util.HashMap;

import techguns.entities.projectiles.GenericProjectile;
import techguns.items.guns.ammo.AmmoType;
import techguns.items.guns.ammo.AmmoVariant;
import techguns.items.guns.ammo.DamageModifier;

public class ProjectileSelector<T extends GenericProjectile> {

	protected static class ProjectileFactoryPair<T extends GenericProjectile> {
		public IProjectileFactory<T> factory;
		public byte projectile_type;
		public DamageModifier modifier;
		
		public ProjectileFactoryPair(IProjectileFactory<T> factory, byte projectile_type, DamageModifier modifier) {
			this.factory = factory;
			this.projectile_type = projectile_type;
			this.modifier = modifier;
		}
	}
	
	protected HashMap<String, ProjectileFactoryPair<T>> map = new HashMap<>();
	public AmmoType ammoType;
	
	/**
	 * Amount of Variants in type has to be the same as number of passed Factories
	 * @param type
	 * @param factories
	 */
	public ProjectileSelector(AmmoType type, @SuppressWarnings("unchecked") IProjectileFactory<T>... factories) {
		this.ammoType=type;
		for(int i=0;i<type.getVariants().size();i++) {
			AmmoVariant v = type.getVariants().get(i);
			byte projectile_Type = v.getProjectile_type();
			DamageModifier mod = v.getModifier();
			map.put(v.getKey(), new ProjectileFactoryPair<T>(factories[i], projectile_Type, mod));
		}
	}

	public IProjectileFactory<T> getFactoryForType(String key){
		if(map.containsKey(key)) {
			ProjectileFactoryPair<T> pair = map.get(key);
			return pair.factory;
		}
		ProjectileFactoryPair<T> default_factory = map.get("default");
		return default_factory.factory;
	}
	
	public byte getProjectileTypeForType(String key){
		if(map.containsKey(key)) {
			ProjectileFactoryPair<T> pair = map.get(key);
			return pair.projectile_type;
		}
		ProjectileFactoryPair<T> default_factory = map.get("default");
		return default_factory.projectile_type;
	}
	
	public DamageModifier getDamageModifierForType(String key){
		if(map.containsKey(key)) {
			ProjectileFactoryPair<T> pair = map.get(key);
			return pair.modifier;
		}
		ProjectileFactoryPair<T> default_factory = map.get("default");
		return default_factory.modifier;
	}
}
