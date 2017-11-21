package tk.nukeduck.lightsaber.item;

import java.util.List;

import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.util.Strings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemLens extends Item {
	private IIcon[] icons = new IIcon[Strings.COLORS.length];
	
	/** Constructor.<br/>
	 * Sets multiple types. */
	public ItemLens() {
		super();
		this.setHasSubtypes(true);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List items) {
		for(int i = 0; i < icons.length; i += 1) {
			items.add(new ItemStack(item, 1, i));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
	for(int i = 0; i < icons.length; i++) {
			icons[i] = ir.registerIcon(Strings.MOD_ID + ":lens_" + Strings.COLORS[i]);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage) {
		return icons[damage];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return "item.lens";
	}
	
	/** Adds colour prefix. */
	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		String color = Strings.translate(Strings.getColor(Strings.COLORS[itemStack.getItemDamage()])) + " ";
		return (color + Strings.translate(this.getUnlocalizedNameInefficiently(itemStack) + ".name")).trim();
	}
}