package com.sammccreery.cherry.item;

import java.util.List;

import com.sammccreery.cherry.util.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemEndSword extends ItemSword {
	public ItemEndSword(ToolMaterial material) {
		super(material);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float dx, float dy, float dz) {
		return release(stack, world, player, x, y, z);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target) {
		return capture(stack, player.worldObj, player, target);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean par4) {
		boolean hasCapture = hasCapture(stack);

		EntityLivingBase capture = getCapture(stack, Minecraft.getMinecraft().theWorld);
		tooltip.add(I18n.format("gui.held", hasCapture ? capture.getCommandSenderName() : I18n.format("gui.none")));

		if(hasCapture) {
			tooltip.add(I18n.format("gui.health",
				Util.toPlaces(capture.getHealth() / 2, 1), Util.toPlaces(capture.getMaxHealth() / 2, 1)));
		}
	}

	private static final String ENTITY_TAG = "Entity";

	public static boolean hasCapture(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.getItem().onCreated(stack, null, null);
			return false;
		} else {
			return stack.getTagCompound().hasKey(ENTITY_TAG);
		}
	}

	public static EntityLivingBase getCapture(ItemStack stack, World world) {
		return (EntityLivingBase)EntityList.createEntityFromNBT(stack.getTagCompound().getCompoundTag(ENTITY_TAG), world);
	}

	public static void clearCapture(ItemStack stack) {
		stack.getTagCompound().removeTag(ENTITY_TAG);
	}

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		stack.setTagCompound(new NBTTagCompound());
	}

	/** Attempts to capture an entity, if the sword is empty
	 * @return {@code true} if the entity was captured successfully */
	public static boolean capture(ItemStack stack, World world, EntityPlayer player, EntityLivingBase capture) {
		if(!hasCapture(stack)) {
			NBTTagCompound entityTag;
			stack.getTagCompound().setTag("Entity", entityTag = new NBTTagCompound());

			capture.writeToNBT(entityTag);
			entityTag.setString("id", EntityList.getEntityString(capture));

			Util.teleportEffect(world, capture.posX, capture.posY, capture.posZ, 30, 1.0F);

			if(!world.isRemote) {
				world.removeEntity(capture);
			}
			stack.damageItem(1, player);

			return true;
		} else {
			return false;
		}
	}

	/** Attempts to release an entity, if one has been captured
	 * @return {@code true} if the entity was released successfully */
	public static boolean release(ItemStack stack, World world, EntityPlayer player, int x, int y, int z) {
		if(hasCapture(stack)) {
			EntityLivingBase capture = getCapture(stack, world);
			capture.setVelocity(0, 0, 0);

			// What
			if(!world.isRemote) {
				if(world.getBlock(x, y, z).getCollisionBoundingBoxFromPool(world, x, y, z) != null) {
					capture.setLocationAndAngles(x + 0.5F, world.getBlock(x, y, z).getCollisionBoundingBoxFromPool(world, x, y, z).maxY, z + 0.5F, 0, 0);
			} else {
				capture.setLocationAndAngles(x + 0.5F, y, z + 0.5F, 0, 0);
			}
			Util.teleportEffect(world, x, y + 1, z, 30, 1.0F);

			//if(world.canPlaceEntityOnSide(world.getBlock(x, y, z), x, y, z, world.getBlock(x, y, z).getCollisionBoundingBoxFromPool(world, x, y, z) == null, side, player, itemstack)) {
				world.spawnEntityInWorld(capture);
				clearCapture(stack);
			//}
			}
			return true;
		} else {
			return false;
		}
	}
}
