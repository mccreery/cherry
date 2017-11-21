package tk.nukeduck.generation.client.codeblocks.logic;

import net.minecraft.nbt.NBTTagCompound;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.IBlockState;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockBlockEqual extends CodeBlockCompare<IBlockState, IBlockState> {
	public CodeBlockBlockEqual() {
		super(IBlockState.class, IBlockState.class, Constants.EQUAL, 19);
		this.headerParts.add(0, new CodeBlockTranslate("block"));
		this.headerParts.add(2, new CodeBlockTranslate("is"));
	}

	@Override
	public ICodeBlock copy() {
		return super.copy(new CodeBlockBlockEqual());
	}

	@Override
	public boolean compare(IBlockState a, IBlockState b) {
		return a.getBlockState().block == b.getBlockState().block &&
			a.getBlockState().meta == b.getBlockState().meta;
	}

	@Override
	public String getUnlocalizedName() {
		return "equalBlock";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		return super.construct(tag, new CodeBlockBlockEqual());
	}
}
