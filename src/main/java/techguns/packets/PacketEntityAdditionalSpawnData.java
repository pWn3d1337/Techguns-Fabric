package techguns.packets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import techguns.TGPacketsS2C;
import techguns.entities.projectiles.GenericProjectile;

public class PacketEntityAdditionalSpawnData extends TGBasePacket {

	private int entityId;
	private NbtCompound additionalData;
	
	public PacketEntityAdditionalSpawnData(GenericProjectile ent) {
		this.entityId=ent.getId();
		this.additionalData = new NbtCompound();
		ent.getAdditionalSpawnData(this.additionalData);
	}
	
	public PacketEntityAdditionalSpawnData() {
		super();
	}

	@Override
	public void pack(PacketByteBuf buf) {
		buf.writeInt(entityId);
		buf.writeNbt(additionalData);
	}

	@Override
	public void unpack(PacketByteBuf buf) {
		this.entityId = buf.readInt();
		this.additionalData = buf.readNbt();
	}

	@Override
	public void handle(PlayerEntity player) {
		Entity ent = player.world.getEntityById(this.entityId);
		if(ent!=null && ent instanceof GenericProjectile) {
			GenericProjectile projectile = (GenericProjectile) ent;
			projectile.parseAdditionalData(this.additionalData);
			projectile.clientInitializeFinal();
		}
	}

	@Override
	public Identifier getID() {
		return TGPacketsS2C.PROJECTILE_ADDITIONAL_SPAWNDATA;
	}

	
}
