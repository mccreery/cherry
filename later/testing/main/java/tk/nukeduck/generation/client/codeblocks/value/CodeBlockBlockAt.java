package tk.nukeduck.generation.client.codeblocks.value;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;

public class CodeBlockBlockAt extends CodeBlock implements IBlockState {
	public CodeBlockContainerTyped<IInteger> xPos = CodeBlockContainerTyped.create(IInteger.class);
	public CodeBlockContainerTyped<IInteger> yPos = CodeBlockContainerTyped.create(IInteger.class);
	public CodeBlockContainerTyped<IInteger> zPos = CodeBlockContainerTyped.create(IInteger.class);

	private BlockState value;

	public CodeBlockBlockAt() {
		super(BlockType.VALUE, 255);
		this.headerParts.add(new CodeBlockTranslate("blockAt"));
		this.headerParts.add(xPos);
		this.headerParts.add(yPos);
		this.headerParts.add(zPos);
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		this.xPos.evaluate(world, x, y, z);
		this.yPos.evaluate(world, x, y, z);
		this.zPos.evaluate(world, x, y, z);
		int i = xPos.get().getInteger(), j = yPos.get().getInteger(), k = zPos.get().getInteger();

		this.value = new BlockState(
			world.getBlock(i, j, k), 
			world.getBlockMetadata(i, j, k));
	}

	@Override
	public BlockState getBlockState() {
		return this.value;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ZERO;
	}
}
