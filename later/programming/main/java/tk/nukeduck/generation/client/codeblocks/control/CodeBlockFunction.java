package tk.nukeduck.generation.client.codeblocks.control;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tk.nukeduck.generation.client.codeblocks.BlockCategory;
import tk.nukeduck.generation.client.codeblocks.BlockStack;
import tk.nukeduck.generation.client.codeblocks.CodeBlockTranslate;
import tk.nukeduck.generation.client.codeblocks.ICodeBlock;
import tk.nukeduck.generation.client.codeblocks.data.CodeBlockTextBox;
import tk.nukeduck.generation.client.codeblocks.logic.CodeBlockOpen;
import tk.nukeduck.generation.util.Constants;

public class CodeBlockFunction extends CodeBlockOpen {
	private CodeBlockTextBox name;

	public CharSequence getName() {
		return this.name.getText();
	}
	public CodeBlockFunction setName(CharSequence name) {
		this.name.textbox.setText(name);
		return this;
	}

	public CodeBlockFunction() {
		super(BlockCategory.CONTROL, Constants.FUNCTION, 0);
		this.headerParts.add(new CodeBlockTranslate("function"));
		this.headerParts.add(this.name = new CodeBlockTextBox(""));
	}

	@Override
	public ICodeBlock copy() {
		CodeBlockFunction block = new CodeBlockFunction().setName(this.getName());
		for(ICodeBlock block2 : this.blockBlocks) {
			block.addToBlock(block2.copy());
		}
		return block;
	}

	@Override
	public void evaluate(World world, int x, int y, int z) {
		this.evaluateChildren(world, x, y, z);
	}

	@Override
	public String getUnlocalizedName() {
		return "function";
	}

	@Override
	public ICodeBlock construct(NBTTagCompound tag) {
		CodeBlockFunction block = super.construct(tag, new CodeBlockFunction());
		block.name.textbox.setText(tag.getString("Name"));
		return block;
	}

	@Override
	public NBTTagCompound serialize() {
		NBTTagCompound tag = super.serialize();
		tag.setString("Name", this.name.getString());
		return tag;
	}

	@Override
	public boolean insertAfter(ICodeBlock block) {
		// Nothing can come after a function
		return false;
	}
}
