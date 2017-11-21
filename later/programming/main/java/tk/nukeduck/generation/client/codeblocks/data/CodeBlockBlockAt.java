package tk.nukeduck.generation.client.codeblocks.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockContainerTyped;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockBlockAt extends CodeBlock implements IBlockState {
	public CodeBlockContainerTyped<IInteger> xPos = CodeBlockContainerTyped.create(IInteger.class);
	public CodeBlockContainerTyped<IInteger> yPos = CodeBlockContainerTyped.create(IInteger.class);
	public CodeBlockContainerTyped<IInteger> zPos = CodeBlockContainerTyped.create(IInteger.class);

	private BlockState value;

	public CodeBlockBlockAt() {
		super(BlockCategory.VARIABLE, Constants.BLOCK_AT, 255);
		this.headerParts.add(new CodeBlockTranslate("blockAt"));
		this.headerParts.add(xPos);
		this.headerParts.add(yPos);
		this.headerParts.add(zPos);
	}

	@Override
	public ICodeBlock copy() {
		CodeBlockBlockAt block = new CodeBlockBlockAt();
		if(this.xPos.hasChild()) block.xPos.setChild(this.xPos.getChild().copy());
		if(this.yPos.hasChild()) block.yPos.setChild(this.yPos.getChild().copy());
		if(this.zPos.hasChild()) block.zPos.setChild(this.zPos.getChild().copy());
		return block;
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		this.xPos.evaluate(world, x, y, z);
		this.yPos.evaluate(world, x, y, z);
		this.zPos.evaluate(world, x, y, z);
		int i = xPos.getChildTyped().getInteger(), j = yPos.getChildTyped().getInteger(), k = zPos.getChildTyped().getInteger();

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

	@Override
	public String getUnlocalizedName() {
		return "blockAt";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		CodeBlockBlockAt block = new CodeBlockBlockAt();
		if(tag.hasKey("X")) block.xPos.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("X")));
		if(tag.hasKey("Y")) block.yPos.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("Y")));
		if(tag.hasKey("Z")) block.zPos.setChild(ICodeBlock.loadFromNBT(tag.getCompoundTag("Z")));
		return block;
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = new NBTTagCompound();
		if(this.xPos.hasChild()) tag.setTag("X", this.xPos.getChild().writeToNBT());
		if(this.yPos.hasChild()) tag.setTag("Y", this.yPos.getChild().writeToNBT());
		if(this.zPos.hasChild()) tag.setTag("Z", this.zPos.getChild().writeToNBT());
		return tag;
	}

	@Override
	public int getBlockCount() {
		int length = 1;
		if(this.xPos.hasChild()) length += this.xPos.getChild().getBlockCount();
		if(this.yPos.hasChild()) length += this.yPos.getChild().getBlockCount();
		if(this.zPos.hasChild()) length += this.zPos.getChild().getBlockCount();
		return length;
	}
}
