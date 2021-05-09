package techguns.items.guns;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import techguns.TGSounds;
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils;

public class MiningDrill extends GenericGunMeleeCharge{

    public MiningDrill(String name, ChargedProjectileSelector projectile_selector, boolean semiAuto, int minFiretime,
                       int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL,
                       float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge, float meleeDamage,
                       float meleeDamageUnpowered, float attackspeed, float miningspeed, MiningHead[] miningHeads) {

        super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound,
                TTL, accuracy, fullChargeTime, ammoConsumedOnFullCharge, meleeDamage, meleeDamageUnpowered, attackspeed,
                miningspeed, miningHeads);
        miningSound = TGSounds.DRILLER_BREAK;
        hitSound = TGSounds.DRILLER_HIT;
    }

    @Override
    public int getMiningRadius(PlayerEntity player, ItemStack stack) {
        if( player.isSneaking()){
            return 0;
        }
        return 1;
    }

    @Override
    public TGDamageSource getMeleeDamageSource(PlayerEntity player, ItemStack stack) {
        TGDamageSource src = new TGDamageSource("player", player, player, DamageType.PHYSICAL, EntityDeathUtils.DeathType.GORE);
        if(this.getCurrentAmmo(stack)>0){
            src.goreChance=0.5f;
            src.armorPenetration=this.penetration;
            src.knockbackMultiplier=1f;
        } else{
            src.deathType = EntityDeathUtils.DeathType.DEFAULT;
        }
        return src;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand handIn) {
        ItemStack stack = player.getStackInHand(handIn);
        this.shootGunPrimary(stack, world, player, false, handIn, null);
        return new TypedActionResult<ItemStack>(ActionResult.FAIL, stack);
    }
}
