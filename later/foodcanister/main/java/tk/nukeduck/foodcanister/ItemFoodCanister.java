package tk.nukeduck.foodcanister;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemFoodCanister extends Item {
	public ItemFoodCanister() {
		super();
		//this.setAlwaysEdible();
		setMaxStackSize(1);
		this.setMaxDamage(100);
	}
	
	IIcon openIcon;
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1; // return any value greater than zero
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("foodcanister:food_canister");
        this.openIcon = par1IconRegister.registerIcon("foodcanister:food_canister_open");
    }
    
    @Override
    public IIcon getIcon(ItemStack i, int i2) {
    	return itemIcon;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
    // only want to check on server - the client gui will be opened automatically by the gui handler
   
    	if (!world.isRemote) {
    // you may or may not want to check if the player is sneaking - up to you
    if (player.isSneaking()) {
    player.openGui(FoodCanister.instance, 1, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
    } else {
    	stack.damageItem(5, player);
    }
    }
    return stack;
    }
}