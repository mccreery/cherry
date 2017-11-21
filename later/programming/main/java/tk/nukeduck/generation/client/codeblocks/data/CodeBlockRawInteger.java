package tk.nukeduck.generation.client.codeblocks.data;

import java.util.List;

import tk.nukeduck.generation.util.IError;

public class CodeBlockRawInteger extends CodeBlockTextBox implements IInteger {
	public CodeBlockRawInteger(String text) {
		super(text);
	}

	@Override
	public void checkErrors(List<IError> errors) {
		if(!isInteger(this.getString())) {
			errors.add(new IError() {
				@Override
				public String getDescription() {
					return String.format("Value \"%s\" is not a valid integer.", getString());
				}
			});
		}
	}

	public static final boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

	@Override
	public Integer getInteger() {
		return Integer.parseInt(this.getText().toString());
	}

	public void setInteger(int value) {
		this.textbox.clear();
		this.textbox.write(String.valueOf(value));
	}

	@Override
	public String getUnlocalizedName() { 
		return "text.numeric";
	}
}
