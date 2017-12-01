package com.sammccreery.cherry.item;

import com.sammccreery.cherry.Cherry;
import com.sammccreery.cherry.util.Name;
import com.sammccreery.cherry.util.Name.Format;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemFoodCanister extends Item {
	public ItemFoodCanister() {
		super();
		setCreativeTab(CreativeTabs.tabTools);
		setMaxStackSize(1);
		setMaxDamage(100);
	}

	IIcon openIcon;

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		super.registerIcons(register);
		openIcon = register.registerIcon(new Name(this.getIconString()).append("open").format(Format.SNAKE, true));
	}

	@Override
	public IIcon getIcon(ItemStack i, int i2) {
		return itemIcon;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			if(player.isSneaking()) {
				player.openGui(Cherry.INSTANCE, 1, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
			} else {
				stack.damageItem(5, player);
			}
		}
		return stack;
	}
}
