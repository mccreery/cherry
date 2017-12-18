package tk.nukeduck.generation.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tk.nukeduck.generation.block.ContainerProgrammer;
import tk.nukeduck.generation.block.TileEntityProgrammer;
import tk.nukeduck.generation.client.GuiProgrammer;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	public void registerRenderers() {}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntityProgrammer tileEntity = (TileEntityProgrammer) world.getTileEntity(x, y, z);
		if(tileEntity != null) {
			return new ContainerProgrammer(player.inventory, tileEntity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntityProgrammer tileEntity = (TileEntityProgrammer) world.getTileEntity(x, y, z);
		if(tileEntity != null) {
			return new GuiProgrammer(new ContainerProgrammer(player.inventory, tileEntity));
		}
		return null;
	}
}
