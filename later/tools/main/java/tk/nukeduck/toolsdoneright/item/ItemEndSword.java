package tk.nukeduck.toolsdoneright.item;

import java.util.List;

import tk.nukeduck.toolsdoneright.ToolsDoneRight;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemEndSword extends ItemTDRSword {
	public ItemEndSword(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
	}
	
	public void onUpdate(ItemStack stack, World world, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
		if(stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
	}
	
	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		System.out.println("Hi!");
		itemstack.stackTagCompound = new NBTTagCompound();
		//itemstack.stackTagCompound.setTag("Entity", new EntityChicken(world).getEntityData());
	}
	
    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
    	if(itemstack.stackTagCompound != null && itemstack.stackTagCompound.hasKey("Entity") && player != null) {
    		Entity e = EntityList.createEntityFromNBT(itemstack.stackTagCompound.getCompoundTag("Entity"), world);
    		if(e != null && !world.isRemote) {
    			e.readFromNBT(itemstack.stackTagCompound.getCompoundTag("Entity"));
	    		//e.setVelocity(0.0D, 0.0D, 0.0D);
    			if(world.getBlock(x, y, z).getCollisionBoundingBoxFromPool(world, x, y, z) != null) {
    				e.setLocationAndAngles(x + 0.5F, world.getBlock(x, y, z).getCollisionBoundingBoxFromPool(world, x, y, z).maxY, z + 0.5F, 0, 0);
    			} else {
    				e.setLocationAndAngles(x + 0.5F, y, z + 0.5F, 0, 0);
    			}
    			
    			ToolsDoneRight.spawnParticlesAt(e.worldObj, x, y + 1, z, 30, 1.0F);
    			
	    		//if(world.canPlaceEntityOnSide(world.getBlock(x, y, z), x, y, z, world.getBlock(x, y, z).getCollisionBoundingBoxFromPool(world, x, y, z) == null, side, player, itemstack)) {
		    		world.spawnEntityInWorld(e);
		    		itemstack.stackTagCompound.removeTag("Entity");
	    		//}
    		}
    	}
        return true;
    }
    
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
    	if(itemstack.stackTagCompound != null && itemstack.stackTagCompound.hasKey("Entity")) {
    		list.add("Entity held: " + EnumChatFormatting.DARK_PURPLE + ((EntityLiving)EntityList.createEntityFromNBT(itemstack.stackTagCompound.getCompoundTag("Entity"), player.worldObj)).getCommandSenderName());
    		list.add("Health: " + EnumChatFormatting.DARK_RED + String.valueOf(itemstack.stackTagCompound.getCompoundTag("Entity").getFloat("Health") / 2) + EnumChatFormatting.DARK_AQUA + "/" + EnumChatFormatting.DARK_GREEN + String.valueOf(((EntityLiving)EntityList.createEntityFromNBT(itemstack.stackTagCompound.getCompoundTag("Entity"), player.worldObj)).getMaxHealth() / 2) + EnumChatFormatting.GRAY + " hearts");
    	} else {
    		list.add("Entity held: " + EnumChatFormatting.DARK_PURPLE + "None");
    	}
    }
}