package techguns.items.guns;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import techguns.TGSounds;
import techguns.api.damagesystem.DamageType;
import techguns.damagesystem.TGDamageSource;
import techguns.deatheffects.EntityDeathUtils;

public class PowerHammer extends GenericGunMeleeCharge {
    public PowerHammer(String name, ChargedProjectileSelector projectile_selector, boolean semiAuto, int minFiretime,
                       int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL,
                       float accuracy, float fullChargeTime, int ammoConsumedOnFullCharge, float meleeDamage,
                       float meleeDamageUnpowered, float attackspeed, float miningspeed, MiningHead[] miningHeads) {

        super(name, projectile_selector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound,
                TTL, accuracy, fullChargeTime, ammoConsumedOnFullCharge, meleeDamage, meleeDamageUnpowered, attackspeed,
                miningspeed, miningHeads);

        miningSound = TGSounds.POWERHAMMER_SWING;
        hitSound = TGSounds.POWERHAMMER_IMPACT;
    }

    @Override
    public TGDamageSource getMeleeDamageSource(PlayerEntity player, ItemStack stack) {
        TGDamageSource src = new TGDamageSource("player", player, player, DamageType.PHYSICAL, EntityDeathUtils.DeathType.GORE);
        if(this.getCurrentAmmo(stack)>0){
            src.goreChance=1.0f;
            src.armorPenetration=this.penetration;
            src.knockbackMultiplier=1.5f;
        } else{
            src.deathType = EntityDeathUtils.DeathType.DEFAULT;
        }
        return src;
    }

}
