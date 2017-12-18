package tk.nukeduck.generation.world.loot.parse.condition;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import tk.nukeduck.generation.util.BlockPos;

public class ConditionBiome implements ICondition {
	private BiomeGenBase biome;

	public ConditionBiome(String biome) {
		for(BiomeGenBase b : BiomeGenBase.getBiomeGenArray()) {
			if(b != null && b.biomeName.equalsIgnoreCase(biome)) {
				this.biome = b;
				return;
			}
		}
	}

	@Override
	public boolean isMet(World world, BlockPos pos) {
		return world.getBiomeGenForCoords(pos.x, pos.z) == this.biome;
	}
}
