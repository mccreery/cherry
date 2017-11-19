package nukeduck.craftconvenience;

import java.util.Random;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import nukeduck.craftconvenience.events.DropEvents;
import nukeduck.craftconvenience.registry.CraftingRegistry;

@Mod(modid=CraftConvenience.MODID, name="Craft Convenience", version="1.0")
public class CraftConvenience {
	public static final String MODID = "craft_convenience";

	@Instance(value=MODID)
	public CraftConvenience instance;

	public static final Random random = new Random();
	private DropEvents events;

	@EventHandler
	public void init(FMLInitializationEvent e) {
		FMLCommonHandler.instance().bus().register(events = new DropEvents());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		CraftingRegistry.init();
	}
}
