package tk.nukeduck.generation.client;

import java.util.Calendar;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import tk.nukeduck.generation.block.TileEntityMimic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityMimicRenderer extends TileEntitySpecialRenderer {
	private static final ResourceLocation CHRISTMAS = new ResourceLocation("textures/entity/chest/christmas.png");
	private static final ResourceLocation NORMAL = new ResourceLocation("textures/entity/chest/normal.png");
	private ModelChest model = new ModelChest();
	private boolean useChristmas;

	public TileEntityMimicRenderer() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DATE);
		this.useChristmas = calendar.get(Calendar.MONTH) == Calendar.DECEMBER && day >= 24 && day <= 26;
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_) {
		if(!(tileEntity instanceof TileEntityMimic)) return;
		TileEntityMimic mimic = (TileEntityMimic) tileEntity;

		this.bindTexture(this.useChristmas ? CHRISTMAS : NORMAL);

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

		GL11.glRotatef((tileEntity.getBlockMetadata() - 2) * 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		model.renderAll();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
