package tk.nukeduck.generation.client.codeblocks;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import tk.nukeduck.generation.client.codeblocks.ICodeBlock.BlockLevel;
import tk.nukeduck.generation.client.codeblocks.value.BlockState;
import tk.nukeduck.generation.client.codeblocks.value.IBlockState;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CodeBlockBlock extends ICodeBlock implements IBlockState {
	Block block;
	int metadata;

	public static final int SIZE = 20;

	public CodeBlockBlock(Block block, int metadata) {
		super(BlockType.VALUE, 18);
		this.block = block;
		this.metadata = metadata;
	}

	@Override
	public boolean populateClipboard(int x, int y, int mouseX, int mouseY, List<ICodeBlock> clipboard, boolean duplicate) {
		if(mouseX >= x && mouseY >= y &&
				mouseX < x + 20 && mouseY < y + 20) {
			clipboard.add(this);
			return true;
		}
		return false;
	}

	@Override
	public void render(int x, int y) {
		this.drawRect(x, y, x + this.getWidth(), y + this.getHeight(), this.type.getColor());
		this.drawRect(x, y, x + this.getWidth(), y + 1, this.type.getHighlight());

		RenderItem render = RenderItem.getInstance();
		RenderHelper.enableGUIStandardItemLighting();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		short short1 = 240;
		short short2 = 240;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)short1 / 1.0F, (float)short2 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		render.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), new ItemStack(block, 1, metadata), x + 2, y + 2);
		RenderHelper.disableStandardItemLighting();
	}

	@Override
	public void validate() {}
	@Override
	public int getWidth() {
		return SIZE;
	}
	@Override
	public int getHeight() {
		return SIZE;
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {}

	@Override
	public BlockState getBlockState() {
		return new BlockState(block, metadata);
	}

	@Override
	public boolean placeClipboard(int x, int y, int mouseX, int mouseY, List<ICodeBlock> clipboard) {
		return false;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}
}
