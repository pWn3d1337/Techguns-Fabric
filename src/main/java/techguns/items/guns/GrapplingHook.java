package techguns.items.guns;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.world.World;


public class GrapplingHook extends GenericGunCharge{

	public GrapplingHook(String name, ChargedProjectileSelector<?> projectile_selector, boolean semiAuto, int minFiretime,
			int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL,
			float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge) {
		super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL,
				accuracy, fullChargeTime, ammoConsumedOnFullCharge);
		// TODO Auto-generated constructor stub
	}
	
	
//	@Override
//	public void shootGunPrimary(ItemStack stack, World world, PlayerEntity player, boolean zooming, Hand hand, Entity target) {
//		//Do nothing on primary fire;
//		//Might not even be needed because we can turn off ShootsWithLeftClick
//	}

	
	protected void startCharge(ItemStack item, World world, PlayerEntity player, Hand hand) {
		//Fire Projectile to get GrapplingTarget:
		super.shootGunPrimary(item, world, player, false, hand, null);
		
	}
	
	@Override
	public void onStoppedUsing(ItemStack item, World world, LivingEntity entityLiving, int timeLeft) {
		super.onStoppedUsing(item, world, entityLiving, timeLeft);
		//TODO: Abort Grappling, pull in hook
	}


}
