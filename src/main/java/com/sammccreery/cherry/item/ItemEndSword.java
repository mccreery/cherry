package com.sammccreery.cherry.item;

import com.sammccreery.cherry.util.Funcs;

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
		return release(stack, world, x, y, z);
	}

	private static final String ENTITY_TAG = "Entity";

	public static boolean hasCapture(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(ENTITY_TAG);
	}
	public static EntityLivingBase getCapture(ItemStack stack, World world) {
		return (EntityLivingBase)EntityList.createEntityFromNBT(stack.getTagCompound().getCompoundTag(ENTITY_TAG), world);
	}
	public static void clearCapture(ItemStack stack) {
		stack.getTagCompound().removeTag(ENTITY_TAG);
	}

	/** Attempts to capture an entity, if the sword is empty
	 * @return {@code true} if the entity was captured successfully */
	public static boolean capture(ItemStack stack, World world, EntityPlayer player, EntityLivingBase capture) {
		if(!hasCapture(stack)) {
			NBTTagCompound entityTag;
			stack.getTagCompound().setTag("Entity", entityTag = new NBTTagCompound());

			capture.writeToNBT(entityTag);
			entityTag.setInteger("id", EntityList.getEntityID(capture));

			Funcs.spawnParticlesAt(world, capture.posX - 0.5D, capture.posY, capture.posZ - 0.5D, 30, 1.0F);

			if(!world.isRemote) {
				world.removeEntity(world.getEntityByID(capture.getEntityId()));
			}
			stack.damageItem(1, player);

			return true;
		} else {
			return false;
		}
	}

	/** Attempts to release an entity, if one has been captured
	 * @return {@code true} if the entity was released successfully */
	public static boolean release(ItemStack stack, World world, int x, int y, int z) {
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
			Funcs.spawnParticlesAt(capture.worldObj, x, y + 1, z, 30, 1.0F);

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
