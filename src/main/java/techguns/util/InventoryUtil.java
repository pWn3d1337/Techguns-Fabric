package techguns.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class InventoryUtil {

	public static int addAmmoToPlayerInventory(PlayerEntity ply, ItemStack ammo){
		//TODO ExtendedInventory
    	//TGExtendedPlayer props = TGExtendedPlayer.get(ply);
    	
    	/*if (props!=null){
    		int amount = addItemToInventory(props.tg_inventory.inventory, ammo, props.tg_inventory.SLOTS_AMMO_START, props.tg_inventory.SLOTS_AMMO_END+1);
    		if (amount>0){
    			return addItemToInventory(ply.inventory.mainInventory, ammo, 0, ply.inventory.mainInventory.size());
    		} else {
    			return 0;
    		}
    	} else {*/
    		return addItemToInventory(ply.getInventory().main, ammo, 0, ply.getInventory().main.size());
    	//}
    }
	
	public static int addAmmoToAmmoInventory(PlayerEntity ply, ItemStack ammo){
    	//TGExtendedPlayer props = TGExtendedPlayer.get(ply);
    	
    	//if (props!=null){
    	//	int amount = addItemToInventory(props.tg_inventory.inventory, ammo, props.tg_inventory.SLOTS_AMMO_START, props.tg_inventory.SLOTS_AMMO_END+1);
    	//	return amount;
    	//}
    	return ammo.getCount();
    }
	
	/**
     * Try merge itemstack with inventory, return amount of stacksize that could NOT be merged.
     * @param mainInventory
     * @param item2
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static int addItemToInventory(DefaultedList<ItemStack> mainInventory, ItemStack item2, int startIndex, int endIndex){
    	ItemStack item = item2.copy();
    	
    	for(int i=startIndex;i<endIndex;i++){
    		//1st try a merge
    		if (ItemStack.areItemsEqual(mainInventory.get(i), item)) {
    			int diff = (mainInventory.get(i).getCount() + item.getCount()) -item.getMaxCount();
    			if (diff < 0){
    				int c = mainInventory.get(i).getCount();
    				mainInventory.get(i).setCount(c+item.getCount());

    				item.setCount(0);
    				return 0;
    			} else {
    				mainInventory.get(i).setCount(item.getMaxCount());
    				item.setCount(diff);
    			}
    		}

    	}
    	
    	/**
    	 * could not fully merge, try fill empty slot
    	 */
    	if(item.getCount()>0){
    		for(int i=startIndex;i<endIndex;i++){
        		if(mainInventory.get(i).isEmpty()){
        			mainInventory.set(i,item.copy());
        			item.setCount(0);
        			return 0;
        		}

        	}
    	}
    	
    	//Return number of items that could not be put into inventory
    	return item.getCount();
    }
	
	
	public static boolean consumeAmmoPlayer(PlayerEntity ply, ItemStack[] ammo){
    	if(ammo.length==1) {
    		return consumeAmmoPlayer(ply,ammo[0]);
    	} else {
    	
	    	boolean canconsume=true;
	    	for(int i=0;i<ammo.length;i++) {
	    		if(!canConsumeAmmoPlayer(ply, ammo[i])) {
	    			canconsume=false;
	    			break;
	    		}
	    	}
	    	if(canconsume) {
	    		for(int i=0;i<ammo.length;i++) {
	    			consumeAmmoPlayer(ply, ammo[i]);
	        	}
	    		return true;
	    	} else {
	    		return false;
	    	}
    	}
    }
    
    public static boolean consumeAmmoPlayer(PlayerEntity ply, ItemStack ammo){
    	//TODO Extended Invetory
    	//TGExtendedPlayer props = TGExtendedPlayer.get(ply);

    	//if ( props!=null ){
        	int amount = ammo.getCount();
        	if (amount ==1){     
	        	//if (consumeAmmo(props.tg_inventory.inventory, ammo,TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1)){
	        	//	return true;
	        	//} else {
	        		return consumeAmmo(ply.getInventory().main,ammo,0,ply.getInventory().main.size());
	        	//}
        	} else {
        		
        		//Check first if amount can be consumed
        		//int needed = canConsumeItem(props.tg_inventory.inventory, ammo,TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1);
        		//System.out.println("needed1:"+needed);
        		int needed2 = canConsumeItem(ply.getInventory().main,ammo,0,ply.getInventory().main.size());
        		//System.out.println("needed2:"+needed);
        		if ( /*needed+*/needed2 <= amount){
        			
        			//int missing = consumeItem(props.tg_inventory.inventory, ammo,TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1);
        			
        			//if (missing >0){
        				//return consumeItem(ply.inventory.mainInventory,TGItems.newStack(ammo, missing),0,ply.inventory.mainInventory.size()) <= 0;
        			return consumeItem(ply.getInventory().main,ammo,0,ply.getInventory().main.size()) <= 0;
        			//} else {
        			//	return true;
        			//}
        			
        			
        		} else {
        			return false;
        		}

        	}
    	//} else {
    	//	return false;
    	//}
    	
    }
    
    public static boolean canConsumeAmmoPlayer(PlayerEntity ply, ItemStack ammo){
    	//TGExtendedPlayer props = TGExtendedPlayer.get(ply);
    	
    	//if ( props!=null ){
        	int amount = ammo.getCount();
        	if (amount ==1){     
	        	//if (canConsumeItem(props.tg_inventory.inventory, ammo,TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1)<=0){
	        	//	return true;
	        	//} else {
	        		return canConsumeItem(ply.getInventory().main,ammo,0,ply.getInventory().main.size())<=0;
	        	//}
        	} else {
        		
        		//Check first if amount can be consumed
        		//int needed = canConsumeItem(props.tg_inventory.inventory, ammo,TGPlayerInventory.SLOTS_AMMO_START, TGPlayerInventory.SLOTS_AMMO_END+1);
        		int needed2 = canConsumeItem(ply.getInventory().main,ammo,0,ply.getInventory().main.size());

        		if ( /*needed+*/needed2 <= amount){
        			
        			return true;
        			
        		} else {
        			return false;
        		}

        	}
    	//} else {
    	//	return false;
    	//}
    	
    }
    
    /**
     * Return missing items, that can not be consumed, 0 == ammount can be consumed
     * @param inv
     * @param item
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static int canConsumeItem(DefaultedList<ItemStack> inv,ItemStack item,int startIndex, int endIndex){
    	int needed = item.getCount();
    	
    	for (int i = startIndex; i < endIndex; ++i)
        {
            if (!inv.get(i).isEmpty() && inv.get(i).getItem() == item.getItem() && inv.get(i).getDamage()==item.getDamage())
            {
                needed -= inv.get(i).getCount();
                if(needed<=0){
                	return 0;
                }
            }
        }
    	return needed;
    }

    /**
     * Consumes from multiple stacks, returns amount that could not be consumed, does not check if total ammount can be consumed,
     * use canConsumeItem() to check that before
     * @param inv
     * @param item
     * @param startIndex
     * @param endIndex
     * @return
     */
    public static int consumeItem(DefaultedList<ItemStack> inv,ItemStack item,int startIndex, int endIndex){
    	int needed=item.getCount();
    	
    	for (int i = startIndex; i < endIndex; ++i)
        {
            if (!inv.get(i).isEmpty() && inv.get(i).getItem() == item.getItem() && inv.get(i).getDamage()==item.getDamage())
            {
            	if (inv.get(i).getCount()<=needed){
                    needed -= inv.get(i).getCount();
                    inv.set(i, ItemStack.EMPTY);
            	} else {
            		inv.get(i).setCount(inv.get(i).getCount()-needed);
            		return 0;
                }
            }
        }
    	return needed;
    }
    
    /**
     * Search for item in inventory with same damage value;
     * @param inv
     * @param stack
     * @return
     */
    private static int searchItem(DefaultedList<ItemStack> inv,ItemStack stack,int startIndex, int endIndex)
    {
        for (int i = startIndex; i < endIndex; ++i)
        {
            if (!inv.get(i).isEmpty() && inv.get(i).getItem() == stack.getItem() && inv.get(i).getDamage()==stack.getDamage())
            {
                return i;
            }
        }

        return -1;
    }
    
    public static boolean consumeAmmo(DefaultedList<ItemStack> inv,ItemStack ammo,int startIndex, int endIndex){
    	int i = searchItem(inv, ammo, startIndex, endIndex);

        if (i < 0)
        {
            return false;
        }
        else
        {
        	inv.get(i).setCount(inv.get(i).getCount()-1);
        	if (inv.get(i).getCount()<=0){
        		inv.set(i, ItemStack.EMPTY);
        	}

            return true;
        }
    }
}
