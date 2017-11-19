package tk.nukeduck.hearts.event;

import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import tk.nukeduck.hearts.HeartCrystal;
import tk.nukeduck.hearts.registry.HeartsBlocks;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class CommonEvents {
	public static final UUID BOOST_UUID = UUID.nameUUIDFromBytes(new byte[] {0x62, 0x75, 0x6D});
	public static final String HEARTS_KEY = "HeartCrystals";

	@SubscribeEvent
	public void onRespawn(PlayerRespawnEvent e) {
		NBTTagCompound persisted = e.player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);

		if(persisted.hasKey(HEARTS_KEY)) {
			double hearts = persisted.getDouble(HEARTS_KEY);
			this.onEat(e.player, hearts);
		}
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent e) {
		if(e.entityLiving instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) e.entityLiving;
			AttributeModifier boost = this.getBoost(player);

			if(boost != null) {
				float keptRate = HeartCrystal.config.getKeptRate();
				double totalKept = 0.0;
				if(player.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
					totalKept = boost.getAmount();
				} else {
					for(int i = 0; i < boost.getAmount(); i += 2) {
						if(HeartCrystal.random.nextFloat() < keptRate) {
							totalKept += 2.0;
						}
					}
					this.onEat(player, -totalKept);
				}

				if(!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
					player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
				}
				NBTTagCompound persisted = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);

				persisted.setDouble(HEARTS_KEY, totalKept);
			}
		}
	}

	@SubscribeEvent
	public void onDrop(LivingDropsEvent e) {
		if(!(e.entityLiving instanceof EntityPlayer)) return;
		EntityPlayer player = (EntityPlayer) e.entityLiving;

		AttributeModifier modifier = this.getBoost(player);
		if(modifier != null) {
			int stackSize = (int) (modifier.getAmount() / 2.0);
			ItemStack stack = new ItemStack(HeartsBlocks.crystal, stackSize);
			player.func_146097_a(stack, true, false);
		}
		this.clearBoost(player);

		if(!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
			player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
		}
		NBTTagCompound persisted = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		this.onEat(player, persisted.getDouble(HEARTS_KEY));
	}

	public void onEat(EntityPlayer player) {
		this.onEat(player, 2.0);
	}
	public void onEat(EntityPlayer player, double value) {
		IAttributeInstance attr = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
		AttributeModifier modifier = attr.getModifier(BOOST_UUID);

		if(modifier != null) {
			value += modifier.getAmount();
			attr.removeModifier(modifier);
		}
		AttributeModifier modifierNew = new AttributeModifier(BOOST_UUID, "Heart Crystal", value, 0);
		attr.applyModifier(modifierNew);
	}

	public AttributeModifier getBoost(EntityPlayer player) {
		IAttributeInstance attr = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
		return attr.getModifier(BOOST_UUID);
	}

	public void clearBoost(EntityPlayer player) {
		IAttributeInstance attr = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
		AttributeModifier modifier = attr.getModifier(BOOST_UUID);
		if(modifier != null) attr.removeModifier(modifier);
	}
}
