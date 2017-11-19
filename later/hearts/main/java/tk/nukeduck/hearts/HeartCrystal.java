package tk.nukeduck.hearts;

import java.util.Random;

import net.minecraftforge.common.MinecraftForge;
import tk.nukeduck.hearts.block.TileEntityHeartCrystal;
import tk.nukeduck.hearts.block.TileEntityHeartLantern;
import tk.nukeduck.hearts.event.CommonEvents;
import tk.nukeduck.hearts.event.WorldGeneratorHearts;
import tk.nukeduck.hearts.network.IProxy;
import tk.nukeduck.hearts.registry.HeartsBlocks;
import tk.nukeduck.hearts.registry.HeartsCrafting;
import tk.nukeduck.hearts.registry.HeartsItems;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid=HeartCrystal.MODID, name="Heart Crystal", version="1.1.1")
public class HeartCrystal {
	@Instance
	public static HeartCrystal instance;
	public static final String MODID = "hearts";

	@SidedProxy(clientSide="tk.nukeduck.hearts.network.ClientProxy", serverSide="tk.nukeduck.hearts.network.CommonProxy")
	public static IProxy proxy;
	public static CommonEvents events;
	public static HeartsConfig config;
	public static final Random random = new Random();

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		this.config = new HeartsConfig(e.getSuggestedConfigurationFile()).load();
		HeartsBlocks.init();
		HeartsItems.init();
		HeartsCrafting.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		GameRegistry.registerTileEntity(TileEntityHeartCrystal.class, "HeartCrystal");
		GameRegistry.registerTileEntity(TileEntityHeartLantern.class, "HeartLantern");
		GameRegistry.registerWorldGenerator(new WorldGeneratorHearts(), 1);
		
		MinecraftForge.EVENT_BUS.register(events = new CommonEvents());
		FMLCommonHandler.instance().bus().register(events);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.renderInit();
	}
}
