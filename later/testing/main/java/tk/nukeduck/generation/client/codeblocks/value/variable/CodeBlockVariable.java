package tk.nukeduck.generation.client.codeblocks.value.variable;

import java.util.HashMap;

import tk.nukeduck.generation.client.codeblocks.CodeBlock;
import tk.nukeduck.generation.client.codeblocks.value.CodeBlockTextBox;
import tk.nukeduck.generation.client.codeblocks.value.IString;

public abstract class CodeBlockVariable extends CodeBlock implements IString {
	public static final HashMap<String, Object> variables = new HashMap<String, Object>();

	public CodeBlockTextBox key;
	public String getKey() {
		return this.key.getString();
	}

	public CodeBlockVariable(String key) {
		super(BlockType.VARIABLE, 255);
		this.headerParts.add(this.key = new CodeBlockTextBox(key));
	}

	public static void set(String key, Object value) {
		variables.put(key, value);
	}

	public static Object get(String key) {
		return variables.get(key);
	}

	@Override
	public String getString() {
		return get(this.getKey().toString()).toString();
	}
}
