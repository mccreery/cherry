package tk.nukeduck.generation.registry;

import java.util.Locale;

public class ObjectName {
	private static final Locale LOCALE = Locale.ENGLISH;

	public final String[] parts;
	private String[] partsLower;
	private String[] partsUpper;
	private String camelCase, camelCaseHeadless, lower, upper, underscoredLower, underscoredUpper;

	private Format format = Format.CAMEL_CASE_HEADLESS;
	private StringBuilder builder;

	public static ObjectName join(ObjectName... names) {
		int n = 0;
		for(ObjectName name : names) {
			n += name.parts.length;
		}

		String[] parts = new String[n];
		n = 0;
		for(ObjectName name : names) {
			for(String part : name.parts) {
				parts[n++] = part;
			}
		}
		return new ObjectName(parts);
	}

	public static ObjectName prefix(ObjectName name, String... suffixes) {
		String[] parts = new String[name.parts.length + suffixes.length];

		int n = 0;
		for(String part : suffixes) {
			parts[n++] = part;
		}
		for(String part : name.parts) {
			parts[n++] = part;
		}
		return new ObjectName(parts);
	}
	public static ObjectName suffix(ObjectName name, String... suffixes) {
		String[] parts = new String[name.parts.length + suffixes.length];

		int n = 0;
		for(String part : name.parts) {
			parts[n++] = part;
		}
		for(String part : suffixes) {
			parts[n++] = part;
		}
		return new ObjectName(parts);
	}

	public ObjectName(String... parts) {
		this.parts = parts;
	}
	public ObjectName(Format format, String... parts) {
		this(parts);
		this.format = format;
	}

	private void lazyLoad(boolean lower, boolean upper) {
		if(lower &= this.partsLower == null) this.partsLower = new String[this.parts.length];
		if(upper &= this.partsUpper == null) this.partsUpper = new String[this.parts.length];

		for(int i = 0; i < this.parts.length; i++) {
			if(lower) this.partsLower[i] = this.parts[i].toLowerCase(LOCALE);
			if(upper) this.partsUpper[i] = this.parts[i].toUpperCase(LOCALE);
		}
	}

	private String getCamelCase() {
		if(this.camelCase != null) return this.camelCase;
		this.builder.setLength(0);

		this.lazyLoad(true, true);
		for(int i = 0; i < this.parts.length; i++) {
			this.builder.append(this.partsUpper[i].substring(0, 1)).append(this.partsLower[i].substring(1));
		}
		return this.camelCase = this.builder.toString();
	}

	private String getCamelCaseHeadless() {
		if(this.camelCaseHeadless != null) return this.camelCaseHeadless;
		this.builder.setLength(0);

		this.lazyLoad(true, true);
		for(int i = 0; i < this.parts.length; i++) {
			if(i == 0) {
				this.builder.append(this.partsLower[i]);
			} else {
				this.builder.append(this.partsUpper[i].substring(0, 1)).append(this.partsLower[i].substring(1));
			}
		}
		return this.camelCaseHeadless = this.builder.toString();
	}

	private String getLower() {
		if(this.lower != null) return this.lower;
		this.builder.setLength(0);

		this.lazyLoad(true, false);
		for(int i = 0; i < this.parts.length; i++) {
			this.builder.append(this.partsLower[i]);
		}
		return this.lower = this.builder.toString();
	}

	private String getUpper() {
		if(this.upper != null) return this.upper;
		this.builder.setLength(0);

		this.lazyLoad(false, true);
		for(int i = 0; i < this.parts.length; i++) {
			this.builder.append(this.partsUpper[i]);
		}
		return this.upper = this.builder.toString();
	}

	private String getUnderscoredLower() {
		if(this.underscoredLower != null) return this.underscoredLower;
		this.builder.setLength(0);

		this.lazyLoad(true, false);
		for(int i = 0; i < this.parts.length; i++) {
			if(i != 0) this.builder.append('_');
			this.builder.append(this.partsLower[i]);
		}
		return this.underscoredLower = this.builder.toString();
	}

	private String getUnderscoredUpper() {
		if(this.underscoredUpper != null) return this.underscoredUpper;
		this.builder.setLength(0);

		this.lazyLoad(false, true);
		for(int i = 0; i < this.parts.length; i++) {
			if(i != 0) this.builder.append('_');
			this.builder.append(this.partsUpper[i]);
		}
		return this.underscoredUpper = this.builder.toString();
	}

	public String toString() {
		return this.toString(this.format);
	}
	public String toString(Format format) {
		if(this.builder == null) {
			int n = 0;
			for(String s : this.parts) {
				n += s.length() + 1;
			}
			this.builder = new StringBuilder(n);
		}

		switch(format) {
			case CAMEL_CASE:
				return this.getCamelCase();
			case CAMEL_CASE_HEADLESS:
				return this.getCamelCaseHeadless();
			case LOWER:
				return this.getLower();
			case UPPER:
				return this.getUpper();
			case UNDERSCORED_LOWER:
				return this.getUnderscoredLower();
			case UNDERSCORED_UPPER:
				return this.getUnderscoredUpper();
			default:
				return this.toString();
		}
	}

	public enum Format {
		CAMEL_CASE,
		CAMEL_CASE_HEADLESS,
		LOWER,
		UPPER,
		UNDERSCORED_LOWER,
		UNDERSCORED_UPPER;
	}
}
