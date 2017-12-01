package com.sammccreery.cherry;

import com.sammccreery.cherry.event.Events;
import com.sammccreery.cherry.net.CommonProxy;
import com.sammccreery.cherry.registry.CherryBlocks;
import com.sammccreery.cherry.registry.CherryGeneration;
import com.sammccreery.cherry.registry.CherryItems;
import com.sammccreery.cherry.registry.CherryRecipes;
import com.sammccreery.cherry.registry.CherryTileEntities;
import com.sammccreery.cherry.registry.Registry;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;

@Mod(modid=Cherry.MODID, name="Cherry", version="0.1", useMetadata=true)
public class Cherry {
	public static final String MODID = "cherry";

	@Instance
	public static Cherry INSTANCE;

	@SidedProxy(clientSide="com.sammccreery.cherry.net.ClientProxy", serverSide="com.sammccreery.cherry.net.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		new Config(e.getSuggestedConfigurationFile()).load();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		Registry.addRegister(Item.class, new CherryItems());
		Registry.addRegister(Block.class, new CherryBlocks());
		Registry.addRegister(Object.class, new Events());
		Registry.addRegister(IRecipe.class, new CherryRecipes());
		Registry.addRegister(IWorldGenerator.class, new CherryGeneration());
		Registry.addRegister(Class.class, new CherryTileEntities());
		Registry.initAll(e);

		// TODO move this to a register?
		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		Registry.initAll(e);
		proxy.init();
	}
}
