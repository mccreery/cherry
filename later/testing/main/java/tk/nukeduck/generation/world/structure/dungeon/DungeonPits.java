package tk.nukeduck.generation.world.structure.dungeon;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenForest;
import tk.nukeduck.generation.util.BlockInfo;
import tk.nukeduck.generation.util.Constants;
import tk.nukeduck.generation.util.WeightedRandom;
import tk.nukeduck.generation.world.BoundingBox;
import tk.nukeduck.generation.world.WorldUtils;
import tk.nukeduck.generation.world.placer.BlockPlacerRandom;
import tk.nukeduck.generation.world.placer.BlockPlacerStatic;
import tk.nukeduck.generation.world.placer.BlockPlacerWeightedRandom;
import tk.nukeduck.generation.world.placer.IBlockPlacer;
import tk.nukeduck.generation.world.structure.StructureBoxed;

public class DungeonPits extends StructureBoxed {
	public DungeonPits(Dimension dimension) {
		super(dimension);
		this.replaceThreshold = 0.5F;
	}

	private final IBlockPlacer floor = new BlockPlacerWeightedRandom(new WeightedRandom<BlockInfo>()
		.set(new BlockInfo(Blocks.cobblestone), 1.0F)
		.set(new BlockInfo(Blocks.mossy_cobblestone), 0.8F)
		.set(new BlockInfo(Blocks.air), 0.6F)
		.set(null, 0.4F));

	private final IBlockPlacer lava = new BlockPlacerStatic(new BlockInfo(Blocks.lava)) {
		@Override
		public BlockInfo getBlockPlaced(World world, int x, int y, int z, Random random) {
			if(world.getBlock(x, y, z) != Blocks.stone) return null;
			return super.getBlockPlaced(world, x, y, z, random);
		}
	};

	protected static BoundingBox generateBoundingBox(Random random) {
		return new BoundingBox(random.nextInt(6) + 5, 4, random.nextInt(6) + 5);
	}

	@Override
	public void generate(World world, int x, int y, int z, Random random) {
		System.out.println("yysgys");
		this.setBoundingBox(this.generateBoundingBox(random));

		BoundingBox bb = this.boundingBox.offset(x, y, z);
		WorldUtils.fill(world, bb, new BlockPlacerStatic(new BlockInfo(Blocks.air)), random);
		WorldUtils.fill(world, bb.clone().setHeight(1), floor, random);

		for(int i = 0; i < random.nextInt(5) + 3; i++) {
			int radius = random.nextInt(3) + 2;
			int r2 = radius * 2;
			BoundingBox sphere = new BoundingBox(
				bb.getX() + Math.round(bb.getWidth() * random.nextFloat()) - radius,
				bb.getY() - radius,
				bb.getZ() + Math.round(bb.getDepth() * random.nextFloat()) - radius, 
				r2, r2, r2);

			WorldUtils.fillEllipsoid(world, sphere, lava, random);
		}
	}

	@Override
	public float getWeight(BiomeGenBase biome) {
		return 1.0F;/*biome instanceof BiomeGenForest ? Constants.DUNGEON_PITS_WEIGHT : 0.0F;*/
	}
}
