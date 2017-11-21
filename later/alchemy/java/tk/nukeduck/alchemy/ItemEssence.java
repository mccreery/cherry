package tk.nukeduck.alchemy;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ItemEssence extends Item {
	public ItemEssence() {
		this.setMaxDamage(100);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
	public String[] itemNames = new String[] {
		"Tetra", "Ignor", "Aquer", "Moren", "Sancti", "Motus", "Aera", "Voxun", "Auctus", "Ferrus", "Pretios", "Mutatio", "Clarim", "Veneficia", "Visun", "Glaci"
	};
	
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return itemNames[par1ItemStack.getTagCompound().getByte("type")].toLowerCase();
	}
}