package tk.nukeduck.alchemy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAlchemyPot extends TileEntity {
	int howFull = 0; // Max should be 1000
	
	public void updateEntity() {
		//
	}
	
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.howFull = par1NBTTagCompound.getInteger("howFull");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("howFull", howFull);
    }
}