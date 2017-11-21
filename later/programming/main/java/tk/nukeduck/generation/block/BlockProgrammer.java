package tk.nukeduck.generation.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tk.nukeduck.generation.Generation;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockProgrammer extends Block implements ITileEntityProvider {
	protected IIcon frontIcon, sideIcon, backIcon;

	public BlockProgrammer() {
		super(Material.iron);
		this.setCreativeTab(Generation.COMPUTER_TAB);
		this.setBlockTextureName("alchemy:programmer_top");
		this.setBlockName("programmer");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityProgrammer();
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if(((TileEntityProgrammer) world.getTileEntity(x, y, z)).isUseableByPlayer(player)) {
			player.openGui(Generation.instance, 0, world, x, y, z);
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		super.registerBlockIcons(register);
		this.frontIcon = register.registerIcon("alchemy:programmer_front");
		this.sideIcon = register.registerIcon("alchemy:programmer_side");
		this.backIcon = register.registerIcon("alchemy:programmer_back");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if(side < 2) {
			return super.getIcon(side, meta);
		} else {
			ForgeDirection facing = ForgeDirection.getOrientation(2 + meta);
			if(side == facing.ordinal()) {
				return this.frontIcon; 
			} else if(side == facing.getOpposite().ordinal()) {
				return this.backIcon;
			}
			return this.sideIcon;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int direction = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		if(direction == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);
		} else if(direction == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		} else if(direction == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
		} else {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
	}
}
