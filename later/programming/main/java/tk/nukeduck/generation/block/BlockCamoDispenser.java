package tk.nukeduck.generation.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tk.nukeduck.generation.network.ClientProxy;
import tk.nukeduck.generation.util.BlockInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCamoDispenser extends BlockDispenser {
	private IIcon sideOverlay;
	private IIcon topOverlay;
	private IIcon frameOverlay;

	public BlockCamoDispenser() {
		super();
		this.setHardness(3.5F);
		this.setStepSound(soundTypePiston);
		this.setLightOpacity(255);
		this.opaque = true;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityCamoDispenser();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
		TileEntityCamoDispenser dispenser = (TileEntityCamoDispenser) world.getTileEntity(x, y, z);

		if(dispenser != null && dispenser.hasItem()) {
			ItemStack stack = dispenser.getItem();

			double locX = (double) x + this.field_149942_b.nextDouble() * 0.8 + 0.1;
			double locY = (double) y + this.field_149942_b.nextDouble() * 0.8 + 0.1;
			double locZ = (double) z + this.field_149942_b.nextDouble() * 0.8 + 0.1;
			EntityItem entityitem = new EntityItem(world, locX, locY, locZ, stack);

			double f3 = 0.05;
			entityitem.motionX = this.field_149942_b.nextGaussian() * f3;
			entityitem.motionY = this.field_149942_b.nextGaussian() * f3 + 0.2;
			entityitem.motionZ = this.field_149942_b.nextGaussian() * f3;
			world.spawnEntityInWorld(entityitem);
		}

		super.breakBlock(world, x, y, z, block, p_149749_6_);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntityCamoDispenser tileEntity = (TileEntityCamoDispenser) world.getTileEntity(x, y, z);
		if(tileEntity == null) {
			return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
		}

		if(tileEntity.getBlock() == null) {
			BlockInfo block = this.getBlock(world, player, x, y, z, side, hitX, hitY, hitZ, player.getHeldItem());
			if(block != null) {
				ItemStack stack = player.getHeldItem().copy();
				stack.stackSize = 1;
				tileEntity.setBlock(stack, block.getBlock(), block.getMetadata());
				this.playBlockPlaceSound(world, block.getBlock(), x, y, z);

				if(!world.isRemote) {
					world.markBlockForUpdate(x, y, z);
					tileEntity.markDirty();
				}
				if(!player.capabilities.isCreativeMode) player.getHeldItem().stackSize--;
				return true;
			}
		}
		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}

	public void playBlockPlaceSound(World world, Block block, int x, int y, int z) {
		if(!world.isRemote) {
			double locX = (double) x + 0.5;
			double locY = (double) y + 0.5;
			double locZ = (double) z + 0.5;
			float volume = (block.stepSound.getVolume() + 1.0F) / 2.0F;
			float pitch = block.stepSound.getPitch() * 0.8F;

			world.playSoundEffect(locX, locY, locZ, block.stepSound.func_150496_b(), volume, pitch);
		}
	}

	/** Generates a block info object representing the block that should be placed
	 * from the given itemstack, or {@code null} if no such block exists or it is invalid. */
	public BlockInfo getBlock(World world, EntityPlayer player, int x, int y, int z, int side, float hitX, float hitY, float hitZ, ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemBlock)) {
			return null;
		}

		ItemBlock itemBlock = (ItemBlock) stack.getItem();
		Block block = Block.getBlockFromItem(itemBlock);

		if(this.isBlockValid(block)) {
			int meta = itemBlock.getMetadata(stack.getItemDamage());
			meta = block.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, meta);

			int restoreMeta = world.getBlockMetadata(x, y, z);

			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
			block.onBlockPlacedBy(world, x, y, z, player, stack);
			block.onPostBlockPlaced(world, x, y, z, meta);
			meta = world.getBlockMetadata(x, y, z);

			world.setBlockMetadataWithNotify(x, y, z, restoreMeta, 2);

			return new BlockInfo(block, meta);
		}
		return null;
	}

	public boolean isBlockValid(Block block) {
		int renderType = block.getRenderType();
		return block.isOpaqueCube() && block.getRenderBlockPass() == 0 &&
			(renderType == 0 || renderType == 31 || renderType == 39);
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return true;
	}

	// Rendering stuff from this point on

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon("furnace_side");
		this.field_149944_M = iconRegister.registerIcon("furnace_top");
		this.field_149945_N = iconRegister.registerIcon("dispenser_front_horizontal");
		this.field_149946_O = iconRegister.registerIcon("dispenser_front_vertical");
		this.sideOverlay = iconRegister.registerIcon("alchemy:camo_side");
		this.topOverlay = iconRegister.registerIcon("alchemy:camo_top");
		this.frameOverlay = iconRegister.registerIcon("alchemy:camo_frame");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		TileEntityCamoDispenser dispenser = (TileEntityCamoDispenser) world.getTileEntity(x, y, z);
		if(dispenser == null || dispenser.getBlock() == null) {
			return super.getIcon(world, x, y, z, side);
		}

		return dispenser.getBlock().getIcon(side, dispenser.getMetadata());
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		/* RenderBlocks uses this method rather than the proper one with world access
		 * to determine whether or not to use special coloring for grass */
		if(this.hasGrass) {
			return Blocks.grass.getIcon(side, meta);
		} else {
			return super.getIcon(side, meta);
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getOverlayIcon(int side, int meta) {
		if(side <= 1) {
			return this.topOverlay;
		} else {
			return this.sideOverlay;
		}
	}

	@SideOnly(Side.CLIENT)
	public IIcon getFrameIcon() {
		return this.frameOverlay;
	}

	@Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		if(this.overlay) return super.colorMultiplier(world, x, y, z);
		TileEntityCamoDispenser dispenser = (TileEntityCamoDispenser) world.getTileEntity(x, y, z);

		if(dispenser == null || dispenser.getBlock() == null) {
			return super.colorMultiplier(world, x, y, z);
		} else {
			return dispenser.getBlock().colorMultiplier(world, x, y, z);
		}
	}

	/** {@code true} if the current rendering phase for this block is the overlay. */
	@SideOnly(Side.CLIENT)
	public boolean overlay = false;

	/** {@code true} if the currently rendering block has grass as its disguise, used for coloring */
	@SideOnly(Side.CLIENT)
	public boolean hasGrass = false;

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		if(!super.shouldSideBeRendered(world, x, y, z, side)) {
			return false;
		} else if(this.overlay) {
			ForgeDirection direction = ForgeDirection.getOrientation(side);
			int meta = world.getBlockMetadata(x - direction.offsetX, y - direction.offsetY, z - direction.offsetZ);

			return side == (meta & 7);
		} else {
			return true;
		}
	}

	@Override
	public int getRenderType() {
		return ClientProxy.renderId;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
