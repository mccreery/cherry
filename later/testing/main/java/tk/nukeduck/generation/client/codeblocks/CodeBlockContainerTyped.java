package tk.nukeduck.generation.client.codeblocks;

public class CodeBlockContainerTyped<T> extends CodeBlockContainer {
	Class<T> type;

	private CodeBlockContainerTyped(Class<T> type) {
		super();
		this.type = type;
	}

	public static <T> CodeBlockContainerTyped<T> create(Class<T> type) {
		return new CodeBlockContainerTyped<T>(type);
	}

	@Override
	public boolean isChildValid(ICodeBlock child) {
		return this.type.isAssignableFrom(child.getClass());
	}

	public T get() {
		return this.type.cast(this.child);
	}
}
