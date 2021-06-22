package techguns.damagesystem;

import java.util.ArrayList;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import techguns.api.damagesystem.DamageType;
import techguns.deatheffects.EntityDeathUtils.DeathType;

public class TGDamageSource extends EntityDamageSource {

		protected boolean attackSuccessful=false;
	
		public Entity attacker=null;
		public DamageType damageType;
		public DeathType deathType;
		public float goreChance=0.5f;
		
		public boolean knockbackOnShieldBlock=true;
		
		/**
		 * Anti-Toughness
		 */
		public float armorPenetration=0.0f;
		public boolean ignoreHurtresistTime=false;
		public float knockbackMultiplier=1.0f;
		
		/**
		 * set to true when this damagesource was converted from another damage source, with new TGDamageSource(DamageSource src) 
		 */
		public boolean wasConverted=false;
		
		protected static ArrayList<String> unresistableTypes = new ArrayList<String>();
		static {
			unresistableTypes.add("inWall");
			unresistableTypes.add("drown");
			unresistableTypes.add("starve");
			unresistableTypes.add("fall");
			unresistableTypes.add("outOfWorld");
		}

		public void setAttackSuccessful() {
			this.attackSuccessful=true;
		}
		
		public boolean wasSuccessful() {
			return this.attackSuccessful;
		}
		
		public static TGDamageSource causeBulletDamage(Entity projectile, Entity shooter, DeathType deathType){
			TGDamageSource src = new TGDamageSource("tg_bullet",projectile, shooter, DamageType.PROJECTILE, deathType);
			src.knockbackOnShieldBlock=false;
			src.ignoreHurtresistTime=true;
			return src;
		}
		
		public static TGDamageSource causeExplosionDamage(Entity projectile, Entity shooter, DeathType deathType){
			return new TGDamageSource("tg_explosion",projectile, shooter, DamageType.EXPLOSION, deathType);
		}
		
		public static TGDamageSource causePoisonDamage(Entity projectile, Entity shooter, DeathType deathType){
			TGDamageSource src = new TGDamageSource("tg_poison",projectile, shooter, DamageType.POISON, deathType);
			src.ignoreHurtresistTime=true;
			src.knockbackOnShieldBlock=false;
			return src;
		}
		
		
		public static TGDamageSource causeFireDamage(Entity projectile, Entity shooter, DeathType deathType){
			TGDamageSource src = new TGDamageSource("tg_fire",projectile, shooter, DamageType.FIRE, deathType);
			src.ignoreHurtresistTime=true;
			src.knockbackOnShieldBlock=false;
			return src;
		}
		
		public static TGDamageSource getKnockbackDummyDmgSrc(Entity projectile, Entity shooter){
			return new TGDamageSource("tg_knockback", projectile, shooter, DamageType.PHYSICAL, DeathType.DEFAULT);
		}
		
		public static TGDamageSource causeEnergyDamage(Entity projectile, Entity shooter, DeathType deathType){
			TGDamageSource src = new TGDamageSource("tg_energy",projectile, shooter, DamageType.ENERGY, deathType);
			src.ignoreHurtresistTime=true;
			src.knockbackOnShieldBlock=false;
			return src;
		}

		public static TGDamageSource causeRadiationDamage(Entity projectile, Entity shooter, DeathType deathType){
			TGDamageSource src = new TGDamageSource("tg_rad",projectile, shooter, DamageType.RADIATION, deathType);
			src.ignoreHurtresistTime=true;
			src.knockbackOnShieldBlock=false;
			return src;
		}
		
		public static TGDamageSource causeLethalRadPoisoningDamage(Entity projectile, Entity shooter, DeathType deathType){
			TGDamageSource src = new TGDamageSource("tg_rad_poisoning",projectile, shooter, DamageType.UNRESISTABLE, deathType);
			src.ignoreHurtresistTime=true;
			src.knockbackOnShieldBlock=false;
			src.goreChance=1.0f;
			return src;
		}
		
		public static TGDamageSource causeLightningDamage(Entity projectile, Entity shooter, DeathType deathType){
			TGDamageSource src = new TGDamageSource("tg_lightning",projectile, shooter, DamageType.LIGHTNING, deathType);
			src.ignoreHurtresistTime=true;
			src.knockbackOnShieldBlock=false;
			return src;
		}
		
		public static TGDamageSource causeDarkDamage(Entity projectile, Entity shooter, DeathType deathType){
			TGDamageSource src = new TGDamageSource("tg_dark",projectile, shooter, DamageType.DARK, deathType);
			src.ignoreHurtresistTime=true;
			src.knockbackOnShieldBlock=false;
			return src;
		}
		
		public static TGDamageSource getFromGenericDamageSource(DamageSource src){
			if(src instanceof TGDamageSource){
				return (TGDamageSource) src;
			}
			return new TGDamageSource(src);
		}
		
		public  TGDamageSource setNoKnockback(){
			this.knockbackMultiplier=0.0f;
			return this;
		}
		public TGDamageSource setKnockback(float mult){
			this.knockbackMultiplier=mult;
			return this;
		}
		
		public boolean hasKnockback(){
			return this.knockbackMultiplier>0.0f;
		}
		
		
		
		
	    @Override
		public Text getDeathMessage(LivingEntity entity) {
	    	Text itextcomponent;
			ItemStack itemstack = ItemStack.EMPTY;
			if(this.attacker==null && this.attacker==null) {	
				itextcomponent = entity.getDisplayName();
			} else {	
				itextcomponent = this.attacker == null ? this.attacker.getDisplayName() : this.attacker.getDisplayName();
				itemstack = this.attacker instanceof LivingEntity ? ((LivingEntity)this.attacker).getMainHandStack(): ItemStack.EMPTY;  
			}
	        String s = "death.attack." + this.getName();
	        String s1 = s + ".item";
	        return !itemstack.isEmpty() && itemstack.hasCustomName() && I18n.hasTranslation(s1) ? new TranslatableText(s1, new Object[] {entity.getDisplayName(), itextcomponent, itemstack.getName()}) : new TranslatableText(s, new Object[] {entity.getDisplayName(), itextcomponent});
	 
		}
		
		public static TGDamageSource copyWithNewEnt(TGDamageSource other, Entity damagingEntity, Entity attacker) {
			TGDamageSource newSrc = new TGDamageSource(other.getName(), damagingEntity, attacker, other.damageType, other.deathType);
			newSrc.knockbackMultiplier = other.knockbackMultiplier;
			newSrc.armorPenetration = other.armorPenetration;
			newSrc.goreChance = other.goreChance;
			newSrc.ignoreHurtresistTime = other.ignoreHurtresistTime;
			return newSrc;
		}
		
		public TGDamageSource(String name, Entity damagingEntity, Entity attacker, DamageType damageType, DeathType deathType) {
			super(name, damagingEntity);
			this.attacker = attacker;
			this.damageType = damageType;
			this.deathType = deathType;
			setBehaviourForVanilla();
		}

		public void setBehaviourForVanilla(){
			switch(damageType){
				case ENERGY:
					this.setUsesMagic();
					break;
				case EXPLOSION:
					this.setExplosive();
					break;
				case FIRE:
					//not set as fire damage since this would cause immunity with fire resistance :-/
					this.setUsesMagic();
					break;
				case ICE:
					this.setUsesMagic();
					break;
				case LIGHTNING:
					this.setUsesMagic();
					break;
				case PHYSICAL:
					break;
				case POISON:
					this.setUsesMagic();
					break;
				case PROJECTILE:
					this.setProjectile();
					break;
				case RADIATION:
					this.setUsesMagic();
					break;
				case DARK:
					this.setUsesMagic();
					break;
				case UNRESISTABLE:
					this.setBypassesArmor();
					this.setUnblockable();
					break;
				default:
					break;
			}
		}
		
		public TGDamageSource(ProjectileDamageSource dmg){
			this((EntityDamageSource)dmg);
		}
		
		public TGDamageSource(EntityDamageSource dmg){
			this((DamageSource)dmg);
			if(dmg.isThorns()) {
				this.setThorns();
			}
		}
		
		public TGDamageSource(DamageSource dmg){
			super(dmg.name, dmg.getSource());
			this.attacker=dmg.getAttacker();
			this.determineTGDamageType(dmg);
			this.wasConverted=true;
			
			if(dmg.isOutOfWorld()) {
				this.setOutOfWorld();
			}
			if(dmg.isScaledWithDifficulty()){
				this.setScaledWithDifficulty();
			}
			if(dmg.isExplosive()){
				this.setExplosive();
			}
			if(dmg.isFire()){
				this.setFire();
			}
			if(dmg.isMagic()){
				this.setUsesMagic();
			}
			if(dmg.isProjectile()){
				this.setProjectile();
			}
			if(dmg.isUnblockable()){
				this.setUnblockable();
			}
			if(dmg.bypassesArmor()) {
				this.setBypassesArmor();
			}
		}
		
		private void determineTGDamageType(DamageSource dmg){
			if( dmg.isExplosive()){
				damageType = DamageType.EXPLOSION;
			} else if (dmg.isMagic()) {
				damageType = DamageType.ENERGY;
			} else if (dmg.isFire() || dmg.name.equals("dragonBreath")) {
				damageType = DamageType.FIRE;
			} else if (dmg.isProjectile()){
				damageType = DamageType.PROJECTILE;
			} else if (dmg.name.equals("wither")){
				damageType = DamageType.POISON;
			} else if (dmg.name.equals("lightningBolt")){
				damageType = DamageType.LIGHTNING;
			} else if(dmg.isOutOfWorld() || unresistableTypes.contains(dmg.name)){
				damageType = DamageType.UNRESISTABLE;
			} else {
				damageType = DamageType.PHYSICAL;
			}
		}
		
		@Override
		public Entity getAttacker() {
			return this.attacker;
		}

		@Override
		public Entity getSource() {
			return this.source;
		}

		public boolean knockbackOnShieldBlock() {
			return this.knockbackOnShieldBlock;
		}

		public TGDamageSource setKnockbackOnShieldBlock(boolean knockbackOnShieldBlock) {
			this.knockbackOnShieldBlock = knockbackOnShieldBlock;
			return this;
		}
		
}
