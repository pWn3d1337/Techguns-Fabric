package techguns.api.damagesystem;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Formatting;

public enum DamageType {
	PHYSICAL, //Melee hits
	PROJECTILE, //Arrows, bullets,..
	FIRE, //fire, flamethrower
	EXPLOSION, // Explosions like TNT, Rockets
	ENERGY, // Energy Weapons, Lasers, Magic
	POISON, //gas, slimy stuff
	UNRESISTABLE, //Things that should never get reduced with armor value: starve, falldmg, suffocate...
	ICE, //anything cold related
	LIGHTNING, //electricity, shock, lightning magic
	RADIATION, //
	DARK; // dark energy, black holes, dark magic

	@Override
	public String toString() {
		switch(this){
			
			case PROJECTILE:
				return Formatting.GRAY+I18n.translate("techguns.TGDamageType.PROJECTILE", new Object[0]);
			case FIRE:
				return Formatting.RED+I18n.translate("techguns.TGDamageType.FIRE",new Object[0]);
			case EXPLOSION:
				return Formatting.DARK_RED+I18n.translate("techguns.TGDamageType.EXPLOSION",new Object[0]);
			case ENERGY:
				return Formatting.DARK_AQUA+I18n.translate("techguns.TGDamageType.ENERGY",new Object[0]);
			case POISON:
				return Formatting.DARK_GREEN+I18n.translate("techguns.TGDamageType.POISON",new Object[0]);
			case UNRESISTABLE:
				return Formatting.BLACK+I18n.translate("techguns.TGDamageType.UNRESISTABLE",new Object[0]);
			case ICE:
				return Formatting.AQUA+I18n.translate("techguns.TGDamageType.ICE",new Object[0]);
			case LIGHTNING:
				return Formatting.YELLOW+I18n.translate("techguns.TGDamageType.LIGHTNING",new Object[0]);
			case RADIATION:
				return Formatting.GREEN+I18n.translate("techguns.TGDamageType.RADIATION",new Object[0]);
			case DARK:
				return Formatting.BLACK+I18n.translate("techguns.TGDamageType.DARK",new Object[0]);
			case PHYSICAL:
			default:
				return Formatting.DARK_GRAY+I18n.translate("techguns.TGDamageType.PHYSICAL",new Object[0]);
		}
	}
	
	
}
