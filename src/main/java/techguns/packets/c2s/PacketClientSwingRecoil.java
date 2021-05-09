package techguns.packets.c2s;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import techguns.TGPacketsC2S;
import techguns.TGPacketsS2C;
import techguns.packets.GunFiredMessage;
import techguns.packets.PacketPlaySound;
import techguns.packets.TGBasePacket;
import techguns.sounds.TGSoundCategory;

/**
 * Tell the server to play a recoil animation for a GenericGun MeleeSwing
 */
public class PacketClientSwingRecoil extends TGBasePacket {

    protected Hand hand;
    protected boolean checkRecoil;
    protected int muzzleflashtime;
    protected int recoiltime;
    protected String soundname;

    public PacketClientSwingRecoil() {
    }

    public PacketClientSwingRecoil(int recoiltime, int muzzleflashtime, Hand hand, boolean checkRecoil, SoundEvent soundEvent) {
        this.hand = hand;
        this.checkRecoil = checkRecoil;
        this.recoiltime = recoiltime;
        this.muzzleflashtime = muzzleflashtime;
        this.soundname = Registry.SOUND_EVENT.getId(soundEvent).toString();
    }

    @Override
    public void unpack(PacketByteBuf buf) {
        this.recoiltime = buf.readInt();
        this.muzzleflashtime = buf.readInt();
        this.hand=buf.readBoolean() ? Hand.OFF_HAND : Hand.MAIN_HAND;
        this.checkRecoil = buf.readBoolean();
        this.soundname = buf.readString();
    }

    @Override
    public void pack(PacketByteBuf buf) {
        buf.writeInt(recoiltime);
        buf.writeInt(muzzleflashtime);
        buf.writeBoolean(this.hand == Hand.OFF_HAND);
        buf.writeBoolean(this.checkRecoil);
        buf.writeString(this.soundname);
    }

    @Override
    public void handle(PlayerEntity player) {
        TGPacketsS2C.sendToAllTracking(new GunFiredMessage(player, recoiltime, muzzleflashtime, (byte)0, checkRecoil, hand), player, false);

        SoundEvent soundEvent = Registry.SOUND_EVENT.get(new Identifier(this.soundname));
        if(soundEvent!=null) {
            TGPacketsS2C.sendToAllTracking(new PacketPlaySound(soundEvent, player, 1.0f, 1.0f, false, false, true, false, TGSoundCategory.GUN_FIRE), player, false);
        }
    }

    @Override
    public Identifier getID() {
        return TGPacketsC2S.CLIENT_SWING_RECOIL;
    }
}
