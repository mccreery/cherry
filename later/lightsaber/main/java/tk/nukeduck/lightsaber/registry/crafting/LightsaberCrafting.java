package tk.nukeduck.lightsaber.registry.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.RecipeSorter;
import tk.nukeduck.lightsaber.registry.LightsaberBlocks;
import tk.nukeduck.lightsaber.registry.LightsaberItems;
import tk.nukeduck.lightsaber.util.Strings;
import cpw.mods.fml.common.registry.GameRegistry;

public class LightsaberCrafting {
	/** Registers all the crafting recipes for the mod. */
	public static void registerCrafting() {
		for(int i = 0; i < LightsaberItems.lightsabers.length - 1; i += 2) {
			ItemStack currentLens = new ItemStack(LightsaberItems.lens, 1, i / 2);
			GameRegistry.addRecipe(currentLens, new Object[] {" # ", "#$#", "$N$", '#', Blocks.glass, '$', new ItemStack(LightsaberBlocks.crystal, 1, i / 2), 'N', Items.nether_star});
			
			for(int h = 0; h < Strings.HILTS.length; h++) {
				ItemStack currentSaber = new ItemStack(LightsaberItems.lightsabers[i], 1, LightsaberItems.lightsabers[i].getMaxDamage());
				ItemStack currentSaberDouble = new ItemStack(LightsaberItems.lightsabers[i + 1], 1, LightsaberItems.lightsabers[i + 1].getMaxDamage());
				
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("HiltIndex", h);
				currentSaber.setTagCompound(nbt);
				currentSaberDouble.setTagCompound(nbt);
				
				ItemStack hilt = new ItemStack(LightsaberItems.hilt, 1, h);
				
				GameRegistry.addRecipe(currentSaber, new Object[] {"# ", " H", '#', currentLens, 'H', hilt});
				GameRegistry.addRecipe(currentSaberDouble, new Object[] {"#  ", " H ", "  #", '#', currentLens, 'H', hilt});
			}
		}
		
		GameRegistry.addRecipe(new ItemStack(LightsaberItems.energyCapsules[0], 1, LightsaberItems.energyCapsules[0].getMaxDamage()), new Object[] {
			"I#I", "RBR", "I#I", 'I', Items.iron_ingot, '#', Blocks.cobblestone, 'R', Items.repeater, 'B', Blocks.redstone_block
		});
		
		GameRegistry.addRecipe(new ItemStack(LightsaberItems.energyCapsules[1], 1, LightsaberItems.energyCapsules[1].getMaxDamage()), new Object[] {
			"I#I", "RBR", "I#I", 'I', Items.gold_ingot, '#', Blocks.cobblestone, 'R', Items.repeater, 'B', LightsaberItems.energyCapsules[0]
		});
		
		GameRegistry.addRecipe(new ItemStack(LightsaberItems.energyCapsules[2], 1, LightsaberItems.energyCapsules[2].getMaxDamage()), new Object[] {
			"I#I", "RBR", "I#I", 'I', Items.quartz, '#', Blocks.cobblestone, 'R', Items.repeater, 'B', LightsaberItems.energyCapsules[1]
		});
		
		GameRegistry.addRecipe(new ItemStack(LightsaberBlocks.charger, 1, 0), new Object[] {
			"IGI", "ERE", "III", 'I', Items.iron_ingot, 'G', Blocks.glass, 'E', LightsaberItems.energyCapsules[0], 'R', Blocks.redstone_block
		});
		
		GameRegistry.addRecipe(new ItemStack(LightsaberBlocks.charger, 1, 1), new Object[] {
			"BGB", "ERE", "III", 'B', Blocks.iron_block, 'G', Blocks.glass, 'E', LightsaberItems.energyCapsules[1], 'R', Blocks.redstone_block, 'I', Items.iron_ingot
		});
		
		GameRegistry.addRecipe(new ItemStack(LightsaberBlocks.charger, 1, 2), new Object[] {
			"BGB", "ERE", "BDB", 'B', Blocks.iron_block, 'G', Blocks.glass, 'E', LightsaberItems.energyCapsules[2], 'R', Blocks.redstone_block, 'D', Items.diamond
		});
		
		RecipeSorter.register("lightsaber:capsulefilling", EnergyCapsuleRefillHandler.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		GameRegistry.addRecipe(new EnergyCapsuleRefillHandler());
	}
}