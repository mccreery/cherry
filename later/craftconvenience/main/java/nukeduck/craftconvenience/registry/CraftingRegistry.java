package nukeduck.craftconvenience.registry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockStairs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import nukeduck.craftconvenience.CraftConvenience;

public class CraftingRegistry {
	public static void init() {
		removeOld();
		addNew();
	}

	private static void removeOld() {
		List recipes = CraftingManager.getInstance().getRecipeList();
		Iterator it = recipes.iterator();
		while(it.hasNext()) {
			IRecipe n = (IRecipe) it.next();

			ItemStack output = n.getRecipeOutput();
			if(output == null) continue;
			Item item = output.getItem();
			if(item == null) continue;

			ResourceLocation blockName = (ResourceLocation) Item.itemRegistry.getNameForObject(item);
			if(!blockName.getResourceDomain().equals("minecraft")) continue;

			if(item instanceof ItemBlock) {
				Block block = Block.getBlockFromItem(item);
				if(block instanceof BlockStairs ||
						block == Blocks.stained_hardened_clay ||
						block == Blocks.stained_glass) {
					it.remove();
				}
			} else if(item == Items.glass_bottle ||
					item == Items.rabbit_stew ||
					item == Items.cookie ||
					item == Items.book) {
				it.remove();
			}
		}
	}

	private static void addNew() {
		GameRegistry.addShapelessRecipe(new ItemStack(Item.getItemFromBlock(Blocks.cobblestone_wall), 1, 1),
			new ItemStack(Blocks.cobblestone_wall, 1, 0), Item.getItemFromBlock(Blocks.vine));
		GameRegistry.addRecipe(new ItemStack(Blocks.trapped_chest),
			"WWW", "WHW", "WWW", 'W', Blocks.planks, 'H', Blocks.tripwire_hook);
		GameRegistry.addRecipe(new ItemStack(Items.painting),
			"SSS", "SPS", "SSS", 'S', Items.stick, 'P', Items.paper);
		GameRegistry.addRecipe(new ItemStack(Items.repeater),
			"R R", "TRT", "SSS", 'R', Items.redstone, 'T', Items.stick, 'S', Blocks.stone);
		GameRegistry.addRecipe(new ItemStack(Items.carrot_on_a_stick),
			"  T", " TS", "TCS", 'T', Items.stick, 'S', Items.string, 'C', Items.carrot);
		GameRegistry.addRecipe(new ItemStack(Items.glass_bottle, 3),
			"G G", " G ", 'G', Blocks.glass_pane);
		GameRegistry.addRecipe(new ItemStack(Items.glass_bottle, 8),
			"G G", " G ", 'G', Blocks.glass);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.rabbit_stew),
			Items.baked_potato, Items.cooked_rabbit, Items.bowl);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.cookie, 6),
			Items.wheat, new ItemStack(Items.dye, 1, EnumDyeColor.BROWN.getDyeDamage()));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.book, 2),
			Items.paper, Items.paper, Items.paper, Items.leather);
		addStairs();

		GameRegistry.addRecipe(new StainCrafting(Blocks.hardened_clay, Blocks.stained_hardened_clay));
		GameRegistry.addRecipe(new StainCrafting(Blocks.glass, Blocks.stained_glass));
		GameRegistry.addRecipe(new StainCrafting(Blocks.glass_pane, Blocks.stained_glass_pane));
		GameRegistry.addRecipe(new SurroundCrafting(Item.getItemFromBlock(Blocks.piston), Item.getItemFromBlock(Blocks.slime_block), Item.getItemFromBlock(Blocks.sticky_piston)));
		GameRegistry.addRecipe(new SurroundCrafting(Items.blaze_powder, Item.getItemFromBlock(Blocks.slime_block), Items.magma_cream));
		GameRegistry.addRecipe(new ToolUpgrades());

		RecipeSorter.register(CraftConvenience.MODID + ":staining", StainCrafting.class, Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register(CraftConvenience.MODID + ":surround", SurroundCrafting.class, Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register(CraftConvenience.MODID + ":tool_upgrades", ToolUpgrades.class, Category.SHAPELESS, "after:minecraft:shapeless");
	}

	private static void addStairs() {
		HashMap<ItemStack, Block> map = new HashMap<ItemStack, Block>();
		map.put(new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.getMetadata()), Blocks.oak_stairs);
		map.put(new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.getMetadata()), Blocks.birch_stairs);
		map.put(new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.spruce_stairs);
		map.put(new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()), Blocks.jungle_stairs);
		map.put(new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.ACACIA.getMetadata()), Blocks.acacia_stairs);
		map.put(new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.DARK_OAK.getMetadata()), Blocks.dark_oak_stairs);
		map.put(new ItemStack(Blocks.cobblestone), Blocks.stone_stairs);
		map.put(new ItemStack(Blocks.brick_block), Blocks.brick_stairs);
		map.put(new ItemStack(Blocks.stonebrick), Blocks.stone_brick_stairs);
		map.put(new ItemStack(Blocks.nether_brick), Blocks.nether_brick_stairs);
		map.put(new ItemStack(Blocks.sandstone), Blocks.sandstone_stairs);
		map.put(new ItemStack(Blocks.red_sandstone), Blocks.red_sandstone_stairs);
		map.put(new ItemStack(Blocks.quartz_block), Blocks.quartz_stairs);

		for(Map.Entry<ItemStack, Block> entry : map.entrySet()) {
			GameRegistry.addRecipe(new ItemStack(entry.getValue(), 4),
				"# ", "##", '#', entry.getKey());
		}
	}
}
