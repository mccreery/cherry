package tk.nukeduck.lightsaber.item;

import java.util.List;

import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.util.Strings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemBlockCrystal extends ItemBlock {
	public ItemBlockCrystal(Block block) {
		super(block);
		this.setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName() {
		return "item.crystal";
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		return (Strings.translate(Strings.getColor(Strings.COLORS[itemStack.getItemDamage()])) + " " + Strings.translate(this.getUnlocalizedName(itemStack) + Strings.NAME_SUFFIX)).trim();
	}
	
	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}
	
	/** Icons for the different colours of crystal. */
	public static IIcon[] icons = new IIcon[Strings.COLORS.length];
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		super.registerIcons(ir);
		for(int i = 0; i < Strings.COLORS.length; i++) {
			this.icons[i] = ir.registerIcon(Strings.MOD_ID + ":crystal_" + Strings.COLORS[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack itemStack) {
		return itemStack.getItemDamage() >= icons.length ? icons[0] : icons[itemStack.getItemDamage()];
	}
}