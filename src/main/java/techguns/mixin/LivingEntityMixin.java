package techguns.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import techguns.TGPacketsS2C;
import techguns.api.entity.ITGLivingEntity;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.packets.PacketEntityDeathType;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ITGLivingEntity {

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	
	@Shadow
	protected boolean dead;
	
	@Unique
	protected DeathType techguns_deathType;

	@Override
	public DeathType getDeathType() {
		return techguns_deathType;
	}
	
	@Override
	public void setDeathType(DeathType deathType) {
		this.techguns_deathType = deathType;
	}

	@Shadow
	public abstract boolean blockedByShield(DamageSource source);
	
	@Shadow
	public abstract void damageShield(float amount);
	
	@Shadow
	public abstract void takeShieldHit(LivingEntity attacker);
	
	@Shadow
	public abstract void applyDamage(DamageSource source, float amount);
	
	@Shadow
	public abstract void playHurtSound(DamageSource source);
	
	@Shadow
	public abstract boolean tryUseTotem(DamageSource source);
	
	@Shadow
	public abstract SoundEvent getDeathSound();
	
	@Shadow
	public abstract float getSoundVolume();

	@Shadow
	public abstract float getSoundPitch();

	@Shadow
	protected float lastDamageTaken;
	
	@Shadow
	protected int despawnCounter;
	
	@Shadow
	protected PlayerEntity attackingPlayer;

	@Shadow
	protected int playerHitTimer;
	
	@Shadow
	private DamageSource lastDamageSource;

	@Shadow
	private long lastDamageTime;
	
	@Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
	public boolean damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
		boolean ret = false;
		if (source instanceof TGDamageSource) {
			ret = Techguns_damage_func((TGDamageSource)source, amount);
			info.setReturnValue(ret);
			info.cancel();
		}
		return ret;
	}

	/**
	 * A customized copy of the regular damage function to use with Techguns
	 * 
	 * @param source
	 * @param amount
	 * @return
	 */
	public boolean Techguns_damage_func(TGDamageSource source, float amount) {
		LivingEntity self = (LivingEntity) (Object) this;

		if (this.isInvulnerableTo(source)) {
			return false;
		} else if (this.world.isClient) {
			return false;
		} else if (self.isDead()) {
			return false;
		} else if (source.isFire() && self.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
			return false;
		} else {
			if (self.isSleeping() && !this.world.isClient) {
				self.wakeUp();
			}

			this.despawnCounter = 0;
			float f = amount;
			/*if ((source == DamageSource.ANVIL || source == DamageSource.FALLING_BLOCK)
					&& !self.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
				self.getEquippedStack(EquipmentSlot.HEAD).damage(
						(int) (amount * 4.0F + this.random.nextFloat() * amount * 2.0F), self, (livingEntityx) -> {
							livingEntityx.sendEquipmentBreakStatus(EquipmentSlot.HEAD);
						});
				amount *= 0.75F;
			}*/

			boolean bl = false;
			float g = 0.0F;
			if (amount > 0.0F && this.blockedByShield(source)) {
				this.damageShield(amount);
				g = amount;
				amount = 0.0F;
				if (!source.isProjectile()) {
					Entity entity = source.getSource();
					if (entity instanceof LivingEntity) {
						this.takeShieldHit((LivingEntity) entity);
					}
				}

				bl = true;
			}

			self.limbDistance = 1.5F;
			boolean bl2 = true;
			//Edit: check ingore hurtresist time
			if (!source.ignoreHurtresistTime && (float) this.timeUntilRegen > 10.0F) {
				if (amount <= this.lastDamageTaken) {
					return false;
				}

				this.applyDamage(source, amount - this.lastDamageTaken);
				this.lastDamageTaken = amount;
				bl2 = false;
			} else {
				this.lastDamageTaken = amount;
				this.timeUntilRegen = 20;
				this.applyDamage(source, amount);
				if(!source.ignoreHurtresistTime) {
					self.maxHurtTime = 10;
					self.hurtTime = self.maxHurtTime;
				}
			}

			//System.out.println("Techguns Damage Func!!!");
			
			self.knockbackVelocity = 0.0F;
			Entity entity2 = source.getAttacker();
			if (entity2 != null) {
				if (entity2 instanceof LivingEntity) {
					self.setAttacker((LivingEntity) entity2);
				}

				if (entity2 instanceof PlayerEntity) {
					this.playerHitTimer = 100;
					this.attackingPlayer = (PlayerEntity) entity2;
				} else if (entity2 instanceof WolfEntity) {
					WolfEntity wolfEntity = (WolfEntity) entity2;
					if (wolfEntity.isTamed()) {
						this.playerHitTimer = 100;
						LivingEntity livingEntity = wolfEntity.getOwner();
						if (livingEntity != null && livingEntity.getType() == EntityType.PLAYER) {
							this.attackingPlayer = (PlayerEntity) livingEntity;
						} else {
							this.attackingPlayer = null;
						}
					}
				}
			}

			if (bl2) {
				if (bl) {
					this.world.sendEntityStatus(this, (byte) 29);
				} else if (source instanceof EntityDamageSource && ((EntityDamageSource) source).isThorns()) {
					this.world.sendEntityStatus(this, (byte) 33);
				} else {
					byte e;
					if (source == DamageSource.DROWN) {
						e = 36;
					} else if (source.isFire()) {
						e = 37;
					} else if (source == DamageSource.SWEET_BERRY_BUSH) {
						e = 44;
					} else {
						e = 2;
					}

					this.world.sendEntityStatus(this, e);
				}

				if (source != DamageSource.DROWN && (!bl || amount > 0.0F)) {
					this.scheduleVelocityUpdate();
				}

				if (entity2 != null) {
					double h = entity2.getX() - this.getX();

					double i;
					for (i = entity2.getZ() - this.getZ(); h * h + i * i < 1.0E-4D; i = (Math.random() - Math.random())
							* 0.01D) {
						h = (Math.random() - Math.random()) * 0.01D;
					}

					self.knockbackVelocity = (float) (MathHelper.atan2(i, h) * 57.2957763671875D - (double) this.yaw);
					//Edit: Knockback_Stregth from damage_source
					float knockback_strength = 0.4F*source.knockbackMultiplier;
                    if (knockback_strength>0) {
                    	self.takeKnockback(knockback_strength, h, i);
                    }
				} else {
					self.knockbackVelocity = (float) ((int) (Math.random() * 2.0D) * 180);
				}
			}

			if (self.isDead()) {
				if (!this.tryUseTotem(source)) {
					SoundEvent soundEvent = this.getDeathSound();
					if (bl2 && soundEvent != null) {
						this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
					}

					self.onDeath(source);
				}
			} else if (bl2) {
				this.playHurtSound(source);
			}

			boolean bl3 = !bl || amount > 0.0F;
			if (bl3) {
				this.lastDamageSource = source;
				this.lastDamageTime = this.world.getTime();
			}

			if (self instanceof ServerPlayerEntity) {
				Criteria.ENTITY_HURT_PLAYER.trigger((ServerPlayerEntity) self, source, f, amount, bl);
				if (g > 0.0F && g < 3.4028235E37F) {
					((ServerPlayerEntity) self).increaseStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(g * 10.0F));
				}
			}

			if (entity2 instanceof ServerPlayerEntity) {
				Criteria.PLAYER_HURT_ENTITY.trigger((ServerPlayerEntity) entity2, this, source, f, amount, bl);
			}

			return bl3;
		}
	}
	

	
	@Inject(at = @At("HEAD"), method="onDeath(Lnet/minecraft/entity/damage/DamageSource;)V")
	public void onDeath(DamageSource source, CallbackInfo info) {
		if (!this.world.isClient /*&& ((ITGLivingEntity)this).getDeathType() == null*/ && !this.removed && !this.dead) {			
			if (source instanceof TGDamageSource) {
				TGDamageSource tgs = (TGDamageSource) source;
				LivingEntity entity = (LivingEntity)(Object)this;
				//((ITGLivingEntity)this).setDeathType(tgs.deathType);
				if (tgs.deathType != DeathType.DEFAULT) {		
					//TODO Gore chance
					//if(Math.random()<tgs.goreChance) {
						if (EntityDeathUtils.hasSpecialDeathAnim(entity, tgs.deathType)) {
							System.out.println("Entity "+entity.getName().asString()+" got rekt with DT "+tgs.deathType.toString());
							TGPacketsS2C.sendToAllTracking(new PacketEntityDeathType(entity, tgs.deathType), entity, true);
						}
					//}
				}
			}
		}
	}
}
