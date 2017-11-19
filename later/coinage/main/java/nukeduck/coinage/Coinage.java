package nukeduck.coinage;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import nukeduck.coinage.network.CommonProxy;
import nukeduck.coinage.registry.BlockRegister;
import nukeduck.coinage.registry.CraftingRegister;
import nukeduck.coinage.registry.EnchantmentRegister;
import nukeduck.coinage.registry.EntityRegister;
import nukeduck.coinage.registry.IRegister;
import nukeduck.coinage.registry.ItemRegister;
import scala.util.Random;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = "1.0")
public class Coinage {
	public static Coinage instance;
	public static Random random = new Random();
	
	@SidedProxy(clientSide="nukeduck.coinage.network.ClientProxy", serverSide="nukeduck.coinage.network.CommonProxy")
    public static CommonProxy proxy;
	
	public IRegister[] registers;
	public ItemRegister itemRegister;
	public CraftingRegister craftingRegister;
	public EnchantmentRegister enchantRegister;
	public CoinDropRegister coinDropRegister;
	public EntityRegister entityRegister;
	public BlockRegister blockRegister;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		instance = this;
		
		registers = new IRegister[] {
			coinDropRegister = new CoinDropRegister(),
			itemRegister = new ItemRegister(),
			craftingRegister = new CraftingRegister(),
			enchantRegister = new EnchantmentRegister(),
			entityRegister = new EntityRegister(),
			blockRegister = new BlockRegister(itemRegister)
		};
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		for(IRegister register : registers) {
			register.init();
		}
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
		proxy.registerEvents();
		proxy.registerRenderThings();
	}
}
