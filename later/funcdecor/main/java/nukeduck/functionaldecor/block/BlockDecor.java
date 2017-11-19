package nukeduck.functionaldecor.block;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import nukeduck.functionaldecor.client.renderers.DecorRenderer;
import nukeduck.functionaldecor.client.renderers.Renderers;

public abstract class BlockDecor extends Block implements ITileEntityProvider {
	private byte renderId;

	protected BlockDecor(Material material, byte renderId) {
		super(material);
		this.renderId = renderId;
	}

	public String getUnlocalizedName(int metadata) {
		return super.getUnlocalizedName();
	}
	public abstract int[] getMetaList();
	public abstract NBTTagCompound getNBTForItemMeta(int metadata);
	public abstract int getItemMetaForNBT(NBTTagCompound compound);

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int metadata) {
		TileEntity tileEntity;
		if((tileEntity = world.getTileEntity(x, y, z)) == null) return;

		NBTTagCompound base = new NBTTagCompound();
		tileEntity.writeToNBT(base);
		base.setTag(TileEntityDecor.DATA_KEY, this.getNBTForItemMeta(metadata));

		tileEntity.readFromNBT(base);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		if(willHarvest) {
			return true;
		}
		return super.removedByPlayer(world, player, x, y, z, willHarvest);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		super.harvestBlock(world, player, x, y, z, meta);
		world.setBlockToAir(x, y, z);
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		TileEntityDecor tileEntity;
		if((tileEntity = (TileEntityDecor) world.getTileEntity(x, y, z)) == null) {
			return this.damageDropped(world.getBlockMetadata(x, y, z));
		}

		return this.getItemMetaForNBT(tileEntity.getCustomData());
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		int count = quantityDropped(metadata, fortune, world.rand);
		for(int i = 0; i < count; i++) {
			Item item = getItemDropped(metadata, world.rand, fortune);

			if(item != null) {
				ret.add(new ItemStack(item, 1, this.getDamageValue(world, x, y, z)));
			}
		}
		return ret;
	}

	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta) {
		return true;
	}

	public DecorRenderer getRenderer() {
		return Renderers.getRenderer(this.renderId);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityDecor();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int meta = MathHelper.floor_double((double)(Minecraft.getMinecraft().thePlayer.rotationYaw * 16.0F / 360.0F) + 0.5D) & 0xF;
		world.setBlockMetadataWithNotify(x, y, z, meta, 2);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return false;
	}
}
