package tk.nukeduck.hearts.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tk.nukeduck.hearts.HeartCrystal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHeartCrystal extends ItemBlock {
	public ItemHeartCrystal(Block block) {
		super(block);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(HeartCrystal.MODID + ":heart_crystal");
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return true;
	}

	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		stack.stackSize--;
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		HeartCrystal.events.onEat(player);
		return stack;
	}

	public boolean hasSpace(EntityPlayer player) {
		AttributeModifier modifier = HeartCrystal.events.getBoost(player);
		return modifier == null || (int) (modifier.getAmount() / 2) < HeartCrystal.config.getMaxHearts();
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(player.canEat(true) && this.hasSpace(player)) {
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}
		return stack;
	}

	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.eat;
	}

	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}
}
