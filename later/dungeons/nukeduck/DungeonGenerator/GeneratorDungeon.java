package tk.nukeduck.DungeonGenerator;

import java.util.Random;

import net.minecraft.server.v1_7_R1.World;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

public class GeneratorDungeon extends ChunkGenerator {
	public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid) {
		byte[][] result = new byte[world.getHeight() / 16][];
		
		for(int x = 0; x < 16; x++) {
			for(int z = 0; z < 16; z++) {
				setBlock(result, x, 0, z, (byte) Material.SMOOTH_BRICK.getId());
				for(int y = 1; y < 4; y++) {
					setBlock(result, x, y, z, (byte) (x % 15 == 0 || z % 15 == 0 ? Material.SMOOTH_BRICK.getId() : x == 1 || x == 14 || z == 1 || z == 14 ? Material.COBBLESTONE.getId() : Material.DIRT.getId()));
				}
				setBlock(result, x, 5, z, (byte) (x % 15 == 0 || z % 15 == 0 ? Material.SMOOTH_BRICK.getId() : x == 1 || x == 14 || z == 1 || z == 14 ? Material.COBBLESTONE.getId() : Material.DIRT.getId()));
				if(x % 15 == 0 || z % 15 == 0) setBlock(result, x, 6, z, (byte) ((x + z) % 2 == 0 ? 44 : 126));
				
				biomeGrid.setBiome(x, z, Biome.PLAINS);
			}
		}
		
		setBlock(result, 7, 4, 0, (byte) Material.GLOWSTONE.getId()); setBlock(result, 7, 5, 0, (byte) Material.STAINED_GLASS.getId());
		setBlock(result, 8, 4, 0, (byte) Material.GLOWSTONE.getId()); setBlock(result, 8, 5, 0, (byte) Material.STAINED_GLASS.getId());
		setBlock(result, 7, 4, 15, (byte) Material.GLOWSTONE.getId()); setBlock(result, 7, 5, 15, (byte) Material.STAINED_GLASS.getId());
		setBlock(result, 8, 4, 15, (byte) Material.GLOWSTONE.getId()); setBlock(result, 8, 5, 15, (byte) Material.STAINED_GLASS.getId());
		
		setBlock(result, 0, 4, 7, (byte) Material.GLOWSTONE.getId()); setBlock(result, 0, 5, 7, (byte) Material.STAINED_GLASS.getId());
		setBlock(result, 0, 4, 8, (byte) Material.GLOWSTONE.getId()); setBlock(result, 0, 5, 8, (byte) Material.STAINED_GLASS.getId());
		setBlock(result, 15, 4, 7, (byte) Material.GLOWSTONE.getId()); setBlock(result, 15, 5, 7, (byte) Material.STAINED_GLASS.getId());
		setBlock(result, 15, 4, 8, (byte) Material.GLOWSTONE.getId()); setBlock(result, 15, 5, 8, (byte) Material.STAINED_GLASS.getId());
		
		return result;
	}
	
	void setBlock(byte[][] result, int x, int y, int z, byte blkid) {
		if (result[y >> 4] == null) {
			result[y >> 4] = new byte[4096];
		}
		
		result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
	}
}
