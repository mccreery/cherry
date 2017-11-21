package tk.nukeduck.alchemy;

import java.util.List;

import cpw.mods.fml.common.Mod.EventHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAlchemyPot extends BlockContainer {
	public BlockAlchemyPot(Material par2Material) {
		super(par2Material);
		this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB aabb, List par6List, Entity par7Entity) {
		setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.125f, 0.875f, 0.9375f);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, aabb, par6List, par7Entity);
		setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.125f);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, aabb, par6List, par7Entity);
		
		this.setBlockBounds(0.875f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, aabb, par6List, par7Entity);
		this.setBlockBounds(0.0625f, 0.0f, 0.875f, 0.9375f, 0.875f, 0.9375f);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, aabb, par6List, par7Entity);
		
		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375f, 0.3125F, 0.9375f);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, aabb, par6List, par7Entity);
		
		this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
	}
	
	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
		return new TileEntityAlchemyPot();
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
		int l = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		
		switch (l) {
			case 0:
				world.setBlockMetadataWithNotify(i, j, k, 2, 3);
				break;
			case 1:
				world.setBlockMetadataWithNotify(i, j, k, 5, 3);
				break;
			case 2:
				world.setBlockMetadataWithNotify(i, j, k, 3, 3);
				break;
			case 3:
				world.setBlockMetadataWithNotify(i, j, k, 4, 3);
				break;
		}
	}
}