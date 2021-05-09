package techguns.items.guns;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils.DeathType;

public class Chainsaw extends GenericGunMeleeCharge {

	public Chainsaw(String name, @SuppressWarnings("rawtypes") ChargedProjectileSelector projectile_selector, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL,
			float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge, float meleeDamage,
			float meleeDamageUnpowered, float attackspeed, float miningspeed, MiningHead[] miningHeads) {
		super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL,
				accuracy, fullChargeTime, ammoConsumedOnFullCharge, meleeDamage, meleeDamageUnpowered, attackspeed,
				miningspeed, miningHeads);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand handIn) {
		ItemStack stack = player.getStackInHand(handIn);
		this.shootGunPrimary(stack, world, player, false, handIn, null);
		return new TypedActionResult<ItemStack>(ActionResult.FAIL, stack);
	}
	
	@Override
	public DamageSource getMeleeDamageSource(PlayerEntity player, ItemStack stack) {
		TGDamageSource src = (TGDamageSource) super.getMeleeDamageSource(player, stack);
		src.goreChance=1.0f;
		src.deathType = DeathType.GORE;
		return src;
	}
		
}
