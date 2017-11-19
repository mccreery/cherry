package tk.nukeduck.hearts.block;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tk.nukeduck.hearts.HeartCrystal;
import tk.nukeduck.hearts.network.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHeartCrystal extends Block implements ITileEntityProvider {
	public BlockHeartCrystal() {
		super(Material.glass);
		this.setStepSound(Block.soundTypeGlass);
		this.setBlockBounds(0.125f, 0.0f, 0.125f, 0.875f, 0.9375f, 0.875f);
		this.setLightLevel(0.5F);
	}

	public String getParticle() {
		return "reddust";
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		double xRand = (double) x + random.nextDouble();
		double yRand = (double) y + random.nextDouble();
		double zRand = (double) z + random.nextDouble();
		world.spawnParticle(this.getParticle(), xRand, yRand, zRand,
			random.nextDouble() * 0.7 + 0.3, 0.0, 0.0);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityHeartCrystal();
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) {
		this.blockIcon = icon.registerIcon(HeartCrystal.MODID + ":heart_crystal");
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
