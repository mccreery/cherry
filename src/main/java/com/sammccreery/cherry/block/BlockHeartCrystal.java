package com.sammccreery.cherry.block;

import static com.sammccreery.cherry.util.Util.RANDOM;

import java.util.Random;

import com.sammccreery.cherry.net.ClientProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHeartCrystal extends Block implements ITileEntityProvider {
	public BlockHeartCrystal() {
		super(Material.glass);
		this.setStepSound(Block.soundTypeGlass);
		this.setBlockBounds(0.125f, 0.0f, 0.125f, 0.875f, 0.9375f, 0.875f);
		this.setLightLevel(0.5F);
		setCreativeTab(CreativeTabs.tabMisc);
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		spawnParticles(world, x, y, z, this, 1);
	}

	public static void spawnParticles(World world, int x, int y, int z, Block block, int n) {
		double dx = block.getBlockBoundsMaxX() - block.getBlockBoundsMinX();
		double dy = block.getBlockBoundsMaxY() - block.getBlockBoundsMinY();
		double dz = block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ();

		for(int i = 0; i < n; i++) {
			double px = x + block.getBlockBoundsMinX() + RANDOM.nextDouble() * dx;
			double py = y + block.getBlockBoundsMinY() + RANDOM.nextDouble() * dy;
			double pz = z + block.getBlockBoundsMinZ() + RANDOM.nextDouble() * dz;

			world.spawnParticle("reddust", px, py, pz, RANDOM.nextDouble() * 0.7 + 0.3, 0.0, 0.0);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityHeartCrystal();
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return ClientProxy.renderId;
	}

	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}
}
