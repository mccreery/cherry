package tk.nukeduck.lightsaber.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.client.gui.tome.GuiTomeLore;
import tk.nukeduck.lightsaber.util.Strings;

public class ItemTome extends Item {
	/** Constructor.<br/>
	 * Sets up names and max stack size. */
	public ItemTome() {
		super();
		this.setUnlocalizedName("forceTome");
		this.setTextureName(Strings.MOD_ID + ":tome");
		this.setMaxStackSize(1);
	}
	
	/** {@inheritDoc}<br/>
	 * Adds in opening GUI on right click. */
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		Lightsaber.mc.displayGuiScreen(new GuiTomeLore(p_77659_3_));
		return p_77659_1_;
	}
}