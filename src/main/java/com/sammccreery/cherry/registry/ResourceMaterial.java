package com.sammccreery.cherry.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sammccreery.cherry.item.CItemAxe;
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
import com.sammccreery.cherry.util.Name;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public enum ResourceMaterial {
	RUBY(CherryItems.GEM, MapColor.redColor, 4, "ruby"), EMERALD(CherryItems.GEM, "emerald") {
		@Override
		protected void addTools() {
			map.put(ItemType.SWORD, new ItemEmeraldSword(material));
			map.put(ItemType.PICKAXE, new ItemEmeraldPickaxe(material));
			map.put(ItemType.AXE, new ItemEmeraldAxe(material));
			map.put(ItemType.SHOVEL, new ItemEmeraldShovel(material));
			map.put(ItemType.HOE, new ItemHoe(material));
		}

		@Override
		protected void addResource() {
			map.put(ItemType.RESOURCE, Items.emerald);
			map.put(ItemType.BLOCK, Blocks.emerald_block);
			map.put(ItemType.ORE, Blocks.emerald_ore);
		}

		@Override
		public boolean skipResource() {
			return true;
		}
	},
	SAPPHIRE(CherryItems.GEM, MapColor.blueColor, 6, "sapphire"), TOPAZ(CherryItems.GEM, MapColor.yellowColor, 8, "topaz"),
	OBSIDIAN(CherryItems.OBSIDIAN, "obsidian") {
		@Override
		protected void addResource() {
			map.put(ItemType.RESOURCE, Blocks.obsidian);
			map.put(ItemType.BLOCK, Blocks.obsidian);
		}
	}, END(CherryItems.END, "end") {
		@Override
		protected void addTools() {
			map.put(ItemType.SWORD, new ItemEndSword(material));
			map.put(ItemType.PICKAXE, new ItemEndPickaxe(material));
			map.put(ItemType.AXE, new ItemEndAxe(material));
			map.put(ItemType.SHOVEL, new ItemEndShovel(material));
			map.put(ItemType.HOE, new ItemEndHoe(material));
		}

		@Override
		protected void addResource() {
			map.put(ItemType.RESOURCE, Blocks.end_stone);
			map.put(ItemType.BLOCK, Blocks.end_stone);
		}

		@Override
		protected Object[] getRecipe(ItemType type) {
			switch(type) {
				case SWORD:   return new Object[] {" # ", " # ", "@|@", '@', Items.ender_eye};
				case PICKAXE: return new Object[] {"###", " @ ", " | ", '@', Items.ender_eye};
				case AXE:     return new Object[] {"##",  "#@",  " |",  '@', Items.ender_eye};
				case SHOVEL:  return new Object[] {"#",   "@",   "|",   '@', Items.ender_eye};
				case HOE:     return new Object[] {"##",  " @",  " |",  '@', Items.ender_eye};
				default:      return super.getRecipe(type);
			}
		}
	};

	public final ToolMaterial material;
	public final MapColor color;
	public final Name name;
	protected final Map<ItemType, Object> map = new HashMap<ItemType, Object>();
	public final int xpLow, xpHigh;
	public final int veinSize;

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
		this(material, null, 3, 7, 8, name);
	}
	ResourceMaterial(ToolMaterial material, MapColor color, int veinSize, String name) {
		this(material, color, 3, 7, veinSize, name);
	}
	ResourceMaterial(ToolMaterial material, MapColor color, int xpLow, int xpHigh, int veinSize, String name) {
		this.material = material;
		this.color = color;
		this.xpLow = xpLow;
		this.xpHigh = xpHigh;
		this.veinSize = veinSize;
		this.name = new Name(name);
	}

	public void init() {
		addResource();
		addTools();

		registerObjects();
		registerRecipes();

		if(!skipResource()) {
			GameRegistry.addSmelting(getBlock(ItemType.ORE), new ItemStack(getItem(ItemType.RESOURCE)), 0.8F);
		}
	}

	protected void addTools() {
		map.put(ItemType.SWORD, new ItemSword(material));
		map.put(ItemType.PICKAXE, new ItemPickaxe(material));
		map.put(ItemType.AXE, new CItemAxe(material));
		map.put(ItemType.SHOVEL, new ItemSpade(material));
		map.put(ItemType.HOE, new ItemHoe(material));
	}

	protected void addResource() {
		map.put(ItemType.RESOURCE, new Item().setCreativeTab(CreativeTabs.tabMaterials));
		map.put(ItemType.BLOCK, new BlockCompressed(color).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F));
		map.put(ItemType.ORE, new BlockResourceOre(this).setStepSound(Block.soundTypePiston).setHardness(3.0F).setResistance(5.0F));
	}

	/** @return {@code true} if the material's resource doesn't need to be registered */
	public boolean skipResource() {
		return map.get(ItemType.RESOURCE) == map.get(ItemType.BLOCK);
	}

	/** @return {@code true} if the specified type needs registering */
	protected boolean shouldRegister(ItemType type) {
		return !skipResource()
			|| (type != ItemType.RESOURCE && type != ItemType.BLOCK && type != ItemType.ORE);
	}

	private void registerObjects() {
		for(Entry<ItemType, Object> entry : map.entrySet()) {
			if(shouldRegister(entry.getKey())) {
				map.put(entry.getKey(), Registry.register(entry.getValue(), Name.join(name, entry.getKey().name)));
			}
		}
	}

	protected ItemStack getCraftingOutput(ItemType type) {
		List<ItemStack> subItems = new ArrayList<ItemStack>();
		Item item = getItem(type);
		item.getSubItems(item, item.getCreativeTab(), subItems);

		ItemStack stack = !subItems.isEmpty() ? subItems.get(0) : new ItemStack(item);
		if(type == ItemType.RESOURCE) stack.stackSize = 9;
		return stack;
	}

	protected void registerRecipes() {
		final Object[] shortcuts = {
			'#', map.get(ItemType.RESOURCE),
			'|', Items.stick,
			'B', map.get(ItemType.BLOCK)
		};

		for(ItemType type : map.keySet()) {
			Object[] recipe = getRecipe(type);

			if(recipe != null) {
				recipe = Arrays.copyOf(recipe, recipe.length + shortcuts.length);
				System.arraycopy(shortcuts, 0, recipe, recipe.length - shortcuts.length, shortcuts.length);

				GameRegistry.addRecipe(getCraftingOutput(type), recipe);
			}
		}
	}

	protected Object[] getRecipe(ItemType type) {
		return shouldRegister(type) && type.recipe.length != 0 ? type.recipe : null;
	}

	public enum ItemType {
		SWORD(ItemSword.class, new Name("sword"), true, "#", "#", "|"),
		PICKAXE(net.minecraft.item.ItemPickaxe.class, new Name("pickaxe"), true, "###", " | ", " | "),
		AXE(net.minecraft.item.ItemAxe.class, new Name("axe"), true, "##", "#|", " |"),
		SHOVEL(ItemSpade.class, new Name("shovel"), true, "#", "|", "|"),
		HOE(ItemHoe.class, new Name("hoe"), true, "##", " |", " |"),
		RESOURCE(Item.class, Name.EMPTY, false, "B"),
		BLOCK(ItemBlock.class, new Name("block"), false, "###", "###", "###"),
		ORE(ItemBlock.class, new Name("ore"), false);

		public final Class<? extends Item> base;
		public final Name name;
		public final boolean isTool;
		public final Object[] recipe;

		/** The slot index of the first stick in the recipe. All other sticks will be ignored when upgrading tools */
		public final int firstStick;

		private static ItemType[] tools;
		static {
			List<ItemType> tools = new ArrayList<ItemType>(values().length);

			for(ItemType type : values()) {
				if(type.isTool) tools.add(type);
			}
			ItemType.tools = tools.toArray(new ItemType[tools.size()]);
		}

		public static ItemType[] tools() {
			return tools;
		}

		public InventoryCrafting getTemplate(ItemStack resource) {
			InventoryCrafting inv = new InventoryCrafting(new Container() {
				@Override
				public boolean canInteractWith(EntityPlayer player) {
					return true;
				}
			}, 3, 3);
			final ItemStack stick = new ItemStack(Items.stick);

			int height = recipe.length;
			int width = ((String)recipe[0]).length();

			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					int i = y * 3 + x;
					char c = ((String)recipe[y]).charAt(x);

					if(c == '#') inv.setInventorySlotContents(i, resource);
					else if(c == '|') inv.setInventorySlotContents(i, stick);
				}
			}
			return inv;
		}

		ItemType(Class<? extends Item> base, Name name, boolean isTool, Object... recipe) {
			this.base = base;
			this.name = name;
			this.isTool = isTool;
			this.recipe = recipe;

			int slot = -1;
			for(int y = 0; y < recipe.length; y++) {
				int x = ((String)recipe[y]).indexOf('|');

				if(x != -1) {
					slot = y*3 + x;
					break;
				}
			}
			this.firstStick = slot;
		}
	}
}
