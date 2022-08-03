package techguns;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.registry.Registry;
import techguns.damagesystem.TGDamageSource;

public class TGEntityAttributes implements ITGInitializer {

    public static final EntityAttribute ARMOR_JUMPBOOST = register("armor_jumpboost", 0.0, 0.0, 64.0, true);
    public static final EntityAttribute ARMOR_MININGSPEED = register("armor_miningspeed", 0.0, 0.0, 1024.0, true);
    public static final EntityAttribute ARMOR_WATERMININGSPEED = register("armor_waterminingspeed", 0.0, 0.0, 5.0, true);

    private static final double MAX_ARMOR = 30.0D;
    public static final EntityAttribute ARMOR_PROJECTILE = register("armor_projectile", 0.0, 0.0, MAX_ARMOR, true);
    public static final EntityAttribute ARMOR_FIRE =       register("armor_fire", 0.0, 0.0, MAX_ARMOR, true);
    public static final EntityAttribute ARMOR_EXPLOSION =  register("armor_explosion", 0.0, 0.0, MAX_ARMOR, true);
    public static final EntityAttribute ARMOR_ENERGY =     register("armor_energy", 0.0, 0.0, MAX_ARMOR, true);
    public static final EntityAttribute ARMOR_POISON =     register("armor_poison", 0.0, 0.0, MAX_ARMOR, true);
    public static final EntityAttribute ARMOR_ICE =        register("armor_ice", 0.0, 0.0, MAX_ARMOR, true);
    public static final EntityAttribute ARMOR_LIGHTNING =  register("armor_lightning", 0.0, 0.0, MAX_ARMOR, true);
    public static final EntityAttribute ARMOR_RADIATION =  register("armor_radiation", 0.0, 0.0, MAX_ARMOR, true);
    public static final EntityAttribute ARMOR_DARK =       register("armor_dark", 0.0, 0.0, MAX_ARMOR, true);

    @Override
    public void init() {
        //no non-static init needed
    }

    protected static EntityAttribute register(String id, double fallback, double min, double max, boolean tracked){
        return Registry.register(Registry.ATTRIBUTE, new TGIdentifier(id), new ClampedEntityAttribute("attribute.name."+Techguns.MODID+"."+id, fallback, min, max).setTracked(tracked));
    };
}
