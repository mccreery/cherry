package tk.nukeduck.hearts.block;

import tk.nukeduck.hearts.HeartCrystal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHeartLantern extends TileEntity {
	float chargeLevel;

	public void updateEntity() {
		if(this.chargeLevel >= 1.0F) {
			this.chargeLevel = 1.0F;
			return;
		}

		if(HeartCrystal.random.nextFloat() < 0.05F) {
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
