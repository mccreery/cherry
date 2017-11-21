package tk.nukeduck.lightsaber.block;

import java.util.List;

import org.lwjgl.input.Keyboard;

import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.block.tileentity.TileEntityRefillUnit;
import tk.nukeduck.lightsaber.util.Strings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockRefillUnit extends BlockContainer {
	/** Constructor. Sets variables relating to how the block interacts with the world. */
	public BlockRefillUnit(Material par2Material) {
		super(par2Material);
		this.setCreativeTab(Lightsaber.lightsaberTab);
		this.setBlockTextureName(Strings.MOD_ID + ":charger");
		this.setBlockBounds(0.125f, 0.0f, 0.125f, 0.875f, 0.875f, 0.875f);
		this.setCreativeTab(Lightsaber.lightsaberTab);
		this.setHardness(5.0F);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityRefillUnit(metadata);
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
	
	/** {@inheritDoc}<br/>
	 * Opens the GUI. */
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		TileEntityRefillUnit tileentity = (TileEntityRefillUnit) par1World.getTileEntity(par2, par3, par4);
		par5EntityPlayer.openGui(
			Lightsaber.instance, 
			0, 
			par1World, 
			par2, par3, par4);
		return true;
	}
	
	/** Drops items into the world. */
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
		TileEntityRefillUnit tileEntity = (TileEntityRefillUnit) world.getTileEntity(x, y, z);
		
		if (tileEntity != null) {
			for (int i = 0; i < tileEntity.getSizeInventory(); i++) {
				ItemStack itemstack = tileEntity.getStackInSlot(i);
				
				if (itemstack != null) {
					float f = Lightsaber.random.nextFloat() * 0.8F + 0.1F;
					float f1 = Lightsaber.random.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;
					
					for (float f2 = Lightsaber.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem)) {
						int j1 = Lightsaber.random.nextInt(21) + 10;
						
						if (j1 > itemstack.stackSize) {
							j1 = itemstack.stackSize;
						}
						
						itemstack.stackSize -= j1;
						entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (double)((float) Lightsaber.random.nextGaussian() * f3);
						entityitem.motionY = (double)((float) Lightsaber.random.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = (double)((float) Lightsaber.random.nextGaussian() * f3);
						
						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
						}
					}
				}
			}
			
			world.func_147453_f(x, y, z, block);
		}
		
		super.breakBlock(world, x, y, z, block, p_149749_6_);
	}
	
	/**
	 * Called when the block is placed in the world.
	 */
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
		int dir = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, dir, 2);
	}
}