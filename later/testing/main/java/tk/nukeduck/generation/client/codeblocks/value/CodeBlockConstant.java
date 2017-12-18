package tk.nukeduck.generation.client.codeblocks.value;

import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.CodeBlockText;

public class CodeBlockConstant extends CodeBlock implements IInteger, IString {
	private ConstantType constant;
	int value;

	public CodeBlockConstant(ConstantType constant) {
		super(BlockType.VALUE, 255);
		this.constant = constant;
		this.headerParts.add(new CodeBlockText(this.constant.name()));
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		switch(this.constant) {
			case X:
				this.value = x;
				break;
			case Y:
				this.value = y;
				break;
			case Z:
				this.value = z;
				break;
			case BIOME:
				this.value = world.getBiomeGenForCoords(x, z).biomeID;
				break;
		}
	}

	@Override
	public Integer getInteger() {
		return this.value;
	}

	@Override
	public String getString() {
		return String.valueOf(this.getInteger());
	}

	public enum ConstantType {
		X, Y, Z,
		BIOME;
	}

	@Override
	public BlockLevel getLevel() {
		return BlockLevel.ONE;
	}
}
