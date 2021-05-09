package techguns.items.guns;

import java.util.Arrays;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.TGItems;
import techguns.TGPacketsS2C;
import techguns.api.entity.ITGExtendedPlayer;
import techguns.api.guns.GunHandType;
import techguns.client.ClientProxy;
import techguns.client.ShooterValues;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;
import techguns.items.guns.ammo.DamageModifier;
import techguns.packets.PacketEntityAdditionalSpawnData;
import techguns.packets.PacketSpawnParticleOnEntity;
import techguns.sounds.TGSoundCategory;
import techguns.util.EntityCondition;
import techguns.util.InventoryUtil;
import techguns.util.SoundUtil;

public class GenericGunCharge extends GenericGun {

	private static final int MAX_USE_TIME = 288000;
	/**
	 * In ticks
	 */
	public float fullChargeTime=20.0f;
	public int ammoConsumedOnFullCharge=10;

	@SuppressWarnings("rawtypes")
	protected ChargedProjectileSelector chargedProjectile_selector;
	
	public boolean hasChargedFireAnim = true;
	public boolean canFireWhileCharging = false;
	
	SoundEvent startChargeSound = null;
	String chargeFX = null;
	private float chargeFXoffsetX = 0.0f;
	private float chargeFXoffsetY = 0.0f;
	private float chargeFXoffsetZ = 0.0f;
	
	public GenericGunCharge(String name, @SuppressWarnings("rawtypes") ChargedProjectileSelector projectile_selector, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound,
			int TTL, float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge) {
		super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL, accuracy);
		this.fullChargeTime=fullChargeTime;
		this.ammoConsumedOnFullCharge=ammoConsumedOnFullCharge;
		this.chargedProjectile_selector=projectile_selector;
	}
	
	
	
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand handIn) {
		ITGExtendedPlayer extendedPlayer = (ITGExtendedPlayer)player;
		ItemStack item = player.getStackInHand(handIn);	
		
		/*
		 * Check if player wants to zoom
		 */
		if (canZoom && player.isSneaking() && this.toggleZoom) {
			if (world.isClient) {
				ClientProxy cp = ClientProxy.get();
				if (cp.isZooming()) {
					cp.resetZoom();
				} else {
					cp.setZooming(this.zoomMult);
				}
			}

		} else {
			//int dur = item.getItemDamage();
			int ammo = this.getCurrentAmmo(item);
			
			if (ammo > 0) {
				// bullets left

				int firedelay = extendedPlayer.getFireDelay(handIn);

				if (firedelay<=0) {

					this.startCharge( item, world, player, handIn);
					extendedPlayer.setFireDelay(handIn, this.minFiretime);

					player.setCurrentHand(handIn);
					//player.setItemInUse(item, this.getMaxItemUseDuration(item));
					
					extendedPlayer.setChargingWeapon(true);
					
					if (this.startChargeSound != null) {
						SoundUtil.playSoundOnEntityGunPosition(world, player ,this.startChargeSound, SOUND_DISTANCE, 1.0F, false, true, false, TGSoundCategory.GUN_FIRE, EntityCondition.CHARGING_WEAPON);
						//SoundUtil.playSoundOnEntityGunPosition(world, player ,this.startChargeSound, SOUND_DISTANCE, 1.0F, false, false, TGSoundCategory.GUN_FIRE);
					}
					
					if (this.chargeFX != null) {
						float x;
						if (player.getMainArm() == Arm.RIGHT) {
							x = this.chargeFXoffsetX;
						} else {
							x = -this.chargeFXoffsetX;
						}
						if (!world.isClient) {
							TGPacketsS2C.sendToAllTracking(new PacketSpawnParticleOnEntity(this.chargeFX, player, x, this.chargeFXoffsetY, this.chargeFXoffsetZ, true, EntityCondition.CHARGING_WEAPON), player, true);
						}
					}
				}

			} else {
				// mag empty, reload needed

				// look for ammo
				if (InventoryUtil.consumeAmmoPlayer(player, this.ammoType.getAmmo(this.getCurrentAmmoVariant(item)))) {

					Arrays.stream(this.ammoType.getEmptyMag()).forEach( e -> {
    					if (!e.isEmpty()){
        					int amount=InventoryUtil.addAmmoToPlayerInventory(player, TGItems.newStack(e, 1));
        					if(amount >0 && !world.isClient){
        						player.world.spawnEntity(new ItemEntity(player.world, player.getPos().x, player.getPos().y, player.getPos().z, TGItems.newStack(e, amount)));
        					}
        				}
    				});

					// stop toggle zooming when reloading
					if (world.isClient) {
						if (canZoom && this.toggleZoom) {
							ClientProxy cp = ClientProxy.get();
							if (cp.isZooming()) {
								cp.resetZoom();
							}
						}
					}
					
					extendedPlayer.setFireDelay(handIn, this.reloadtime-this.minFiretime);

					if (ammoCount > 1) {
						int i = 1;
						while (i < ammoCount && InventoryUtil.consumeAmmoPlayer(player, this.ammoType.getAmmo(this.getCurrentAmmoVariant(item)))) {
							i++;
						}
						this.reloadAmmo(item, i);
					} else {
						this.reloadAmmo(item);

					}

					SoundUtil.playSoundOnEntityGunPosition(world, player, reloadsound, 1.0f, 1.0f, false, true, TGSoundCategory.RELOAD);
					
					if (world.isClient) {
						int time = (int) (((float) reloadtime / 20.0f) * 1000);

						ShooterValues.setReloadtime(player, handIn==Hand.OFF_HAND, System.currentTimeMillis()+time, time, (byte)1);
						
						client_startReload();
					}

				} else {

					//TODO emptySound
					/*if (!world.isRemote) {
						world.playSoundAtEntity(player, "mob.villager.idle", 1.0F, 1.0F);
					}*/
				}
			}
		}
	
		return new TypedActionResult<ItemStack>(ActionResult.PASS, item);
	}

	/**
	 * Override for onCharge code.
	 */
	protected void startCharge(ItemStack item, World world, PlayerEntity player, Hand hand) {}

		
	@Override
	public void shootGunPrimary(ItemStack stack, World world, PlayerEntity player, boolean zooming, Hand hand, Entity target) {
		if (this.canFireWhileCharging || player.getActiveItem() != stack) {
			super.shootGunPrimary(stack, world, player, zooming, hand, target);
		}
	}
	
	@Override
	public void onStoppedUsing(ItemStack item, World world, LivingEntity entityLiving, int timeLeft) {
		if ( entityLiving instanceof PlayerEntity){
			PlayerEntity player = (PlayerEntity) entityLiving;
			
			ITGExtendedPlayer txp = (ITGExtendedPlayer)player;
			txp.setChargingWeapon(false);
			
			int j = this.getMaxUseTime(item) - timeLeft;
	
			float f = j/this.fullChargeTime;
			
			if (f > 1.0F) {
				f = 1.0F;
			}
	

			int	ammoConsumed = this.consumeAmmoCharge(item, f,player.abilities.creativeMode);
			
			//reduce charge value if ammo is low
			if (ammoConsumed < (int) Math.ceil(f * this.ammoConsumedOnFullCharge)) {
				f =   (float)ammoConsumed / (float)this.ammoConsumedOnFullCharge;
			}
			
			if (!world.isClient) {
	
				// If SERVER, create projectile
	
				EnumBulletFirePos firePos;
	        	if (player.getMainArm() == Arm.RIGHT) {
	        		firePos = EnumBulletFirePos.RIGHT;
	        	}else {
	        		firePos = EnumBulletFirePos.LEFT;
	        	}
				
				//Charged shot has to be from main hand!
				spawnChargedProjectile(world, player, item, accuracy, projectileForwardOffset, f, ammoConsumed, firePos);
				if (shotgun) {
					for (int i = 0; i < bulletcount; i++) {
						spawnChargedProjectile(world, player, item, spread, projectileForwardOffset, f, ammoConsumed,  firePos);
					}
				}
				
				if (this.hasChargedFireAnim) {
	
					this.playChargedFiresound(item, world, player, f);
	
				}
				
			} else {
	
				if (this.hasChargedFireAnim) {
					// If CLIENT, do Effects
		
					int recoiltime_l = getRecoilTime(f);
					int muzzleFlashtime_l = getMuzzleFlashTime(f);
					
					ShooterValues.setRecoiltime(player, player.getActiveHand()==Hand.OFF_HAND, System.currentTimeMillis() + recoiltime_l, recoiltime_l, (byte)1,f);
					ShooterValues.setMuzzleFlashTime(player, player.getActiveHand()==Hand.OFF_HAND, System.currentTimeMillis() + muzzleFlashtime_l, muzzleFlashtime_l);
	
				}
				client_weaponFired();
			}
		}
	}

	protected void playChargedFiresound(ItemStack item, World world, PlayerEntity player, float chargeProgress) {
		
		SoundUtil.playSoundOnEntityGunPosition(world, player, firesound, SOUND_DISTANCE, 1.0f, false, false, true, TGSoundCategory.GUN_FIRE);

		if (!(rechamberSound==null)) {
			SoundUtil.playSoundOnEntityGunPosition(world, player, rechamberSound, 1.0f, 1.0f, false, false, true, TGSoundCategory.GUN_FIRE);
		}
	}
	
	
	
	@Override
	public int getMaxUseTime(ItemStack stack) {
		return MAX_USE_TIME;
	}

	public int getRecoilTime(float charge) {
		return ((int) (((float) recoiltime / 20.0f) * 1000.0f));
	}

	public int getMuzzleFlashTime(float charge) {
		return ((int) (((float) muzzleFlashtime / 20.0f) * 1000.0f));
	}

	/**
	 * consume ammo from NBTTag
	 * @param item
	 * @param f charge amount
	 * @return ammount of ammo actually consumed
	 */
	public int consumeAmmoCharge(ItemStack item, float f, boolean creative) {
		
		int amount = (int) Math.ceil(f * this.ammoConsumedOnFullCharge);
		
		if(!creative){
			amount = this.useAmmo(item, amount);
		}
		return amount;
	}

	public void spawnChargedProjectile(final World world, final LivingEntity player, ItemStack itemStack, float spread, float offset, float charge, int ammoConsumed, EnumBulletFirePos firePos) {
		
		@SuppressWarnings("rawtypes")
		IChargedProjectileFactory projectileFactory = this.chargedProjectile_selector.getFactoryForType(this.getCurrentAmmoVariantKey(itemStack));
		
		DamageModifier mod = projectileFactory.getDamageModifier();
		byte projectileType = projectileFactory.getProjectileType();
				
		float modified_speed = mod.getVelocity(speed);
		
		GenericProjectile projectile = projectileFactory.createChargedProjectile(world, player, mod.getDamage(damage), modified_speed, mod.getTTL(this.getScaledTTL()), spread, mod.getRange(this.damageDropStart), mod.getRange(damageDropEnd), mod.getDamage(this.damageMin), penetration, getDoBlockDamage(player), firePos, mod.getRadius(radius), gravity, charge, ammoConsumed);
		
		if (projectile != null) {
			projectile.setProjectileType(projectileType);
			projectile.setProperties(player, player.pitch, player.yaw, 0.0F, modified_speed, 1.0F);
			
			if (offset > 0.0f) {
				projectile.shiftForward(offset/modified_speed);
			}
			world.spawnEntity(projectile);
			if(!world.isClient) {
				TGPacketsS2C.sentToAllTrackingPos(new PacketEntityAdditionalSpawnData(projectile), world, new BlockPos(projectile.getPos()));
			}
		}
	}

	@Override
	public boolean canCharge() {
		return true;
	}
	
	public GenericGunCharge setChargeFireAnims(boolean hasAnims) {
		this.hasChargedFireAnim = hasAnims;
		return this;
	}
	
	public GenericGunCharge setFireWhileCharging(boolean canFire) {
		this.canFireWhileCharging = canFire;
		return this;
	}
	
	@Override
	public boolean hasRightClickAction() {
		return this.getGunHandType()==GunHandType.TWO_HANDED;
	}

	public GenericGunCharge setChargeSound(SoundEvent startChargeSound) {
		this.startChargeSound = startChargeSound;
		return this;
	}
	
	public GenericGunCharge setChargeFX(String fx, float offsetX, float offsetY, float offsetZ) {
		this.chargeFX = fx;
		this.chargeFXoffsetX = offsetX;
		this.chargeFXoffsetY = offsetY;
		this.chargeFXoffsetZ = offsetZ;
		return this;
	}
}
