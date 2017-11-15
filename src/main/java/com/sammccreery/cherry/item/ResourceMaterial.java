package com.sammccreery.cherry.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.sammccreery.cherry.registry.CherryItems;
import com.sammccreery.cherry.util.ResourceName;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;

public enum ResourceMaterial {
	RUBY(CherryItems.GEM, "ruby"), EMERALD(CherryItems.GEM, "emerald") {
		@Override
		protected void addItems() {
			items.put(ItemType.RESOURCE, Items.emerald);
			items.put(ItemType.SWORD, new ItemEmeraldSword(material));
			items.put(ItemType.PICKAXE, new ItemEmeraldPickaxe(material));
			items.put(ItemType.AXE, new ItemEmeraldAxe(material));
			items.put(ItemType.SHOVEL, new ItemEmeraldShovel(material));
			items.put(ItemType.HOE, new ItemEmeraldHoe(material));
		}
	},
	SAPPHIRE(CherryItems.GEM, "sapphire"), TOPAZ(CherryItems.GEM, "topaz"),
	OBSIDIAN(CherryItems.OBSIDIAN, "obsidian") {
		@Override
		protected void addItems() {
			super.addItems();
			items.put(ItemType.RESOURCE, Item.getItemFromBlock(Blocks.obsidian));
		}
	}, END(CherryItems.END, "end") {
		@Override
		protected void addItems() {
			items.put(ItemType.RESOURCE, Item.getItemFromBlock(Blocks.end_stone));
			items.put(ItemType.SWORD, new ItemEndSword(material));
		}
	};

	public final ToolMaterial material;
	public final ResourceName name;
	public final Map<ItemType, Item> items = new HashMap<ItemType, Item>();

	ResourceMaterial(ToolMaterial material, String name) {
		this.material = material;
		this.name = new ResourceName(name);
	}

	public void init() {
		addItems();
		registerItems();
	}

	protected void addItems() {
		items.put(ItemType.RESOURCE, new Item());
		items.put(ItemType.SWORD, new ItemSword(material));
		items.put(ItemType.PICKAXE, new ItemPickaxe(material));
		items.put(ItemType.AXE, new ItemAxe(material));
		items.put(ItemType.SHOVEL, new ItemSpade(material));
		items.put(ItemType.HOE, new ItemHoe(material));
	}

	private void registerItems() {
		for(Entry<ItemType, Item> entry : items.entrySet()) {
			if(Item.getIdFromItem(entry.getValue()) == -1) {
				ResourceName itemName = new ResourceName(name, entry.getKey().name);
				items.put(entry.getKey(), CherryItems.registerItem(entry.getValue(), itemName));
			}
		}
	}

	public enum ItemType {
		SWORD(new ResourceName("sword")),
		PICKAXE(new ResourceName("pickaxe")),
		AXE(new ResourceName("axe")),
		SHOVEL(new ResourceName("shovel")),
		HOE(new ResourceName("hoe")),
		RESOURCE(new ResourceName(""));

		public final ResourceName name;

		ItemType(ResourceName name) {
			this.name = name;
		}
	}
}
