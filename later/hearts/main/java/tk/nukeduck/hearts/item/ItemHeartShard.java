package tk.nukeduck.hearts.item;

import tk.nukeduck.hearts.HeartCrystal;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHeartShard extends ItemFood {
	public ItemHeartShard() {
		super(0, 0, false);
		this.setPotionEffect(10, 5, 2, 1.0F);
		this.setAlwaysEdible();
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(HeartCrystal.MODID + ":heart_shard");
	}
}
