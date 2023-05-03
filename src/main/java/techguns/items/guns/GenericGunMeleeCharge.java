package techguns.items.guns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import net.fabricmc.fabric.impl.mininglevel.MiningLevelManagerImpl;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;

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
import techguns.util.TextUtil;

public class GenericGunMeleeCharge extends GenericGunCharge {

	protected final Multimap<EntityAttribute, EntityAttributeModifier>[] attributeModifiers;
	protected final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers_unpowered;
		
	protected final float miningSpeed;
	
	protected int swingSoundDelay = 5;
	
	protected SoundEvent miningSound = TGSounds.CHAINSAW_LOOP;
	protected SoundEvent hitSound = TGSounds.CHAINSAW_SWING;

	protected ArrayList<Pair<TagKey<Block>, Integer>> mininglevels = new ArrayList<>();

	protected final MiningHead[] miningHeads;

	public static class MiningHead {
		protected final Item item;
		protected final int mininglevel_bonus;
		protected final double attack_damage_bonus;
		protected final float mining_speed_bonus;
		protected final Formatting text_color;

		public MiningHead(Item item, int mininglevel_bonus, double attack_damage_bonus, float mining_speed_bonus, Formatting text_color) {
			this.item = item;
			this.mininglevel_bonus = mininglevel_bonus;
			this.attack_damage_bonus = attack_damage_bonus;
			this.mining_speed_bonus = mining_speed_bonus;
			this.text_color = text_color;
		}

		public MiningHead(Item item, Formatting text_color) {
			this.item = item;
			this.mininglevel_bonus = 0;
			this.attack_damage_bonus = 0D;
			this.mining_speed_bonus = 0F;
			this.text_color = text_color;
		}
	}

	public GenericGunMeleeCharge(String name, @SuppressWarnings("rawtypes") ChargedProjectileSelector projectile_selector, boolean semiAuto,
			int minFiretime, int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound,
			int TTL, float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge, float meleeDamage, float meleeDamageUnpowered, float attackspeed, float miningspeed, MiningHead[] miningHeads) {
		super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL,
				accuracy, fullChargeTime, ammoConsumedOnFullCharge);
		
	      this.miningSpeed = miningspeed;

	      this.attributeModifiers = new Multimap[miningHeads.length];
	      this.miningHeads = miningHeads;

	      Builder<EntityAttribute, EntityAttributeModifier> builder;
	      for(int i=0; i< miningHeads.length; i++) {
			  builder = ImmutableMultimap.builder();
			  builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", (double) meleeDamage + miningHeads[i].attack_damage_bonus, EntityAttributeModifier.Operation.ADDITION));
			  builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", (double) attackspeed, EntityAttributeModifier.Operation.ADDITION));
			  this.attributeModifiers[i] = builder.build();
		  }

	      builder = ImmutableMultimap.builder();
	      builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", (double)meleeDamageUnpowered, EntityAttributeModifier.Operation.ADDITION));
	      builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", (double)attackspeed, EntityAttributeModifier.Operation.ADDITION));
	      this.attributeModifiers_unpowered = builder.build();
	}

	public GenericGunMeleeCharge addToolLevel(TagKey<Block> tags, Integer mininglevel){
		this.mininglevels.add(new Pair<>(tags, mininglevel));
		return this;
	}

	public int getMiningRadius(PlayerEntity player, ItemStack stack){
		return 0;
	}

	@Nullable
	public BlockHitResult getMiningTarget(PlayerEntity player, BlockView world){
		HitResult hitResult = player.raycast(16F, 0F, false);
		if(hitResult !=null && hitResult instanceof BlockHitResult){
			return (BlockHitResult) hitResult;
		}
		return null;
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
			
			ClientProxy.get().handleSoundEvent(player, player.getId(), this.miningSound, 1.0f, 1.0f, false, false, true, true, TGSoundCategory.GUN_FIRE,  EntityCondition.NONE);

		}
	}

	@Override
	protected void addMiningHeadTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.addMiningHeadTooltip(stack, world, tooltip, context);
		int level = getMiningHeadLevel(stack);
		if(level >=0){
			Item head = this.miningHeads[level].item;
			Text t = head.getName();
			String itemname = "";
			if (t instanceof TranslatableTextContent) {
				itemname = TextUtil.trans(((TranslatableTextContent)t).getKey());
			}
			tooltip.add(Text.of(this.miningHeads[level].text_color+itemname));
		}
	}

	public boolean shouldSwing(ItemStack stack) {
		return this.getCurrentAmmo(stack) <= 0;
	}

	/*@Override
	public int getMiningLevel(Tag<Item> tag, BlockState state, ItemStack stack, @Nullable LivingEntity user) {
		if (this.toollevels.containsKey(tag) && this.getCurrentAmmo(stack) > 0 ) {
			int level = this.getMiningHeadLevel(stack);
			return this.toollevels.get(tag) + this.miningHeads[level].mininglevel_bonus;
		}
		return 0;
	}*/



	/*@Override
	public float getMiningSpeedMultiplier(Tag<Item> tag, BlockState state, ItemStack stack,
			@Nullable LivingEntity user) {
		if (this.toollevels.containsKey(tag)) {
			if (this.getCurrentAmmo(stack) > 0) {
				int level = this.getMiningHeadLevel(stack);
				return this.miningSpeed + this.miningHeads[level].mining_speed_bonus;
			}
		}
		return 1.0f;
	}*/

	protected boolean isEffectiveOn(BlockState state){
		return this.mininglevels.stream().anyMatch(x -> state.isIn(x.getLeft()));
	}

	@Override
	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
		if(this.getCurrentAmmo(stack) > 0 && this.isEffectiveOn(state)){
			int level = this.getMiningHeadLevel(stack);
			return this.miningSpeed + this.miningHeads[level].mining_speed_bonus;
		}
		return 1.0F;
	}


	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if(this.hitSound!=null && !target.world.isClient && this.getCurrentAmmo(stack)>0) {
			TGPacketsS2C.sendToAllTracking(new PacketPlaySound(this.hitSound, attacker, 1.0f, 1.0f, false, false, true, true, TGSoundCategory.GUN_FIRE, EntityCondition.NONE), target, true);
		}
		if(attacker instanceof PlayerEntity){
			PlayerEntity player = (PlayerEntity)attacker;
			if (!player.isCreative() && !player.isSpectator()){
				this.useAmmo(stack, 1);
			}
		}
		return true;
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		/*if(!miner.world.isClient && this.getCurrentAmmo(stack)>0) {
			TGPacketsS2C.sendToAllTracking(new PacketPlaySound(this.hitSound, miner, 1.0f, 1.0f, false, false, true, true, TGSoundCategory.GUN_FIRE, EntityCondition.NONE), miner, true);
		}*/
		if(miner instanceof PlayerEntity){
			PlayerEntity player = (PlayerEntity)miner;
			if (!player.isCreative() && !player.isSpectator()){
				this.useAmmo(stack, 1);
			}
		}
		return true;
	}

	@Override
	protected void addInitialTags(NbtCompound tags) {
		super.addInitialTags(tags);
		tags.putByte("mininghead", (byte)0);
	}

	public ItemStack getMiningHeadItemForLevel(int level){
		if (level >= 0 && level < miningHeads.length){
			return new ItemStack(miningHeads[level].item);
		}
		return ItemStack.EMPTY;
	}

	public int getMiningHeadLevel(ItemStack stack){
		NbtCompound tag = stack.getNbt();
		if(tag!=null){
			byte b = tag.getByte("mininghead");
			if(b >= 0 && b < miningHeads.length){
				return b;
			}
		}
		return 0;
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
		if (slot == EquipmentSlot.MAINHAND) {
			if (this.getCurrentAmmo(stack) > 0) {
				int level = this.getMiningHeadLevel(stack);
				return attributeModifiers[level];
			} else {
				return attributeModifiers_unpowered;
			}
		}
		return super.getAttributeModifiers(stack, slot);
	}

	@Override
	public boolean isSuitableFor(ItemStack stack, BlockState state) {
		if (this.getCurrentAmmo(stack) > 0 && this.isEffectiveOn(state)) {
			int headlevel = this.getMiningHeadLevel(stack);

			var entry = this.mininglevels.stream().filter(x -> state.isIn(x.getLeft())).findFirst();
			if (entry.isPresent()) {
				return (entry.get().getRight() + this.miningHeads[headlevel].mininglevel_bonus) >=  MiningLevelManagerImpl.getRequiredMiningLevel(state);
			}
		}
		return false;
	}

	/**
	 * Return -1 when not a valid head
	 * @param stack
	 * @return
	 */
	public int getMiningHeadLevelForItem(ItemStack stack){
		for (int i=0; i<miningHeads.length; i++){
			if (miningHeads[i].item == stack.getItem()){
				return i;
			}
		}
		return -1;
	}
	
}
