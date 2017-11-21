package tk.nukeduck.lightsaber.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.util.Strings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEnergyCapsule extends ItemChargeable {
	public int mark = 0;
	
	/** Constructor.<br/>
	 * Sets the mark. */
	public ItemEnergyCapsule(int mark) {
		super();
		this.setMaxDamage(201);
		this.setTextureName(Strings.MOD_ID + ":energy_capsule_" + mark);
		this.setUnlocalizedName("energyCapsule");
		this.setCreativeTab(Lightsaber.lightsaberTab);
		this.mark = mark;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, getMaxDamage()));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return "item.energyCapsule";
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		String suffix = Strings.SPACE + Strings.translate(Strings.MARK_PREFIX + ((ItemEnergyCapsule) itemStack.getItem()).mark + Strings.NAME_SUFFIX);
		return (this.getItemNamePrefix(itemStack) + Strings.translate(this.getUnlocalizedNameInefficiently(itemStack) + Strings.NAME_SUFFIX) + suffix).trim();
	}
}