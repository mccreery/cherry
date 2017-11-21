package tk.nukeduck.lightsaber.block.generation;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import tk.nukeduck.lightsaber.Lightsaber;
import static tk.nukeduck.lightsaber.util.Strings.*;
import tk.nukeduck.lightsaber.registry.LightsaberBlocks;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorCrystal implements IWorldGenerator {
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		int dId = world.provider.dimensionId;
		int min = dId == -1 ? 32 : 5;
		int max = dId == 0 ? 20 : dId == 1 ? 75 : 120;
		int diff = max - min;
		Block spawnIn = dId == 0 ? Blocks.stone : dId == -1 ? Blocks.netherrack : dId == 1 ? Blocks.end_stone : Blocks.stone;
		
		int baseX = chunkX * 16, baseZ = chunkZ * 16;
		
		for(int k = 0; k < (dId == 1 ? 15 : 7); k++) {
			int x = baseX + random.nextInt(16);
			int y = min + random.nextInt(diff);
			int z = baseZ + random.nextInt(16);
			
			int[] possibleColors = getColorIndicesForBiome(world, x, z);
			
			if(possibleColors.length > 0 && (world.getBlock(x, y - 1, z) == spawnIn
				|| world.getBlock(x, y + 1, z) == spawnIn
				|| world.getBlock(x - 1, y, z) == spawnIn
				|| world.getBlock(x + 1, y, z) == spawnIn
				|| world.getBlock(x, y, z - 1) == spawnIn
				|| world.getBlock(x, y, z + 1) == spawnIn)
				&& world.getBlock(x, y, z) == Blocks.air) {
				int metaForBiome = possibleColors.length > 1 ? possibleColors[random.nextInt(possibleColors.length)] : possibleColors[0];
				world.setBlock(x, y, z, LightsaberBlocks.crystal);
				world.setBlockMetadataWithNotify(x, y, z, metaForBiome, 2);
			}
		}
	}
	
	/** {@code HashMap} containing the colours of crystal that are able to spawn in each biome. */
	private static final HashMap<BiomeGenBase, int[]> colorsPerBiome = new HashMap<BiomeGenBase, int[]>() {
		{
			/** Colour ID for red. */
			int redIndex = colorIndex(RED);
			/** Colour ID for orange. */
			int orangeIndex = colorIndex(ORANGE);
			/** Colour ID for yellow. */
			int yellowIndex = colorIndex(YELLOW);
			/** Colour ID for purple. */
			int purpleIndex = colorIndex(PURPLE);
			/** Colour ID for green. */
			int greenIndex = colorIndex(GREEN);
			
			/** Array containing the IDs of colours that spawn in Mesa biomes. */
			int[] mesaColors = {redIndex, orangeIndex, yellowIndex};
			/** Array containing the IDs of colours that spawn in Birch Forest biomes. */
			int[] birchColors = {greenIndex, colorIndex(WHITE)};
			
			/** Array containing only the ID of yellow. */
			int[] yellow = {yellowIndex};
			/** Array containing only the ID of blue. */
			int[] blue = {colorIndex(BLUE)};
			/** Array containing only the ID of green. */
			int[] green = {greenIndex};
			/** Array containing only the ID of purple. */
			int[] purple = {purpleIndex};
			
			put(BiomeGenBase.desert,				yellow);
			put(BiomeGenBase.desertHills,			yellow);
			put(BiomeGenBase.beach,					yellow);
			put(BiomeGenBase.ocean,					blue);
			put(BiomeGenBase.deepOcean,				blue);
			put(BiomeGenBase.frozenOcean,			blue);
			put(BiomeGenBase.river,					blue);
			put(BiomeGenBase.plains,				green);
			put(BiomeGenBase.extremeHills,			green);
			put(BiomeGenBase.extremeHillsPlus,		green);
			put(BiomeGenBase.jungle,				green);
			put(BiomeGenBase.jungleHills,			green);
			put(BiomeGenBase.jungleEdge,			green);
			put(BiomeGenBase.birchForest,			birchColors);
			put(BiomeGenBase.birchForestHills,		birchColors);
			put(BiomeGenBase.savanna,				green);
			put(BiomeGenBase.savannaPlateau,		green);
			put(BiomeGenBase.mesa,					mesaColors);
			put(BiomeGenBase.mesaPlateau,			mesaColors);
			put(BiomeGenBase.mesaPlateau_F,			mesaColors);
			put(BiomeGenBase.hell,					new int[] {redIndex});
			put(BiomeGenBase.sky,					new int[] {purpleIndex, colorIndex(BLACK)});
			put(BiomeGenBase.mushroomIsland,		purple);
			put(BiomeGenBase.mushroomIslandShore,	purple);
		}
	};
	
	/** Searches through the biome colours to find crystal colours which can spawn at the given location.
	 * @param world The world to look for a biome in.
	 * @param x The X coordinate in the world to look for a biome at.
	 * @param z The Z coordinate in the world to look for a biome at.
	 * @return The colours of crystal which can spawn at the given X and Z coordinates in the world. */
	private static int[] getColorIndicesForBiome(World world, int x, int z) {
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		if(colorsPerBiome.containsKey(biome)) {
			return colorsPerBiome.get(biome);
		}
		return new int[] {};
	}
}