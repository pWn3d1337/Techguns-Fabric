package techguns.mixin;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.server.ServerProxy;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    /*@Inject(method = "getEnchantments",at = @At("RETURN"), cancellable = true)
    public void getEnchantments(CallbackInfoReturnable<ListTag> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        if(stack.getItem() instanceof GenericGunMeleeCharge){
            MinecraftServer server = ServerProxy.SERVER_INSTANCE;
            if(server!=null){
                for(PlayerEntity player : server.getPlayerManager().getPlayerList()){
                    if (stack == player.getMainHandStack()){
                        System.out.println("Found player!");
                        if(player.isSneaking()){
                            System.out.println("Sneaking!");
                            ListTag enchantments = cir.getReturnValue().copy();

                            Identifier identifier = new Identifier("silk_touch");

                            boolean has_silk_touch=false;
                            for(int i = 0; i < enchantments.size() && !has_silk_touch; ++i) {
                                CompoundTag compoundTag = enchantments.getCompound(i);
                                Identifier identifier2 = Identifier.tryParse(compoundTag.getString("id"));
                                if (identifier2 != null && identifier2.equals(identifier)) {
                                    has_silk_touch=true;
                                }
                            }
                            if (!has_silk_touch){
                                CompoundTag silktouch = new CompoundTag();
                                silktouch.putString("id", identifier.toString());
                                silktouch.putShort("lvl",(short) 1);
                                enchantments.add(silktouch);
                            }
                            cir.setReturnValue(enchantments);
                            cir.cancel();
                        }
                    }
                }
            }
        }

    }*/

}
