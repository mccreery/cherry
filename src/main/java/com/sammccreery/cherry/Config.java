package com.sammccreery.cherry;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config extends Configuration {
	public Config(File file) {
		super(file);
	}

	private static int maxHearts, genHeight, genCount;
	private static float keptRate;
	private static boolean oldModel;

	public static final String CATEGORY_HEART_CRYSTALS = "heartCrystals";

	@Override
	public void load() {
		super.load();

		maxHearts = getNatural("maxHearts", CATEGORY_HEART_CRYSTALS, 10, "Sets how many extra hearts a player can gain");
		genHeight = getInt("genHeight", CATEGORY_HEART_CRYSTALS, 20, 1, 256, "Sets the height of heart crystal generation, from 0 (inclusive) to X (exclusive)");
		genCount  = getNatural("genCount", CATEGORY_HEART_CRYSTALS, 4, "Sets the chances of heart crystals spawning. Set to 0 for no spawning");
		keptRate  = getFloat("keptRate", CATEGORY_HEART_CRYSTALS, 0.0F, 0.0F, 1.0F, "Sets the chance of retaining individual hearts upon death. 0.0F to always drop hearts, 1.0F to always keep them.");
		oldModel  = getBoolean("oldModel", CATEGORY_HEART_CRYSTALS, false, "Enable this to use the old 3D model for heart crystals.");

		save();
	}

	public static int     getMaxHearts() {return maxHearts;}
	public static int     getGenHeight() {return genHeight;}
	public static int     getGenCount()  {return genCount;}
	public static float   getKeptRate()  {return keptRate;}
	public static boolean getOldModel()  {return oldModel;}

	public int getNatural(String name, String category, int defaultValue, String comment) {
		Property prop = this.get(category, name, defaultValue);
		prop.setLanguageKey(name);
		prop.comment = comment + " [default: " + defaultValue + "]";
		prop.setMinValue(0);
		return prop.getInt(defaultValue) < 0 ? 0 : prop.getInt(defaultValue);
	}
}
