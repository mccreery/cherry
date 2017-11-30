package com.sammccreery.cherry.util;

import java.util.regex.Pattern;

import com.sammccreery.cherry.Cherry;

import net.minecraft.util.ResourceLocation;

public class UniversalName extends ResourceLocation {
	public static final UniversalName EMPTY = new UniversalName(new String[0]);

	private final String[] words;

	private static final Pattern TOKENIZER = Pattern.compile(
		"[_-]|(?<!\\p{Lu}|[_-])(?=\\p{Lu})" + // Capital letters
		"|(?<!\\d|[_-])(?=\\d)" +             // Digits start
		"|(?<=\\d)(?!\\d)");                  // Digits end

	private static String[] tokenize(String name) {
		if(name != null && !name.isEmpty()) {
			return TOKENIZER.split(name);
		} else {
			return new String[0];
		}
	}

	public UniversalName(String name) {
		super(name.contains(":") ? name.substring(0, name.indexOf(':')) : Cherry.MODID,
			name.contains(":") ? name.substring(name.indexOf(':') + 1) : name);
		words = tokenize(name);
	}
	public UniversalName(String modid, String name) {
		super(modid, name);
		words = tokenize(name);
	}
	public UniversalName(String[] words) {
		this(Cherry.MODID, words);
	}
	public UniversalName(String modid, String[] words) {
		super(modid, formatName(words, Format.SNAKE));
		this.words = words;
	}

	public UniversalName(UniversalName a, UniversalName b) {
		super(a.getResourceDomain(), a.getResourcePath() + b.getResourcePath());

		if(a.getResourceDomain() != b.getResourceDomain()) {
			throw new IllegalArgumentException(String.format("Differing mod IDs: %s and %s",
				a.getResourceDomain(), b.getResourceDomain()));
		}

		words = new String[a.words.length + b.words.length];
		int i, j;
		for(i = 0; i < a.words.length; i++) {
			words[i] = a.words[i];
		}
		for(j = 0; j < b.words.length; i++, j++) {
			words[i] = b.words[j];
		}
	}

	@Override
	public boolean equals(Object other) {
		// This will always match ResourceLocations, regardless of format
		if(other instanceof UniversalName) {
			UniversalName name = (UniversalName)other;

			if(getResourceDomain() != name.getResourceDomain() || words.length != name.words.length) {
				return false;
			} else {
				for(int i = 0; i < words.length; i++) {
					if(!words[i].equalsIgnoreCase(name.words[i])) {
						return false;
					}
				}
				return true;
			}
		} else if(other instanceof ResourceLocation) {
			ResourceLocation resource = (ResourceLocation)other;
			return equals(new UniversalName(resource.getResourceDomain(), resource.getResourcePath()));
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		// This will match ResourceLocation keys only if the resource location is in snake_case
		return new ResourceLocation(getResourceDomain(), format(false, Format.SNAKE)).hashCode();
	}

	public String format(boolean qualify, Format format) {
		if(format != null) {
			StringBuilder builder = new StringBuilder();

			if(qualify) {
				// Bad to hard-code this? Will it ever change?
				builder.append(getResourceDomain()).append(':');
			}
			return formatName(builder, words, format);
		} else {
			return qualify ? toString() : getResourcePath();
		}
	}

	private static String formatName(String[] words, Format format) {
		return formatName(new StringBuilder(), words, format);
	}
	private static String formatName(StringBuilder builder, String[] words, Format format) {
		for(int i = 0; i < words.length; i++) {
			format.append(builder, i, words[i]);
		}
		return builder.toString();
	}

	public enum Format {
		LOWERCASE {
			@Override
			public void append(StringBuilder builder, int i, String word) {
				builder.append(word.toLowerCase());
			}
		}, SNAKE {
			@Override
			public void append(StringBuilder builder, int i, String word) {
				if(i > 0) builder.append('_');
				LOWERCASE.append(builder, i, word);
			}
		}, UPPERCASE {
			@Override
			public void append(StringBuilder builder, int i, String word) {
				builder.append(word.toUpperCase());
			}
		}, CONSTANT {
			@Override
			public void append(StringBuilder builder, int i, String word) {
				if(i > 0) builder.append('_');
				UPPERCASE.append(builder, i, word);
			}
		}, CAMELCASE {
			@Override
			public void append(StringBuilder builder, int i, String word) {
				if(word != null && !word.isEmpty()) {
					builder.append(Character.toUpperCase(word.charAt(0)));
					builder.append(word.toLowerCase(), 1, word.length());
				}
			}
		}, HEADLESS {
			@Override
			public void append(StringBuilder builder, int i, String word) {
				(i > 0 ? CAMELCASE : LOWERCASE).append(builder, i, word);
			}
		};

		public abstract void append(StringBuilder builder, int i, String word);
	}
}
