package tk.nukeduck.generation.world.structure;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import tk.nukeduck.generation.block.TileEntityCamoDispenser;
import tk.nukeduck.generation.registry.GenerationBlocks;
import tk.nukeduck.generation.util.BlockInfo;
import tk.nukeduck.generation.util.BlockPos;
import tk.nukeduck.generation.util.WeightedRandom;
import tk.nukeduck.generation.world.BoundingBox;
import tk.nukeduck.generation.world.GenFlag;
import tk.nukeduck.generation.world.WorldUtils;
import tk.nukeduck.generation.world.loot.LootGeneratorDispenser;
import tk.nukeduck.generation.world.loot.LootGeneratorDungeon;
import tk.nukeduck.generation.world.placer.BlockPlacerRandom;
import tk.nukeduck.generation.world.placer.BlockPlacerStatic;
import tk.nukeduck.generation.world.placer.BlockPlacerWeightedRandom;
import tk.nukeduck.generation.world.placer.IBlockPlacer;

public class StructureRoom extends StructureBoxed {
	public StructureRoom(Dimension dimension) {
		super(dimension, new BoundingBox(5, 5, 5));
	}

	@Override
	public void generate(World world, int x, int y, int z, Random random) {
		IBlockPlacer floor = new BlockPlacerWeightedRandom(new WeightedRandom<BlockInfo>()
			.set(new BlockInfo(Blocks.planks), 1.0F)
			.set(null, 0.1F));
		IBlockPlacer cobble = new BlockPlacerRandom(new BlockInfo(Blocks.cobblestone), new BlockInfo(Blocks.mossy_cobblestone));

		BoundingBox bb = this.getBoundingBox().offset(x, y, z);
		WorldUtils.fill(world, bb, new BlockPlacerStatic(new BlockInfo(Blocks.air)), random);
		WorldUtils.fill(world, bb, cobble, random, GenFlag.HOLLOW);
		WorldUtils.fill(world, new BoundingBox(bb.getX(), bb.getY(), bb.getZ(), bb.getWidth(), 1, bb.getDepth()), floor, random);
		ItemStack sword = new ItemStack(Items.diamond_sword);
		sword.addEnchantment(Enchantment.sharpness, 2);

		WorldUtils.setContainer(world, x + 2, y + 1, z + 2, (BlockContainer) Blocks.trapped_chest, 4, new LootGeneratorDungeon(random)/*new ItemStack[] {sword, new ItemStack(Items.diamond), new ItemStack(Items.gold_ingot), new ItemStack(Items.iron_ingot)}*/, random, true);
		WorldUtils.setContainer(world, x + 4, y + 2, z + 2, (BlockContainer) GenerationBlocks.camoDispenser, 4, new LootGeneratorDispenser(), random, true);
		if(world.getTileEntity(x + 4, y + 2, z + 2) != null) {
			((TileEntityCamoDispenser) world.getTileEntity(x + 4, y + 2, z + 2)).setBlock(Blocks.cobblestone, 0);
		}

		BlockPos[] cobbles = new BlockPos[] {
			new BlockPos(x + 2, y - 2, z + 2),
			new BlockPos(x + 3, y - 2, z + 2),
			new BlockPos(x + 4, y - 1, z + 2),
			new BlockPos(x + 5, y, z + 2),
			new BlockPos(x + 5, y + 2, z + 2)
		};

		for(BlockPos pos : cobbles) {
			if(world.getBlock(pos.x, pos.y, pos.z).isReplaceable(world, pos.x, pos.y, pos.z)) {
				cobble.setBlock(world, pos.x, pos.y, pos.z, random);
			}
		}
		world.setBlock(x + 2, y - 1, z + 2, Blocks.redstone_wire);
		world.setBlock(x + 3, y - 1, z + 2, Blocks.unpowered_repeater, 1, 2);
		world.setBlock(x + 5, y - 1, z + 2, Blocks.redstone_torch, 1, 2);
		world.setBlock(x + 5, y + 1, z + 2, Blocks.unlit_redstone_torch, 5, 2);

		world.setBlock(x, y + 1, z + 2, Blocks.wooden_door, 0 | 4, 2);
		world.setBlock(x, y + 2, z + 2, Blocks.wooden_door, 0 | 4 | 8, 2);

		WorldUtils.fillCylinder(world, bb.offset(0, bb.getHeight(), 0), cobble, random, GenFlag.HOLLOW);
	}

	@Override
	public float getWeight(BiomeGenBase biome) {
		return 1.0F;
	}
}
