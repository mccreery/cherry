package tk.nukeduck.alchemy;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "alchemy", name = "NukeDuck's Alchemy Mod", version = "1.0")

public class Alchemy {
	public static Block alchemyPot;
	public static Fluid emalgamescence;
	public static Block emalgamescenceFluid;
	public static Item essence;
	
	@SidedProxy(clientSide="tk.nukeduck.alchemy.ClientProxy", serverSide="tk.nukeduck.alchemy.CommonProxy")
    public static CommonProxy proxy;
    public static ClientProxy clientProxy = new ClientProxy();
    
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		alchemyPot = new BlockAlchemyPot(Material.iron).setHardness(25.0F).setResistance(6000.0F).setBlockName("alchemyPot").setBlockTextureName("alchemy:alchemy_pot");
		GameRegistry.registerBlock(alchemyPot, "AlchemyPot");
		
		GameRegistry.registerTileEntity(TileEntityAlchemyPot.class, "AlchemyPot");
		clientProxy.registerRenderers();
		
		emalgamescence = new Fluid("emalgamescence").setLuminosity(20).setDensity(5000).setTemperature(350).setViscosity(2500);
		FluidRegistry.registerFluid(emalgamescence);
		emalgamescenceFluid = new BlockEmalgamescence(emalgamescence, Material.water).setBlockName("emalgamescence");
		GameRegistry.registerBlock(emalgamescenceFluid, "EmalgamescenceFluid");
		emalgamescence.setUnlocalizedName(emalgamescenceFluid.getUnlocalizedName());
		
		essence = new ItemEssence();
	}
}