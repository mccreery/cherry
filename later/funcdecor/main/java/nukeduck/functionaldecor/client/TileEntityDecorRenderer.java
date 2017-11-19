package nukeduck.functionaldecor.client;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslated;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import nukeduck.functionaldecor.block.BlockDecor;
import nukeduck.functionaldecor.block.TileEntityDecor;

public class TileEntityDecorRenderer extends TileEntitySpecialRenderer {
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
		TileEntityDecor decor = (TileEntityDecor) tileEntity;
		glPushMatrix();

		glTranslated(x, y, z);
		glTranslated(0.5, 0.0, 0.5);
		glRotatef(-tileEntity.getBlockMetadata() / 16.0F * 360.0F, 0, 1, 0);
		glTranslated(-0.5, 0.0, -0.5);

		((BlockDecor) tileEntity.blockType).getRenderer().render(decor, partialTicks);
		glPopMatrix();
	}
}
