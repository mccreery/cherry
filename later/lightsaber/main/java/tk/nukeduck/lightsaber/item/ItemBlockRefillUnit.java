package tk.nukeduck.lightsaber.item;

import java.util.List;

import tk.nukeduck.lightsaber.block.tileentity.TileEntityRefillUnit;
import tk.nukeduck.lightsaber.util.Constants;
import tk.nukeduck.lightsaber.util.Strings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockRefillUnit extends ItemBlock {
	public ItemBlockRefillUnit(Block block) {
		super(block);
		setHasSubtypes(true);
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
		for(int i = 0; i < Constants.REFILL_UNIT_CAPACITIES.length; i++) {
			p_150895_3_.add(new ItemStack(p_150895_1_, 1, i));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		int max = Constants.REFILL_UNIT_CAPACITIES[itemStack.getItemDamage()][0];
		String speed = (int) Math.ceil(20.0 / (Constants.REFILL_UNIT_CAPACITIES[itemStack.getItemDamage()][1] + 1.0)) + Strings.translate(Strings.TRANSFER_RATE);
		if(Constants.REFILL_UNIT_CAPACITIES[itemStack.getItemDamage()][1] > 20) {
			speed = Strings.translate(Strings.LESS_THAN_ONE) + Strings.translate(Strings.TRANSFER_RATE);
		}
		list.add(Strings.translate(Strings.CAPACITY_LITERAL) + max + Strings.translate(Strings.ENERGY_SYMBOL));
		list.add(Strings.translate(Strings.TRANSFER_RATE_LITERAL) + speed);
	}
	
	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}
	
	@Override
	public String getUnlocalizedName() {
		return "item.refillUnit";
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		return (Strings.translate(this.getUnlocalizedNameInefficiently(itemStack) + Strings.NAME_SUFFIX) + Strings.SPACE + Strings.translate(Strings.MARK_PREFIX + itemStack.getItemDamage() + Strings.NAME_SUFFIX)).trim();
	}
}