package tk.nukeduck.lightsaber.client.gui;

import tk.nukeduck.lightsaber.block.tileentity.ContainerRefillUnit;
import tk.nukeduck.lightsaber.block.tileentity.TileEntityRefillUnit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class RefillUnitGuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		return tileEntity instanceof TileEntityRefillUnit
			? new ContainerRefillUnit(player.inventory, (TileEntityRefillUnit) tileEntity)
			: null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		return tileEntity instanceof TileEntityRefillUnit
			? new GuiRefillUnit(player.inventory, (TileEntityRefillUnit) tileEntity)
			: null;
	}
}