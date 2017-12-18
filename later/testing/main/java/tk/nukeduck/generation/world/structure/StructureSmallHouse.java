package tk.nukeduck.generation.world.structure;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import tk.nukeduck.generation.util.BlockInfo;
import tk.nukeduck.generation.util.WeightedRandom;
import tk.nukeduck.generation.world.BoundingBox;
import tk.nukeduck.generation.world.GenFlag;
import tk.nukeduck.generation.world.WorldUtils;
import tk.nukeduck.generation.world.placer.BlockPlacerStatic;
import tk.nukeduck.generation.world.placer.BlockPlacerWeightedRandom;
import tk.nukeduck.generation.world.placer.IBlockPlacer;

public class StructureSmallHouse extends StructureBoxed {
	public StructureSmallHouse(Dimension dimension) {
		super(dimension, new BoundingBox(7, 4, 7));
	}

	@Override
	public void generate(World world, int x, int y, int z, Random random) {
		BoundingBox bb = this.getBoundingBox().offset(x, y, z);
		WorldUtils.fillFloor(world, bb, new BlockPlacerStatic(new BlockInfo(Blocks.stonebrick)), random, GenFlag.HOLLOW);

		IBlockPlacer walls = new BlockPlacerWeightedRandom(new WeightedRandom<BlockInfo>()
			.set(new BlockInfo(Blocks.planks), 1.0F)
			.set(null, 0.2F));
		IBlockPlacer window = new BlockPlacerStatic(new BlockInfo(Blocks.glass_pane));

		WorldUtils.fill(world, bb, walls, random, GenFlag.HOLLOW, GenFlag.HOLLOW_ENDS);
		WorldUtils.fill(world, new BoundingBox(bb.getX() + 1, bb.getY() - 1, bb.getZ() + 1, bb.getWidth() - 2, 1, bb.getDepth() - 2), new BlockPlacerStatic(new BlockInfo(Blocks.double_stone_slab)), random);
	}

	@Override
	public float getWeight(BiomeGenBase biome) {
		return 1.0F;
	}

	@Override
	public boolean canGenerate(World world, int x, int y, int z) {
		return super.canGenerate(world, x, y, z) && WorldUtils.hasFloor(world, this.getBoundingBox().offset(x, y, z), 20);
	}
}
