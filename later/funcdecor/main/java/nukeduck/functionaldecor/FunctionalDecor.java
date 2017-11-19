package nukeduck.functionaldecor;

import java.util.Random;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import nukeduck.functionaldecor.block.DecorBlocks;
import nukeduck.functionaldecor.block.TileEntityDecor;
import nukeduck.functionaldecor.network.CommonProxy;
import nukeduck.functionaldecor.util.BlockItemName;

@Mod(modid=FunctionalDecor.MODID, name="Functional Decoration", version="1.0")
public class FunctionalDecor {
	public static final String MODID = "functional_decor";
	public static final Random RANDOM = new Random();

	public static final CreativeTabs TAB = new CreativeTabs("functionalDecor") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(DecorBlocks.alarmClock);
		}
	};

	@Instance
	public static FunctionalDecor INSTANCE;

	@SidedProxy(clientSide="nukeduck.functionaldecor.network.ClientProxy", serverSide="nukeduck.functionaldecor.network.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		BlockItemName.setNamespace(this);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		GameRegistry.registerTileEntity(TileEntityDecor.class, "TileEntityDecor");
		DecorBlocks.init();

		GameRegistry.addShapedRecipe(new ItemStack(DecorBlocks.alarmClock, 1, 0),
			"GCI", "ITI", 'G', Blocks.glass_pane, 'C', Items.clock, 'I', Items.iron_ingot, 'T', Blocks.redstone_torch);

		GameRegistry.addShapelessRecipe(new ItemStack(DecorBlocks.alarmClock, 1, 0), DecorBlocks.alarmClock, new ItemStack(Items.dye, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(DecorBlocks.alarmClock, 1, 1), DecorBlocks.alarmClock, new ItemStack(Items.dye, 1, 10));
		GameRegistry.addShapelessRecipe(new ItemStack(DecorBlocks.alarmClock, 1, 2), DecorBlocks.alarmClock, new ItemStack(Items.dye, 1, 4));
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.initRenders();
	}
}
