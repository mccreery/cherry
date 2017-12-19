package com.sammccreery.cherry.registry;

import com.sammccreery.cherry.block.TileEntityHeartCrystal;
import com.sammccreery.cherry.block.TileEntityHeartLantern;
import com.sammccreery.cherry.util.Name;
import com.sammccreery.cherry.util.Name.Format;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.tileentity.TileEntity;

@SuppressWarnings("rawtypes")
public class CherryTileEntities extends Registry<Class> {
	@SuppressWarnings("unchecked")
	@Override
	public Class registerLocal(Class obj, Name name) {
		GameRegistry.registerTileEntity((Class<? extends TileEntity>)obj, name.format(Format.CAMELCASE, false));
		return obj;
	}

	@Override
	public void init() {
		registerLocal(TileEntityHeartCrystal.class, Name.HEART_CRYSTAL);
		registerLocal(TileEntityHeartLantern.class, Name.HEART_LANTERN);
	}
}
