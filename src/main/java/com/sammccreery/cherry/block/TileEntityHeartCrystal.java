package com.sammccreery.cherry.block;

import net.minecraft.tileentity.TileEntity;

public class TileEntityHeartCrystal extends TileEntity {
	public int rotation = 0;

	@Override
	public void updateEntity() {
		rotation += 2;
		if(rotation > 360) rotation -= 360;
	}
}
