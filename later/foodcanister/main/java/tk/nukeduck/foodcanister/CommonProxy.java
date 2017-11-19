package tk.nukeduck.foodcanister;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	public void registerRenders() {
		
	}

	@Override
	public Object getClientGuiElement(int arg0, EntityPlayer arg1, World arg2, int arg3, int arg4, int arg5) {
		System.out.println("Client'n");
		if(arg0 == 1) {
			return new GuiFoodCanister((ContainerFoodCanister) new ContainerFoodCanister(arg1, arg1.inventory, new InventoryFoodCanister(arg1.getHeldItem())));
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int arg0, EntityPlayer arg1, World arg2,
			int arg3, int arg4, int arg5) {
		System.out.println("Server'n");
		if(arg0 == 1) {
			return new ContainerFoodCanister(arg1, arg1.inventory, new InventoryFoodCanister(arg1.getHeldItem()));
		}
		return null;
	}
}