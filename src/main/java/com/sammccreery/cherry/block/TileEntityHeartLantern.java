package com.sammccreery.cherry.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import static com.sammccreery.cherry.util.WorldUtil.RANDOM;

public class TileEntityHeartLantern extends TileEntity {
	float chargeLevel;

	public void updateEntity() {
		if(this.chargeLevel >= 1.0F) {
			this.chargeLevel = 1.0F;
			return;
		}

		if(RANDOM.nextFloat() < 0.05F) {
			this.chargeLevel += 0.05F;
		}
	}

	public static final String CHARGE_KEY = "ChargeLevel";

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.chargeLevel = compound.getFloat(CHARGE_KEY);
	}

	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setFloat(CHARGE_KEY, this.chargeLevel);
	}
}
