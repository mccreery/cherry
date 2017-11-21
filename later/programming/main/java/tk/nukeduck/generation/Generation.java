package tk.nukeduck.generation;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import tk.nukeduck.generation.block.BlockMimic;
import tk.nukeduck.generation.block.BlockProgrammer;
import tk.nukeduck.generation.block.TileEntityCamoDispenser;
import tk.nukeduck.generation.block.TileEntityMimic;
import tk.nukeduck.generation.block.TileEntityProgrammer;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.entity.EntityMimic;
import tk.nukeduck.generation.network.CommonProxy;
import tk.nukeduck.generation.registry.ArmorSet;
import tk.nukeduck.generation.registry.GenerationBlocks;
import tk.nukeduck.generation.registry.ObjectName;
import tk.nukeduck.generation.util.Constants;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION)
public class Generation {
	@Instance
	public static Generation instance;

	@SidedProxy(clientSide="tk.nukeduck.generation.network.ClientProxy", serverSide="tk.nukeduck.generation.network.CommonProxy")
	public static CommonProxy proxy;

	public static final Minecraft mc = Minecraft.getMinecraft();
	public static final Random random = new Random();

	private static int mobId = 0;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		GenerationBlocks.init();
	}

	public static final CreativeTabs COMPUTER_TAB = new CreativeTabs("computer") {
		@Override
		public Item getTabIconItem() {
			return disc;
		}
	};

	public static final ItemDisc disc = new ItemDisc();
	public static final BlockProgrammer programmer = new BlockProgrammer();

	public static final Item ruby = new Item().setTextureName("ruby").setUnlocalizedName("ruby");

	public static final ArmorSet emerald = new ArmorSet(Generation.class, new ObjectName("emerald"), Items.emerald, 100, 4, 6, 5, 3, 20);
	public static final ArmorSet rubyArmor = new ArmorSet(Generation.class, new ObjectName("ruby"), ruby, 100, 4, 6, 5, 3, 20);

	public static final Item cookedFlesh = new ItemFood(5, 0.6F, true).setUnlocalizedName("cookedFlesh").setTextureName("alchemy:rotten_flesh_cooked").setCreativeTab(CreativeTabs.tabFood);

	@EventHandler
	public void init(FMLInitializationEvent event) {
		GameRegistry.registerItem(cookedFlesh, "rotten_flesh_cooked");
		GameRegistry.addSmelting(Items.rotten_flesh, new ItemStack(cookedFlesh), 0.1F);

		NetworkRegistry.INSTANCE.registerGuiHandler(this, this.proxy);

		GameRegistry.registerBlock(programmer, "programmer");
		GameRegistry.registerItem(ruby, "ruby");
		emerald.init();
		rubyArmor.init();
		GameRegistry.registerItem(disc, "optical_disc");

		EntityRegistry.registerGlobalEntityID(EntityMimic.class, "Mimic", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityMimic.class, "Mimic", mobId++, this, 80, 1, true);

		GameRegistry.registerTileEntity(TileEntityCamoDispenser.class, "CamoDispenser");
		GameRegistry.registerTileEntity(TileEntityProgrammer.class, "Programmer");

		GameRegistry.registerBlock(new BlockMimic(Material.wood).setStepSound(Block.soundTypeWood), "mimic");
		GameRegistry.registerTileEntity(TileEntityMimic.class, "Mimic");

		proxy.registerRenderers();

		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);

		ICodeBlock.registerBlocks();

		/*GameRegistry.registerWorldGenerator(new WorldGeneratorStructures()
			//.addStructure(new StructureRoom(Dimension.OVERWORLD))
			//.addStructure(new StructureSmallHouse(Dimension.OVERWORLD))
			.addStructure(new DungeonPits(Dimension.OVERWORLD)), 1);*/
	}
}
