package nukeduck.coinage.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nukeduck.coinage.Coinage;
import nukeduck.coinage.Constants;

public class ItemCoinBag extends Item {
	public ItemCoinBag() {
		super();
		//this.setMaxDamage(0);
		//this.setHasSubtypes(false);
		this.setMaxStackSize(1);
		this.setMaxDamage(1000);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
	/** @return The amount of coins of specified metadata added when picked up. */
	public static int getCoinsAdded(ItemStack stack, int base, boolean ignoreGreed) {
		if(base <= 0 || ignoreGreed) return base; // Removing or doing nothing doesn't take Greed into account
		
		NBTTagList ench = stack.getEnchantmentTagList();
		for(int i = 0; i < ench.tagCount(); i++) {
			NBTTagCompound thisEnch = ench.getCompoundTagAt(i);
			if(thisEnch.getShort("id") == Constants.GREED_ID) {
				int level = thisEnch.getShort("lvl") - 1;
				if(Coinage.random.nextFloat() <= EnchantmentGreed.CHANCES[level]) {
					return base * EnchantmentGreed.MULTIPLIERS[level];
				}
				break;
			}
		}
		return base;
	}
	
	public boolean lock(ItemStack stack, EntityPlayer player, boolean lock) {
		if(!stack.hasTagCompound() || !this.isLockable()) return false;
		
		String uuid = player.getUUID(player.getGameProfile()).toString();
		NBTTagCompound compound = stack.getTagCompound();
		boolean prevLocked = compound.hasKey("PlayerLock");
		if(prevLocked == lock) return true; // No action is needed
		
		if(prevLocked) {
			NBTTagCompound playerLock = compound.getCompoundTag("PlayerLock");
			if(!playerLock.getString("UUID").equals(uuid)) return false; // Player isn't allowed to unlock this
			compound.removeTag("PlayerLock");
		} else {
			NBTTagCompound playerLock = new NBTTagCompound();
			playerLock.setString("UUID", uuid);
			playerLock.setString("DisplayName", player.getDisplayNameString());
			compound.setTag("PlayerLock", playerLock);
		}
		return true;
	}
	
	protected boolean isLockable() {
		return false;
	}
	
	public boolean isLocked(ItemStack stack) {
		return this.isLockable() && stack.hasTagCompound() && stack.getTagCompound().hasKey("PlayerLock");
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(!stack.hasTagCompound()) {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setTag("Items", new NBTTagList());
			compound.setIntArray("CoinCount", new int[] {0, 0, 0});
			compound.setInteger("TotalCount", 0);
			stack.setTagCompound(compound);
		}
		
		/*if(isSelected && entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			if(player.openContainer != null && player.openContainer instanceof ContainerCoinBag) {
				ContainerCoinBag container = (ContainerCoinBag) player.openContainer;
				if(container.needsUpdate) {
					container.writeToNBT();
					container.needsUpdate = false;
				}
			}
		}*/
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 1;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		if(player.isSneaking()) {
			if(this.isLockable()) {
				this.lock(itemstack, player, !this.isLocked(itemstack));
			}
		} else {
			if(!this.isLocked(itemstack)) {
				player.openGui(Coinage.instance, 0, world, (int) player.posX, (int) player.posY, (int) player.posZ);
			} else {
				if(player instanceof EntityPlayerMP) ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(new S02PacketChat(new ChatComponentTranslation("container.isLocked", new Object[] {this.getItemStackDisplayName(itemstack)}), (byte)2));
				player.playSound("random.door_" + (Coinage.random.nextBoolean() ? "open" : "close"), 1.0f, 1.0f);
			}
		}
		return itemstack;
    }
	
	/*@SideOnly(Side.CLIENT)
	@Override
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
		if(getCoins() / 10000 >= 1) return gold;
		else if(getCoins() / 100 >= 1) return silver;
		else if(getCoins() >= 1) return copper;
		else return null;
	}*/
	
	/*public static int countCoins(ItemStack stack) {
		if(!stack.hasTagCompound()) return 0;
		int total = 0;
		
		NBTTagList items = stack.getTagCompound().getTagList("Items", 10);
		for(int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound compound = (NBTTagCompound) items.get(i);
			ItemStack coinStack = ItemStack.loadItemStackFromNBT(compound);
			if(coinStack != null && coinStack.getItem().equals(GMASItems.coin)) {
				total += coinStack.stackSize * ItemCoin.getCoinMultiplier(coinStack);
			}
		}
		
		return total;
	}*/
	
	public static int coinCount(ItemStack stack) {
		return stack.hasTagCompound() ? stack.getTagCompound().getInteger("TotalCount") : 0;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
		int[] coinCount = {0, 0, 0};
		if(stack.hasTagCompound()) coinCount = stack.getTagCompound().getIntArray("CoinCount");
		
		int largestCoin = 0;
		for(int i = 0; i < coinCount.length; i++) {
			if(coinCount[i] > 0) largestCoin = i;
		}
		
		String s = "";
		for(int i = largestCoin; i >= 0; i--) {
			s += String.valueOf(coinCount[i]);
			s += ChatFormatting.WHITE + String.valueOf((char) (0x2591 + i)) + ChatFormatting.GRAY;
			if(i > 0) s += " ";
		}
		tooltip.add(s);
	}
	
	public static FontRenderer fontRendererObj;
	
	@Override
	@SideOnly(Side.CLIENT)
	public FontRenderer getFontRenderer(ItemStack stack) {
		return Coinage.proxy.getFontRendererCoins();
	}
	
	@Override
    public int getItemEnchantability() {
        return 7;
    }
	
    /**
     * Return whether this item is repairable in an anvil.
     *  
     * @param toRepair The ItemStack to be repaired
     * @param repair The ItemStack that should repair this Item (leather for leather armor, etc.)
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == Items.leather;
    }
}
