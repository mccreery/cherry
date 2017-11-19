package tk.nukeduck.foodcanister;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "foodcanister", name = "Food Canister Mod", version = "1.0")

public class FoodCanister {
	/** Blocks and items */
	public static Item foodCanister;
	
	@SidedProxy(clientSide="tk.nukeduck.foodcanister.ClientProxy", serverSide="tk.nukeduck.foodcanister.CommonProxy")
    public static CommonProxy proxy;
	
	@Instance("foodcanister")
	public static FoodCanister instance = new FoodCanister();
    
	@EventHandler
	/**
	 * Initializes the mod's items, blocks, world generators, etc.
	 * @param event The FMLInitializationEvent that initializes the mod.
	 */
	public void init(FMLInitializationEvent event) {
		foodCanister = new ItemFoodCanister().setUnlocalizedName("foodcanister").setCreativeTab(CreativeTabs.tabFood);
		GameRegistry.registerItem(foodCanister, "foodcanister");
		
		proxy.registerRenders();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
	}
}