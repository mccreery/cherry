package tk.nukeduck.lightsaber.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.apache.commons.lang3.ArrayUtils;

import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.client.ClientEvents;
import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import tk.nukeduck.lightsaber.registry.LightsaberItems;
import tk.nukeduck.lightsaber.util.Strings;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLightsaber extends ItemSword {
	/** How much damage this lightsaber does. */
	private double damageValue = 10;
	/** Tick counter which counts down until the lightsaber will next make a humming noise. */
	private long nextHum = 50;
	/** Holds whether or not the lightsaber has a double blade. */
	private boolean isDoubled;
	
	/** @return {@code true} if this lightsaber has a double blade. */
	public boolean getDoubled() {
		return this.isDoubled;
	}
	
	/** Sets whether or not this lightsaber should be doubled.
	 * @param Whether or not the lightsaber should have a double blade.
	 * @return This item. */
	public Item setDoubled(boolean doubled) {
		this.isDoubled = doubled;
		this.damageValue = doubled ? 15 : 10;
		return this;
	}
	
	/** {@inheritDoc}<br/>
	 * Adds different colours and hilts of lightsaber. */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List items) {
		for(int i = 0; i < Strings.HILTS.length; i++){
			ItemStack a = new ItemStack(item, 1, 1);
			a.setTagCompound(new NBTTagCompound());
			this.setHilt(a, i);
			items.add(a);
		}
	}
	
	/** {@inheritDoc}<br/>
	 * Creates tag compound, if it doesn't already exist. */
	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		if(!itemStack.hasTagCompound()) itemStack.setTagCompound(new NBTTagCompound());
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return "item.lightsaber";
	}
	
	/** Adds colour and doubled prefixes. */
	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		String color = Strings.translate(Strings.getColor(Strings.COLORS[getColor(itemStack)])) + Strings.SPACE;
		String doubled = ((ItemLightsaber) itemStack.getItem()).getDoubled() ? Strings.translate(Strings.DOUBLE) + Strings.SPACE : "";
		return (this.getItemNamePrefix(itemStack) + color + doubled + Strings.translate(this.getUnlocalizedName(itemStack) + Strings.NAME_SUFFIX)).trim();
	}
	
	/** @return The colour of the blade of the given item.
	 * @param itemStack The {@code ItemStack} to look through. */
	public static int getColor(ItemStack itemStack) {
		return ArrayUtils.contains(LightsaberItems.lightsabers, itemStack.getItem()) ? ArrayUtils.indexOf(LightsaberItems.lightsabers, itemStack.getItem()) / 2 : 0;
	}
	
	/** Icons for the different types of lightsaber */
	public static IIcon[] icons = new IIcon[Strings.COLORS.length * 2];
	public static IIcon[] hilts = new IIcon[Strings.HILTS.length * 2];
	private static boolean hasInitialised = false;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir) {
		if(!hasInitialised) {
			for(int i = 0; i < icons.length - 1; i += 2) {
				String currentColor = Strings.COLORS[i / 2];
				this.icons[i] = ir.registerIcon(Strings.MOD_ID + ":lightsaber_" + currentColor);
				this.icons[i + 1] = ir.registerIcon(Strings.MOD_ID + ":lightsaber_" + currentColor + "_double");
			}
			
			for(int i = 0; i < hilts.length - 1; i += 2) {
				this.hilts[i] = ir.registerIcon(Strings.MOD_ID + ":hilt_" + Strings.HILTS[i / 2]);
				this.hilts[i + 1] = ir.registerIcon(Strings.MOD_ID + ":hilt_" + Strings.HILTS[i / 2] + "_double");
			}
			
			hasInitialised = true;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack itemStack) {
		return getDoubled() ? icons[getColor(itemStack) * 2 + 1] : icons[getColor(itemStack) * 2];
	}
	
	/** Gets the icon index of this lightsaber when it isn't open. */
	@SideOnly(Side.CLIENT)
	public IIcon getEmptyIconIndex(ItemStack itemStack) {
		return getDoubled() ? hilts[getHiltIndex(itemStack) * 2 + 1] : hilts[getHiltIndex(itemStack) * 2];
	}
	
	/** Tick counter counting down until the next discharging. */
	private byte tickCountDown = 10;
	private static byte tickMax = 20;
	
	/**
	 * Used to decide whether or not the switching animation should be played
	 * while switching between lightsabers.
	 */
	private static int currentType = -1;
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
		if(Lightsaber.mc.thePlayer.getHeldItem() == itemStack) {
			currentType = ArrayUtils.indexOf(LightsaberItems.lightsabers, itemStack.getItem());
		}
		
		if (itemStack.getTagCompound() == null) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setBoolean("Enabled", false);
			itemStack.setTagCompound(nbt);
		}
		
		if(entity instanceof EntityPlayer) {
			ItemStack heldItem = ((EntityPlayer) entity).getHeldItem();
			if(ClientEvents.disableCountdown > 0 &&
					!(heldItem != null && heldItem.getItem() instanceof ItemLightsaber && heldItem.hasTagCompound() && heldItem.getTagCompound().getBoolean("Enabled") && heldItem.getItem() == LightsaberItems.lightsabers[currentType])) {
				ClientEvents.disableCountdown = 0;
			}
		}
		
		if(isEnabled(itemStack)) {
			NBTTagCompound tag = itemStack.getTagCompound(); {
				int animation = tag.getInteger("Animation");
				if(animation < 8) {
					tag.setInteger("Animation", animation + 1);
				}
				
				if(!world.isRemote && entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)) {
					tickCountDown--;
					if(tickCountDown == 0) {
						tickCountDown = (byte) (tickMax / (((ItemLightsaber)itemStack.getItem()).getDoubled() ? 2 : 1));
						if(itemStack.getItemDamage() == itemStack.getMaxDamage()) {
							setEnabled(Lightsaber.mc.thePlayer, itemStack, false);
						} else {
							if(entity instanceof EntityPlayer) {
								ItemStack heldItem = ((EntityPlayer) entity).getHeldItem();
								if(itemStack.hasTagCompound() && itemStack.getTagCompound().getBoolean("Enabled") && itemStack.getTagCompound().getInteger("Animation") == 8) {
									itemStack.damageItem(1, (EntityLivingBase) entity);
									
									/* Set countdown to some extra ticks so that
									 * the game will disable the switching animation for a little while. */
									if(heldItem == itemStack) {
										ClientEvents.disableCountdown = 21;
									}
								}
							}
						}
					}
				}
				
				nextHum--;
				if(nextHum <= 0) {
					entity.playSound(Strings.MOD_ID + ":random.hum", 0.05F, 1.0F);
					nextHum = Lightsaber.random.nextInt(50);
				}
			}
		}
		
		//---- CHARGER STUFF ----//
		if(itemStack.getItemDamage() <= 0) itemStack.setItemDamage(1);
	}
	
	/** @param itemStack The itemstack to check.
	 * @return The hilt index of the given itemstack. */
	public static int getHiltIndex(ItemStack itemStack) {
		return !itemStack.hasTagCompound() ? 0 : itemStack.getTagCompound().getInteger("HiltIndex");
	}
	
	/** Sets the hilt index of the given itemstack.
	 * @param itemStack The itemstack to set.
	 * @param hilt The hilt to set. */
	public void setHilt(ItemStack itemStack, int hilt) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		nbt.setInteger("HiltIndex", hilt);
	}
	
	/** @return Whether or not the given itemstack is currenly opened.
	 * @param itemStack The itemstack to check. */
	public static boolean isEnabled(ItemStack itemStack) {
		return !itemStack.hasTagCompound() || itemStack.getTagCompound().getBoolean("Enabled");
	}
	
	/** Sets the lightsaber to enabled or disabled.
	 * @param entity The entity holding the lightsaber.
	 * @param itemStack The lightsaber to change.
	 * @param enabled {@code true} if the lightsaber should be enabled, or {@code false} otherwise. */
	public void setEnabled(Entity entity, ItemStack itemStack, boolean enabled) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		nbt.setBoolean("Enabled", enabled);
		
		if(enabled && !(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)) {
			itemStack.setItemDamage(Math.min(itemStack.getItemDamage() + 5, itemStack.getMaxDamage()));
		} else {
			ClientEvents.disableCountdown = 0;
		}
		
		if(entity != null) {
			if(enabled) {
				entity.playSound("lightsaber:open", 0.3f, 1.0f);
				nbt.setInteger("Animation", 0);
			} else {
				entity.playSound("lightsaber:close", 0.3f, 1.0f);
			}
		}
		
		if(!nbt.hasKey("AttributeModifiers", 10))
			nbt.setTag("AttributeModifiers", new NBTTagList());
		
		NBTTagList modifierList = nbt.getTagList("AttributeModifiers", 10); // Compound
		int modifierId = -1;
		for(int i = 0; i < modifierList.tagCount(); i++) {
			if(modifierList.getCompoundTagAt(i).getString("Name") == "LightsaberDamage") {
				modifierId = i;
				break;
			}
		}
		
		if(enabled) {
			if(modifierId == -1) {
				damageModifier.setDouble("Amount", damageValue);
				modifierList.appendTag(damageModifier);
			} else {
				modifierList.getCompoundTagAt(modifierId).setDouble("Amount", damageValue);
			}
		} else {
			if(modifierId != -1) {
				modifierList.removeTag(modifierId);
			}
		}
	}
	
	/** Damage modifier to apply to a lightsaber when it is active. */
	public static NBTTagCompound damageModifier = new NBTTagCompound() {
		{
			setString("AttributeName", "generic.attackDamage");
			setString("Name", "LightsaberDamage");
			setInteger("Operation", 0);
			setLong("UUIDLeast", 587063109);
			setLong("UUIDMost", 140344514);
		}
	};
	
	/** {@inheritDoc}<br/>
	 * Handles activating and deactivating the lightsaber. */
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if(player.isSneaking()) {
			return super.onItemRightClick(itemStack, world, player);
		}
		
		if(itemStack.getItemDamage() < itemStack.getMaxDamage()) {
			NBTTagCompound tag = itemStack.getTagCompound(); {
				int damage = itemStack.getItemDamage();
				boolean isEnabled = !isEnabled(itemStack);
				setEnabled(player, itemStack, isEnabled);
			}
		} else {
			player.playSound("random.click", 0.3f, 0.7f);
		}
		return itemStack;
	}
	
	/** Last time this lightsaber has been swung. */
	private long lastSwing = 0;
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if(isEnabled(stack) && System.currentTimeMillis() - lastSwing > 100) {
			entityLiving.playSound(Strings.MOD_ID + ":random.swing", 0.3f, (Lightsaber.random.nextFloat() * 0.5f) + 0.75f);
			lastSwing = System.currentTimeMillis();
		}
		return false;
	}
	
	/** Raises damage and gives progress points to the player. */
	@Override
	public boolean hitEntity(ItemStack itemStack, EntityLivingBase victim, EntityLivingBase attacker) {
		if(isEnabled(itemStack) && attacker instanceof EntityPlayerMP) {
			if(!((EntityPlayer) attacker).capabilities.isCreativeMode) {
				itemStack.setItemDamage(Math.min(itemStack.getItemDamage() + 3, itemStack.getMaxDamage()));
			}
			ExtendedPropertiesForceSkills.get((EntityPlayer) attacker).addProgress((byte) 100, (EntityPlayerMP) attacker);
		}
		return false;
	}
	
	/** Damages the item upon breaking a block. */
	@Override
	public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving) {
		if ((double)block.getBlockHardness(world, x, y, z) != 0.0D){
			itemStack.setItemDamage(Math.min(itemStack.getItemDamage() + 2, itemStack.getMaxDamage()));
		}
		return true;
	}
	
	/** {@inheritDoc}<br/>
	 * This prevents a tooltip rendering bug - remove default sword damage modifier */
	public Multimap getItemAttributeModifiers() {
		return HashMultimap.create();
	}
	
	//---- CHARGING STUFF ----// (Can't extend both ItemSword and ItemChargeable)
	
	/** Item to use to inherit charging capabilities. */
	private static ItemChargeable charging = new ItemChargeable();
	
	public ItemLightsaber() {
		super(LightsaberItems.LIGHTSABER);
		this.setNoRepair();
		this.setMaxStackSize(1);
		this.setCreativeTab(Lightsaber.lightsaberTab);
		this.setUnlocalizedName("lightsaber");
		this.setFull3D();
		this.setMaxChargeLevel(500);
	}
	
	public Item setMaxChargeLevel(int max) {return this.setMaxDamage(max + 1);}
	public int getMaxChargeLevel() {return this.getMaxDamage() - 1;}
	public boolean isEmpty(ItemStack itemStack) {return charging.isEmpty(itemStack);}
	public boolean isFull(ItemStack itemStack) {return charging.isFull(itemStack);}
	public String getItemNamePrefix(ItemStack itemStack) {return charging.getItemNamePrefix(itemStack);}
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advancedInfo) {
		list.add((this.getMaxDamage() - itemStack.getItemDamage()) + "/" + this.getMaxChargeLevel() + StatCollector.translateToLocal("lightsaber.energySymbol"));
		list.add(Strings.translate(Strings.HILT) + Strings.SPACE + Strings.translate(Strings.HILT_PREFIX + Strings.HILTS[getHiltIndex(itemStack)]));
	}
}