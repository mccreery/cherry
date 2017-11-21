package tk.nukeduck.generation.client.codeblocks;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.Generation;
import tk.nukeduck.generation.client.codeblocks.data.BlockState;
import tk.nukeduck.generation.client.codeblocks.data.IBlockState;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockBlock extends ICodeBlock implements IBlockState {
	Block block;
	int metadata;

	public static final int SIZE = 20;

	public CodeBlockBlock(Block block, int metadata) {
		super(BlockCategory.DATA, Constants.BLOCK, 49);
		this.block = block;
		this.metadata = metadata;
	}

	@Override
	public ICodeBlock copy() {
		return new CodeBlockBlock(this.block, this.metadata);
	}

	@Override
	public boolean pickUpBlock(int x, int y, int mouseX, int mouseY, BlockStack clipboard, boolean duplicate) {
		if(mouseX >= x && mouseY >= y && mouseX < x + 20 && mouseY < y + 20) {
			clipboard.append(this);
			return true;
		}
		return false;
	}

	@Override
	public void render(int x, int y) {
		this.drawDefaultRect(x, y, x + this.getWidth(), y + this.getHeight(), this.category);

		RenderItem render = RenderItem.getInstance();
		RenderHelper.enableGUIStandardItemLighting();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		short short1 = 240;
		short short2 = 240;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)short1 / 1.0F, (float)short2 / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		render.renderItemAndEffectIntoGUI(Generation.mc.fontRenderer, Generation.mc.getTextureManager(), new ItemStack(block, 1, metadata), x + 2, y + 2);
		RenderHelper.disableStandardItemLighting();
	}

	@Override
	public void recalculateSize() {}
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
	public boolean placeBlock(int x, int y, int mouseX, int mouseY, BlockStack clipboard) {
		return false;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}

	@Override
	public String getUnlocalizedName() {
		return "block";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return new CodeBlockBlock(Block.getBlockById(tag.getByte("Id")), tag.getByte("Meta"));
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setByte("Id", (byte) Block.getIdFromBlock(this.block));
		tag.setByte("Meta", (byte) this.metadata);
		return tag;
	}

	@Override
	public int getBlockCount() {
		return 1;
	}
}
