package com.sammccreery.cherry.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerDummy extends Container {
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
