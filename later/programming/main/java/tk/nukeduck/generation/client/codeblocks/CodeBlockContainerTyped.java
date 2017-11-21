package tk.nukeduck.generation.client.codeblocks;

import net.minecraft.nbt.NBTTagCompound;

public class CodeBlockContainerTyped<T> extends CodeBlockContainer {
	Class<T> type;

	protected CodeBlockContainerTyped(Class<T> type) {
		super();
		this.type = type;
	}

	@Override
	public ICodeBlock copy() {
		return null;
	}

	/** Factory method to create a typed container from a class.<br/>
	 * Creating a generic typed object from a class is hard otherwise. */
	public static <T> CodeBlockContainerTyped<T> create(Class<T> type) {
		return new CodeBlockContainerTyped<T>(type);
	}

	@Override
	public boolean isChildValid(ICodeBlock child) {
		return this.type.isAssignableFrom(child.getClass());
	}

	public T getChildTyped() {
		return this.type.cast(this.child);
	}

	@Override
	public boolean pickUpBlock(int x, int y, int mouseX, int mouseY, BlockStack clipboard, boolean duplicate) {
		// TODO Auto-generated method stub
		return false;
	}
}
