package techguns.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGEntityAttributes;
import techguns.TGPacketsS2C;
import techguns.api.entity.AttackTime;
import techguns.api.entity.ITGExtendedPlayer;
import techguns.api.guns.GunManager;
import techguns.entities.projectiles.GrapplingHookProjectile;
import techguns.items.armors.GenericArmor;
import techguns.items.armors.PoweredArmor;
import techguns.items.armors.TGArmorBonus;
import techguns.items.guns.GenericGun;
import techguns.items.guns.GenericGunCharge;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.packets.PacketSwapWeapon;
import techguns.util.BlockUtil;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class TGPlayerEntityMixin extends LivingEntity implements ITGExtendedPlayer {

	@Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

	@Shadow public abstract PlayerInventory getInventory();

	@Shadow protected abstract void initDataTracker();

	@Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

	@Unique
	public AttackTime techguns_attackTimes_mh = new AttackTime();
	@Unique
	public AttackTime techguns_attackTimes_oh = new AttackTime();

	@Unique
	public int techguns_fireDelayMainhand=0;
	@Unique
	public int techguns_fireDelayOffhand=0;
	@Unique
	public int techguns_loopSoundDelayMainhand=0;
	@Unique
	public int techguns_loopSoundDelayOffhand=0;
	@Unique
	public int techguns_swingSoundDelay=0;
	@Unique
	public ItemStack techguns_gunMainHand=ItemStack.EMPTY;
	@Unique
	public ItemStack techguns_gunOffHand=ItemStack.EMPTY;
	
	@Unique
	private static TrackedData<Boolean> TECHGUNS_DATA_CHARGING_WEAPON = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	
	@Unique
	public Entity techguns_lockOnEntity; //Guided Missile tracking target
	@Unique
	public int techguns_lockOnTicks; //number of ticks the tracked target has been locked on.

	@Unique
	public GrapplingHookProjectile.GrapplingStatus techguns_grapplingStatus = GrapplingHookProjectile.GrapplingStatus.NONE;

	@Unique
	private static TrackedData<Boolean> TECHGUNS_SAFE_MODE = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	@Unique
	private static TrackedData<Boolean> TECHGUNS_STEP_ASSIST = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	/**
	 * used by client only, but saved serverside
	 */
	@Unique
	public boolean techguns_showHudElements=true;
	
	public TGPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Inject(at = @At(value = "RETURN"), method="tick", cancellable = false)
	public void tick(CallbackInfo info) {
		this.playerTickEvent(!this.world.isClient);
	}

	@Inject(at = @At(value = "RETURN"), method="initDataTracker", cancellable = false)
	protected void initDataTracker(CallbackInfo info) {
	    this.dataTracker.startTracking(TECHGUNS_DATA_CHARGING_WEAPON, false);
	    this.dataTracker.startTracking(TECHGUNS_SAFE_MODE, false);
	    this.dataTracker.startTracking(TECHGUNS_STEP_ASSIST, false);
	}

	/**
	 * Hook for armor miningspeed bonus
	 */
	@Inject(at = @At(value="RETURN"), method = "getBlockBreakingSpeed", cancellable = true)
	protected void getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir){
		double speedbonus = this.getAttributeValue(TGEntityAttributes.ARMOR_MININGSPEED);
		double waterspeedbonus = this.getAttributeValue(TGEntityAttributes.ARMOR_WATERMININGSPEED);
		if (speedbonus != 0.0 || waterspeedbonus != 0.0){
			float retval = cir.getReturnValue();
			retval *= (1.0F+speedbonus);

			if (waterspeedbonus != 0.0 && this.isSubmergedIn(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(this)) {
				retval *= (1.0F+waterspeedbonus);
			}
			cir.setReturnValue(retval);
		}
	};


	@Override
	public AttackTime getAttackTime(boolean offHand) {
		if(offHand) {
			return this.techguns_attackTimes_oh;
		}
		return this.techguns_attackTimes_mh;
	}

	@Override
	public int getFireDelay(Hand hand) {
		switch(hand) {
		case OFF_HAND:
			return this.techguns_fireDelayOffhand;
		default:
			return this.techguns_fireDelayMainhand;
		}
	}

	@Override
	public void setFireDelay(Hand hand, int delay) {
		switch(hand) {
		case OFF_HAND:
			this.techguns_fireDelayOffhand = delay;
		default:
			this.techguns_fireDelayMainhand = delay;
		}
	}
	
	@Override
	public void swapAttackTimes() {
		AttackTime a = this.techguns_attackTimes_mh;
		this.techguns_attackTimes_mh=this.techguns_attackTimes_oh;
		this.techguns_attackTimes_oh=a;
		
		int i = techguns_fireDelayMainhand;
		this.techguns_fireDelayMainhand=this.techguns_fireDelayOffhand;
		this.techguns_fireDelayOffhand=i;
	}
	
	@Override
	public boolean hasEnabledSafemode() {
		return this.dataTracker.get(TECHGUNS_SAFE_MODE);
	}
	
	@Override
	public int getLockOnTicks() {
		return this.techguns_lockOnTicks;
	}

	@Override
	public void setLockOnTicks(int value) {
		this.techguns_lockOnTicks=value;
	}
	
	public int getLoopSoundDelay(Hand hand) {
		switch(hand){
			case MAIN_HAND:
				return techguns_loopSoundDelayMainhand;
			case OFF_HAND:
				return techguns_loopSoundDelayOffhand;
		}
		return 0;
	}

	public void setLoopSoundDelay(Hand hand, int delay) {
		switch(hand){
		case MAIN_HAND:
			this.techguns_loopSoundDelayMainhand=delay;
			break;
		case OFF_HAND:
			this.techguns_loopSoundDelayOffhand=delay;
			break;
		}
	}
		
	public boolean isChargingWeapon() {
		return this.dataTracker.get(TECHGUNS_DATA_CHARGING_WEAPON);
	}
	
	public void setChargingWeapon(boolean charging) {
		this.dataTracker.set(TECHGUNS_DATA_CHARGING_WEAPON, charging);
	}
	
	@Override
	public Entity getLockOnEntity() {
		return this.techguns_lockOnEntity;
	}

	@Override
	public void setLockOnEntity(Entity ent) {
		this.techguns_lockOnEntity=ent;
	}

	@Override
	public void setGrapplingStatus(GrapplingHookProjectile.GrapplingStatus status) {
		this.techguns_grapplingStatus = status;
	}
	@Override
	public GrapplingHookProjectile.GrapplingStatus getGrapplingStatus() {
		return this.techguns_grapplingStatus;
	}

	public void playerTickEvent(boolean server) {
		// reduce fire delays on both sides
		if (this.techguns_fireDelayMainhand > 0) {
			this.techguns_fireDelayMainhand--;
		}
		if (this.techguns_loopSoundDelayMainhand > 0) {
			this.techguns_loopSoundDelayMainhand--;
		}
		if (this.techguns_fireDelayOffhand > 0) {
			this.techguns_fireDelayOffhand--;
		}
		if (this.techguns_loopSoundDelayOffhand > 0) {
			this.techguns_loopSoundDelayOffhand--;
		}

		// SERVER ONLY CODE
		if (server) {
			if (this.techguns_swingSoundDelay > 0) {
				this.techguns_swingSoundDelay--;
			}
		}

		/**
		 * detect weapon swaps
		 */
		if (server) { // does only work server side
			ItemStack gunMH = ItemStack.EMPTY;
			ItemStack gunOH = ItemStack.EMPTY;
			if (!this.getMainHandStack().isEmpty() && this.getMainHandStack().getItem() instanceof GenericGun) {
				gunMH = this.getMainHandStack();
			}
			if (!this.getOffHandStack().isEmpty() && this.getOffHandStack().getItem() instanceof GenericGun) {
				gunOH = this.getOffHandStack();
			}

			if (!gunOH.isEmpty() && this.techguns_gunMainHand == gunOH
					|| !gunMH.isEmpty() && this.techguns_gunOffHand == gunMH) {
				TGPacketsS2C.sendToAllTracking(new PacketSwapWeapon((PlayerEntity) (Object) this),
						(PlayerEntity) (Object) this, true);

				int i = this.techguns_fireDelayMainhand;
				this.techguns_fireDelayMainhand = this.techguns_fireDelayOffhand;
				this.techguns_fireDelayOffhand = i;
			}

			this.techguns_gunMainHand = gunMH;
			this.techguns_gunOffHand = gunOH;
		}

		/*
		 * Charging weapon
		 */
		if (server) {
			if (this.isChargingWeapon()) {
				boolean canChargeMainhand = (this.getMainHandStack().getItem() instanceof GenericGunCharge);
				boolean canChargeOffhand = GunManager.canUseOffhand(this) && this.getOffHandStack().getItem() instanceof GenericGunCharge;

				if (this.getItemUseTime() <= 0 || !(canChargeMainhand || canChargeOffhand)){
					this.setChargingWeapon(false);
				}
			}
		}

		/**
		 * handle armor stuff
		 */
		var player = (PlayerEntity & ITGExtendedPlayer) (Object) this;

		PoweredArmor.calculateConsumptionTick(player);

		if (player.isOnFire()) {
			double cooling = player.getAttributeValue(TGEntityAttributes.ARMOR_COOLINGSYSTEM);
			if (cooling >=1.0){
				PoweredArmor.consumePower(player, EquipmentSlot.CHEST, PoweredArmor.CONSUMPTION_EXTINGUISH);
				PoweredArmor.armorActionEffect(TGEntityAttributes.ARMOR_COOLINGSYSTEM, EquipmentSlot.CHEST, player);
				player.extinguish();
			}
		}
		/**
		 * Check if maxHealth dropped and Health is now too high, caused by armor provided entity Attributes
		 */
		if (player.getHealth() > player.getMaxHealth()){
			player.setHealth(player.getMaxHealth());
		}
	}

	@Override
	public boolean showTGHudElements() {
		return this.techguns_showHudElements;
	}

	@Override
	public void setShowTGHudElements(boolean value) {
		this.techguns_showHudElements=value;
	}

	@Override
	public void setSafeMode(boolean value) {
		this.dataTracker.set(TECHGUNS_SAFE_MODE, value);
	}

	@Override
	public void setStepAssist(boolean value) {
		this.dataTracker.set(TECHGUNS_STEP_ASSIST, value);
	}

	@Override
	public boolean hasEnabledStepAssist(){
		return this.dataTracker.get(TECHGUNS_STEP_ASSIST);
	}
}
