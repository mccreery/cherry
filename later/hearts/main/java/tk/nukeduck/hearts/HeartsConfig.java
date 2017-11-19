package tk.nukeduck.hearts;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class HeartsConfig {
	public Configuration config;
	public HeartsConfig(File file) {
		this.config = new Configuration(file);
	}

	private int maxHearts;
	private int genHeight;
	private int genCount;
	private float keptRate;
	private boolean oldModel;

	public HeartsConfig load() {
		config.load();

		this.maxHearts = config.getInt("maxHearts", config.CATEGORY_GENERAL, 10, 0, (int) Double.MAX_VALUE, "Sets how many extra hearts a player can gain");
		this.genHeight = config.getInt("genHeight", config.CATEGORY_GENERAL, 20, 1, 256, "Sets the height of heart crystal generation, from 0 (inclusive) to X (exclusive)");
		this.genCount = config.getInt("genCount", config.CATEGORY_GENERAL, 4, 0, 1000, "Sets the chances of heart crystals spawning. Set to 0 for no spawning");
		this.keptRate = config.getFloat("keptRate", config.CATEGORY_GENERAL, 0.0F, 0.0F, 1.0F, "Sets the chance of retaining individual hearts upon death. 0.0F to always drop hearts, 1.0F to always keep them.");
		this.oldModel = config.getBoolean("oldModel", config.CATEGORY_GENERAL, false, "Enable this to use the old 3D model for heart crystals.");

		config.save();
		return this;
	}

	public int getMaxHearts() {
		return this.maxHearts;
	}

	public int getGenHeight() {
		return this.genHeight;
	}

	public int getGenCount() {
		return this.genCount;
	}

	public float getKeptRate() {
		return this.keptRate;
	}

	public boolean getOldModel() {
		return this.oldModel;
	}
}
