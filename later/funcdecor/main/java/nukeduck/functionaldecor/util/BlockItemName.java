package nukeduck.functionaldecor.util;

import cpw.mods.fml.common.Mod;

public class BlockItemName {
	private final String[] parts;

	public BlockItemName(Object... names) {
		int size = 0;
		for(Object name : names) {
			if(name instanceof BlockItemName) size += ((BlockItemName) name).getParts().length;
			else if(name instanceof String[]) size += ((String) name).length();
			else if(name instanceof String) size++;
		}
		this.parts = new String[size];

		int x = 0;
		for(Object name : names) {
			if(name instanceof BlockItemName) {
				for(String sub : ((BlockItemName) name).getParts()) parts[x++] = sub;
			} else if(name instanceof Object[]) {
				for(Object sub : (Object[]) name) parts[x++] = sub.toString();
			} else {
				parts[x++] = name.toString();
			}
		}
	}

	public String[] getParts() {
		return this.parts;
	}

	public String toNamespacedString(FormatType type, String namespace) {
		StringBuilder builder = new StringBuilder();
		builder.append(namespace).append(':');

		for(int i = 0; i < this.parts.length; i++) {
			type.addPart(builder, this.parts[i]);
		}
		type.post(builder);

		return builder.toString();
	}
	public String toNamespacedString(String namespace) {
		return this.toNamespacedString(defaultFormat, namespace);
	}
	public String toNamespacedString(FormatType type, Mod mod) {
		return this.toNamespacedString(type, mod.modid());
	}
	public String toNamespacedString(Mod mod) {
		return this.toNamespacedString(defaultFormat, mod.modid());
	}
	public String toNamespacedString(FormatType type, Object mod) {
		return this.toNamespacedString(type, mod.getClass().getAnnotation(Mod.class));
	}
	public String toNamespacedString(Object mod) {
		return this.toNamespacedString(defaultFormat, mod.getClass().getAnnotation(Mod.class));
	}
	public String toNamespacedString(FormatType type) {
		return this.toNamespacedString(type, defaultNamespace);
	}
	public String toNamespacedString() {
		return this.toNamespacedString(defaultFormat, defaultNamespace);
	}

	public String toString(FormatType type) {
		StringBuilder builder = new StringBuilder();

		for(int i = 0; i < this.parts.length; i++) {
			type.addPart(builder, this.parts[i]);
		}
		type.post(builder);

		return builder.toString();
	}
	@Override public String toString() {
		return this.toString(defaultFormat);
	}

	public static final void setNamespace(String namespace) {
		defaultNamespace = namespace;
	}
	public static final void setNamespace(Mod mod) {
		setNamespace(mod.modid());
	}
	public static final void setNamespace(Object mod) {
		setNamespace(mod.getClass().getAnnotation(Mod.class));
	}
	private static String defaultNamespace = "minecraft";

	public static final void setDefault(FormatType type) {
		defaultFormat = type;
	}
	private static FormatType defaultFormat = FormatType.HEADLESS_CAMELCASE;

	public enum FormatType {
		CAMELCASE {
			@Override
			public void addPart(StringBuilder builder, String part) {
				builder.append(Character.toTitleCase(part.charAt(0))).append(part.substring(1));
			}

			@Override public void post(StringBuilder builder) {}
		},
		HEADLESS_CAMELCASE {
			@Override public void addPart(StringBuilder builder, String part) {CAMELCASE.addPart(builder, part);}

			@Override
			public void post(StringBuilder builder) {
				if(builder.length() > 0) builder.setCharAt(0, Character.toLowerCase(builder.charAt(0)));
			}
		},
		LOWERCASE {
			@Override
			public void addPart(StringBuilder builder, String part) {
				builder.append(part);
			}

			@Override public void post(StringBuilder builder) {}
		},
		UPPERCASE {
			@Override
			public void addPart(StringBuilder builder, String part) {
				builder.append(part.toUpperCase());
			}

			@Override public void post(StringBuilder builder) {}
		},
		LOWERCASE_UNDERSCORED {
			@Override
			public void addPart(StringBuilder builder, String part) {
				LOWERCASE.addPart(builder, part);
				builder.append('_');
			}

			@Override
			public void post(StringBuilder builder) {
				if(builder.length() > 0) builder.setLength(builder.length() - 1);
			}
		},
		UPPERCASE_UNDERSCORED {
			@Override
			public void addPart(StringBuilder builder, String part) {
				UPPERCASE.addPart(builder, part);
				builder.append('_');
			}

			@Override
			public void post(StringBuilder builder) {LOWERCASE_UNDERSCORED.post(builder);}
		};

		public abstract void addPart(StringBuilder builder, String part);
		public abstract void post(StringBuilder builder);
	}
}
