package nukeduck.functionaldecor.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;
import nukeduck.functionaldecor.block.BlockDecor;
import nukeduck.functionaldecor.block.TileEntityDecor;

public class ItemRendererDecor implements IItemRenderer {
	private final TileEntitySpecialRenderer renderer;
	private final TileEntityDecor tileEntity;

	public ItemRendererDecor(TileEntitySpecialRenderer renderer, TileEntityDecor tileEntity) {
		this.renderer = renderer;
		this.tileEntity = tileEntity;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		this.tileEntity.blockType = Block.getBlockFromItem(item.getItem());
		this.tileEntity.blockMetadata = 0;
		this.tileEntity.setWorldObj(Minecraft.getMinecraft().theWorld);

		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("x", 0);
		compound.setInteger("y", 0);
		compound.setInteger("z", 0);
		BlockDecor block = (BlockDecor) Block.getBlockFromItem(item.getItem());
		compound.setTag(TileEntityDecor.DATA_KEY, block.getNBTForItemMeta(item.getItemDamage()));

		this.tileEntity.readFromNBT(compound);
		((BlockDecor) this.tileEntity.blockType).getRenderer().itemTransform(type, tileEntity);
		this.renderer.renderTileEntityAt(this.tileEntity, 0.0, 0.0, 0.0, 0.0F);
	}
}
