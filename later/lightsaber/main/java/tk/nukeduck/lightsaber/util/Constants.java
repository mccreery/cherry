package tk.nukeduck.lightsaber.util;

public class Constants {
	/** The maximum progress a player can have before they gain another skill point. */
	public static final byte MAX_PROGRESS = 100;
	/** The maximum amount of skills a player can have selected at once. */
	public static final int MAX_SELECTED = 5;
	/** Used as a numeric return value in place of {@code null}. */
	public static final int NULL_ID = -1;
	
	/** Base mana that all players have without any skills obtained.
	 * @see #ADDITIONAL_MANA */
	public static final int BASE_MANA = 90;
	/** Additional mana added to {@code BASE_MANA} for each skill the player obtains.
	 * @see #BASE_MANA */
	public static final int ADDITIONAL_MANA = 5;
	/** The amount of ticks that pass before all players' mana is recharged by one. */
	public static final int MANA_RECHARGE_RATE = 3;
	
	/** A colour code used in GUIs for text such as titles. */
	public static final int GUI_TEXT_COLOR = 4210752;
	/** The amount of pages in the Tome of the Force */
	public static final int TOME_PAGE_COUNT = 10;
	
	/** Maximum length of lines in tooltips. */
	public static final int TOOLTIP_LINE_LENGTH = 30;
	
	/** Holds the capacity and speed of each tier of refill unit. */
	public static final int[][] REFILL_UNIT_CAPACITIES = {
		{200, 7},
		{500, 2},
		{1000, 0}
	};
	
	/** Used to translate 2D textures in 3D space. */
	public static final float INCREMENT_DIST = 0.001F;
}