package nukeduck.coinage.event;

import java.util.Iterator;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nukeduck.coinage.CoinDropRegister;
import nukeduck.coinage.Coinage;
import nukeduck.coinage.entity.EntityItemCoin;
import nukeduck.coinage.inventory.ContainerCoinBag;
import nukeduck.coinage.inventory.InventoryCoinBag;
import nukeduck.coinage.item.EnchantmentPersistence;
import nukeduck.coinage.item.ItemCoin;
import nukeduck.coinage.item.ItemCoinBag;
import nukeduck.coinage.util.WorldUtil;
import scala.util.Random;

@SideOnly(Side.CLIENT)
public class CommonEvents {
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onRespawn(PlayerRespawnEvent e) {
		NBTTagCompound persisted = e.player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		
		if(persisted.hasKey("CoinBags")) {
			NBTTagList coinBags = persisted.getTagList("CoinBags", 10);
			for(int i = 0; i < coinBags.tagCount(); i++) {
				NBTTagCompound item = coinBags.getCompoundTagAt(i);
				ItemStack stack = new ItemStack(Blocks.stone);
				stack.readFromNBT(item);
				e.player.inventory.setInventorySlotContents(item.getByte("Slot"), stack);
			}
		}
	}
	
	@SubscribeEvent
	public void onDeath(LivingDeathEvent e) {
		if(e.entityLiving instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) e.entityLiving;
			if(!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
				player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
			}
			
			NBTTagCompound persisted = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
			NBTTagList coinBags = new NBTTagList();
			
			for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);
				
				if(stack != null && stack.getItem() instanceof ItemCoinBag) {
					NBTTagCompound item = new NBTTagCompound();
					item.setByte("Slot", (byte) i);
					stack.writeToNBT(item);
					coinBags.appendTag(item);
				}
			}
			persisted.setTag("CoinBags", coinBags);
		}
	}
	
	@SubscribeEvent
	public void onDrops(LivingDropsEvent e) {
		int coins = 0;
		boolean allowGreed = false;
		
		if(e.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e.entityLiving;
			Iterator<EntityItem> it = e.drops.iterator();
			while(it.hasNext()) {
				EntityItem i = it.next();
				if(i.getEntityItem().getItem() instanceof ItemCoinBag) {
					it.remove();
					ItemStack stack = i.getEntityItem();
					
					InventoryCoinBag inventory = new InventoryCoinBag(stack);
					int totalCoins = ItemCoinBag.coinCount(stack);
					int keptCoins = EnchantmentPersistence.getCoinsPersisted(stack);
					
					coins += inventory.removeCoins(totalCoins - keptCoins, 0);
				}
			}
		} else {
			coins = Coinage.instance.coinDropRegister.get(e.entityLiving);
			allowGreed = true;
		}
		
		if(coins == 0) return;
		WorldUtil.createCoins(coins, e.entityLiving.worldObj, e.entityLiving.posX, e.entityLiving.posY, e.entityLiving.posZ, e.drops, allowGreed);
	}
	
	@SubscribeEvent
	public void onPickup(EntityItemPickupEvent e) {
		if(!(e.item instanceof EntityItemCoin) || !(e.item.getEntityItem().getItem() instanceof ItemCoin)) return;
		ItemStack item = e.item.getEntityItem();
		
		for(int i = 0; i < e.entityPlayer.inventory.getSizeInventory(); i++) {
			ItemStack stack = e.entityPlayer.inventory.getStackInSlot(i);
			if(stack != null && stack.getItem() instanceof ItemCoinBag) {
				NBTTagCompound compound = stack.getTagCompound();
				
				InventoryCoinBag inventory = new InventoryCoinBag(stack);
				int success = inventory.addCoins(item.stackSize, item.getItemDamage() > 2 ? 0 : item.getItemDamage(), !((EntityItemCoin) e.item).allowsGreed());
				if(success > 0) {
					e.entityPlayer.worldObj.playSoundAtEntity(e.item, "random.orb", 0.1F, Coinage.random.nextFloat() * 0.2F + 1.6F);
					if(e.entityPlayer.openContainer != null && e.entityPlayer.openContainer instanceof ContainerCoinBag) {
						ContainerCoinBag container = (ContainerCoinBag) e.entityPlayer.openContainer;
						if(container.inventory.parentItem == stack) {
							container.inventory.readFromNBT(container.inventory.parentItem.getTagCompound());
						}
					}
				}
				item.stackSize -= success;
				stack.damageItem((int) Math.ceil((double) success / 5.0), e.entityPlayer);
				
				if(item.stackSize <= 0) {
					e.setCanceled(true);
					return;
				}
			}
		}
	}
}
