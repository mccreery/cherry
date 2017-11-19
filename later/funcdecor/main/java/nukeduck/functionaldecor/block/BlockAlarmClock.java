package nukeduck.functionaldecor.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nukeduck.functionaldecor.client.renderers.Renderers;

public class BlockAlarmClock extends BlockDecor {
	protected BlockAlarmClock() {
		super(DecorBlocks.IRON_HAND, Renderers.alarmClock);
		this.setBlockTextureName("stone");
		this.setStepSound(Block.soundTypeMetal);
		this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.25F, 0.8125F);
	}

	@Override
	public NBTTagCompound getNBTForItemMeta(int metadata) {
		NBTTagCompound cmpd = new NBTTagCompound();
		cmpd.setByte("Type", (byte) metadata);
		return cmpd;
	}

	@Override
	public int getItemMetaForNBT(NBTTagCompound compound) {
		return compound.getByte("Type");
	}

	private final String[] itemNames = new String[] {
		"red", "green", "blue"
	};

	@Override
	public String getUnlocalizedName(int metadata) {
		return super.getUnlocalizedName() + "." + itemNames[metadata < itemNames.length ? metadata : 0];
	}

	private final int[] metaList = new int[] {0, 1, 2};
	@Override
	public int[] getMetaList() {
		return this.metaList;
	}
}
