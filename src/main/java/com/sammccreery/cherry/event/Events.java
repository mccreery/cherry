package com.sammccreery.cherry.event;

import com.sammccreery.cherry.item.ItemEndAxe;
import com.sammccreery.cherry.item.ItemEndPickaxe;
import com.sammccreery.cherry.item.ItemEndShovel;
import com.sammccreery.cherry.item.ItemEndSword;
import com.sammccreery.cherry.util.Util;
import com.sammccreery.cherry.util.InventoryUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
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

			Util.teleportEffect(e.world, e.x + 0.5D, e.y + 0.5D, e.z + 0.5D, 10, 0.3F);
		}
	}

	@SubscribeEvent
	public void onEntityRightClick(EntityInteractEvent e) {
		ItemStack stack = e.entityPlayer.getHeldItem();

		if(stack.getItem() instanceof ItemEndSword && e.target instanceof EntityLivingBase) {
			ItemEndSword.capture(stack, e.entityLiving.worldObj, e.entityPlayer, (EntityLivingBase)e.target);
		}
	}
}
