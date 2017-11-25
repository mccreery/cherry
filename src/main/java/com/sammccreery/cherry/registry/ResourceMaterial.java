package com.sammccreery.cherry.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sammccreery.cherry.item.ItemAxe;
import com.sammccreery.cherry.item.ItemEmeraldAxe;
import com.sammccreery.cherry.item.ItemEmeraldPickaxe;
import com.sammccreery.cherry.item.ItemEmeraldShovel;
import com.sammccreery.cherry.item.ItemEmeraldSword;
import com.sammccreery.cherry.item.ItemEndAxe;
import com.sammccreery.cherry.item.ItemEndHoe;
import com.sammccreery.cherry.item.ItemEndPickaxe;
import com.sammccreery.cherry.item.ItemEndShovel;
import com.sammccreery.cherry.item.ItemEndSword;
import com.sammccreery.cherry.item.ItemPickaxe;
import com.sammccreery.cherry.util.UniversalName;

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
			map.put(ItemType.RESOURCE, Items.emerald);
			map.put(ItemType.SWORD, new ItemEmeraldSword(material));
			map.put(ItemType.PICKAXE, new ItemEmeraldPickaxe(material));
			map.put(ItemType.AXE, new ItemEmeraldAxe(material));
			map.put(ItemType.SHOVEL, new ItemEmeraldShovel(material));
			map.put(ItemType.HOE, new ItemHoe(material));
		}

		@Override
		protected void addBlock() {
			map.put(ItemType.BLOCK, Item.getItemFromBlock(Blocks.emerald_block));
		}

		@Override
		protected boolean shouldGenerateExtras() {
			return false;
		}

		@Override
		protected boolean shouldRegister(ItemType type) {
			return type != ItemType.RESOURCE && type != ItemType.BLOCK;
		}
	},
	SAPPHIRE(CherryItems.GEM, MapColor.blueColor, "sapphire"), TOPAZ(CherryItems.GEM, MapColor.yellowColor, "topaz"),
	OBSIDIAN(CherryItems.OBSIDIAN, null, "obsidian") {
		@Override
		protected void addItems() {
			super.addItems();
			map.put(ItemType.RESOURCE, Item.getItemFromBlock(Blocks.obsidian));
		}

		@Override
		protected void addBlock() {
			map.put(ItemType.BLOCK, Item.getItemFromBlock(Blocks.obsidian));
		}
	}, END(CherryItems.END, null, "end") {
		@Override
		protected void addItems() {
			map.put(ItemType.RESOURCE, Item.getItemFromBlock(Blocks.end_stone));
			map.put(ItemType.SWORD, new ItemEndSword(material));
			map.put(ItemType.PICKAXE, new ItemEndPickaxe(material));
			map.put(ItemType.AXE, new ItemEndAxe(material));
			map.put(ItemType.SHOVEL, new ItemEndShovel(material));
			map.put(ItemType.HOE, new ItemEndHoe(material));
		}

		@Override
		protected void registerRecipes() {
			GameRegistry.addRecipe(createStack(ItemType.SWORD),
				" # ", " # ", "@|@", '#', getItem(ItemType.RESOURCE), '|', Items.stick, '@', Items.ender_eye);
			GameRegistry.addRecipe(createStack(ItemType.PICKAXE),
				"###", " @ ", " | ", '#', getItem(ItemType.RESOURCE), '|', Items.stick, '@', Items.ender_eye);
			GameRegistry.addRecipe(createStack(ItemType.AXE),
				"##", "#@", " |", '#', getItem(ItemType.RESOURCE), '|', Items.stick, '@', Items.ender_eye);
			GameRegistry.addRecipe(createStack(ItemType.SHOVEL),
				"#", "@", "|", '#', getItem(ItemType.RESOURCE), '|', Items.stick, '@', Items.ender_eye);
			GameRegistry.addRecipe(createStack(ItemType.HOE),
				"##", " @", " |", '#', getItem(ItemType.RESOURCE), '|', Items.stick, '@', Items.ender_eye);
		}

		@Override
		protected void addBlock() {
			map.put(ItemType.BLOCK, Item.getItemFromBlock(Blocks.end_stone));
		}
	};

	public final ToolMaterial material;
	public final MapColor color;
	public final UniversalName name;
	protected final Map<ItemType, Object> map = new HashMap<ItemType, Object>();
	public final int xpLow, xpHigh;

	public Block getBlock(ItemType type) {
		Object obj = map.get(type);

		if(obj instanceof Item) {
			return Block.getBlockFromItem((Item)obj);
		} else if(obj instanceof Block) {
			return (Block)obj;
		} else {
			return null;
		}
	}

	public Item getItem(ItemType type) {
		Object obj = map.get(type);

		if(obj instanceof Item) {
			return (Item)obj;
		} else if(obj instanceof Block) {
			return Item.getItemFromBlock((Block)obj);
		} else {
			return null;
		}
	}

	ResourceMaterial(ToolMaterial material, String name) {
		this(material, null, 3, 7, name);
	}
	ResourceMaterial(ToolMaterial material, MapColor color, String name) {
		this(material, color, 3, 7, name);
	}
	ResourceMaterial(ToolMaterial material, MapColor color, int xpLow, int xpHigh, String name) {
		this.material = material;
		this.color = color;
		this.xpLow = xpLow;
		this.xpHigh = xpHigh;
		this.name = new UniversalName(name);
	}

	public void init() {
		addBlock();
		addItems();
		if(shouldGenerateExtras()) addOre();
		registerObjects();
		registerRecipes();
	}

	/** @return {@code true} if ores and compressed blocks should be generated */
	protected boolean shouldGenerateExtras() {
		return map.get(ItemType.RESOURCE) != map.get(ItemType.BLOCK);
	}

	/** @return {@code true} if the specified type needs registering */
	protected boolean shouldRegister(ItemType type) {
		return shouldGenerateExtras() || (type != ItemType.RESOURCE && type != ItemType.BLOCK && type != ItemType.ORE);
	}

	protected void addBlock() {
		Block block = new BlockCompressed(color);
		block.setStepSound(Block.soundTypeMetal);
		block.setHardness(5.0F);
		block.setResistance(10.0F);

		map.put(ItemType.BLOCK, block);
	}

	protected void addOre() {
		Block ore = new BlockResourceOre(this);
		ore.setStepSound(Block.soundTypePiston);
		ore.setHardness(3.0F);
		ore.setResistance(5.0F);

		map.put(ItemType.ORE, ore);
	}

	protected void addItems() {
		map.put(ItemType.RESOURCE, new Item().setCreativeTab(CreativeTabs.tabMaterials));
		map.put(ItemType.SWORD, new ItemSword(material));
		map.put(ItemType.PICKAXE, new ItemPickaxe(material));
		map.put(ItemType.AXE, new ItemAxe(material));
		map.put(ItemType.SHOVEL, new ItemSpade(material));
		map.put(ItemType.HOE, new ItemHoe(material));
	}

	private void registerObjects() {
		for(Entry<ItemType, Object> entry : map.entrySet()) {
			if(shouldRegister(entry.getKey())) {
				map.put(entry.getKey(), Registry.register(entry.getValue(), new UniversalName(name, entry.getKey().name)));
			}
		}
	}

	protected ItemStack createStack(ItemType type) {
		List<ItemStack> temp = new ArrayList<ItemStack>();
		Item item = getItem(type);
		item.getSubItems(item, item.getCreativeTab(), temp);

		return !temp.isEmpty() ? temp.get(0) : new ItemStack(item);
	}

	protected void registerRecipes() {
		for(ItemType type : map.keySet()) {
			if(type.recipe.length > 0) {
				Object[] params = new Object[type.recipe.length + 6];

				int i;
				for(i = 0; i < type.recipe.length; i++) {
					params[i] = type.recipe[i];
				}
				params[i++] = '#';
				params[i++] = map.get(ItemType.RESOURCE);
				params[i++] = '|';
				params[i++] = Items.stick;
				params[i++] = 'B';
				params[i++] = map.get(ItemType.BLOCK);

				GameRegistry.addRecipe(createStack(type), params);
			}
		}

		if(shouldGenerateExtras()) {
			registerCompressRecipes();
		}
	}

	protected void registerCompressRecipes() {
		GameRegistry.addRecipe(createStack(ItemType.BLOCK), "###", "###", "###", '#', map.get(ItemType.RESOURCE));

		ItemStack resourceStack = createStack(ItemType.RESOURCE);
		resourceStack.stackSize = 9;
		GameRegistry.addShapelessRecipe(resourceStack, map.get(ItemType.BLOCK));
	}

	public enum ItemType {
		SWORD(new UniversalName("sword"), "#", "#", "|"),
		PICKAXE(new UniversalName("pickaxe"), "###", " | ", " | "),
		AXE(new UniversalName("axe"), "##", "#|", " |"),
		SHOVEL(new UniversalName("shovel"), "#", "|", "|"),
		HOE(new UniversalName("hoe"), "##", " |", " |"),
		RESOURCE(new UniversalName("")),
		BLOCK(new UniversalName("block")),
		ORE(new UniversalName("ore"));

		public final UniversalName name;
		public final String[] recipe;

		ItemType(UniversalName name, String... recipe) {
			this.name = name;
			this.recipe = recipe;
		}
	}
}
