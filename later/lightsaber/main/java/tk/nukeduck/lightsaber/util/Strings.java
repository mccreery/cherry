package tk.nukeduck.lightsaber.util;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.commons.lang3.ArrayUtils;

import tk.nukeduck.lightsaber.Lightsaber;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class Strings {
	public static final String
		MOD_ID = "lightsaber",
		
		SPACE = " ",
		SINGULAR_SUFFIX = ".singular",
		PLURAL_SUFFIX = ".plural",
		
		EMPTY = "lightsaber.empty.name",
		FULL = "lightsaber.full.name",
		DOUBLE = "lightsaber.double.name",
		
		SKILLS_MENU = "lightsaber.menu.skills",
		REQUIRES_SKILL = "lightsaber.requiresSkill",
		POINTS = "lightsaber.points",
		COSTS = "lightsaber.costs",
		BOUGHT = "lightsaber.bought",
		IMPORT = "ioModes.importRule",
		EXPORT = "ioModes.exportRule",
		IMPORT_ARROW = "ioModes.importRule.prefix",
		EXPORT_ARROW = "ioModes.exportRule.prefix",
		INVENTORY = "container.inventory",
		TRANSFER_RATE = "lightsaber.transferRate",
		LESS_THAN_ONE = "lightsaber.lessThan1",
		ENERGY_SYMBOL = "lightsaber.energySymbol",
		CAPACITY_LITERAL = "lightsaber.capacityLiteral",
		TRANSFER_RATE_LITERAL = "lightsaber.transferRateLiteral",
		ENERGY_LITERAL = "lightsaber.energyLiteral",
		OUT_OF = "lightsaber.outOf",
		
		COLOR_PREFIX = "lightsaber.",
		RED = "red",
		GREEN = "green",
		BLUE = "blue",
		PURPLE = "purple",
		YELLOW = "yellow",
		BLACK = "black",
		ORANGE = "orange",
		WHITE = "white",
		COLOR_SUFFIX = ".name",
		
		HILT = "lightsaber.hilt",
		HILT_PREFIX = "lightsaber.hilt.",
		HILT_A = "a",
		HILT_B = "b",
		HILT_C = "c",
		HILT_D = "d",
		
		UNKNOWN = "lightsaber.unknown",
		
		SKILL_PREFIX = "forceSkill.",
		SKILL_MEDITATION = "meditation",
		SKILL_FOCUS = "focus",
		SKILL_CONTROL_I = "controlI",
		SKILL_SPEED_I = "speedI",
		SKILL_SPEED_II = "speedII",
		SKILL_SPEED_III = "speedIII",
		SKILL_CONTROL_II = "controlII",
		SKILL_CLOAK = "cloak",
		SKILL_BODY = "body",
		SKILL_SENSE = "sense",
		SKILL_SIGHT = "sight",
		SKILL_AURA = "aura",
		SKILL_ALTER = "alter",
		SKILL_TELEKINESIS = "telekinesis",
		SKILL_LEAP = "leap",
		SKILL_PUSH = "push",
		SKILL_PULL = "pull",
		SKILL_BLAST = "blast",
		SKILL_FIRE = "combustion",
		DESC_SUFFIX = ".desc",
		
		MARK_PREFIX = "lightsaber.mark.",
		NAME_SUFFIX = ".name",
		
		TOME_LORE = "tome.lore.",
		TOME_TITLE_SUFFIX = ".title",
		DAMAGE_FORCE_SUFFIX = ".force";
	
	/** All colours in the mod in order of index. */
	public static final String[] COLORS = new String[] {
		BLUE,
		RED,
		GREEN,
		PURPLE,
		YELLOW,
		BLACK,
		ORANGE,
		WHITE
	};
	
	/** All hilts in the mod in order of index. */
	public static final String[] HILTS = new String[] {
		HILT_A,
		HILT_B,
		HILT_C,
		HILT_D
	};
	
	/** Get the index of the specified colour.
	 * @param color The colour to find.
	 * @return The index of the colour. */
	public static int colorIndex(String color) {
		return ArrayUtils.indexOf(COLORS, color);
	}
	
	/** @param name The colour to input.
	 * @return The full unlocalised name of the given colour. */
	public static String getColor(String name) {
		return COLOR_PREFIX + name + COLOR_SUFFIX;
	}
	
	/** @param path The unlocalised string to translate.
	 * @return The translated version of the given string, using the local translation or fallback if that doesn't work. */
	public static String translate(String path) {
		String translation = StatCollector.translateToLocal(path);
		return translation == path ? StatCollector.translateToFallback(path) : translation;
	}
	
	/** @param skill The skill to input.
	 * @return The full unlocalised name of the given skill. */
	public static String getSkill(String skill) {
		return SKILL_PREFIX + skill;
	}
	
	/** @param skill The skill to input.
	 * @return The full unlocalised name of the description of the given skill. */
	public static String getSkillDesc(String skill) {
		return getSkill(skill) + DESC_SUFFIX;
	}
	
	/** The unlocalised names of all the IO modes. */
	public static String[][] ioModes = {
		{"" + ChatFormatting.GREEN, "allow"},
		{"" + ChatFormatting.RED, "deny"},
		{"" + ChatFormatting.BLUE, "allowEmpty"},
		{"" + ChatFormatting.BLUE, "allowFull"}
	};
	
	public static final String IO_PREFIX = "ioModes.";
	/** @param i The mode to input.
	 * @return The full unlocalised name of the IO mode. */
	public static String getIOMode(int i) {
		return IO_PREFIX + ioModes[i][1];
	}
	
	/** @param i The index of the IO mode to return.
	 * @return the IO mode of the given modifier. */
	public static String getIOModifier(int i) {
		return ioModes[i][0];
	}
	
	/** The minimum and maximum characters, used for garbling. */
	private static final char MIN = ' ', MAX = '~';
	/** Replaces random indices in the text with random characters, in order to make it unreadable.
	 * @param text The input text to garble.
	 * @param level How unreadable the text should be.
	 * @return The garbled text. */
	public static String garble(String text, int level) {
		char[] chars = text.toCharArray();
		int diff = MAX - MIN;
		for(int b = 0; b < chars.length; b++) {
			if(Lightsaber.random.nextInt(4) < level) {
				chars[b] = (char) (Lightsaber.random.nextInt(diff) + MIN);
			}
		}
		return String.valueOf(chars);
	}
	
	/** Adds an "s" or the localised plural suffix to the given count, if it isn't one.
	 * @param count The counter to use.
	 * @param suffix The base suffix to add. */
	public static String formatCounter(int count, String suffix) {
		return count + SPACE + translate(suffix + (count == 1 ? SINGULAR_SUFFIX : PLURAL_SUFFIX));
	}
	
	/** Concatenates all the given codes.
	 * @param codes A list of codes to sequence. */
	public static String sequenceCodes(ChatFormatting... codes) {
		String s = "";
		for(ChatFormatting code : codes) s += code;
		return s;
	}
	
	/** Splits the given string into the line length given by word.
	 * @param input The string to format.
	 * @param maxLineLength The maximum length per line, in characters. */
	public static ArrayList<String> formatLines(String input, int maxLineLength) {
		StringTokenizer tok = new StringTokenizer(input, SPACE);
		String line = "";
		ArrayList<String> lines = new ArrayList<String>();
		while(tok.hasMoreTokens()) {
			String word = tok.nextToken();
			if(line.length() + word.length() > maxLineLength) {
				lines.add(line.substring(0, line.length() - 1));
				line = "";
			}
			line += word + SPACE;
		}
		if(line.length() > 0) {
			lines.add(line.substring(0, line.length() - 1));
		}
		return lines;
	}
	
	/** Splits the given string into the pixel width given by word.
	 * @param fr The font renderer to measure strings with.
	 * @param input The string to format.
	 * @param maxWidth The maximum length per line, in pixels. */
	public static ArrayList<String> formatLinesWidth(FontRenderer fr, String input, int maxWidth) {
		StringTokenizer tok = new StringTokenizer(input, SPACE);
		String line = "";
		boolean indent = false;
		ArrayList<String> lines = new ArrayList<String>();
		while(tok.hasMoreTokens()) {
			String word = tok.nextToken();
			if(word.equals("\\n")) {
				if(line == "") line = " ";
				lines.add(line.substring(0, line.length() - 1));
				line = "";
				indent = true;
			} else {
				if(fr.getStringWidth((indent ? "  " : "") + line + word) > maxWidth) {
					lines.add((indent ? "  " : "") + line.substring(0, line.length() - 1));
					line = "";
					indent = false;
				}
				line += word + SPACE;
			}
		}
		if(line.length() > 0) {
			lines.add(line.substring(0, line.length() - 1));
		}
		return lines;
	}
	
	/** Splits the given string into the pixel width given by word.<br/>
	 * Supports varying lengths.
	 * @param fr The font renderer to measure strings with.
	 * @param input The string to format.
	 * @param maxWidth The maximum lengths per line, in pixels. */
	public static ArrayList<String> formatLinesWidth(FontRenderer fr, String input, int... maxWidths) {
		StringTokenizer tok = new StringTokenizer(input, SPACE);
		String line = "";
		ArrayList<String> lines = new ArrayList<String>();
		while(tok.hasMoreTokens()) {
			String word = tok.nextToken();
			if(fr.getStringWidth(line + word) > maxWidths[Math.min(maxWidths.length - 1, lines.size())]) {
				lines.add(line.substring(0, line.length() - 1));
				line = "";
			}
			line += word + SPACE;
		}
		if(line.length() > 0) {
			lines.add(line.substring(0, line.length() - 1));
		}
		return lines;
	}
}