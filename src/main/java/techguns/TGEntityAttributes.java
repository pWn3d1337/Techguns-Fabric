package techguns;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.registry.Registry;

public class TGEntityAttributes implements ITGInitializer {

    public static final EntityAttribute ARMOR_JUMPBOOST = register("armor_jumpboost", 0.0, 0.0, 64.0, true);
    public static final EntityAttribute ARMOR_MININGSPEED = register("armor_miningspeed", 0.0, 0.0, 1024.0, true);
    public static final EntityAttribute ARMOR_WATERMININGSPEED = register("armor_waterminingspeed", 0.0, 0.0, 5.0, true);

    @Override
    public void init() {
        //no non-static init needed
    }

    protected static EntityAttribute register(String id, double fallback, double min, double max, boolean tracked){
        return Registry.register(Registry.ATTRIBUTE, new TGIdentifier(id), new ClampedEntityAttribute("attribute.name."+Techguns.MODID+"."+id, fallback, min, max).setTracked(tracked));
    };
}
