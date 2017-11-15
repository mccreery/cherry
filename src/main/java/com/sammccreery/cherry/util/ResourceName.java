package com.sammccreery.cherry.util;

import java.util.regex.Pattern;

import com.sammccreery.cherry.Cherry;

import net.minecraft.util.ResourceLocation;

public class ResourceName extends ResourceLocation {
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

	public ResourceName(String name) {
		super(name.contains(":") ? name.substring(0, name.indexOf(':')) : Cherry.MODID,
			name.contains(":") ? name.substring(name.indexOf(':') + 1) : name);
		words = tokenize(name);
	}
	public ResourceName(String modid, String name) {
		super(modid, name);
		words = tokenize(name);
	}
	public ResourceName(String[] words) {
		this(Cherry.MODID, words);
	}
	public ResourceName(String modid, String[] words) {
		super(modid, formatName(words, Format.SNAKE));
		this.words = words;
	}

	public ResourceName(ResourceName a, ResourceName b) {
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
