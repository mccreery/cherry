package tk.nukeduck.lightsaber.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.block.tileentity.TileEntityCrystal;
import tk.nukeduck.lightsaber.client.renderer.CrystalRenderer;
import tk.nukeduck.lightsaber.client.renderer.EntityLightFX;
import tk.nukeduck.lightsaber.item.ItemBlockCrystal;
import tk.nukeduck.lightsaber.util.Strings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrystal extends Block implements ITileEntityProvider {
	/** Constructor. Sets variables relating to how the block interacts with the world. */
	public BlockCrystal() {
		super(Material.glass);
		this.setStepSound(Block.soundTypeGlass);
		
		this.setCreativeTab(Lightsaber.lightsaberTab);
		this.setBlockTextureName(Strings.MOD_ID + ":crystal_red");
		
		float f = 1F / 16F;
		this.setBlockBounds(f, 0, f, 1 - f, f * 10, 1 - f);
		
		this.setHardness(0.2F);
		this.setLightLevel(0.5F);
	}
	
	/** {@inheritDoc}<br/>
	 * Adds different colours of crystal.
	 * @param item The base item.
	 * @param creativeTab The tab items will be added to.
	 * @param items The list of items to add to. */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List items) {
		for(int i = 0; i < Strings.COLORS.length; i++) {
			items.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return
			world.getBlock(x, y, z - 1).isSideSolid(world, x, y, z - 1, ForgeDirection.SOUTH) ||
			world.getBlock(x, y, z + 1).isSideSolid(world, x, y, z + 1, ForgeDirection.NORTH) |
			world.getBlock(x - 1, y, z).isSideSolid(world, x - 1, y, z, ForgeDirection.EAST) ||
			world.getBlock(x + 1, y, z).isSideSolid(world, x + 1, y, z, ForgeDirection.WEST) ||
			world.getBlock(x, y + 1, z).isSideSolid(world, x, y + 1, z, ForgeDirection.DOWN) ||
			world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, ForgeDirection.UP);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if(!canBlockStay(world, x, y, z)) {
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int p_149707_5_) {
		return canBlockStay(world, x, y, z);
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityCrystal(Lightsaber.random.nextInt(8) + 6, Lightsaber.random.nextInt(256), Lightsaber.random.nextInt(256), Lightsaber.random.nextInt(256));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 0;
	}
	
	/** {@inheritDoc}<br/>
	 * Spawns particles in the world. */
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		if(random.nextBoolean()) {
			Lightsaber.mc.effectRenderer.addEffect(new EntityLightFX(world, null,
				x + random.nextFloat(),
				y + random.nextFloat(),
				z + random.nextFloat(),
				CrystalRenderer.colors[world.getBlockMetadata(x, y, z)]
			));
		}
	}
	
	/** {@inheritDoc}<br/>
	 * Changes bounding box according to which wall the crystal is stuck to. */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		float f = 1F / 16f;
		float minX = f, minY = 0, minZ = f, maxX = 1 - f, maxY = f * 10, maxZ = 1 - f;
		
		if(world.getBlock(x, y, z - 1).isSideSolid(world, x, y, z - 1, ForgeDirection.SOUTH)) {
			this.setBlockBounds(minX, minZ, minY, maxX, maxZ, maxY);
		} else if(world.getBlock(x, y, z + 1).isSideSolid(world, x, y, z + 1, ForgeDirection.NORTH)) {
			this.setBlockBounds(minX, minZ, 1 - maxY, maxX, maxZ, 1 - minY);
		} else if(world.getBlock(x - 1, y, z).isSideSolid(world, x - 1, y, z, ForgeDirection.EAST)) {
			this.setBlockBounds(minY, minX, minZ, maxY, maxX, maxZ);
		} else if(world.getBlock(x + 1, y, z).isSideSolid(world, x + 1, y, z, ForgeDirection.WEST)) {
			this.setBlockBounds(1 - maxY, minX, minZ, 1 - minY, maxX, maxZ);
		} else if(world.getBlock(x, y + 1, z).isSideSolid(world, x, y + 1, z, ForgeDirection.DOWN) && !world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, ForgeDirection.UP)) {
			this.setBlockBounds(minX, 1 - maxY, minZ, maxX, 1 - minY, maxZ);
		} else {
			this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
	}
	
	/** {@inheritDoc}<br/>
	 * Changes bounding box according to which wall the crystal is stuck to. */
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		float f = 1F / 16f;
		float minX = f, minY = 0, minZ = f, maxX = 1 - f, maxY = f * 10, maxZ = 1 - f;
		
		if(world.getBlock(x, y, z - 1).isSideSolid(world, x, y, z - 1, ForgeDirection.SOUTH)) {
			return AxisAlignedBB.getBoundingBox(x + minX, y + minZ, z + minY, x + maxX, y + maxZ, z + maxY);
		} else if(world.getBlock(x, y, z + 1).isSideSolid(world, x, y, z + 1, ForgeDirection.NORTH)) {
			return AxisAlignedBB.getBoundingBox(x + minX, y + minZ, z + 1 - maxY, x + maxX, y + maxZ, z + 1 - minY);
		} else if(world.getBlock(x - 1, y, z).isSideSolid(world, x - 1, y, z, ForgeDirection.EAST)) {
			return AxisAlignedBB.getBoundingBox(x + minY, y + minX, z + minZ, x + maxY, y + maxX, z + maxZ);
		} else if(world.getBlock(x + 1, y, z).isSideSolid(world, x + 1, y, z, ForgeDirection.WEST)) {
			return AxisAlignedBB.getBoundingBox(x + 1 - maxY, y + minX, z + minZ, x + 1 - minY, y + maxX, z + maxZ);
		} else if(world.getBlock(x, y + 1, z).isSideSolid(world, x, y + 1, z, ForgeDirection.DOWN) && !world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, ForgeDirection.UP)) {
			return AxisAlignedBB.getBoundingBox(x + minX, y + 1 - maxY, z + minZ, x + maxX, y + 1 - minY, z + maxZ);
		} else {
			return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
		}
	}
	
	/** {@inheritDoc}<br/>
	 * Changes bounding box according to which wall the crystal is stuck to. */
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		float f = 1F / 16f;
		float minX = f, minY = 0, minZ = f, maxX = 1 - f, maxY = f * 10, maxZ = 1 - f;
		
		if(world.getBlock(x, y, z - 1).isSideSolid(world, x, y, z - 1, ForgeDirection.SOUTH)) {
			return AxisAlignedBB.getBoundingBox(x + minX, y + minZ, z + minY, x + maxX, y + maxZ, z + maxY);
		} else if(world.getBlock(x, y, z + 1).isSideSolid(world, x, y, z + 1, ForgeDirection.NORTH)) {
			return AxisAlignedBB.getBoundingBox(x + minX, y + minZ, z + 1 - maxY, x + maxX, y + maxZ, z + 1 - minY);
		} else if(world.getBlock(x - 1, y, z).isSideSolid(world, x - 1, y, z, ForgeDirection.EAST)) {
			return AxisAlignedBB.getBoundingBox(x + minY, y + minX, z + minZ, x + maxY, y + maxX, z + maxZ);
		} else if(world.getBlock(x + 1, y, z).isSideSolid(world, x + 1, y, z, ForgeDirection.WEST)) {
			return AxisAlignedBB.getBoundingBox(x + 1 - maxY, y + minX, z + minZ, x + 1 - minY, y + maxX, z + maxZ);
		} else if(world.getBlock(x, y + 1, z).isSideSolid(world, x, y + 1, z, ForgeDirection.DOWN) && !world.getBlock(x, y - 1, z).isSideSolid(world, x, y - 1, z, ForgeDirection.UP)) {
			return AxisAlignedBB.getBoundingBox(x + minX, y + 1 - maxY, z + minZ, x + maxX, y + 1 - minY, z + maxZ);
		} else {
			return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
		}
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return ItemBlockCrystal.icons[meta];
	}
}