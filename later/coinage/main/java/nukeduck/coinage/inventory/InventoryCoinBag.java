package nukeduck.coinage.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import nukeduck.coinage.Coinage;
import nukeduck.coinage.item.ItemCoin;
import nukeduck.coinage.item.ItemCoinBag;
import nukeduck.coinage.registry.ItemRegister;

public class InventoryCoinBag implements IInventory {
	public static final int SIZE = 10;
	private ItemStack[] inventory = new ItemStack[SIZE];
	
	public ItemStack parentItem;
	int coinCount = 0;
	
	public InventoryCoinBag(ItemStack stack) {
		this.parentItem = stack;
		if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		readFromNBT(stack.getTagCompound());
	}
	
	@Override
	public String getName() {
		return "coinBag";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentTranslation("menu.coinBag");
	}

	@Override
	public int getSizeInventory() {
		return SIZE;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = getStackInSlot(index);
		if(stack != null) {
			if(stack.stackSize > count) {
				stack = stack.splitStack(count);
				if(stack.stackSize == 0) {
					setInventorySlotContents(index, null);
				}
			} else {
				this.setInventorySlotContents(index, null);
			}
			this.markDirty();
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		ItemStack stack = getStackInSlot(index);
		if(stack != null) {
			this.setInventorySlotContents(index, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.inventory[index] = stack;
		if(stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
		this.markDirty();
	}
	
	public void setInventorySlotContentsWithoutUpdate(int index, ItemStack stack) {
		this.inventory[index] = stack;
		if(stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	/** @return The amount of coins successfully transferred */
	public int removeCoins(int coins, int metadata) {
		return -this.addCoins(-coins, metadata, true);
	}
	
	/** @return The amount of coins successfully transferred
	 * @param coins The maximum amount of coins to add
	 * @param metadata The metadata of the coins to add */
	public int addCoins(int coins, int metadata, boolean ignoreGreed) {
		int multiplier = ItemCoin.getCoinMultiplier(metadata);
		
		// Note that if the item has Greed, it will always fail if the bag will overflow given any multiplier
		int tried = coins; // The amount of coins that the player gave us, or the maximum that will fit
		
		// Trying to remove more coins than this bag holds
		if(this.coinCount + tried * multiplier < 0) {
			tried = (int) -Math.floor(this.coinCount / multiplier);
		}
		
		int succeeded = 0;
		
		boolean flag = true;
		tried++; // Add 1, as we need to take one away immediately
		while(flag) {
			tried--;
			succeeded = ItemCoinBag.getCoinsAdded(this.parentItem, tried, ignoreGreed);
			int totalCoins = this.coinCount + succeeded;
			
			// The amount of coins actually added to the container
			int currentSlot = 0;
			
			for(int i = 2; i >= 0; i--) {
				int mult = ItemCoin.getCoinMultiplier(i);
				int count = 0;
				while(totalCoins >= mult) {
					totalCoins -= mult;
					count++;
				}
				
				while(count > 0) {
					count -= 99;
					currentSlot++;
				}
			}
			flag = currentSlot > 10;
		}
		
		markDirty(succeeded * multiplier);
		return tried;
	}
	
	public static final int MAX_STACK_SIZE = 99;
	
	@Override
	public int getInventoryStackLimit() {
		return MAX_STACK_SIZE;
	}
	
	@Override
	public void markDirty() {
		markDirty(0);
	}
	
	public void markDirty(int toAdd) {
		int count = toAdd;
		for(int i = 0; i < this.getSizeInventory(); i++) {
			ItemStack s = this.getStackInSlot(i);
			if(s != null) {
				count += ItemCoin.getCoinMultiplier(s) * s.stackSize;
				this.setInventorySlotContentsWithoutUpdate(i, null);
			}
		}
		
		int maxStack = 99;
		int currentSlot = 0;
		
		int totalCoins = 0;
		int[] coinTotals = new int[3];
		
		for(int i = 2; i >= 0; i--) {
			int mult = ItemCoin.getCoinMultiplier(i);
			int coins = 0;
			while(count >= mult) {
				coins++;
				count -= mult;
			}
			coinTotals[i] = coins;
			totalCoins += coins * mult;
			
			while(coins > 0) {
				this.setInventorySlotContentsWithoutUpdate(currentSlot, new ItemStack(Coinage.instance.itemRegister.coin, Math.min(maxStack, coins), i));
				coins -= maxStack;
				currentSlot++;
			}
		}
		this.writeToNBT(parentItem.getTagCompound());
		parentItem.getTagCompound().setIntArray("CoinCount", coinTotals);
		parentItem.getTagCompound().setInteger("TotalCount", totalCoins);
		this.coinCount = totalCoins;
		
		// Make it appear as if the player has added to the bag, even when they haven't yet made permanent changes
		if(Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().theWorld.isRemote) {
			if(Minecraft.getMinecraft().thePlayer.openContainer instanceof ContainerCoinBag) {
				Minecraft.getMinecraft().thePlayer.inventory.setInventorySlotContents(Minecraft.getMinecraft().thePlayer.inventory.currentItem, this.parentItem);
			}
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return stack.getItem() instanceof ItemCoin;
	}
	
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList items = compound.getTagList("Items", 10);
		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = items.getCompoundTagAt(i);
			int b0 = nbttagcompound1.getByte("Slot");
			if (b0 >= 0 && b0 < this.getSizeInventory()) {
				this.setInventorySlotContentsWithoutUpdate(b0, ItemStack.loadItemStackFromNBT(nbttagcompound1));
			}
		}
		this.coinCount = compound.getInteger("TotalCount");
	}
	
	public void writeToNBT(NBTTagCompound compound) {
		NBTTagList items = new NBTTagList();
		for(int i = 0; i < this.getSizeInventory(); i++) {
			if(this.getStackInSlot(i) != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.getStackInSlot(i).writeToNBT(nbttagcompound1);
				items.appendTag(nbttagcompound1);
			}
		}
		compound.setTag("Items", items);
	}

	@Override
	public int getField(int id) {return 0;}
	@Override
	public void setField(int id, int value) {}
	@Override
	public int getFieldCount() {return 0;}
	@Override
	public void clear() {}
}
