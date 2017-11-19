package nukeduck.coinage.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nukeduck.coinage.Coinage;
import nukeduck.coinage.block.tileentity.TileEntityCoinPile;
import nukeduck.coinage.item.ItemCoin;
import nukeduck.coinage.registry.ItemRegister;
import nukeduck.coinage.util.WorldUtil;

public class BlockCoinPile extends Block implements ITileEntityProvider {
	public BlockCoinPile() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCoinPile();
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 2;
	}

	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
		return super.getSelectedBoundingBox(worldIn, pos);
	}

	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(playerIn.getHeldItem() == null || !(playerIn.getHeldItem().getItem() instanceof ItemCoin)) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te != null && te instanceof TileEntityCoinPile) {
				TileEntityCoinPile pile = (TileEntityCoinPile) te;
				for(int i = 2; i >= 0; i--) {
					if(pile.getCoin(i) > 0) {
						pile.setCoin(i, pile.getCoin(i) - 1);
						if(pile.getCoin(0) + pile.getCoin(1) + pile.getCoin(2) == 0) worldIn.setBlockToAir(pos);
						if(!worldIn.isRemote) {
							ArrayList<EntityItem> items = new ArrayList<EntityItem>();
							WorldUtil.createCoins(ItemCoin.getCoinMultiplier(i), worldIn, pos.getX() + Coinage.random.nextDouble(), pos.getY() - 1 + Coinage.random.nextDouble(), pos.getZ() + Coinage.random.nextDouble(), items, false);
							for(EntityItem item : items) {
								worldIn.spawnEntityInWorld(item);
							}
						}
						return true;
					}
				}
			}
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if(te != null && te instanceof TileEntityCoinPile) {
			TileEntityCoinPile pile = (TileEntityCoinPile) world.getTileEntity(pos);
			for(int i = 2; i >= 0; i--) {
				if(pile.getCoin(i) > 0) {
					return new ItemStack(ItemRegister.coin, 1, i);
				}
			}
		}
		return new ItemStack(ItemRegister.coin, 1, 0);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(hasTileEntity(state)) {
			if(worldIn.getTileEntity(pos) instanceof TileEntityCoinPile) {
				TileEntityCoinPile pile = (TileEntityCoinPile) worldIn.getTileEntity(pos);
				int[] coins = pile.getCoins();
				for(int i = 0; i < 3; i++) {
					while(coins[i] > 0) {
						EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemRegister.coin, Math.min(64, coins[i]), i));
						item.posX += Coinage.random.nextDouble();
						item.posY += Coinage.random.nextDouble();
						item.posZ += Coinage.random.nextDouble();
						item.setDefaultPickupDelay();
						item.setVelocity(Coinage.random.nextDouble() * 0.1, Coinage.random.nextDouble() * 0.2, Coinage.random.nextDouble() * 0.1);
						worldIn.spawnEntityInWorld(item);
						coins[i] -= 64;
					}
				}
			}
			worldIn.removeTileEntity(pos);
		}
	}

	/*public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		System.out.println("ihauh");
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		TileEntity te = world.getTileEntity(pos);
		if(te != null && te instanceof TileEntityCoinPile) {
			TileEntityCoinPile pile = (TileEntityCoinPile) te;
			for(int i = 0; i < 3; i++) {
				int total = pile.getCoin(i);
				while(total > 0) {
					items.add(new ItemStack(ItemRegister.coin, Math.min(64, total), pile.getCoin(i)));
					total -= 64;
				}
			}
			//int coins = ((TileEntityCoinPile) te).getTotalCoins();
		}
		return items;
	}*/
}
