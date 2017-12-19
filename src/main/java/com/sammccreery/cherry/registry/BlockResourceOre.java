package com.sammccreery.cherry.registry;

import java.util.Random;

import com.sammccreery.cherry.registry.ResourceMaterial.ItemType;
import static com.sammccreery.cherry.util.WorldUtil.RANDOM;

import net.minecraft.block.BlockOre;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;

public class BlockResourceOre extends BlockOre {
	private final ResourceMaterial material;

	public BlockResourceOre(ResourceMaterial material) {
		this.material = material;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return material.getItem(ItemType.RESOURCE);
	}

	@Override
	public int getExpDrop(IBlockAccess p_149690_1_, int p_149690_5_, int p_149690_7_) {
		return RANDOM.nextInt(material.xpHigh - material.xpLow) + material.xpLow;
	}
}
