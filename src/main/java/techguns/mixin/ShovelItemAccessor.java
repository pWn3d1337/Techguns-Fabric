package techguns.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.ShovelItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(ShovelItem.class)
public interface ShovelItemAccessor {

    @Accessor(value = "EFFECTIVE_BLOCKS")
    public static Set<Block> getEFFECTIVE_BLOCKS(){
        throw new AssertionError();
    }
}
