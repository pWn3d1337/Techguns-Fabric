package techguns.packets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import techguns.TGPacketsS2C;
import techguns.entities.projectiles.GenericProjectile;

public class PacketEntityAdditionalSpawnData extends TGBasePacket {

	private int entityId;
	private CompoundTag additionalData;
	
	public PacketEntityAdditionalSpawnData(GenericProjectile ent) {
		this.entityId=ent.getEntityId();
		this.additionalData = new CompoundTag();
		ent.getAdditionalSpawnData(this.additionalData);
	}
	
	public PacketEntityAdditionalSpawnData() {
		super();
	}

	@Override
	public void pack(PacketByteBuf buf) {
		buf.writeInt(entityId);
		buf.writeCompoundTag(additionalData);
	}

	@Override
	public void unpack(PacketByteBuf buf) {
		this.entityId = buf.readInt();
		this.additionalData = buf.readCompoundTag();
	}

	@Override
	public void handle(PlayerEntity player) {
		Entity ent = player.world.getEntityById(this.entityId);
		if(ent!=null && ent instanceof GenericProjectile) {
			GenericProjectile projectile = (GenericProjectile) ent;
			projectile.parseAdditionalData(this.additionalData);
		}
	}


	@Override
	public Identifier getID() {
		return TGPacketsS2C.PROEJCTILE_ADDITIONAL_SPAWNDATA;
	}

	
}
