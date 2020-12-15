package techguns.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import techguns.items.guns.GenericGun;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

    @Shadow
    private float equipProgressMainHand;
    @Shadow
    private float prevEquipProgressMainHand;
    @Shadow
    private float equipProgressOffHand;
    @Shadow
    private float prevEquipProgressOffHand;
    @Shadow
    private ItemStack mainHand;
    @Shadow
    private ItemStack offHand;

    @Unique
    private int techguns_last_selected_slot =0;

    @Inject(method="updateHeldItems()V", at=@At(value="INVOKE", target="Lnet/minecraft/util/math/MathHelper;clamp(FFF)F", shift=At.Shift.AFTER, ordinal = 3), locals = LocalCapture.CAPTURE_FAILHARD)
    private void equipProgressInject(CallbackInfo ci, ClientPlayerEntity clientPlayerEntity, ItemStack itemStack, ItemStack itemStack2){
        if ((!itemStack.isEmpty() && itemStack.getItem() instanceof GenericGun) || (!this.mainHand.isEmpty() && this.mainHand.getItem() instanceof GenericGun)){
            float f = clientPlayerEntity.getAttackCooldownProgress(1.0f);

            //revert calculations, check own calc, we want to check if item is equal, but NBT tabs may have changed
            this.equipProgressMainHand = this.prevEquipProgressMainHand;
            this.equipProgressOffHand = this.prevEquipProgressOffHand;

            boolean slotchanged = techguns_last_selected_slot != clientPlayerEntity.inventory.selectedSlot;
            boolean play_requip_mh = slotchanged || !this.mainHand.isItemEqual(itemStack);

            this.equipProgressMainHand += MathHelper.clamp((!play_requip_mh ? f * f * f : 0.0F) - this.equipProgressMainHand, -0.4F, 0.4F);
            this.equipProgressOffHand += MathHelper.clamp((float)(this.offHand.isItemEqual(itemStack2) ? 1 : 0) - this.equipProgressOffHand, -0.4F, 0.4F);

            if (!play_requip_mh && !Objects.equals(this.mainHand, itemStack)){
                this.mainHand = itemStack;
            }
            if (!play_requip_mh && !Objects.equals(this.offHand, itemStack2)){
                this.offHand = itemStack2;
            }
        }
        techguns_last_selected_slot = clientPlayerEntity.inventory.selectedSlot;
    }

}
