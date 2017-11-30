package com.sammccreery.cherry.event;

import static com.sammccreery.cherry.util.Util.RANDOM;

import com.sammccreery.cherry.item.ItemEndAxe;
import com.sammccreery.cherry.item.ItemEndPickaxe;
import com.sammccreery.cherry.item.ItemEndShovel;
import com.sammccreery.cherry.registry.CherryItems;
import com.sammccreery.cherry.registry.Registry;
import com.sammccreery.cherry.util.StackUtils;
import com.sammccreery.cherry.util.UniversalName;
import com.sammccreery.cherry.util.Util;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class Events extends Registry<Object> {
	@SubscribeEvent
	public void onBlockBreak(HarvestDropsEvent e) {
		if(e.harvester == null || !isEndTool(e.harvester.getHeldItem())) {
			return;
		}

		if(e.block == Blocks.ender_chest) {
			e.world.createExplosion(null, e.x + 0.5, e.y + 0.5, e.z + 0.5, 3.0F, true);
		} else if(!e.world.isRemote && e.world.getGameRules().getGameRuleBooleanValue("doTileDrops") && !e.world.restoringBlockSnapshots) {
			InventoryBasic enderChest = e.harvester.getInventoryEnderChest();

			for(ItemStack stack : e.drops) {
				StackUtils.storeStack(enderChest, stack);
			}
			Util.teleportEffect(e.world, e.x + 0.5D, e.y + 0.5D, e.z + 0.5D, 10, 0.3F);
		}
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent e) {
		// Empty cauldron using sponge
		if(!e.world.isRemote && e.action == Action.RIGHT_CLICK_BLOCK
				&& !Util.isUseForced(e.entityPlayer, e.world, e.x, e.y, e.z)
				&& e.entityPlayer.getHeldItem() != null
				&& e.entityPlayer.getHeldItem().getItem() == Item.getItemFromBlock(Blocks.sponge)
				&& e.world.getBlock(e.x, e.y, e.z) == Blocks.cauldron
				&& e.world.getBlockMetadata(e.x, e.y, e.z) != 0) {
			e.world.setBlockMetadataWithNotify(e.x, e.y, e.z, 0, 2);
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onEntityDrops(LivingDropsEvent e) {
		if(e.recentlyHit && RANDOM.nextInt(3) == 0) {
			int hearts = (int)(e.entityLiving.getMaxHealth() * RANDOM.nextFloat() * 0.4f);

			if(hearts > 0) {
				e.entity.dropItem(CherryItems.heart, hearts);
			}
		}
	}

	@SubscribeEvent
	public void onEntityPickup(EntityItemPickupEvent e) {
		ItemStack stack = e.item.getEntityItem();

		if(stack.getItem() == CherryItems.heart) {
			e.entityLiving.heal(stack.stackSize);
			e.entity.worldObj.playSoundAtEntity(e.entity, "random.levelup", 1.0F, 2.0F);
			e.setCanceled(true);
			e.item.setDead();
		}
	}

	private static boolean isEndTool(ItemStack stack) {
		if(StackUtils.isEmpty(stack)) return false;

		Item item = stack.getItem();
		return item instanceof ItemEndPickaxe
			|| item instanceof ItemEndAxe
			|| item instanceof ItemEndShovel;
	}

	@Override
	public Object registerLocal(Object obj, UniversalName name) {
		MinecraftForge.EVENT_BUS.register(obj);
		FMLCommonHandler.instance().bus().register(obj);
		return obj;
	}

	@Override
	public void init() {
		registerLocal(this);
	}
}
