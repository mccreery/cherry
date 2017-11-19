package nukeduck.functionaldecor.block;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import nukeduck.functionaldecor.FunctionalDecor;
import nukeduck.functionaldecor.client.renderers.Renderers;

public class BlockRecycleBin extends BlockDecor {
	protected BlockRecycleBin() {
		super(DecorBlocks.IRON_HAND, Renderers.recycleBin);
		this.setBlockTextureName("iron_block");
		this.setStepSound(Block.soundTypeMetal);
		this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.875F, 0.8125F);
	}

	private final int[] metaList = new int[] {0};
	@Override
	public int[] getMetaList() {
		return this.metaList;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_) {
		NBTTagCompound nbt = ((TileEntityDecor) world.getTileEntity(x, y, z)).getCustomData();
		NBTTagList itemList = nbt.getTagList("Items", 10);

		for(int i = 0; i < itemList.tagCount(); i++) {
			ItemStack stack = new ItemStack(Blocks.stone);
			stack.readFromNBT(itemList.getCompoundTagAt(i));

			double f = FunctionalDecor.RANDOM.nextFloat() * 0.8F + 0.1F;
			double f1 = FunctionalDecor.RANDOM.nextFloat() * 0.8F + 0.1F;
			double f2 = FunctionalDecor.RANDOM.nextFloat() * 0.8F + 0.1F;

			EntityItem entityItem = new EntityItem(world, x + f, y + f1, z + f2, stack);
			entityItem.motionX = FunctionalDecor.RANDOM.nextGaussian() * 0.05;
			entityItem.motionY = FunctionalDecor.RANDOM.nextGaussian() * 0.05 + 0.2;
			entityItem.motionZ = FunctionalDecor.RANDOM.nextGaussian() * 0.05;
			world.spawnEntityInWorld(entityItem);
		}
		super.breakBlock(world, x, y, z, block, p_149749_6_);
	}

	private final String paperCrumple = new ResourceLocation(FunctionalDecor.MODID, "paper_crumple").toString();

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		ItemStack held = player.getHeldItem();

		if(held == null || held.stackSize == 0) {
			if(player.isSneaking()) {
				world.playSound(x + 0.5, y + 0.5, z + 0.5, paperCrumple, 1.0F, 0.625F + FunctionalDecor.RANDOM.nextFloat() * 0.25F, false);

				if(world.isRemote) return true;

				NBTTagList items = ((TileEntityDecor) world.getTileEntity(x, y, z)).getCustomData().getTagList("Items", 10);
				player.addChatMessage(new ChatComponentTranslation("tile.recycleBin.scrapped", items.tagCount()));
				while(items.tagCount() > 0) items.removeTag(0);
				return true;
			}
			return false;
		}

		if(!player.capabilities.isCreativeMode && --held.stackSize <= 0) {
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
		}
		held = held.copy();
		held.stackSize = 1;

		world.playSound(x + 0.5, y + 0.5, z + 0.5, paperCrumple, 1.0F, 0.875F + FunctionalDecor.RANDOM.nextFloat() * 0.25F, false);
		if(world.isRemote) return true;

		NBTTagCompound compound = ((TileEntityDecor) world.getTileEntity(x, y, z)).getCustomData();
		float top = compound.getFloat("Highest");

		if(top == 1.2F) return true;

		NBTTagCompound stackCompound = new NBTTagCompound();
		held.writeToNBT(stackCompound);

		NBTTagList position = new NBTTagList();
		position.appendTag(new NBTTagFloat(FunctionalDecor.RANDOM.nextFloat()));
		position.appendTag(new NBTTagFloat(Math.min(1.2F, top + FunctionalDecor.RANDOM.nextFloat() * 0.05F)));
		position.appendTag(new NBTTagFloat(FunctionalDecor.RANDOM.nextFloat()));

		NBTTagList rotation = new NBTTagList();
		rotation.appendTag(new NBTTagFloat(FunctionalDecor.RANDOM.nextFloat() * 360.0F));
		rotation.appendTag(new NBTTagFloat(FunctionalDecor.RANDOM.nextFloat() * 360.0F));
		rotation.appendTag(new NBTTagFloat(FunctionalDecor.RANDOM.nextFloat() * 360.0F));

		stackCompound.setTag("Pos", position);
		stackCompound.setTag("Rotation", rotation);

		if(position.func_150308_e(1) > top) compound.setFloat("Highest", position.func_150308_e(1));
		compound.getTagList("Items", 10).appendTag(stackCompound);

		return true;
	}

	@Override
	public NBTTagCompound getNBTForItemMeta(int metadata) {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setTag("Items", new NBTTagList());
		return compound;
	}

	@Override
	public int getItemMetaForNBT(NBTTagCompound compound) {
		return 0;
	}
}
