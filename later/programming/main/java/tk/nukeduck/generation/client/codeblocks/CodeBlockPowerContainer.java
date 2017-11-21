package tk.nukeduck.generation.client.codeblocks;

import tk.nukeduck.generation.client.codeblocks.data.IInteger;

public class CodeBlockPowerContainer extends CodeBlockContainerTyped<IInteger> {
	public CodeBlockPowerContainer() {
		super(IInteger.class);
	}

	private static final int OFFSET = 10;

	@Override
	public void recalculateSize() {
		super.recalculateSize();
		this.height += OFFSET;
	}

	@Override
	public void render(int x, int y) {
		if(this.child == null) {
			this.drawRect(x, y, x + this.getWidth(), y + this.getHeight() - OFFSET, 0xFFFFFFFF);
			this.drawRect(x, y, x + this.getWidth(), y + 1, 0xFFCCCCCC);
		} else {
			this.child.render(x, y);
		}
	}
}
