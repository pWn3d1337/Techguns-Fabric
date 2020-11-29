package techguns.items.guns;

import java.util.HashMap;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

import net.fabricmc.fabric.api.tool.attribute.v1.DynamicAttributeTool;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.TGPacketsC2S;
import techguns.TGPacketsS2C;
import techguns.TGSounds;
import techguns.api.client.entity.ITGExtendedPlayerClient;
import techguns.client.ClientProxy;
import techguns.client.ShooterValues;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;
import techguns.packets.PacketPlaySound;
import techguns.packets.c2s.PacketTGToolMiningUpdate;
import techguns.sounds.TGSoundCategory;
import techguns.util.EntityCondition;

public class GenericGunMeleeCharge extends GenericGunCharge implements DynamicAttributeTool {

	protected final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
	protected final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers_unpowered;
		
	protected final float miningSpeed;
	
	protected int swingSoundDelay = 5;
	
	protected SoundEvent miningSound = TGSounds.CHAINSAW_LOOP;
	protected SoundEvent hitSound = TGSounds.CHAINSAW_SWING;
		
	protected HashMap<Tag<Item>, Integer> toollevels = new HashMap<>();
	
	public GenericGunMeleeCharge(String name, @SuppressWarnings("rawtypes") ChargedProjectileSelector projectile_selector, boolean semiAuto,
			int minFiretime, int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound,
			int TTL, float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge, float meleeDamage, float meleeDamageUnpowered, float attackspeed, float miningspeed) {
		super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL,
				accuracy, fullChargeTime, ammoConsumedOnFullCharge);
		
	      this.miningSpeed = miningspeed;
	      
	      Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
	      builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", (double)meleeDamage, EntityAttributeModifier.Operation.ADDITION));
	      builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", (double)attackspeed, EntityAttributeModifier.Operation.ADDITION));
	      this.attributeModifiers = builder.build();

	      builder = ImmutableMultimap.builder();
	      builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", (double)meleeDamageUnpowered, EntityAttributeModifier.Operation.ADDITION));
	      builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", (double)attackspeed, EntityAttributeModifier.Operation.ADDITION));
	      this.attributeModifiers_unpowered = builder.build();
	}
	
	public GenericGunMeleeCharge setToolLevel(Tag<Item> tooltag, int mininglevel) {
		this.toollevels.put(tooltag, mininglevel);
		return this;
	}
	
	
	/**
	 * called by client only
	 * @param player
	 * @param blockHitResult
	 */
	public void onMining(PlayerEntity player, BlockHitResult blockHitResult) {
		if (ShooterValues.getRecoiltime(player, false) < System.currentTimeMillis()) {
			ShooterValues.setRecoiltime(player, false, System.currentTimeMillis() + 250, 250, (byte) 0);
		}
		
		int msg_recoiltime = ((int)(((float)recoiltime/20.0f)*1000.0f));
    	int msg_muzzleflashtime = ((int)(((float)muzzleFlashtime/20.0f)*1000.0f));
    	
		ITGExtendedPlayerClient props = (ITGExtendedPlayerClient)player;
		if(props.getSwingSoundDelay()<=0){
			props.setSwingSoundDelay(this.swingSoundDelay);
			TGPacketsC2S.sendToServer(new PacketTGToolMiningUpdate(msg_recoiltime, msg_muzzleflashtime, blockHitResult.getPos(), this.miningSound));
			
			ClientProxy.get().handleSoundEvent(player, player.getEntityId(), this.miningSound, 1.0f, 1.0f, false, false, true, true, TGSoundCategory.GUN_FIRE,  EntityCondition.NONE);

		}
		
	}
		
	public boolean shouldSwing(ItemStack stack) {
		return this.getCurrentAmmo(stack) <= 0;
	}


	@Override
	public int getMiningLevel(Tag<Item> tag, BlockState state, ItemStack stack, @Nullable LivingEntity user) {
		if (this.toollevels.containsKey(tag) && this.getCurrentAmmo(stack) > 0 ) {
			return this.toollevels.get(tag);
		}
		return 0;
	}

	@Override
	public float getMiningSpeedMultiplier(Tag<Item> tag, BlockState state, ItemStack stack,
			@Nullable LivingEntity user) {
		if (this.toollevels.containsKey(tag)) {
			if (this.getCurrentAmmo(stack) > 0) {
				return this.miningSpeed;
			}
		}
		return 1.0f;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if(this.hitSound!=null && !target.world.isClient && this.getCurrentAmmo(stack)>0) {
			TGPacketsS2C.sendToAllTracking(new PacketPlaySound(this.hitSound, attacker, 1.0f, 1.0f, false, false, true, true, TGSoundCategory.GUN_FIRE, EntityCondition.NONE), target, true);
		}
		this.useAmmo(stack, 1);
		return true;
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		/*if(!miner.world.isClient && this.getCurrentAmmo(stack)>0) {
			TGPacketsS2C.sendToAllTracking(new PacketPlaySound(this.hitSound, miner, 1.0f, 1.0f, false, false, true, true, TGSoundCategory.GUN_FIRE, EntityCondition.NONE), miner, true);
		}*/
		this.useAmmo(stack, 1);
		return true;
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getDynamicModifiers(EquipmentSlot slot, ItemStack stack,
			@Nullable LivingEntity user) {
		if (slot == EquipmentSlot.MAINHAND) {
			if (this.getCurrentAmmo(stack) > 0) {
				return attributeModifiers;
			} else {
				return attributeModifiers_unpowered;
			}
		}
		return DynamicAttributeTool.super.getDynamicModifiers(slot, stack, user);
	}
	
	
}
