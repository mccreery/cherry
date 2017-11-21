package tk.nukeduck.lightsaber.registry;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.item.ItemEnergyCapsule;
import tk.nukeduck.lightsaber.item.ItemHilt;
import tk.nukeduck.lightsaber.item.ItemLens;
import tk.nukeduck.lightsaber.item.ItemLightsaber;
import tk.nukeduck.lightsaber.item.ItemTome;
import tk.nukeduck.lightsaber.util.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

public class LightsaberItems {
	public static Item lens, hilt, tome;
	public static Item[] energyCapsules = new Item[3];
	public static Item[] lightsabers = new Item[Strings.COLORS.length * 2];
	
	/** Tool material used for lightsabers. */
	public static ToolMaterial LIGHTSABER = EnumHelper.addToolMaterial("LIGHTSABER", 3, 500, 7f, -4, 900);
	
	/** Registers all the items in the mod. */
	public static void registerItems() {
		int[] levels = {50, 225, 550};
		for(int i = 0; i < energyCapsules.length; i++) {
			energyCapsules[i] = new ItemEnergyCapsule(i).setMaxChargeLevel(levels[i]);
			GameRegistry.registerItem(energyCapsules[i], "energy_capsule" + (i > 0 ? "_" + i : ""));
		}
		
		lens = new ItemLens().setCreativeTab(Lightsaber.lightsaberTab);
		GameRegistry.registerItem(lens, "lens");
		hilt = new ItemHilt().setCreativeTab(Lightsaber.lightsaberTab);
		GameRegistry.registerItem(hilt, "hilt");
		tome = new ItemTome().setCreativeTab(Lightsaber.lightsaberTab);
		GameRegistry.registerItem(tome, "tome");
		
		for(int i = 0; i < lightsabers.length - 1; i += 2) {
			lightsabers[i] = new ItemLightsaber();
			GameRegistry.registerItem(lightsabers[i], "lightsaber_" + Strings.COLORS[i / 2]);
			
			lightsabers[i + 1] = new ItemLightsaber().setDoubled(true);
			GameRegistry.registerItem(lightsabers[i + 1], "lightsaber_" + Strings.COLORS[i / 2] + "_doubled");
		}
	}
}