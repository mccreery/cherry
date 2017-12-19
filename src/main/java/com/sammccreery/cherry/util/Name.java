package com.sammccreery.cherry.util;

import java.util.regex.Pattern;

import com.sammccreery.cherry.Cherry;

import net.minecraft.util.ResourceLocation;

public class Name extends ResourceLocation {
	public static final Name EMPTY = new Name(new String[0]);
	public static final Name FANCY_BRICK       = new Name("fancy_brick");
	public static final Name WIDE_BRICK        = new Name("wide_brick");
	public static final Name SNOW_BRICK        = new Name("snow_brick");
	public static final Name SNOW_STAIRS       = new Name("snow_stairs");
	public static final Name SNOW_BRICK_STAIRS = new Name("snow_brick_stairs");
	public static final Name SNOW_DOOR         = new Name("snow_door");
	public static final Name ICE_PANE          = new Name("ice_pane");
	public static final Name HEART             = new Name("heart");
	public static final Name HEART_CRYSTAL     = HEART.append("crystal");
	public static final Name HEART_LANTERN     = HEART.append("lantern");
	public static final Name HEART_SHARD       = HEART.append("shard");

	public static final Name TOOL_UPGRADES     = new Name("tool_upgrades");
	public static final Name SURROUND_CRAFTING = new Name("surround");
	public static final Name DYE_CRAFTING      = new Name("dye");
	public static final Name FOOD_CANISTER     = new Name("food_canister");

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

	public Name(String name) {
		this(name.contains(":") ? name.substring(0, name.indexOf(':')) : Cherry.MODID,
			name.contains(":") ? name.substring(name.indexOf(':') + 1) : name);
	}
	public Name(String modid, String name) {
		super(modid, name);
		words = tokenize(name);
	}

	public Name(String[] words) {
		this(Cherry.MODID, words);
	}
	public Name(String modid, String[] words) {
		super(modid, formatName(words, Format.SNAKE));
		this.words = words;
	}

	public static Name join(Name a, Name b) {
		if(a.getResourceDomain() != b.getResourceDomain()) {
			throw new IllegalArgumentException(String.format("Differing mod IDs: %s and %s", a.getResourceDomain(), b.getResourceDomain()));
		}
		return a.append(b.words);
	}

	public Name prepend(String... words) {
		return insert(0, words);
	}
	public Name append(String... words) {
		return insert(this.words.length, words);
	}
	public Name insert(int i, String... inserts) {
		String[] words = new String[this.words.length + inserts.length];
		System.arraycopy(this.words, 0, words, 0, i);
		System.arraycopy(inserts, 0, words, i, inserts.length);
		System.arraycopy(this.words, i, words, i + inserts.length, this.words.length - i);

		return new Name(this.getResourceDomain(), words);
	}

	@Override
	public boolean equals(Object other) {
		// This will always match ResourceLocations, regardless of format
		if(other instanceof Name) {
			Name name = (Name)other;

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
			return equals(new Name(resource.getResourceDomain(), resource.getResourcePath()));
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		// This will match ResourceLocation keys only if the resource location is in snake_case
		return new ResourceLocation(getResourceDomain(), format(Format.SNAKE, false)).hashCode();
	}

	public String format(Format format, boolean qualify) {
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

	@Override
	public String getResourcePath() {
		return format(Format.SNAKE, false);
	}

	@Override
	public String toString() {
		return format(Format.SNAKE, true);
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
