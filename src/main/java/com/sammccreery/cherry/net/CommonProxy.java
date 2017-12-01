package com.sammccreery.cherry.net;

import com.sammccreery.cherry.client.GuiFoodCanister;
import com.sammccreery.cherry.inventory.ContainerFoodCanister;
import com.sammccreery.cherry.inventory.InventoryFoodCanister;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {
	public void init() {}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == 1) {
			return new GuiFoodCanister(new ContainerFoodCanister(player, player.inventory, new InventoryFoodCanister(player.getHeldItem())));
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == 1) {
			return new ContainerFoodCanister(player, player.inventory, new InventoryFoodCanister(player.getHeldItem()));
		}
		return null;
	}
}
