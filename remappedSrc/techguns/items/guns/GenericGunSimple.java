package techguns.items.guns;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import techguns.client.render.ITGItemRenderer;
import techguns.items.GenericItem;

public class GenericGunSimple extends GenericItem implements ITGItemRenderer {

	public GenericGunSimple(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);

		if (!world.isClient) {
			PersistentProjectileEntity persistentProjectileEntity = ((ArrowItem) Items.ARROW).createArrow(world,
					itemStack, user);
			persistentProjectileEntity.setProperties(user, user.pitch, user.yaw, 0.0F, 3.0F, 1.0F);
			persistentProjectileEntity.setCritical(true);
			world.spawnEntity(persistentProjectileEntity);
		}

		return TypedActionResult.success(itemStack);
	}
	
	
}
