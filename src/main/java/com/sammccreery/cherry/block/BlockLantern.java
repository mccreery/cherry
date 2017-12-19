package com.sammccreery.cherry.block;

import static com.sammccreery.cherry.util.WorldUtil.RANDOM;

import java.util.Random;

import com.sammccreery.cherry.net.ClientProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockLantern extends Block implements ITileEntityProvider {
	public BlockLantern() {
		super(Material.glass);
		setStepSound(soundTypeGlass);
		setLightLevel(0.8125F);
		setBlockBounds(0.3125F, 0.0F, 0.3125F, 0.6875F, 0.5F, 0.6875F);
		setHardness(0.2F);
		setResistance(0.2F);
		setHarvestLevel("pickaxe", 0);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(!(tileEntity instanceof TileEntityHeartLantern)) return;
		TileEntityHeartLantern lantern = (TileEntityHeartLantern) tileEntity;
		
		float charge = lantern.chargeLevel;
		BlockHeartCrystal.spawnParticles(world, x, y, z, this, (int)(RANDOM.nextFloat() * charge * 4));
	}

	public static final float CHARGE_TAKEN = 0.1F;

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(!(tileEntity instanceof TileEntityHeartLantern)) return false;

		TileEntityHeartLantern lantern = (TileEntityHeartLantern) tileEntity;

		if(lantern.chargeLevel > CHARGE_TAKEN && player.getHealth() < player.getMaxHealth()) {
			player.heal(1);
			--lantern.chargeLevel;

			for(int i = 0; i < 2; i++) {
				double xRand = x + RANDOM.nextDouble();
				double yRand = y + RANDOM.nextDouble() - 0.5;
				double zRand = z + RANDOM.nextDouble();

				world.spawnParticle("heart", xRand, yRand, zRand,
					RANDOM.nextDouble() * 0.5, RANDOM.nextDouble() * 0.5, RANDOM.nextDouble() * 0.5);
			}
			return true;
		}
		return false;
	}

	@Override
	public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
		if(p_149660_5_ == 0 && p_149660_1_.isSideSolid(p_149660_2_, p_149660_3_ + 1, p_149660_4_, ForgeDirection.DOWN, true)) {
			return 4;
		}
		return 0;
	}

	@Override
	public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
		int l = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, p_149689_1_.getBlockMetadata(p_149689_2_, p_149689_3_, p_149689_4_) + l, 2);
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		if(side == 0) {
			return world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN, true);
		} else if(side == 1) {
			return world.isSideSolid(x, y - 1, z, ForgeDirection.UP, true);
		}
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		int metadata = (int) (world.getBlockMetadata(x, y, z) / 4);
		if(metadata == 0) {
			return world.isSideSolid(x, y - 1, z, ForgeDirection.UP);
		} else {
			return world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if(!this.canPlaceBlockAt(world, x, y, z)) {
			this.dropBlockAsItem(world, x, y, z, 0, 0);
			world.setBlockToAir(x, y, z);
		}
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

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityHeartLantern();
	}
}
