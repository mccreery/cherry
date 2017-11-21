package tk.nukeduck.generation.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import tk.nukeduck.generation.block.BlockCamoDispenser;
import tk.nukeduck.generation.block.TileEntityCamoDispenser;
import tk.nukeduck.generation.network.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class CamoRenderer implements ISimpleBlockRenderingHandler {
	private static final double STEP = 1.0 / 4096.0;

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;

		if (renderer.useInventoryTint) {
			int j = block.getRenderColor(3);
			float f1 = (float)(j >> 16 & 255) / 255.0F;
			float f2 = (float)(j >> 8 & 255) / 255.0F;
			float f3 = (float)(j & 255) / 255.0F;
			GL11.glColor4f(f1, f2, f3, 1.0F);
		}

		BlockCamoDispenser dispenser = (BlockCamoDispenser) block;
		block.setBlockBoundsForItemRender();
		renderer.setRenderBounds(0.0625, 0.0625, 0.0625, 0.9375, 0.9375, 0.9375);

		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, 3));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, 3));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, 3));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, 3));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, 3));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, 3));
		tessellator.draw();

		renderer.setRenderBoundsFromBlock(block);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, -STEP, 0.0D, dispenser.getFrameIcon());
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, STEP, 0.0D, dispenser.getFrameIcon());
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, -STEP, dispenser.getFrameIcon());
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, STEP, dispenser.getFrameIcon());
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, -STEP, 0.0D, 0.0D, dispenser.getFrameIcon());
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, STEP, 0.0D, 0.0D, dispenser.getFrameIcon());
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		int meta = world.getBlockMetadata(x, y, z);
		int side = meta & 7;

		TileEntity entity = world.getTileEntity(x, y, z);
		if(entity == null || !(entity instanceof TileEntityCamoDispenser) || ((TileEntityCamoDispenser) entity).getBlock() == null) {
			renderer.setRenderBounds(0.0625, 0.0625, 0.0625, 0.9375, 0.9375, 0.9375);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

			renderer.setOverrideBlockTexture(((BlockCamoDispenser) block).getFrameIcon());
			double max = 1.0 + STEP;
			renderer.renderStandardBlock(block, x, y, z);
			renderer.clearOverrideBlockTexture();
		} else {
			BlockCamoDispenser blockDispenser = (BlockCamoDispenser) block;
			TileEntityCamoDispenser dispenser = (TileEntityCamoDispenser) entity;
			Block subBlock = dispenser.getBlock();
			int subMeta = dispenser.getMetadata();

			if(subBlock.getRenderType() == 31) { // Log
				int i1 = subMeta & 12;

				if (i1 == 4) {
					renderer.uvRotateEast = 1;
					renderer.uvRotateWest = 1;
					renderer.uvRotateTop = 1;
					renderer.uvRotateBottom = 1;
				} else if (i1 == 8) {
					renderer.uvRotateSouth = 1;
					renderer.uvRotateNorth = 1;
				}

				renderer.renderStandardBlock(block, x, y, z);

				renderer.uvRotateSouth = 0;
				renderer.uvRotateEast = 0;
				renderer.uvRotateWest = 0;
				renderer.uvRotateNorth = 0;
				renderer.uvRotateTop = 0;
				renderer.uvRotateBottom = 0;
			} else if(subBlock.getRenderType() == 39) { // Quartz
				if(subMeta == 3) {
					renderer.uvRotateEast = 1;
					renderer.uvRotateWest = 1;
					renderer.uvRotateTop = 1;
					renderer.uvRotateBottom = 1;
				} else if(subMeta == 4) {
					renderer.uvRotateSouth = 1;
					renderer.uvRotateNorth = 1;
				}

				renderer.renderStandardBlock(block, x, y, z);

				renderer.uvRotateSouth = 0;
				renderer.uvRotateEast = 0;
				renderer.uvRotateWest = 0;
				renderer.uvRotateNorth = 0;
				renderer.uvRotateTop = 0;
				renderer.uvRotateBottom = 0;
			} else {
				if(renderer.getBlockIcon(subBlock).getIconName().equals("grass_top")) {
					blockDispenser.hasGrass = true;
					renderer.renderStandardBlock(block, x, y, z);
					blockDispenser.hasGrass = false;
				} else {
					renderer.renderStandardBlock(block, x, y, z);
				}
			}

			renderer.setOverrideBlockTexture(blockDispenser.getOverlayIcon(side, meta));
			renderer.setRenderBounds(-STEP, -STEP, -STEP, 1.0 + STEP, 1.0 + STEP, 1.0 + STEP);
			blockDispenser.overlay = true;
			renderer.renderStandardBlock(block, x, y, z);
			blockDispenser.overlay = false;
			renderer.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
			renderer.clearOverrideBlockTexture();
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return ClientProxy.renderId;
	}
}
