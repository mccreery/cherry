package nukeduck.snowplus;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "snowplus", name = "Snow+")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)

public class SnowPlus
{
	public static String modid = "snowplus";
	
	public static Block snowStairs;
	public static Block snowBrick;
	public static Block snowBrickStairs;
	public static Block snowDoor;
	public static Block icePane;
	public static Item itemSnowDoor;
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		snowStairs = new BlockSnowPlusStairs(245, Block.blockSnow, 0).setUnlocalizedName(SnowPlus.modid + ":" + "snow_stairs").setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(snowStairs, "SnowStairs");
		LanguageRegistry.addName(snowStairs, "Snow Stairs");
		
		snowBrick = new Block(246, Material.craftedSnow).setUnlocalizedName(SnowPlus.modid + ":" + "snow_brick").setCreativeTab(CreativeTabs.tabBlock).func_111022_d(SnowPlus.modid + ":" + "snow_brick");
		GameRegistry.registerBlock(snowBrick, "SnowBrick");
		LanguageRegistry.addName(snowBrick, "Snow Bricks");
		
		snowBrickStairs = new BlockSnowPlusStairs(247, snowBrick, 0).setUnlocalizedName(SnowPlus.modid + ":" + "snow_brick_stairs").setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(snowBrickStairs, "SnowBrickStairs");
		LanguageRegistry.addName(snowBrickStairs, "Snow Brick Stairs");
		
		snowDoor = new BlockSnowDoor(248, Material.craftedSnow).setUnlocalizedName(SnowPlus.modid + ":" + "snowdoor_bottom").func_111022_d(SnowPlus.modid + ":" + "snowdoor_bottom");
		GameRegistry.registerBlock(snowDoor, "SnowDoor");
		LanguageRegistry.addName(snowDoor, "Snow Door");
		
		itemSnowDoor = new ItemSnowDoor(399).func_111206_d(SnowPlus.modid + ":" + "door_snow");
		LanguageRegistry.addName(itemSnowDoor, "Snow Door");
		
		// Snow Door
		GameRegistry.addRecipe(new ItemStack(itemSnowDoor, 1), new Object[] {
			"##", "##", "##", Character.valueOf('#'), Block.snow
		});
		
		// Snow Bricks
		GameRegistry.addRecipe(new ItemStack(snowBrick, 4), new Object[] {
			"##", "##", Character.valueOf('#'), Block.snow
		});
	}
}