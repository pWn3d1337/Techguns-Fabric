package techguns.mixin;

import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.WindowEventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import techguns.TGPacketsC2S;
import techguns.api.client.ClientDisconnectEvent;
import techguns.client.ShooterValues;
import techguns.items.guns.GenericGun;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.packets.c2s.PacketClientSwingRecoil;
import techguns.sounds.TGSoundCategory;
import techguns.util.SoundUtil;

import java.util.Map;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin extends ReentrantThreadExecutor<Runnable> implements WindowEventHandler{

	public MinecraftClientMixin(String string) {
		super(string);
	}
	
	@Inject(at = @At("HEAD"), method ="disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", cancellable=false)
	private void disconnect(CallbackInfo info) {
		ClientDisconnectEvent.EVENT.invoker().onDisconnect((MinecraftClient)(Object)this);
	}
	
	@Inject(at = @At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V"), method ="doAttack()Z", cancellable=true)
	private void doAttack(CallbackInfoReturnable<Boolean> info) {
		PlayerEntity ply = ((MinecraftClient)(Object)this).player;
		ItemStack stack = ply.getMainHandStack();
		
		if (stack!= ItemStack.EMPTY) {
			if (stack.getItem() instanceof GenericGunMeleeCharge) {
				GenericGunMeleeCharge weapon = (GenericGunMeleeCharge) stack.getItem();
				
				if (!weapon.isShootWithLeftClick() && !weapon.shouldSwing(stack)) {
					info.setReturnValue(false);
					info.cancel();
					TGPacketsC2S.sendToServer(new PacketClientSwingRecoil(weapon.getRecoilTime(0f), weapon.getMuzzleFlashTime(0f), Hand.MAIN_HAND, weapon.shouldCheckRecoil(), weapon.getFiresound()));

					//do recoil anim on client for self
					if (!weapon.shouldCheckRecoil() || !ShooterValues.isStillRecoiling(ply, false, (byte)0)){
						SoundUtil.playSoundOnEntityGunPosition(ply.world, ply, weapon.getFiresound(), GenericGun.SOUND_DISTANCE, 1.0F, false, false, TGSoundCategory.GUN_FIRE);
						ShooterValues.setRecoiltime(ply, false, System.currentTimeMillis()+weapon.getRecoilTime(0f), weapon.getRecoilTime(0f),(byte)0);
						ShooterValues.setMuzzleFlashTime(ply, false, System.currentTimeMillis()+weapon.getMuzzleFlashTime(0f), weapon.getMuzzleFlashTime(0f));
					}
				}
			}
		}
	}
	
	@Inject(at = @At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V"), method ="handleBlockBreaking(Z)V", cancellable=true, locals = LocalCapture.CAPTURE_FAILHARD)
	private void handleBlockBreaking(boolean bl, CallbackInfo info, BlockHitResult blockHitResult, BlockPos blockPos, Direction direction) {
		ItemStack stack = ((MinecraftClient)(Object)this).player.getMainHandStack();
		
		if (stack!= ItemStack.EMPTY) {
			if (stack.getItem() instanceof GenericGunMeleeCharge) {
				GenericGunMeleeCharge weapon = (GenericGunMeleeCharge) stack.getItem();
				
				if (!weapon.isShootWithLeftClick() && !weapon.shouldSwing(stack)) {
					weapon.onMining(((MinecraftClient)(Object)this).player, blockHitResult);
					info.cancel();
				}
			}
		}
	}
}
