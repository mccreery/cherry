package tk.nukeduck.lightsaber.network;

import tk.nukeduck.lightsaber.block.tileentity.ContainerRefillUnit;
import tk.nukeduck.lightsaber.block.tileentity.TileEntityRefillUnit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	public void registerRenderers() {}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && ID == 0 && te instanceof TileEntityRefillUnit) {
			return new ContainerRefillUnit(player.inventory, (TileEntityRefillUnit) te);
		}
		return null;
	}
}