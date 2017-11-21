package tk.nukeduck.magma;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.*;

@Mod(modid="magma", name="Magma", version="1.0")

public class Magma implements IWorldGenerator
{
	/* Magma block mod, created in full by NukeDuck (http://www.minecraftforum.net/user/1335321-nukeduck/)
	 * Idea by TriFlamed (http://www.minecraftforum.net/user/2370639-triflamed/)
	 * Original idea thread here: http://www.minecraftforum.net/topic/1943504-magma-blocks/
	 */
	
	public static Block magma;
	public static Block basalt;
	public static Block ashCloud;
	public static Block ash;
	
	@EventHandler
	public static void init(FMLInitializationEvent e)
	{
		magma = new BlockMagma().setBlockName("magma");
		GameRegistry.registerBlock(magma, "magma");
		magma.setHarvestLevel("pickaxe", 2);
		
		basalt = new BlockBN(Material.rock).setStepSound(Block.soundTypeStone).setHardness(5F).setBlockName("basalt").setBlockTextureName("magma:basalt");
		GameRegistry.registerBlock(basalt, "basalt");
		
		ash = new BlockAsh().setBlockName("ash");
		GameRegistry.registerBlock(ash, "ash");
		
		ashCloud = new BlockAshCloud(Material.sand).setBlockName("ashCloud").setBlockTextureName("magma:ashcloud");
		GameRegistry.registerBlock(ashCloud, "ashCloud");
		
		GameRegistry.registerFuelHandler(new FuelHandler());
		GameRegistry.registerWorldGenerator(new Magma(), 100);
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if(world.provider.dimensionId == -1) // Nether dimension ID
		{
			for(int i = 0; i < 25; i++) {
				int xCoord = chunkX * 16 + random.nextInt(16);
				int yCoord = 1 + random.nextInt(256);
				int zCoord = chunkZ * 16 + random.nextInt(16);
				
				new WorldGenMinable(magma, 30, Blocks.netherrack).generate(world, random, xCoord, yCoord, zCoord);
			}
			
			for(int i = 0; i < 5; i++) {
				int xCoord = chunkX * 16 + random.nextInt(16);
				int yCoord = 1 + random.nextInt(256);
				int zCoord = chunkZ * 16 + random.nextInt(16);
				
				new WorldGenMinable(ashCloud, 50, Blocks.air).generate(world, random, xCoord, yCoord, zCoord);
			}
			
			int xCoord = chunkX * 16 + random.nextInt(16);
			int yCoord = 30;
			int zCoord = chunkZ * 16 + random.nextInt(16);
			
			new WorldGenVolcano().generate(world, random, xCoord, yCoord, zCoord);
		}
	}
}