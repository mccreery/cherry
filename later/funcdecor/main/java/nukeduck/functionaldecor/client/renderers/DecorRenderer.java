package nukeduck.functionaldecor.client.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import nukeduck.functionaldecor.block.TileEntityDecor;

public abstract class DecorRenderer {
	public abstract void render(TileEntityDecor tileEntity, float partialTicks);

	public void itemTransform(ItemRenderType type, TileEntityDecor tileEntity) {
		float h = (float) (tileEntity.blockType.getBlockBoundsMaxY() - tileEntity.blockType.getBlockBoundsMinY());
		float a = (float) (tileEntity.blockType.getBlockBoundsMaxX() - tileEntity.blockType.getBlockBoundsMinX());
		float b = (float) (tileEntity.blockType.getBlockBoundsMaxZ() - tileEntity.blockType.getBlockBoundsMinZ());

		float f = 1.0F / (a > b ? (h > a ? h : a) : (h > b ? h : b));

		GL11.glTranslatef(0.0F, -h, 0.0F);
		GL11.glScalef(f, f, f);
		GL11.glTranslatef(0.0F, h, 0.0F);

		if(type == ItemRenderType.ENTITY) {
			GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
		}

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glRotatef(-90, 0, 1, 0);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
	}
}
