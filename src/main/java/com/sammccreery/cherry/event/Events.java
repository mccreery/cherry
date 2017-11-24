package com.sammccreery.cherry.event;

import com.sammccreery.cherry.item.ItemEndPickaxe;
import com.sammccreery.cherry.item.ItemEndSword;
import com.sammccreery.cherry.util.Funcs;
import com.sammccreery.cherry.util.InventoryUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class Events {
	@SubscribeEvent
	public void onBlockBreak(HarvestDropsEvent e) {
		if(e.harvester != null &&
				e.harvester.getHeldItem() != null &&
				(e.harvester.getHeldItem().getItem() instanceof ItemEndPickaxe || 
				e.harvester.getHeldItem().getItem() instanceof ItemEndAxe || 
				e.harvester.getHeldItem().getItem() instanceof ItemEndShovel)) {
			InventoryBasic enderChest = e.harvester.getInventoryEnderChest();
			for(ItemStack item : e.drops) {
				InventoryUtils.addItemStackToInventory(enderChest, item);
			}

			Funcs.spawnParticlesAt(e.world, e.x, e.y, e.z, 10, 0.3F);
		}
	}

	@SubscribeEvent
	public void onEntityRightClick(EntityInteractEvent e) {
		ItemStack stack = e.entityPlayer.getHeldItem();

		if(stack.getItem() instanceof ItemEndSword) {
			ItemEndSword.capture(stack, e.entityLiving.worldObj, e.entityPlayer, e.entityLiving);
		}
	}
}
