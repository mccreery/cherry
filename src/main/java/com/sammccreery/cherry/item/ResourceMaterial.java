package com.sammccreery.cherry.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sammccreery.cherry.registry.CherryBlocks;
import com.sammccreery.cherry.registry.CherryItems;
import com.sammccreery.cherry.util.ResourceName;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public enum ResourceMaterial {
	RUBY(CherryItems.GEM, MapColor.redColor, "ruby"), EMERALD(CherryItems.GEM, null, "emerald") {
		@Override
		protected void addItems() {
			items.put(ItemType.RESOURCE, Items.emerald);
			items.put(ItemType.SWORD, new ItemEmeraldSword(material));
			items.put(ItemType.PICKAXE, new ItemEmeraldPickaxe(material));
			items.put(ItemType.AXE, new ItemEmeraldAxe(material));
			items.put(ItemType.SHOVEL, new ItemEmeraldShovel(material));
			items.put(ItemType.HOE, new ItemHoe(material));
		}

		@Override
		protected void addBlock() {
			items.put(ItemType.BLOCK, Item.getItemFromBlock(Blocks.emerald_block));
		}

		@Override
		protected void registerCompressRecipes() {} // These recipes already exist
	},
	SAPPHIRE(CherryItems.GEM, MapColor.blueColor, "sapphire"), TOPAZ(CherryItems.GEM, MapColor.yellowColor, "topaz"),
	OBSIDIAN(CherryItems.OBSIDIAN, null, "obsidian") {
		@Override
		protected void addItems() {
			super.addItems();
			items.put(ItemType.RESOURCE, Item.getItemFromBlock(Blocks.obsidian));
		}

		@Override
		protected void addBlock() {
			items.put(ItemType.BLOCK, Item.getItemFromBlock(Blocks.obsidian));
		}
	}, END(CherryItems.END, null, "end") {
		@Override
		protected void addItems() {
			items.put(ItemType.RESOURCE, Item.getItemFromBlock(Blocks.end_stone));
			items.put(ItemType.SWORD, new ItemEndSword(material));
			items.put(ItemType.PICKAXE, new ItemEndPickaxe(material));
			items.put(ItemType.AXE, new ItemEndAxe(material));
			items.put(ItemType.SHOVEL, new ItemEndShovel(material));
			items.put(ItemType.HOE, new ItemEndHoe(material));
		}

		@Override
		protected void registerRecipes() {
			GameRegistry.addRecipe(new ItemStack(items.get(ItemType.SWORD)),
				" # ", " # ", "@|@", '#', items.get(ItemType.RESOURCE), '|', Items.stick, '@', Items.ender_eye);
			GameRegistry.addRecipe(new ItemStack(items.get(ItemType.PICKAXE)),
				"###", " @ ", " | ", '#', items.get(ItemType.RESOURCE), '|', Items.stick, '@', Items.ender_eye);
			GameRegistry.addRecipe(new ItemStack(items.get(ItemType.AXE)),
				"##", "#@", " |", '#', items.get(ItemType.RESOURCE), '|', Items.stick, '@', Items.ender_eye);
			GameRegistry.addRecipe(new ItemStack(items.get(ItemType.SHOVEL)),
				"#", "@", "|", '#', items.get(ItemType.RESOURCE), '|', Items.stick, '@', Items.ender_eye);
			GameRegistry.addRecipe(new ItemStack(items.get(ItemType.HOE)),
				"##", " @", " |", '#', items.get(ItemType.RESOURCE), '|', Items.stick, '@', Items.ender_eye);
		}

		@Override
		protected void addBlock() {
			items.put(ItemType.BLOCK, Item.getItemFromBlock(Blocks.end_stone));
		}
	};

	public final ToolMaterial material;
	public final MapColor color;
	public final ResourceName name;
	public final Map<ItemType, Item> items = new HashMap<ItemType, Item>();

	ResourceMaterial(ToolMaterial material, MapColor color, String name) {
		this.material = material;
		this.color = color;
		this.name = new ResourceName(name);
	}

	public void init() {
		addBlock();
		addItems();
		registerItems();
		registerRecipes();
	}

	protected void addBlock() {
		Block block = new BlockCompressed(color);
		block.setStepSound(Block.soundTypeMetal);
		block.setHardness(5.0F);
		block.setResistance(10.0F);

		CherryBlocks.registerBlock(block, new ResourceName(name, ItemType.BLOCK.name));
		items.put(ItemType.BLOCK, Item.getItemFromBlock(block));
	}

	protected void addItems() {
		items.put(ItemType.RESOURCE, new Item().setCreativeTab(CreativeTabs.tabMaterials));
		items.put(ItemType.SWORD, new ItemSword(material));
		items.put(ItemType.PICKAXE, new ItemPickaxe(material));
		items.put(ItemType.AXE, new ItemAxe(material));
		items.put(ItemType.SHOVEL, new ItemSpade(material));
		items.put(ItemType.HOE, new ItemHoe(material));
	}

	private void registerItems() {
		for(Entry<ItemType, Item> entry : items.entrySet()) {
			if(entry.getKey() != ItemType.BLOCK && Item.getIdFromItem(entry.getValue()) == -1) {
				ResourceName itemName = new ResourceName(name, entry.getKey().name);
				items.put(entry.getKey(), CherryItems.registerItem(entry.getValue(), itemName));
			}
		}
	}

	protected ItemStack createStack(ItemType type) {
		List<ItemStack> temp = new ArrayList<ItemStack>();
		Item item = items.get(type);
		item.getSubItems(item, item.getCreativeTab(), temp);

		return !temp.isEmpty() ? temp.get(0) : new ItemStack(item);
	}

	protected void registerRecipes() {
		for(ItemType type : items.keySet()) {
			if(type.recipe.length > 0) {
				Object[] params = new Object[type.recipe.length + 6];

				int i;
				for(i = 0; i < type.recipe.length; i++) {
					params[i] = type.recipe[i];
				}
				params[i++] = '#';
				params[i++] = items.get(ItemType.RESOURCE);
				params[i++] = '|';
				params[i++] = Items.stick;
				params[i++] = 'B';
				params[i++] = items.get(ItemType.BLOCK);

				GameRegistry.addRecipe(createStack(type), params);
			}
		}

		if(items.get(ItemType.BLOCK) != items.get(ItemType.RESOURCE)) {
			registerCompressRecipes();
		}
	}

	protected void registerCompressRecipes() {
		GameRegistry.addRecipe(createStack(ItemType.BLOCK), "###", "###", "###", '#', items.get(ItemType.RESOURCE));

		ItemStack resourceStack = createStack(ItemType.RESOURCE);
		resourceStack.stackSize = 9;
		GameRegistry.addShapelessRecipe(resourceStack, items.get(ItemType.BLOCK));
	}

	public enum ItemType {
		SWORD(new ResourceName("sword"), "#", "#", "|"),
		PICKAXE(new ResourceName("pickaxe"), "###", " | ", " | "),
		AXE(new ResourceName("axe"), "##", "#|", " |"),
		SHOVEL(new ResourceName("shovel"), "#", "|", "|"),
		HOE(new ResourceName("hoe"), "##", " |", " |"),
		RESOURCE(new ResourceName("")),
		BLOCK(new ResourceName("block"));

		public final ResourceName name;
		public final String[] recipe;

		ItemType(ResourceName name, String... recipe) {
			this.name = name;
			this.recipe = recipe;
		}
	}
}
