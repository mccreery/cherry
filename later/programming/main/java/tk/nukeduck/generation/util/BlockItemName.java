package tk.nukeduck.generation.util;

public class BlockItemName {
	private String[] parts;

	public BlockItemName(String... parts) {
		this.parts = parts;
	}

	/** Formats this name for different uses.
	 * @param format a name format to format the name with
	 * @return A formatted version of this name, using the given format */
	public String format(NameFormat format) {
		StringBuilder sb = new StringBuilder();
		switch(format) {
			case LOWERCASE:
				for(String part : this.parts) {
					sb.append(part.toLowerCase());
				}
				return sb.toString();
			case UPPERCASE:
				for(String part : this.parts) {
					sb.append(part.toUpperCase());
				}
				return sb.toString();
			case UNDERSCORED_LOWERCASE:
				for(String part : this.parts) {
					sb.append(part.toLowerCase());
					sb.append('_');
				}
				sb.setLength(sb.length() - 1);
				return sb.toString();
			case HEADLESS_CAMELCASE:
				sb.append(this.parts[0].toLowerCase());
				for(int i = 1; i < this.parts.length; i++) {
					String part = parts[i].toLowerCase();
					sb.append(Character.toUpperCase(part.charAt(0)));
					sb.append(part.substring(1));
				}
				return sb.toString();
			case CAMELCASE:
				for(int i = 0; i < this.parts.length; i++) {
					String part = parts[i].toLowerCase();
					sb.append(Character.toUpperCase(part.charAt(0)));
					sb.append(part.substring(1));
				}
			default:
				return this.format(NameFormat.HEADLESS_CAMELCASE);
		}
	}

	public enum NameFormat {
		/** Converts all parts to lowercase and concatenates. */
		LOWERCASE,
		/** Converts all parts to uppercase and concatenates. */
		UPPERCASE,
		/** Converts all parts to lowercase, and concatenates
		 * with a delimiter of an underscore ('_'). */
		UNDERSCORED_LOWERCASE,
		/** Converts to {@code camelCase} without a head. */
		HEADLESS_CAMELCASE,
		/** Converts to {@code CamelCase} with a head. */
		CAMELCASE;
	}
}
