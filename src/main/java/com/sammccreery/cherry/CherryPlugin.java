package com.sammccreery.cherry;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@MCVersion("1.7.10")
public class CherryPlugin implements IFMLLoadingPlugin {
	@Override
	public String[] getASMTransformerClass() {
		return new String[] {
			"com.sammccreery.cherry.registry.SpongeClassTransformer"
		};
	}

	@Override
	public String getModContainerClass() {return null;}
	@Override
	public String getSetupClass() {return null;}
	@Override
	public void injectData(Map<String, Object> data) {}
	@Override
	public String getAccessTransformerClass() {return null;}
}
