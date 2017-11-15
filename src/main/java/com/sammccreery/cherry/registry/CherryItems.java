package com.sammccreery.cherry.registry;

import com.sammccreery.cherry.item.ResourceMaterial;
import com.sammccreery.cherry.util.ResourceName;
import com.sammccreery.cherry.util.ResourceName.Format;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public final class CherryItems {
	private CherryItems() {}

	public static final ToolMaterial
		GEM      = EnumHelper.addToolMaterial("gem", 2, 200, 10.0F, 2.5F, 30),
		OBSIDIAN = EnumHelper.addToolMaterial("obsidian", 3, 4000, 3.5F, 2.0F, 5),
		END      = EnumHelper.addToolMaterial("end", 3, 1000, 8.0F, 3.0F, 20);

	public static void init() {
		for(ResourceMaterial material : ResourceMaterial.values()) {
			material.init();
		}
	}

	public static Item registerItem(Item item, ResourceName name) {
		item.setUnlocalizedName(name.format(false, Format.HEADLESS));
		item.setTextureName(name.format(true, Format.SNAKE));
		GameRegistry.registerItem(item, name.format(false, Format.SNAKE));

		return item;
	}
}
