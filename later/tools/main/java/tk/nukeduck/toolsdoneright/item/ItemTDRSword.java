package tk.nukeduck.toolsdoneright.item;

import java.util.List;

import tk.nukeduck.toolsdoneright.ToolsDoneRight;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;

public class ItemTDRSword extends ItemSword {
	public ItemTDRSword(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
	}
	
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		if(this.getUnlocalizedName().contains("emerald")) list.add(ToolsDoneRight.addEnchantment(new ItemStack(item, 1), 21, 2));
		else super.getSubItems(item, tabs, list);
	}
}