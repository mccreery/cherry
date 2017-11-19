package nukeduck.functionaldecor.client.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import nukeduck.functionaldecor.FunctionalDecor;
import nukeduck.functionaldecor.block.TileEntityDecor;

public class RendererRecycleBin extends DecorRenderer {
	private final ResourceLocation texture = new ResourceLocation(FunctionalDecor.MODID, "textures/blocks/recycle_bin.png");

	@Override
	public void render(TileEntityDecor tileEntity, float partialTicks) {
		GL11.glDisable(GL11.GL_CULL_FACE);

		Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);

		final ModelBase model = new ModelBase() {
			final ModelRenderer bin;
			{
				this.bin = new ModelRenderer(this, 0, 0);
				this.bin.addBox(3.0F, 2.0F, 3.0F, 10, 14, 10);
			}

			public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float f) {
				this.bin.render(f);
			}
		};
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.5F, -0.5005F, -0.5F);
		model.render(null, 0, 0, 0, 0, 0, 0.0625F);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		NBTTagList items = tileEntity.getCustomData().getTagList("Items", 10);

		if(items != null && items.tagCount() > 0) {
			for(int i = 0; i < items.tagCount(); i++) {
				NBTTagCompound tag = items.getCompoundTagAt(i);
				NBTTagList position = tag.getTagList("Pos", 5);
				NBTTagList rotation = tag.getTagList("Rotation", 5);

				GL11.glPushMatrix();
				GL11.glTranslatef(0.3125F + 0.375F * position.func_150308_e(0), 0.875F * position.func_150308_e(1), 0.3125F + 0.375F * position.func_150308_e(2));
				GL11.glRotatef(rotation.func_150308_e(0), 1, 0, 0);
				GL11.glRotatef(rotation.func_150308_e(1), 0, 1, 0);
				GL11.glRotatef(rotation.func_150308_e(2), 0, 0, 1);

				GL11.glScalef(0.25F, 0.25F, 0.25F);

				ItemStack stack = new ItemStack(Blocks.stone);
				stack.readFromNBT(tag);
				Minecraft.getMinecraft().entityRenderer.itemRenderer.renderItem(Minecraft.getMinecraft().thePlayer, stack, 0, ItemRenderType.ENTITY);
				GL11.glPopMatrix();
			}
		}
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_CULL_FACE);
	}
}
