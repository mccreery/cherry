package com.sammccreery.cherry.registry;

import com.sammccreery.cherry.item.ItemFoodCanister;
import com.sammccreery.cherry.util.Name;
import com.sammccreery.cherry.util.Name.Format;
import com.sammccreery.cherry.util.Names;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemFood;
import net.minecraftforge.common.util.EnumHelper;

public final class CherryItems extends Registry<Item> {
	public static final ToolMaterial
		GEM      = EnumHelper.addToolMaterial("gem", 2, 200, 10.0F, 2.5F, 30),
		OBSIDIAN = EnumHelper.addToolMaterial("obsidian", 3, 4000, 3.5F, 2.0F, 5),
		END      = EnumHelper.addToolMaterial("end", 3, 1000, 8.0F, 3.0F, 20);

	public static final Item heart = new Item();
	public static final Item heartShard = new ItemFood(0, 0, false).setPotionEffect(10, 5, 2, 1.0F).setAlwaysEdible().setCreativeTab(CreativeTabs.tabMisc);
	public static final Item foodCanister = new ItemFoodCanister();

	@Override
	public void init() {
		for(ResourceMaterial material : ResourceMaterial.values()) {
			material.init();
		}
		registerLocal(heart, Names.HEART);
		registerLocal(heartShard, Names.HEART_SHARD);
		registerLocal(foodCanister, Names.FOOD_CANISTER);
	}

	@Override
	public Item registerLocal(Item item, Name name) {
		item.setUnlocalizedName(name.format(Format.HEADLESS, false));
		item.setTextureName(name.format(Format.SNAKE, true));
		GameRegistry.registerItem(item, name.format(Format.SNAKE, false));

		return item;
	}
}
