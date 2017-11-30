package com.sammccreery.cherry.registry;

import com.sammccreery.cherry.block.TileEntityHeartCrystal;
import com.sammccreery.cherry.block.TileEntityHeartLantern;
import com.sammccreery.cherry.util.Names;
import com.sammccreery.cherry.util.UniversalName;
import com.sammccreery.cherry.util.UniversalName.Format;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.tileentity.TileEntity;

@SuppressWarnings("rawtypes")
public class CherryTileEntities extends Registry<Class> {
	@SuppressWarnings("unchecked")
	@Override
	public Class registerLocal(Class obj, UniversalName name) {
		GameRegistry.registerTileEntity((Class<? extends TileEntity>)obj, name.format(false, Format.CAMELCASE));
		return obj;
	}

	@Override
	public void init() {
		registerLocal(TileEntityHeartCrystal.class, Names.HEART_CRYSTAL);
		registerLocal(TileEntityHeartLantern.class, Names.HEART_LANTERN);
	}
}
