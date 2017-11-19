package nukeduck.functionaldecor.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import nukeduck.functionaldecor.block.BlockDecor;

public class ItemBlockDecor extends ItemBlock {
	private final BlockDecor block;

	public ItemBlockDecor(Block block) {
		super(block);

		if(!(block instanceof BlockDecor)) throw new RuntimeException();
		this.block = (BlockDecor) block;

		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		BlockDecor block = (BlockDecor) Block.getBlockFromItem(item);

		for(int i : block.getMetaList()) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return this.block.getUnlocalizedName(itemStack.getItemDamage());
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		Block block = world.getBlock(x, y, z);

		if(block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 0x0F) < 2) {
			side = 1;
		} else if(block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, x, y, z)) {
			switch(side) {
				case 0: --y; break;
				case 1: ++y; break;
				case 2: --z; break;
				case 3: ++z; break;
				case 4: --x; break;
				case 5: ++x; break;
			}
		}
		if(!world.canPlaceEntityOnSide(this.field_150939_a, x, y, z, false, side, player, itemStack)
				|| !player.canPlayerEdit(x, y, z, side, itemStack)
				|| (y == 255 && this.field_150939_a.getMaterial().isSolid())
				|| itemStack.stackSize == 0) {
			return false;
		}

		int baseMeta = this.getMetadata(itemStack.getItemDamage());
		int placeMeta = this.field_150939_a.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, baseMeta);

		if(this.placeBlockAt(this.block, itemStack, player, world, x, y, z, side, hitX, hitY, hitZ, placeMeta)) {
			world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getPitch() * 0.8F);
			--itemStack.stackSize;
		}
		return true;
	}

	public boolean placeBlockAt(BlockDecor block, ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if(!world.setBlock(x, y, z, block, metadata, 3))
			return false;

		if(world.getBlock(x, y, z) == block) {
			block.onBlockPlacedBy(world, x, y, z, player, stack);
			block.onPostBlockPlaced(world, x, y, z, metadata);
		}
		return true;
	}
}
