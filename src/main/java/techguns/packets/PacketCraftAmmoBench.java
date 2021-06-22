package techguns.packets;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import techguns.TGPacketsC2S;
import techguns.TGRecipes;
import techguns.blocks.entity.AmmoBenchBlockEntity;
import techguns.inventory.AmmoBenchScreenHandler;
import techguns.recipes.AmmoBenchRecipe;

import java.util.List;

public class PacketCraftAmmoBench extends TGBasePacket {
    protected ItemStack result = ItemStack.EMPTY;
    protected BlockPos loc = new BlockPos(0,0,0);

    public PacketCraftAmmoBench(ItemStack result, BlockPos loc) {
        this.result = result;
        this.loc = loc;
    }

    public PacketCraftAmmoBench(){};

    @Override
    public void pack(PacketByteBuf buf) {
        buf.writeItemStack(result);
        buf.writeBlockPos(loc);
    }

    @Override
    public void unpack(PacketByteBuf buf) {
        this.result = buf.readItemStack();
        this.loc = buf.readBlockPos();
    }

    @Override
    public void handle(PlayerEntity player) {
        System.out.println("Handling Craft Packet!");
        if (result.isEmpty()) return;
        BlockEntity ent = player.world.getBlockEntity(loc);
        if(ent !=null && ent instanceof AmmoBenchBlockEntity){
            AmmoBenchBlockEntity ammoBenchEnt = (AmmoBenchBlockEntity) ent;

            List<AmmoBenchRecipe> availableRecipes = player.world.getRecipeManager().listAllOfType(TGRecipes.AMMOBENCH_RECIPE_TYPE);
            AmmoBenchRecipe usedRecipe =null;
            for (AmmoBenchRecipe rec : availableRecipes){
                if (!rec.getOutput().isItemEqual(result) && rec.getOutput().getCount() == result.getCount()) {
                    usedRecipe = rec;
                }
            }

            if (usedRecipe !=null){
                for (int i=0;i<AmmoBenchBlockEntity.INVENTORY_SIZE; i++) {
                    ammoBenchEnt.getInventory().get(i).decrement(1);
                }
                //player.getInventory().setCursorStack(result);
            }

            if (player.currentScreenHandler != null && player.currentScreenHandler instanceof AmmoBenchScreenHandler){
                AmmoBenchScreenHandler sh = (AmmoBenchScreenHandler) player.currentScreenHandler;
                //sh.populateResult();
            }
        }
    }

    @Override
    public Identifier getID() {
        return TGPacketsC2S.CRAFT_AMMO_BENCH;
    }
}
