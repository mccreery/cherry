package nukeduck.craftconvenience.events;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nukeduck.craftconvenience.CraftConvenience;

public class DropEvents {
	private final ArrayList<DropsOverride> dropsHandlers = new ArrayList<DropsOverride>();

	public DropEvents() {
		this.dropsHandlers.add(new DropsOverride<EntityHorse>(EntityHorse.class) {
			@Override
			public void handle(LivingDropsEvent e, EntityHorse entity) {
				e.drops.add(this.getEntityItem(entity, new ItemStack(Items.leather)));
			}
		});
		this.dropsHandlers.add(new DropsOverride<EntityEnderman>(EntityEnderman.class) {
			@Override
			public void handle(LivingDropsEvent e, EntityEnderman entity) {
				e.drops.add(this.getEntityItem(entity, new ItemStack(Items.ender_pearl)));
			}
		});
		this.dropsHandlers.add(new DropsOverride<EntitySkeleton>(EntitySkeleton.class) {
			@Override
			public void handle(LivingDropsEvent e, EntitySkeleton entity) {
				if(entity.getSkeletonType() == 1) return;
				e.drops.clear();

				// Re-add regular wither skeleton drops {
				int j = CraftConvenience.random.nextInt(3 + e.lootingLevel) - 1;
				int k;
				for(k = 0; k < j; ++k) {
					e.drops.add(this.getEntityItem(entity, new ItemStack(Items.coal, 1)));
				}
				j = CraftConvenience.random.nextInt(3 + e.lootingLevel);
				for (k = 0; k < j; ++k) {
					e.drops.add(this.getEntityItem(entity, new ItemStack(Items.bone, 1)));
				}

				NBTTagCompound compound = new NBTTagCompound();
				NBTTagList dropChances = compound.getTagList("DropChances", 5);
				entity.writeToNBT(compound);
				for(k = 0; k < entity.getInventory().length; ++k) {
					ItemStack itemstack = entity.getEquipmentInSlot(j);
					boolean flag1 = dropChances.getFloat(k) > 1.0F;

					if(itemstack != null && (e.recentlyHit || flag1) && CraftConvenience.random.nextFloat() - (float) e.lootingLevel * 0.01F < dropChances.getFloat(k)) {
						if(!flag1 && itemstack.isItemStackDamageable()) {
							int m = Math.max(itemstack.getMaxDamage() - 25, 1);
							int l = itemstack.getMaxDamage() - CraftConvenience.random.nextInt(CraftConvenience.random.nextInt(m) + 1);

							if (l > m) {
								l = m;
							}
							if (l < 1) {
								l = 1;
							}

							itemstack.setItemDamage(l);
						}
						e.drops.add(this.getEntityItem(entity, itemstack));
					}
				}
				// }

				// Add new, more common skull
				if(e.recentlyHit && CraftConvenience.random.nextFloat() < 0.1F + (float) e.lootingLevel * 0.04F) {
					e.drops.add(this.getEntityItem(entity, new ItemStack(Items.skull, 1, 1)));
				}
			}
		});
	}

	@SubscribeEvent
	public void onDrops(LivingDropsEvent e) {
		if(!e.entity.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) return;

		Class<? extends Entity> type = e.entity.getClass();
		for(DropsOverride handler : this.dropsHandlers) {
			if(type == handler.getType()) {
				handler.handle(e, type.cast(e.entity));
			}
		}
	}

	private static abstract class DropsOverride<T extends Entity> {
		private Class<? extends Entity> type;

		public DropsOverride(Class<? extends Entity> type) {
			this.type = type;
		}

		public final Class<? extends Entity> getType() {
			return this.type;
		}

		/** @param entity The entity to base the new item around
		 * @param stack The itemstack to assign to the new item
		 * @return an {@link EntityItem} to be spawned at the entity */
		public final EntityItem getEntityItem(Entity entity, ItemStack stack) {
			EntityItem item = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, stack);
			item.setDefaultPickupDelay();
			return item;
		}

		public abstract void handle(LivingDropsEvent e, T entity);
	}
}
