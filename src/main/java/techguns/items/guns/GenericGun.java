package techguns.items.guns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.TGCamos;
import techguns.TGItems;
import techguns.TGPacketsS2C;
import techguns.TGSounds;
import techguns.api.ICamoChangeable;
import techguns.api.damagesystem.DamageType;
import techguns.api.entity.ITGExtendedPlayer;
import techguns.api.guns.GunHandType;
import techguns.api.guns.IGenericGun;
import techguns.client.ClientProxy;
import techguns.client.ShooterValues;
import techguns.client.render.ITGItemRenderer;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;
import techguns.items.GenericItem;
import techguns.items.guns.ammo.AmmoType;
import techguns.items.guns.ammo.AmmoTypes;
import techguns.items.guns.ammo.DamageModifier;
import techguns.packets.GunFiredMessage;
import techguns.packets.PacketEntityAdditionalSpawnData;
import techguns.packets.PacketSpawnParticleOnEntity;
import techguns.packets.ReloadStartedMessage;
import techguns.sounds.TGSoundCategory;
import techguns.util.EntityCondition;
import techguns.util.InventoryUtil;
import techguns.util.SoundUtil;
import techguns.util.TextUtil;

public class GenericGun extends GenericItem implements IGenericGun, ITGItemRenderer, ICamoChangeable {
	public static final float SOUND_DISTANCE=4.0f;
	
	public String name;
	
	// Weapon Stats
	boolean semiAuto=false; // = false;
	int minFiretime=4; // = 0;
	int clipsize=10; // = 10;
	int reloadtime=40; // = 40; //ticks
	float damage = 2.0f; // = 10;
	SoundEvent firesound = TGSounds.M4_FIRE;
	SoundEvent reloadsound = TGSounds.M4_RELOAD;
	SoundEvent firesoundStart = TGSounds.M4_FIRE;
	SoundEvent rechamberSound = null;
	int ammoCount; // ammo per reload
	// float recoil = 25.0f;
	float zoomMult = 1.0f;
	boolean canZoom = false;
	boolean toggleZoom = false;
	boolean fireCenteredZoomed=false;
	int ticksToLive = 40;
	float speed = 2.0f;

	float damageMin=1.0f;
	float damageDropStart=20f;
	float damageDropEnd=40f;
	float penetration = 0.0f;

	AmmoType ammoType = AmmoTypes.PISTOL_ROUNDS;

	boolean shotgun = false;
	boolean burst = false;
	float spread = 0.015f;
	int bulletcount = 7;
	float accuracy = 0.0f;

	float projectileForwardOffset = 0.0f;
	
	int maxLoopDelay = 0;
	int recoiltime = 5; // ticks;

	int muzzleFlashtime = 5;
	boolean silenced = false;

	boolean checkRecoil = false;
	boolean checkMuzzleFlash = false;
	
	float AI_attackRange = 15.0F; //sqrt of actual distance
	int AI_attackTime = 60;
	int AI_burstCount = 0;
	int AI_burstAttackTime = 0;
	
	int camoCount=1;
	
	int lockOnTicks = 0; //MaximumLockOnTime
	int lockOnPersistTicks = 0;
	
	String fireFX = null;
	float fireFXoffsetX = 0;
	float fireFXoffsetY = 0;
	float fireFXoffsetZ = 0;
	
	EnumCrosshairStyle crossHairStyle = EnumCrosshairStyle.GUN_DYNAMIC;
	
	public ArrayList<Identifier> textures;
	
	@SuppressWarnings("rawtypes")
	protected ProjectileSelector projectile_selector; 
	
	GunHandType handType = GunHandType.TWO_HANDED; 
	//Ammount of bullets the gun gets per bullet item
	//protected float shotsPerBullet;
	
	int miningAmmoConsumption = 1;
	

	/**
	 * spread multiplier while shooting zoomed
	 */
	float zoombonus=1.0f;
	
	float radius=1.0f;

	public static final double DEFAULT_GRAVITY = 0.005000000074505806D;
	double gravity = DEFAULT_GRAVITY;

	boolean shootWithLeftClick=true;
	
	public float turretPosOffsetX=0.0f;
	public float turretPosOffsetY=0.0f;
	public float turretPosOffsetZ=0.0f;
	
	public static ArrayList<GenericGun> guns = new ArrayList<>();
	
	/**
	 * Lightpulse parameters
	 */
	public int light_lifetime =2;
	public float light_radius_start = 3.0f;
	public float light_radius_end = 3.0f;
	public float light_r = 1f;
	public float light_g = 0.9f;
	public float light_b = 0.2f;
	protected boolean muzzelight=true;
		
	boolean hasAmbientEffect=false;
	/**
	 * Should bind the texture on rendering?
	 */
	public boolean hasCustomTexture=true;

	protected RangeTooltipType rangeTooltipType = RangeTooltipType.DROP;

	public enum HoldType {
		CROSSBOW,
		BOW,
		LOWERED
	}
	public HoldType holdType = HoldType.CROSSBOW;
		
	private GenericGun() {
		super(new Item.Settings().maxCount(1).group(ItemGroup.COMBAT));
	}

	public GenericGun(String name, @SuppressWarnings("rawtypes") ProjectileSelector projectileSelector, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL, float accuracy){
		this(true, name,projectileSelector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL, accuracy);
	}
	
	public GenericGun(boolean addToGunList,String name, @SuppressWarnings("rawtypes") ProjectileSelector projectile_selector, boolean semiAuto, int minFiretime, int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL, float accuracy){
		this();
		this.name = name;
		this.ammoType = projectile_selector.ammoType;
		this.semiAuto = semiAuto;
		this.minFiretime = minFiretime;
		this.clipsize = clipsize;
		this.reloadtime = reloadtime;
		this.damage = damage;
		this.firesound = firesound;
		this.reloadsound = reloadsound;

		this.ammoCount=1;
		//this.shotsPerBullet=clipsize;
		this.ticksToLive=TTL;
		this.accuracy =accuracy;
		
		//Defaults to no drop
		this.damageDropStart=TTL;
		this.damageDropEnd=TTL;
		this.damageMin=damage;
		
		this.projectile_selector = projectile_selector;
		
		if (addToGunList) {
			guns.add(this);
		}
	}
	
	public GenericGun setMuzzleLight(float r, float g, float b) {
		this.light_r=r;
		this.light_g=g;
		this.light_b=b;
		return this;
	}
	
	public GenericGun setNoMuzzleLight() {
		this.muzzelight=false;
		return this;
	}
	
	public GenericGun setMuzzleLight(int lifetime, float radius_start, float radius_end, float r, float g, float b) {
		this.light_lifetime = lifetime;
		this.light_radius_start= radius_start;
		this.light_radius_end= radius_end;
		this.setMuzzleLight(r, g, b);
		return this;
	}
	
	public GenericGun setRangeTooltipType(RangeTooltipType type) {
		this.rangeTooltipType=type;
		return this;
	}
	
	public GenericGun setHasAmbient() {
		this.hasAmbientEffect=true;
		return this;
	}
	
	public boolean hasAmbientEffect() {
		return this.hasAmbientEffect;
	}
	
	public GenericGun setNoCustomTexture() {
		this.hasCustomTexture=false;
		return this;
	}
		
	public HoldType getHoldType() {
		return holdType;
	}

	public GenericGun setHoldType(HoldType holdType) {
		this.holdType = holdType;
		return this;
	}

	public GenericGun setGravity(double grav) {
		this.gravity=grav;
		return this;
	}
	
	public GenericGun setAmmoCount(int count) {
		this.ammoCount=count;
		//this.shotsPerBullet=(this.clipsize*1.0f)/(this.ammoCount*1.0f);
		return this;
	}

	public GenericGun setZoom(float mult, boolean toggle, float bonus, boolean fireCenteredWhileZooming){
		this.canZoom=true;
		this.zoomMult = mult;
		this.toggleZoom = toggle;
		this.zoombonus=bonus;
		this.fireCenteredZoomed=fireCenteredWhileZooming;
		return this;
	}
	
	
	public GenericGun setShotgunSpred(int count, float spread){
		return this.setShotgunSpread(count, spread, false);
	}
	
	public GenericGun setShotgunSpread(int count, float spread, boolean burst){
		this.shotgun=true;
		this.spread=spread;
		this.bulletcount=count;
		this.burst=burst;
		return this;
	}
	
	public GenericGun setForwardOffset(float offset) {
		this.projectileForwardOffset = offset;
		return this;
	}
	
	public GenericGun setBulletSpeed(float speed){
		this.speed=speed;
		return this;
	}
	
	public GenericGun setPenetration(float pen){
		this.penetration = pen;
		return this;
	}
	
	public GenericGun setMuzzleFlashTime(int time) {
		this.muzzleFlashtime = time;
		return this;
	}

	public GenericGun setFiresoundStart(SoundEvent firesoundStart) {
		this.firesoundStart = firesoundStart;
		return this;
	}
	
	public GenericGun setMaxLoopDelay(int maxLoopDelay) {
		this.maxLoopDelay = maxLoopDelay;
		return this;
	}
	
	public GenericGun setRecoiltime(int recoiltime){
		this.recoiltime = recoiltime;
		return this;
	}
	
	public GenericGun setRechamberSound(SoundEvent rechamberSound) {
		this.rechamberSound = rechamberSound;
		return this;
	}
	
	
	
	/**
	 * @param x - Offset sideways, +right -left
	 * @param y - Offset height
	 * @param z - offset forward/backward
	 * @return
	 */
	public GenericGun setTurretPosOffset(float x, float y, float z){
		this.turretPosOffsetX=x;
		this.turretPosOffsetY=y;
		this.turretPosOffsetZ=z;
		return this;
	}
	
	protected int getScaledTTL(){
		return (int) Math.ceil(this.ticksToLive/this.speed);
	}
	
	/**
	 * @param ticks Time required to lock target
	 * @param lockExtraTicks Extra time that is added when the lock is complete
	 */
	public GenericGun setLockOn(int ticks, int lockExtraTicks) {
		this.lockOnTicks = ticks;
		this.lockOnPersistTicks = lockExtraTicks;
		return this;
	}
	

	public int getLockOnTicks() {
		return lockOnTicks;
	}

	public int getLockOnPersistTicks() {
		return lockOnPersistTicks;
	}

	/**
	 * Called only clientside!, requires packets for actions other than zoom (clientside
	 * @param player
	 * @param stack
	 */
	public boolean gunSecondaryAction(PlayerEntity player, ItemStack stack) {
		if (player.world.isClient && canZoom  && this.toggleZoom && !ShooterValues.getPlayerIsReloading(player, false)) {
			ClientProxy cp = ClientProxy.get();
			if (cp.player_zoom != 1.0f) {
				cp.player_zoom= 1.0f;
			} else {
				cp.player_zoom = this.zoomMult;
			}
			return true;
    	}
		return false; //(this.getGunHandType() == GunHandType.TWO_HANDED); //Block the mouse click if two-handed gun
	}

	//TODO check
	/*@Override
	public boolean onEntitySwing(LivingEntity entityLiving, ItemStack stack) {
		if(this.shootWithLeftClick){
			return true;
		} else {
			if(this.getCurrentAmmo(stack)>=this.miningAmmoConsumption){
				return true;
			} else {
				return false;
			}
		}
	}*/
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		this.gunSecondaryAction(user, user.getStackInHand(hand));
		return new TypedActionResult<ItemStack>(ActionResult.PASS, user.getStackInHand(hand));
	}

	public ItemStack[] getReloadItem(ItemStack stack) {
		return this.ammoType.getAmmo(this.getCurrentAmmoVariant(stack));
	}
	
	public int getAmmoCount() {
		return ammoCount;
	}
	
	public int getAmmoLeftCountTooltip(ItemStack item){
		int ammo = this.getCurrentAmmo(item);
		if(this.burst){
			return ammo*(this.bulletcount+1);
		} else {
			return ammo;
		}
	}

	public int getClipsizeTooltip() {
		if(this.burst){
			return clipsize*(this.bulletcount+1);
		} else {
			return clipsize;
		}
	}
	
	public int getAmmoLeftCount(int mags){
		int count=(this.clipsize/this.ammoCount) * mags; 
		if(this.burst){
			return count*(this.bulletcount+1);
		} else {
			return count;
		}
	}

	/**
	 * Override in subclass for extra logic on projectile spawn, before spawning in world
	 */
	protected void onProjectileSpawn(GenericProjectile proj, final World world, final LivingEntity player, final ItemStack itemstack, float spread, float offset, float damagebonus, EnumBulletFirePos firePos, Entity target) {}
	
	@SuppressWarnings("unchecked")
	protected void spawnProjectile(final World world, final LivingEntity player, final ItemStack itemstack, float spread, float offset, float damagebonus, EnumBulletFirePos firePos, Entity target) {
		String ammoVariantKey = this.getCurrentAmmoVariantKey(itemstack);
		
		IProjectileFactory<GenericProjectile> projectileFactory = this.projectile_selector.getFactoryForType(ammoVariantKey);
		
		DamageModifier mod = projectileFactory.getDamageModifier();
		byte projectileType = projectileFactory.getProjectileType();
		
		float modified_speed = mod.getVelocity(speed);
				
		GenericProjectile projectile = projectileFactory.createProjectile(this, world, player, mod.getDamage(damage) * damagebonus, modified_speed, mod.getTTL(this.getScaledTTL()), spread, mod.getRange(this.damageDropStart),
				mod.getRange(this.damageDropEnd), mod.getDamage(this.damageMin) * damagebonus, this.penetration, getDoBlockDamage(player), firePos, mod.getRadius(radius), gravity);

		projectile.setProjectileType(projectileType);
		projectile.setProperties(player, projectile.pitch, projectile.yaw, 0.0f, modified_speed, 0.0F);
		//projectile.setProperties(player, player.pitch, player.yaw, 0.0F, modified_speed, 1.0F);
						
		//float f=1.0f;
		//TODO add lights
		/*if(this.muzzelight) {
			Techguns.proxy.createLightPulse(proj.posX+player.getLookVec().x*f, proj.posY+player.getLookVec().y*f, proj.posZ+player.getLookVec().z*f, this.light_lifetime, this.light_radius_start, this.light_radius_end, this.light_r, this.light_g, this.light_b);
		}*/
		//TODO implement silence
		/*if (silenced) {
			proj.setSilenced();
		}*/
		if (offset > 0.0f) {
			projectile.shiftForward(offset/modified_speed);
		}
		// callback for subclasses
		this.onProjectileSpawn(projectile, world, player, itemstack, spread, offset, damagebonus, firePos, target);
		
		world.spawnEntity(projectile);
		
		if(!world.isClient) {
			TGPacketsS2C.sentToAllTrackingPos(new PacketEntityAdditionalSpawnData(projectile), world, new BlockPos(projectile.getPos()));
		}
	}

	public static boolean getDoBlockDamage(LivingEntity elb) {
		boolean blockdamage = false;
		if (elb instanceof PlayerEntity) {
			ITGExtendedPlayer tg_player = (ITGExtendedPlayer) elb;
			if (tg_player != null) {
				blockdamage = !tg_player.hasEnabledSafemode();
			}
		}

		return blockdamage;
	}

	@Override
	public boolean isShootWithLeftClick() {
		return this.shootWithLeftClick;
	}

	@Override
	public boolean isSemiAuto() {
		return this.semiAuto;
	}
	
	public GenericGun setCheckRecoil(){
		this.checkRecoil=true;
		return this;
	}
	
	public GenericGun setCheckMuzzleFlash(){
		this.checkMuzzleFlash=true;
		return this;
	}

	@Override
	public boolean isZooming() {
		return ClientProxy.get().player_zoom==this.zoomMult;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}
	
	@Override
	public boolean isAimed(LivingEntity ent, ItemStack stack) {
		return true;
	}

	@Override
	public void shootGunPrimary(ItemStack stack, World world, PlayerEntity player, boolean zooming, Hand hand, Entity target) {
	
    		int ammo = this.getCurrentAmmo(stack);
    	
    		//System.out.println("Shoot gun:"+stack+" Hand:"+hand);
    		
    		byte ATTACK_TYPE = 0;
    		ITGExtendedPlayer extendedPlayer = (ITGExtendedPlayer)player;
    		if (ammo>0) {
    			//bullets left
		    	int firedelay = extendedPlayer.getFireDelay(hand);

		    	if (firedelay <=0) {
		  
		    		extendedPlayer.setFireDelay(hand,this.minFiretime);
		    
		    		if (!player.abilities.creativeMode) {
	    				this.useAmmo(stack, 1);
		   
		    		}
		    		
			        if (!world.isClient)
			        {
			        	/*
			        	 * If SERVER, create projectile
			        	 */
			        	
			        	float accuracybonus = 1.0f; //TODO AccuracyBonus //MathUtil.clamp(1.0f-GenericArmor.getArmorBonusForPlayer(player, TGArmorBonus.GUN_ACCURACY,false), 0f,1f);
			        	
			        	//add spread penalty when shooting while blocking
			        	if(hand== Hand.MAIN_HAND && player.isBlocking()) {
			        		if(this.handType==GunHandType.ONE_HANDED) {
			        			accuracybonus *= 4.0f;
			        		} else {
			        			accuracybonus *= 8.0f;
			        		}
			        	}
			        	
			        	EnumBulletFirePos firePos;
			        	if ((hand == Hand.MAIN_HAND && player.getMainArm() == Arm.RIGHT) || (hand == Hand.OFF_HAND && player.getMainArm() == Arm.LEFT)) {
			        		firePos = EnumBulletFirePos.RIGHT;
			        	}else {
			        		firePos = EnumBulletFirePos.LEFT;
			        	}
			        	
			        	if(zooming){
			        		accuracybonus*=this.zoombonus;
			        		if (fireCenteredZoomed) {
			        			firePos=EnumBulletFirePos.CENTER;
			        		}
			        	}
			        	this.shootGun(world, player,stack, accuracybonus,1.0f,ATTACK_TYPE, hand,firePos, target);
			        	      
			        	/*
			        	 * Fire FX
			        	 */
			        	if (this.fireFX != null) {
							float x;
							if (firePos == EnumBulletFirePos.RIGHT) {
								x = -this.fireFXoffsetX;
							} else {
								x = this.fireFXoffsetX;
							}
							float y = this.fireFXoffsetY - 0.10000000149011612f;
							TGPacketsS2C.sendToAllTracking(new PacketSpawnParticleOnEntity(this.fireFX, player, x, y, this.fireFXoffsetZ, true, EntityCondition.ENTITY_ALIVE), player, true);
						}
			        	
			        } else {
			        	/*
			        	 * If CLIENT, do Effects
			        	 */	
			          	int recoiltime_l = (int) ((((float)recoiltime/20.0f)*1000.0f));
			        	int muzzleFlashtime_l = (int) ((((float)muzzleFlashtime/20.0f)*1000.0f));

			        	
			        	if (!checkRecoil || !ShooterValues.isStillRecoiling(player,hand==Hand.OFF_HAND, ATTACK_TYPE) ){
			        		//System.out.println("SettingRecoilTime");
				        	ShooterValues.setRecoiltime(player, hand==Hand.OFF_HAND,System.currentTimeMillis() + recoiltime_l, recoiltime_l,ATTACK_TYPE);
				        	
			        	}
			        	
			        	ClientProxy cp = ClientProxy.get();
			        	if (!checkMuzzleFlash || ShooterValues.getMuzzleFlashTime(player, hand==Hand.OFF_HAND) <= System.currentTimeMillis()) {
			        		ShooterValues.setMuzzleFlashTime(player, hand==Hand.OFF_HAND, System.currentTimeMillis() + muzzleFlashtime_l, muzzleFlashtime_l);
			        		Random rand = world.random;
			        		cp.muzzleFlashJitterX = 1.0f-(rand.nextFloat()*2.0f);
			        		cp.muzzleFlashJitterY = 1.0f-(rand.nextFloat()*2.0f);
			        		cp.muzzleFlashJitterScale = 1.0f-(rand.nextFloat()*2.0f);
			        		cp.muzzleFlashJitterAngle = 1.0f-(rand.nextFloat()*2.0f);
			        	}
			        	
				        client_weaponFired();
			        }
			        /*
			         * Do sounds on Client & Server
			         */
		        	if (maxLoopDelay > 0 && extendedPlayer.getLoopSoundDelay(hand)<=0) {

		        		SoundUtil.playSoundOnEntityGunPosition(world, player, this.firesoundStart, SOUND_DISTANCE, 1.0F, false, false, TGSoundCategory.GUN_FIRE);

		        		extendedPlayer.setLoopSoundDelay(hand,this.maxLoopDelay);
		        		
		        	}else {
	
		        		SoundUtil.playSoundOnEntityGunPosition(world, player, this.firesound, SOUND_DISTANCE, 1.0F, false, false, TGSoundCategory.GUN_FIRE);
		        		if (this.maxLoopDelay>0){
		        			extendedPlayer.setLoopSoundDelay(hand,this.maxLoopDelay);
		        		}
		        	}
		        	
		        	if (!(rechamberSound==null)) {
		        		SoundUtil.playSoundOnEntityGunPosition(world, player, rechamberSound, 1.0F, 1.0F, false, false, TGSoundCategory.RELOAD);
		        	}
		        	
			        
		    	} else {
		    		//System.out.println(Thread.currentThread().toString()+": Skip shot, can't fire yet");
		    	}
		    	
    		} else {
    			//mag empty, reload needed
    			
    			//look for ammo
    			if (InventoryUtil.consumeAmmoPlayer(player,this.ammoType.getAmmo(this.getCurrentAmmoVariant(stack)))) {
    			
    				Arrays.stream(this.ammoType.getEmptyMag()).forEach( e -> {
    					if (!e.isEmpty()){
        					int amount=InventoryUtil.addAmmoToPlayerInventory(player, TGItems.newStack(e, 1));
        					if(amount >0 && !world.isClient){
        						player.world.spawnEntity(new ItemEntity(player.world, player.getX(), player.getY(), player.getZ(), TGItems.newStack(e, amount)));
        					}
        				}
    				});
    			 
    				//stop toggle zooming when reloading
    				if (world.isClient) {
    					if (canZoom  && this.toggleZoom) {
    						ClientProxy cp = ClientProxy.get();
    		    			if (cp.player_zoom != 1.0f) {
    		    				cp.player_zoom= 1.0f;
    		    			}
    		    		}
    				}
    				
	    			//START RELOAD

    				extendedPlayer.setFireDelay(hand, this.reloadtime-this.minFiretime);
    						
	    			if (ammoCount >1) {
	    				int i =1;
	    				while (i<ammoCount && InventoryUtil.consumeAmmoPlayer(player,this.ammoType.getAmmo(this.getCurrentAmmoVariant(stack)))){
	    					i++;
	    				}

	    				this.reloadAmmo(stack, i);
	    			} else {
	    				this.reloadAmmo(stack);
	    			}
	    			
	    			SoundUtil.playReloadSoundOnEntity(world,player,reloadsound, 1.0F, 1.0F, false, true, TGSoundCategory.RELOAD);

	    			
    				if (world.isClient) {
    					
    					int time = (int) (((float)reloadtime/20.0f)*1000);
    					ShooterValues.setReloadtime(player,hand==Hand.OFF_HAND,System.currentTimeMillis()+time, time, ATTACK_TYPE);
    					
    					client_startReload();
    				} else{
    					//send reloadpacket
    					//send pakets to clients
    					
				    	int msg_reloadtime = ((int)(((float)reloadtime/20.0f)*1000.0f));
    			    	TGPacketsS2C.sendToAllAroundEntity(new ReloadStartedMessage(player,hand,msg_reloadtime,ATTACK_TYPE), player, 100.0);
				    	
    				}

    			} else {
    			
    				
    				if (!world.isClient)
			        {
    					//TODO emptySound
	    				//world.playSoundAtEntity(player, "mob.villager.idle", 1.0F, 1.0F );
			        }
    			}
    		}
	}
	
	/**
	 * for extra actions in subclass
	 */
	protected void client_weaponFired() {	
	}

	/**
	 * for extra actions in subclass
	 */
	protected void client_startReload() {	
	}
	
	public GenericGun setAIStats(float attackRange, int attackTime, int burstCount, int burstAttackTime) {
		this.AI_attackRange = attackRange;
		this.AI_attackTime = attackTime;
		this.AI_burstCount = burstCount;
		this.AI_burstAttackTime = burstAttackTime;
		return this;
	}
	
	protected void shootGun(World world, LivingEntity player,ItemStack itemstack,float accuracybonus,float damagebonus, int attackType, Hand hand, EnumBulletFirePos firePos, Entity target){
		
		//send pakets to clients
		if (!world.isClient){
	    	int msg_recoiltime = ((int)(((float)recoiltime/20.0f)*1000.0f));
	    	int msg_muzzleflashtime = ((int)(((float)muzzleFlashtime/20.0f)*1000.0f));
	    	TGPacketsS2C.sendToAllAroundEntity(new GunFiredMessage(player,msg_recoiltime,msg_muzzleflashtime,(byte)attackType,checkRecoil,hand), player, 100.0);
		}
    	//
		spawnProjectile(world, player,itemstack, accuracy*accuracybonus, projectileForwardOffset,damagebonus, firePos, target);
		
        if (shotgun){
        	float offset=0;
        	if (this.burst){
        		offset = this.speed/(this.bulletcount);
        	}
        	
        	for (int i=0; i<bulletcount; i++) {
        		spawnProjectile(world, player,itemstack, spread*accuracybonus,projectileForwardOffset+offset*(i+1.0f),damagebonus, firePos, target);
        	}
        }		
	}
	
	@Override
	public void onCraft(ItemStack stack, World world, PlayerEntity player) {
		super.onCraft(stack, world, player);
		CompoundTag tags = stack.getTag();
		if(tags==null){
			tags=new CompoundTag();
			stack.setTag(tags);
		
			int dmg = stack.getDamage();
			tags.putString("camo", "");
			tags.putString("ammovariant", AmmoTypes.TYPE_DEFAULT);
			tags.putShort("ammo", dmg==0 ? (short)this.clipsize : (short)(this.clipsize-dmg));
			stack.setDamage(0);
			this.addInitialTags(tags);
		} else {
			stack.setDamage(0);
		}
	}
	
	/**
	 * Add subclass tags here
	 * @param tags
	 */
	protected void addInitialTags(CompoundTag tags) {
		
	}
	
	public int getCurrentAmmo(ItemStack stack){
		CompoundTag tags = stack.getTag();
		if(tags==null){
			this.onCraft(stack, null, null); //world and player are not needed
			tags = stack.getTag();
		}
		return tags.getShort("ammo");
	}
	
	public int getCurrentAmmoVariant(ItemStack stack){
		String variant = this.getCurrentAmmoVariantKey(stack);
		return this.getAmmoType().getIDforVariantKey(variant);
	}
	
	public String getCurrentAmmoVariantKey(ItemStack stack){
		CompoundTag tags = stack.getTag();
		if(tags==null){
			this.onCraft(stack, null, null); //world and player are not needed
			tags = stack.getTag();
		}
		String var = tags.getString("ammovariant");
		if(var==null || var.equals("")) return AmmoTypes.TYPE_DEFAULT;
		return var;
	}
	
	/**
	 * Reduces ammo by amount, use positive values! amount=3 means ammo-=3;
	 * @param stack
	 * @param amount
	 * @return actually consumed ammo
	 */
	public int useAmmo(ItemStack stack, int amount){
		int ammo = this.getCurrentAmmo(stack);
		CompoundTag tags = stack.getTag();
		if(ammo-amount>=0){
			tags.putShort("ammo", (short) (ammo-amount));
			return amount;
		} else {
			tags.putShort("ammo", (short) 0);
			return ammo;
		}
	}
	
	public void reloadAmmo(ItemStack stack){
		this.reloadAmmo(stack, this.clipsize);
	}
	
	public void reloadAmmo(ItemStack stack, int amount){
		int ammo = this.getCurrentAmmo(stack);
		CompoundTag tags = stack.getTag();
		tags.putShort("ammo", (short) (ammo+amount));
	}
	
	@Override
	public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
		if (this.isIn(group)) {
			ItemStack gun = new ItemStack(this, 1);
			this.onCraft(gun, null, null);
			stacks.add(gun);
		}
	}

	public double getPercentAmmoLeft(ItemStack stack) {
		return ((double)this.getCurrentAmmo(stack))/((double)this.clipsize);
	}
	
	@Override
	public int getAmmoLeft(ItemStack stack) {
		return this.getCurrentAmmo(stack);
	}

	//TODO reequipanim?
	/*@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		
		return slotChanged || !oldStack.isItemEqual(newStack); 
	}*/
	
	public GenericGun setDamageDrop(float start, float end, float minDamage){
		this.damageDropStart=start;
		this.damageDropEnd=end;
		this.damageMin=minDamage;
		return this;
	}
	
	public GenericGun setSilenced(boolean s){
		this.silenced=s;
		return this;
	}

	@Override
	public GunHandType getGunHandType() {
		return this.handType;
	}
	
	public GenericGun setHandType(GunHandType type) {
		this.handType=type;
		return this;
	}

	@Override
	public boolean isHoldZoom() {
		return !this.toggleZoom;
	}

	@Override
	public float getZoomMult() {
		return this.zoomMult;
	}

	protected String getTooltipTextDmg(ItemStack stack, boolean expanded) {
		
		DamageModifier mod = this.projectile_selector.getFactoryForType(this.getCurrentAmmoVariantKey(stack)).getDamageModifier();
		float dmg = mod.getDamage(this.damage);
		if(dmg==this.damage) {
			return ""+this.damage+(this.damageMin<this.damage?"-"+this.damageMin:"");
		} else {
			float dmgmin = mod.getDamage(this.damageMin);
			Formatting prefix=Formatting.GREEN;
			String sgn="+";
			if(dmg<this.damage) {
				prefix = Formatting.RED;
				sgn="-";
			}
			

			String suffix="";

			if(expanded) {
				if(mod.getDmgMul()!=1f) {
					float f = mod.getDmgMul()-1f;
					String x = String.format("%.0f", f*100f);
					suffix += " ("+sgn+x+"%)";
				}
				if(mod.getDmgAdd()!=0f) {
					float add = mod.getDmgAdd();
					suffix += " ("+(add>0?"+":"")+String.format("%.1f", add)+")";
				}
			}
			
			String sd = String.format("%.1f", dmg);
			String sm = String.format("%.1f", dmgmin);
			
			return prefix+""+sd+(dmgmin<dmg?"-"+sm:"")+suffix;
		}
		
	}
	
	protected String getTooltipTextRange(ItemStack stack) {
		DamageModifier mod = this.projectile_selector.getFactoryForType(this.getCurrentAmmoVariantKey(stack)).getDamageModifier();
		
		int ttl = mod.getTTL(this.ticksToLive);
		float rangeStart = mod.getRange(this.damageDropStart);
		float rangeEnd = mod.getRange(this.damageDropEnd);
		
		String prefix = "";	
		String suffix="";
		if(rangeStart != this.damageDropStart) {

			String sgn="+";
			if (rangeStart > this.damageDropStart) {
				prefix = Formatting.GREEN.toString();
			} else {
				prefix = Formatting.RED.toString();
				sgn="-";
			}
			
			if(mod.getRangeMul()!=1f) {
				float f = mod.getRangeMul()-1f;
				String x = String.format("%.0f", f*100f);
				suffix += " ("+sgn+x+"%)";
			}
			if(mod.getRangeAdd()!=0f) {
				float add = mod.getRangeAdd();
				suffix += " ("+(add>0?"+":"")+String.format("%.1f", add)+")";
			}
	
		} 
			
		String sStart = String.format("%.1f", rangeStart);
		String sEnd = String.format("%.1f", rangeEnd);
		
		if(this.rangeTooltipType == RangeTooltipType.DROP) {
			return TextUtil.transTG("gun.tooltip.range")+": "+prefix+sStart+","+sEnd+","+ttl+suffix;
		} else if ( this.rangeTooltipType == RangeTooltipType.NO_DROP) {
			return TextUtil.transTG("gun.tooltip.range")+": "+prefix+sStart+suffix;
		} else {
			return TextUtil.transTG("gun.tooltip.radius")+": "+prefix+sStart+"-"+sEnd+suffix;
		}
	
	}
	
	protected String getTooltipTextVelocity(ItemStack stack) {
		DamageModifier mod = this.projectile_selector.getFactoryForType(this.getCurrentAmmoVariantKey(stack)).getDamageModifier();
		
		float velocity = mod.getVelocity(this.speed);
		
		String prefix = "";	
		String suffix="";
		if(velocity != this.speed) {

			String sgn="+";
			if (velocity >= this.speed) {
				prefix = Formatting.GREEN.toString();
			} else {
				prefix = Formatting.RED.toString();
				sgn="";
			}
			
			if(mod.getVelocityMul()!=1f) {
				float f = mod.getVelocityMul()-1f;
				String x = String.format("%.0f", f*100f);
				suffix += " ("+sgn+x+"%)";
			}
			if(mod.getVelocityAdd()!=0f) {
				float add = mod.getVelocityAdd();
				suffix += " ("+(add>0?"+":"")+String.format("%.1f", add)+")";
			}
	
		} 
			
		String sVelocity = String.format("%.1f", velocity);
		
		return TextUtil.transTG("gun.tooltip.velocity")+": "+prefix+sVelocity+suffix;
	
	}
		
	protected void addMiningTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {}
	
	
	
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		//lshift and rshift
		if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344)){
			tooltip.add(new LiteralText(TextUtil.transTG("gun.tooltip.handtype")+": "+this.getGunHandType().toString()));
			
			ItemStack[] ammo = this.ammoType.getAmmo(this.getCurrentAmmoVariant(stack));
			for(int i=0;i< ammo.length;i++) {
				Text t = ammo[i].getName();
				String itemname = "";
				if (t instanceof TranslatableText) {
					itemname = TextUtil.trans(((TranslatableText)t).getKey());
				}
				tooltip.add(new LiteralText(TextUtil.transTG("gun.tooltip.ammo")+": "+(this.ammoCount>1 ? this.ammoCount+"x " : "")+Formatting.WHITE+itemname));
			}
			this.addMiningTooltip(stack, world, tooltip, context);
			tooltip.add(new LiteralText(TextUtil.transTG("gun.tooltip.damageType")+": "+this.getDamageType(stack).toString()));
			tooltip.add(new LiteralText(TextUtil.transTG("gun.tooltip.damage")+(this.shotgun ? ("(x"+ (this.bulletcount+1)+")") : "" )+": "+getTooltipTextDmg(stack,true)));
			//list.add(TextUtil.trans("techguns.gun.tooltip.range")+": "+this.damageDropStart+","+this.damageDropEnd+","+this.ticksToLive);
			tooltip.add(new LiteralText(getTooltipTextRange(stack)));
			//list.add(TextUtil.trans("techguns.gun.tooltip.velocity")+": "+this.speed);
			tooltip.add(new LiteralText(getTooltipTextVelocity(stack)));
			tooltip.add(new LiteralText(TextUtil.transTG("gun.tooltip.spread")+": "+this.accuracy + (this.zoombonus!=1.0 ? (" Z:"+this.zoombonus*this.accuracy) : "")));
			tooltip.add(new LiteralText(TextUtil.transTG("gun.tooltip.clipsize")+": "+this.clipsize));
			tooltip.add(new LiteralText(TextUtil.transTG("gun.tooltip.reloadTime")+": "+this.reloadtime*0.05f+"s"));
			if (this.penetration>0.0f){
				tooltip.add(new LiteralText(TextUtil.transTG("gun.tooltip.armorPen")+": "+String.format("%.1f", this.penetration)));
			}
			if (this.canZoom) {
				tooltip.add(new LiteralText(TextUtil.transTG("gun.tooltip.zoom")+":"+(this.toggleZoom ? "("+TextUtil.transTG("gun.tooltip.zoom.toogle")+")":"("+TextUtil.transTG("gun.tooltip.zoom.hold")+")")+" "+TextUtil.transTG("gun.tooltip.zoom.multiplier")+":"+this.zoomMult));
			}
			
		} else {
			ItemStack[] ammo = this.ammoType.getAmmo(this.getCurrentAmmoVariant(stack));
			for(int i=0;i< ammo.length;i++) {
				Text t = ammo[i].getName();
				String itemname = "";
				if (t instanceof TranslatableText) {
					itemname = TextUtil.trans(((TranslatableText)t).getKey());
				}
				tooltip.add(new LiteralText(TextUtil.transTG("gun.tooltip.ammo")+": "+(this.ammoCount>1 ? this.ammoCount+"x " : "")+Formatting.WHITE+itemname));
			}
			this.addMiningTooltip(stack, world, tooltip, context);
			tooltip.add(new LiteralText(TextUtil.transTG("gun.tooltip.damage")+(this.shotgun ? ("(x"+ (this.bulletcount+1)+")") : "" )+": "+getTooltipTextDmg(stack,false)));
			tooltip.add(new LiteralText(TextUtil.transTG("gun.tooltip.shift1")+" "+Formatting.GREEN+TextUtil.transTG("gun.tooltip.shift2")+" "+Formatting.GRAY+TextUtil.transTG("gun.tooltip.shift3")));
		}
	}

	public DamageType getDamageType(ItemStack stack) {
		return this.projectile_selector.getFactoryForType(this.getCurrentAmmoVariantKey(stack)).getDamageType();
	}

	//TODO implement camos
	/*@Override
	public int getCamoCount() {
		return this.camoCount;
	}

	@Override
	public String getCurrentCamoName(ItemStack item) {
		NBTTagCompound tags = item.getTagCompound();
		byte camoID=0;
		if (tags!=null && tags.hasKey("camo")){
			camoID=tags.getByte("camo");
		}
		if(camoID>0){
			return TextUtil.trans(this.getUnlocalizedName()+".camoname."+camoID);
		} else {
			return TextUtil.trans("techguns.item.defaultcamo");
		}
	}*/

	/**
	 * Should this weapon shoot with left click instead of mine, defaults to true
	 * @param shootWithLeftClick
	 */
	public GenericGun setShootWithLeftClick(boolean shootWithLeftClick) {
		this.shootWithLeftClick = shootWithLeftClick;
		return this;
	}
	
	public GenericGun setMiningAmmoConsumption(int ammo) {
		this.miningAmmoConsumption = ammo;
		return this;
	}

	public GenericGun setFireFX(String fx, float offsetX, float offsetY, float offsetZ) {
		this.fireFX = fx;
		this.fireFXoffsetX = offsetX;
		this.fireFXoffsetY = offsetY;
		this.fireFXoffsetZ = offsetZ;
		return this;
	}
	
	/**
	 * Override in Subclass to define damagesource used for player melee attacks, only relevant if "shootWithLeftClick" is not set
	 * @return
	 */
	public DamageSource getMeleeDamageSource(PlayerEntity player, ItemStack stack){
		TGDamageSource src = new TGDamageSource("player", player, player, DamageType.PHYSICAL, DeathType.GORE);
		return src;
	}
	
	protected boolean hasSwordSweep() {
		return true;
	}
	
	public int getMiningAmmoConsumption() {
		return miningAmmoConsumption;
	}

	@Override
	public boolean isModelBase(ItemStack stack) {
		return this.hasCustomTexture;
	}
	
	//TODO add AI attacks
	/*public EntityAIRangedAttack getAIAttack(IRangedAttackMob shooter) {
		return new EntityAIRangedAttack(shooter, 1.0D, this.AI_attackTime/3, this.AI_attackTime, this.AI_attackRange, this.AI_burstCount, this.AI_burstAttackTime);
	}*/
	
	 public AmmoType getAmmoType() {
		return ammoType;
	}

	public float getAI_attackRange() {
		return AI_attackRange;
	}
	 
	public boolean isFullyLoaded(ItemStack stack){
		return this.clipsize == this.getCurrentAmmo(stack);
	}
	
	public boolean hasRightClickAction() {
		return this.getGunHandType()==GunHandType.TWO_HANDED && this.canZoom;
	}
	
	/**
     * Weapon is used by NPC
     */
	//TODO npc shooting
    /*public void fireWeaponFromNPC(EntityLivingBase shooter, float dmgscale, float accscale) {
    	
    	SoundUtil.playSoundOnEntityGunPosition(shooter.world, shooter ,firesound, SOUND_DISTANCE, 1.0F, false, false, TGSoundCategory.GUN_FIRE);
    	
    	
    	EnumBulletFirePos firePos = EnumBulletFirePos.RIGHT;
    	
    	if (shooter instanceof NPCTurret){
    		//dmgscale=1.0f;
    		//accscale=1.0f;
    		firePos=EnumBulletFirePos.CENTER;
    	}
    	    	
    	if (!shooter.world.isRemote){
    		this.shootGun(shooter.world, shooter, shooter.getHeldItemMainhand(), this.zoombonus*accscale,dmgscale,0, EnumHand.MAIN_HAND, firePos, null);
    	}

    }*/
    
    /**
     * Get all ammo and magazines the gun currently holds is retrievable
     * @param stack
     * @return
     */
    public List<ItemStack> getAmmoOnUnload(ItemStack stack){
    	List<ItemStack> items = new ArrayList<>();
    	
    	int ammo = this.getCurrentAmmo(stack);
    	
    	if(this.ammoCount>1 && this.getAmmoLeft(stack)>0) {
    		for(ItemStack s : this.getAmmoType().getBullet(this.getCurrentAmmoVariant(stack))) {
    			items.add(TGItems.newStack(s,this.getAmmoLeft(stack)));
    		}
    	} else {
    		if (!this.isFullyLoaded(stack)) {
    			int amount = this.ammoType.getEmptyMag().length;
    			
    			for(int i=0;i<amount;i++) {
			    	int bulletsBack = (int) Math.floor(ammo/this.ammoType.getShotsPerBullet(clipsize, ammo));
					if (bulletsBack>0){
						items.add(TGItems.newStack(this.getAmmoType().getBullet(this.getCurrentAmmoVariant(stack))[i],bulletsBack));
					}
			    	if(!this.ammoType.getEmptyMag()[i].isEmpty()) {
			    		items.add(TGItems.newStack(this.ammoType.getEmptyMag()[i], 1));
			    	}
    			}
    		} else {
    			int amount = this.ammoType.getEmptyMag().length;	
    			for(int i=0;i<amount;i++) {
    				items.add(TGItems.newStack(this.ammoType.getAmmo(this.getCurrentAmmoVariant(stack))[i], 1));
    			}
    		}
    	}

    	return items;
    }
    
    /**
	 * try to force reload the gun, might lose some ammo
	 */

	public void tryForcedReload(ItemStack item, World world,PlayerEntity player, Hand hand){
		ITGExtendedPlayer extendedPlayer = (ITGExtendedPlayer)player;
		
		if (extendedPlayer.getFireDelay(hand)<=0 && !this.isFullyLoaded(item)){
			
			int oldAmmo = this.getCurrentAmmo(item);
			
			//look for ammo
			if (InventoryUtil.consumeAmmoPlayer(player,this.getReloadItem(item))) {
			
				//empty gun and do reload if we can't put in ammo individual
				if (ammoCount <= 1){
					this.useAmmo(item, oldAmmo);
				}
				
				int ammos = this.getAmmoType().getEmptyMag().length;
				for (int i=0;i<ammos;i++) {
					if (!this.ammoType.getEmptyMag()[i].isEmpty()){
						
						int amount=InventoryUtil.addAmmoToPlayerInventory(player, TGItems.newStack(this.ammoType.getEmptyMag()[i], 1));
						if(amount >0 && !world.isClient){
							player.world.spawnEntity(new ItemEntity(player.world, player.getX(), player.getY(), player.getZ(), TGItems.newStack(this.ammoType.getEmptyMag()[i], amount)));
						}
						
						int bulletsBack = (int) Math.floor(oldAmmo/this.ammoType.getShotsPerBullet(clipsize, oldAmmo));
						if (bulletsBack>0){
							int amount2=InventoryUtil.addAmmoToPlayerInventory(player, TGItems.newStack(this.ammoType.getBullet(this.getCurrentAmmoVariant(item))[i], bulletsBack));
							if(amount2 >0 && !world.isClient){
								player.world.spawnEntity(new ItemEntity(player.world, player.getX(), player.getY(), player.getZ(), TGItems.newStack(this.ammoType.getBullet(this.getCurrentAmmoVariant(item))[i], amount2)));
							}
						}
						
					}
				}
				
				//stop toggle zooming when reloading
				if (world.isClient) {
					if (canZoom  && this.toggleZoom) {
						ClientProxy cp = ClientProxy.get();
		    			if (cp.player_zoom != 1.0f) {
		    				cp.player_zoom= 1.0f;
		    			}
		    		}
				}
				
				extendedPlayer.setFireDelay(hand, this.reloadtime);
						    			
    			if (ammoCount >1) {
    				int i =1;
    				while (i<(ammoCount-oldAmmo) && InventoryUtil.consumeAmmoPlayer(player,this.ammoType.getAmmo(this.getCurrentAmmoVariant(item)))){
    					i++;
    				}
    				
    				this.reloadAmmo(item, i);
    			} else {
    				this.reloadAmmo(item);
    			}
    			SoundUtil.playReloadSoundOnEntity(world,player,reloadsound, 1.0F, 1.0F, false, true, TGSoundCategory.RELOAD);

				if (world.isClient) {

					int time = (int) (((float)reloadtime/20.0f)*1000);
					
					ShooterValues.setReloadtime(player, hand==Hand.OFF_HAND, System.currentTimeMillis()+time, time, (byte)0);
					
					client_startReload();
				} else{
					//send reloadpacket
					//send pakets to clients
					
			    	int msg_reloadtime = ((int)(((float)reloadtime/20.0f)*1000.0f));
			    	TGPacketsS2C.sendToAllAroundEntity(new ReloadStartedMessage(player,hand, msg_reloadtime,0), player, 100.0f);
			    	//
				}
				//player.setCurrentHand(hand);
				
			} else {

				//TODO: "can't reload" sound
				if (!world.isClient)
		        {
					world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_VILLAGER_AMBIENT, SoundCategory.PLAYERS, 1.0F, 1.0F);
		        }
			}

			
		}
		
	}

	public int getClipsize() {
		return clipsize;
	}
	
	
	
	
	//TODO check this stuff
	/*@Override
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
		return !(this.handType == GunHandType.TWO_HANDED);
	}

	public boolean canClickBlock(World world, EntityPlayer player, EnumHand hand) {
		 //ItemStack itemstack = player.getHeldItem(hand);
	     RayTraceResult raytraceresult = this.rayTrace(world, player, false);
	    
	     if (raytraceresult==null || raytraceresult.typeOfHit!=RayTraceResult.Type.BLOCK) {
	    	 return false;
	     }
	    
	     return true;
	}*/
	
	//TODO gunstat config
	/*public boolean setGunStat(EnumGunStat stat, float value) {
		switch(stat) {
		case DAMAGE:
			this.damage=value;
			return true;
		case DAMAGE_MIN:
			this.damageMin=value;
			return true;
		case DAMAGE_DROP_START:
			this.damageDropStart=value;
			return true;
		case DAMAGE_DROP_END:
			this.damageDropEnd=value;
			return true;
		case BULLET_SPEED:
			this.speed=value;
			return true;
		case BULLET_DISTANCE:
			this.ticksToLive= (int)value;
			return true;
		case GRAVITY:
			this.gravity=value;
			return true;
		case SPREAD:
			this.spread=value;
			return true;
		default:
			return false;
		}
	}*/

	public float getSpread() {
		return spread;
	}

	public GunHandType getHandType() {
		return handType;
	}

	public float getZoombonus() {
		return zoombonus;
	}

	public EnumCrosshairStyle getCrossHairStyle() {
		return this.crossHairStyle;
	}
	
	public GenericGun setCrossHair(EnumCrosshairStyle crosshair) {
		this.crossHairStyle = crosshair;
		return this;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public ArmPose getArmPose(boolean akimbo) {
		switch(this.holdType) {
		case CROSSBOW:
			if (akimbo) {
				return ArmPose.BOW_AND_ARROW;
			} else {
				return ArmPose.CROSSBOW_HOLD;
			}
		case BOW:
			return ArmPose.BOW_AND_ARROW;
		default:
			return ArmPose.ITEM;
		}
	}

	@Override
	public int getCamoCount() {
		return TGCamos.getCamoCount(this);
	}

}
