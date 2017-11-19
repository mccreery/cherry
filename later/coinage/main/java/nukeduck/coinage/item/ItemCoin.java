package nukeduck.coinage.item;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nukeduck.coinage.block.tileentity.TileEntityCoinPile;
import nukeduck.coinage.client.render.TileEntityCoinPileRenderer;
import nukeduck.coinage.entity.EntityItemCoin;
import nukeduck.coinage.registry.BlockRegister;
import nukeduck.coinage.util.BlockItemName;

public class ItemCoin extends ItemBlock {
	public final BlockItemName[] unlocalizedNames = {
		new BlockItemName("coin", "copper"),
		new BlockItemName("coin", "silver"),
		new BlockItemName("coin", "gold")
	};

	public ItemCoin(Block block) {
		super(block);
		this.setMaxStackSize(99);
		this.setHasSubtypes(true);

		for(int i = 0; i < 3; i++) {
			TileEntityCoinPileRenderer.coinStacks[i] = new ItemStack(this, 1, i);
		}
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
		subItems.add(new ItemStack(itemIn, 1, 0));
		subItems.add(new ItemStack(itemIn, 1, 1));
		subItems.add(new ItemStack(itemIn, 1, 2));
	}

	public String getUnlocalizedName(ItemStack stack) {
		return "item." + unlocalizedNames[stack.getItemDamage() >= unlocalizedNames.length ? 0 : stack.getItemDamage()].getCamelCase();
	}

	public static int getCoinMultiplier(ItemStack stack) {
		if(!(stack.getItem() instanceof ItemCoin)) return 0;
		return getCoinMultiplier(stack.getItemDamage());
	}

	public static int getCoinMultiplier(int metadata) {
		if(metadata == 0) return 1;
		if(metadata == 1) return 100;
		if(metadata == 2) return 10000;
		return 0;
	}

	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}

	private static final Random random = new Random();

	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		EntityItem entityitem = new EntityItemCoin(world, location.posX, location.posY, location.posZ, itemstack, false);
		location.writeToNBT(entityitem.getEntityData());
		entityitem.readFromNBT(entityitem.getEntityData());
		entityitem.motionX = location.motionX;
		entityitem.motionY = location.motionY;
		entityitem.motionZ = location.motionZ;
		return entityitem;
	}
	
	private final Block block = BlockRegister.coinPile;
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);
		if(te != null && te instanceof TileEntityCoinPile) {
			TileEntityCoinPile pile = (TileEntityCoinPile) te;
			int coins = 1;
			if(playerIn.isSneaking()) coins = Math.min(stack.stackSize, 64);
			
			pile.setCoin(stack.getItemDamage(), pile.getCoin(stack.getItemDamage()) + coins);

			worldIn.playSoundEffect((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F, this.block.stepSound.getFrequency() * 0.8F);
			stack.stackSize -= coins;
			return true;
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
	}
	
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		if(!world.setBlockState(pos, newState, 3)) return false;

		IBlockState state = world.getBlockState(pos);
		if(state.getBlock() == this.block) {
			if(!setTileEntityNBT(world, pos, stack, player)) {
				TileEntity te = world.getTileEntity(pos);
				if(te != null && te instanceof TileEntityCoinPile) {
					TileEntityCoinPile pile = (TileEntityCoinPile) te;
					pile.setCoin(stack.getItemDamage(), 1);
				}
			}
			this.block.onBlockPlacedBy(world, pos, state, player, stack);
		}

		return true;
	}
}
