package com.sammccreery.cherry.registry;

import com.sammccreery.cherry.util.UniversalName;
import com.sammccreery.cherry.util.UniversalName.Format;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public final class CherryItems extends Registry<Item> {
	public static final ToolMaterial
		GEM      = EnumHelper.addToolMaterial("gem", 2, 200, 10.0F, 2.5F, 30),
		OBSIDIAN = EnumHelper.addToolMaterial("obsidian", 3, 4000, 3.5F, 2.0F, 5),
		END      = EnumHelper.addToolMaterial("end", 3, 1000, 8.0F, 3.0F, 20);

	@Override
	public void init() {
		for(ResourceMaterial material : ResourceMaterial.values()) {
			material.init();
		}
	}

	@Override
	public Item registerLocal(Item item, UniversalName name) {
		item.setUnlocalizedName(name.format(false, Format.HEADLESS));
		item.setTextureName(name.format(true, Format.SNAKE));
		GameRegistry.registerItem(item, name.format(false, Format.SNAKE));

		return item;
	}
}
