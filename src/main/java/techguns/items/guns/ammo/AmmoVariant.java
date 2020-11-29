package techguns.items.guns.ammo;

import net.minecraft.item.ItemStack;

public class AmmoVariant {

	protected String key;
	protected ItemStack[] ammo;
	protected ItemStack[] bullet;
	protected byte projectile_type;
	protected DamageModifier modifier;
	
	public AmmoVariant(ItemStack[] ammo, ItemStack[] bullet, int projectile_type, DamageModifier modifier) {
		this(AmmoTypes.TYPE_DEFAULT,ammo,bullet, projectile_type, modifier);
	}
	
	public AmmoVariant(String key, ItemStack[] ammo, ItemStack[] bullet, int projectile_type, DamageModifier modifier) {
		super();
		this.key = key;
		this.bullet = bullet;
		this.ammo = ammo;
		this.projectile_type = (byte) projectile_type;
		this.modifier = modifier;
	}

	public String getKey() {
		return key;
	}

	public byte getProjectile_type() {
		return projectile_type;
	}

	public DamageModifier getModifier() {
		return this.modifier;
	}
}
