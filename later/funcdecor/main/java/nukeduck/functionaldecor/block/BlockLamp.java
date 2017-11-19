package nukeduck.functionaldecor.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import nukeduck.functionaldecor.client.renderers.Renderers;

public class BlockLamp extends BlockDecor {
	protected BlockLamp() {
		super(DecorBlocks.IRON_HAND, Renderers.lamp);
		this.setBlockTextureName("stone");
		this.setStepSound(Block.soundTypeMetal);
		this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.875F, 0.75F);

		this.setLightLevel(1.0F);
	}

	private final int[] metaList = new int[] {0};
	@Override
	public int[] getMetaList() {
		return this.metaList;
	}

	@Override
	public NBTTagCompound getNBTForItemMeta(int metadata) {
		return new NBTTagCompound();
	}

	@Override
	public int getItemMetaForNBT(NBTTagCompound compound) {
		return 0;
	}
}
