package nukeduck.functionaldecor.client.renderers;

import org.lwjgl.opengl.GL11;

import javafx.scene.chart.PieChart.Data;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import nukeduck.functionaldecor.FunctionalDecor;
import nukeduck.functionaldecor.block.BlockPeripheral;
import nukeduck.functionaldecor.block.BlockPeripheral.Type;
import nukeduck.functionaldecor.block.TileEntityDecor;
import nukeduck.functionaldecor.client.renderers.model.ModelKeyboard;
import nukeduck.functionaldecor.client.renderers.model.ModelMicrophone;
import nukeduck.functionaldecor.client.renderers.model.ModelMonitor;
import nukeduck.functionaldecor.client.renderers.model.ModelMouse;
import nukeduck.functionaldecor.util.BoundingBox;

public class RendererPeripheral extends DecorRenderer {
	private final ModelMicrophone microphone = new ModelMicrophone();
	public final ResourceLocation microphoneTexture = new ResourceLocation(FunctionalDecor.MODID, "textures/blocks/microphone.png");

	private final ModelMouse mouse = new ModelMouse();
	public final ResourceLocation mouseTexture = new ResourceLocation(FunctionalDecor.MODID, "textures/blocks/mouse.png");

	private final ModelMonitor monitor = new ModelMonitor();
	public final ResourceLocation monitorTexture = new ResourceLocation(FunctionalDecor.MODID, "textures/blocks/monitor.png");

	private final ModelKeyboard keyboard = new ModelKeyboard();
	private final ModelTower tower = new ModelTower();

	@Override
	public void itemTransform(ItemRenderType type, TileEntityDecor tileEntity) {
		BlockPeripheral block = (BlockPeripheral) tileEntity.blockType;
		BoundingBox._join(block.getBoundsBasedOnType(BlockPeripheral.Type.fromId(tileEntity.getCustomData().getByte("ID"))))
				.applyTo(block);
		super.itemTransform(type, tileEntity);
	}

	public void renderType(Type type, TileEntityDecor tileEntity, float partialTicks) {
		switch(type) {
		case MICROPHONE:
			Minecraft.getMinecraft().getTextureManager().bindTexture(this.microphoneTexture);
	
			this.microphone.setRotationAngle(tileEntity.getCustomData().getByte("Rotation"));
	
			GL11.glPushMatrix();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			this.microphone.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
		case MOUSE:
			Minecraft.getMinecraft().getTextureManager().bindTexture(this.mouseTexture);
	
			GL11.glPushMatrix();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			this.mouse.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
		case MONITOR:
			int extras = tileEntity.getCustomData().getByte("Extras");
			Type extra = null;

			for(Type test : type.values()) {
				int checkBit = 0x1 << test.ordinal();
				if((extras & checkBit) == checkBit) {
					extra = test;
					break;
				}
			}

			if(extra != null) {
				GL11.glTranslatef(0.0F, 0.0F, -3.0F / 16.0F);
				this.renderType(extra, tileEntity, partialTicks);
				GL11.glTranslatef(0.0F, 0.0F, 8.0F / 16.0F);
			}

			Minecraft.getMinecraft().getTextureManager().bindTexture(this.monitorTexture);

			GL11.glPushMatrix();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			this.monitor.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();

			int monitorID = tileEntity.getCustomData().getByte("Channel");
			double u = 0.5625 + 0.21875 * (monitorID & 1);
			double v = 0.25 * (monitorID / 2);

			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);

			Tessellator tes = Tessellator.instance;
			tes.startDrawingQuads();
			tes.addVertexWithUV(0.9375, 0.25, 0.437, u, v + 0.25);
			tes.addVertexWithUV(0.0625, 0.25, 0.437, u + 0.21875, v + 0.25);
			tes.addVertexWithUV(0.0625, 0.75, 0.437, u + 0.21875, v);
			tes.addVertexWithUV(0.9375, 0.75, 0.437, u, v);
			tes.draw();

			GL11.glDisable(GL11.GL_BLEND);
			break;
		case KEYBOARD:
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(FunctionalDecor.MODID, "textures/blocks/keyboard.png"));

			GL11.glPushMatrix();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			this.keyboard.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
		case TOWER:
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(FunctionalDecor.MODID, "textures/blocks/computer.png"));

			GL11.glPushMatrix();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			this.tower.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
		default:
			break;
	}
	}

	@Override
	public void render(TileEntityDecor tileEntity, float partialTicks) {
		BlockPeripheral.Type type = BlockPeripheral.Type.fromId(tileEntity.getCustomData().getByte("ID"));
		this.renderType(type, tileEntity, partialTicks);
	}
}
