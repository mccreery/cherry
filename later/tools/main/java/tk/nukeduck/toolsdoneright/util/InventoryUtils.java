package tk.nukeduck.toolsdoneright.util;

import java.util.concurrent.Callable;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;

public class InventoryUtils {
    /**
     * This function stores as many items of an ItemStack as possible in a matching slot and returns the quantity of
     * left over items.
     */
    public static int storePartialItemStack(InventoryBasic inv, ItemStack p_70452_1_)
    {
        Item item = p_70452_1_.getItem();
        int i = p_70452_1_.stackSize;
        int j;

        if (p_70452_1_.getMaxStackSize() == 1)
        {
            j = getFirstEmptyStack(inv);

            if (j < 0)
            {
                return i;
            }
            else
            {
                if (inv.getStackInSlot(j) == null)
                {
                    inv.setInventorySlotContents(j, ItemStack.copyItemStack(p_70452_1_));
                }

                return 0;
            }
        }
        else
        {
            j = storeItemStack(inv, p_70452_1_);

            if (j < 0)
            {
                j = getFirstEmptyStack(inv);
            }

            if (j < 0)
            {
                return i;
            }
            else
            {
                if (inv.getStackInSlot(j) == null)
                {
                	inv.setInventorySlotContents(j, new ItemStack(item, 0, p_70452_1_.getItemDamage()));

                    if (p_70452_1_.hasTagCompound())
                    {
                    	inv.getStackInSlot(j).setTagCompound((NBTTagCompound)p_70452_1_.getTagCompound().copy());
                    }
                }

                int k = i;

                if (i > inv.getStackInSlot(j).getMaxStackSize() - inv.getStackInSlot(j).stackSize)
                {
                    k = inv.getStackInSlot(j).getMaxStackSize() - inv.getStackInSlot(j).stackSize;
                }

                if (k > inv.getInventoryStackLimit() - inv.getStackInSlot(j).stackSize)
                {
                    k = inv.getInventoryStackLimit() - inv.getStackInSlot(j).stackSize;
                }

                if (k == 0)
                {
                    return i;
                }
                else
                {
                    i -= k;
                    inv.getStackInSlot(j).stackSize += k;
                    inv.getStackInSlot(j).animationsToGo = 5;
                    return i;
                }
            }
        }
    }
    
    /**
     * Returns the first item stack that is empty.
     */
    public static int getFirstEmptyStack(InventoryBasic inv)
    {
        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            if (inv.getStackInSlot(i) == null)
            {
                return i;
            }
        }

        return -1;
    }
    
    /**
     * Adds the item stack to the inventory, returns false if it is impossible.
     */
    public static boolean addItemStackToInventory(InventoryBasic inv, final ItemStack p_70441_1_)
    {
        if (p_70441_1_ != null && p_70441_1_.stackSize != 0 && p_70441_1_.getItem() != null)
        {
            try
            {
                int i;

                if (p_70441_1_.isItemDamaged())
                {
                    i = getFirstEmptyStack(inv);

                    if (i >= 0)
                    {
                        inv.setInventorySlotContents(i, ItemStack.copyItemStack(p_70441_1_));
                        p_70441_1_.stackSize = 0;
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    do
                    {
                        i = p_70441_1_.stackSize;
                        p_70441_1_.stackSize = storePartialItemStack(inv, p_70441_1_);
                    }
                    while (p_70441_1_.stackSize > 0 && p_70441_1_.stackSize < i);

                    return p_70441_1_.stackSize < i;
                }
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding item to inventory");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being added");
                crashreportcategory.addCrashSection("Item ID", Integer.valueOf(Item.getIdFromItem(p_70441_1_.getItem())));
                crashreportcategory.addCrashSection("Item data", Integer.valueOf(p_70441_1_.getItemDamage()));
                crashreportcategory.addCrashSectionCallable("Item name", new Callable()
                {
                    private static final String __OBFID = "CL_00001710";
                    public String call()
                    {
                        return p_70441_1_.getDisplayName();
                    }
                });
                throw new ReportedException(crashreport);
            }
        }
        else
        {
            return false;
        }
    }
    
    /**
     * stores an itemstack in the users inventory
     */
    private static int storeItemStack(InventoryBasic inv, ItemStack p_70432_1_)
    {
        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            if (inv.getStackInSlot(i) != null && inv.getStackInSlot(i).getItem() == p_70432_1_.getItem() && inv.getStackInSlot(i).isStackable() && inv.getStackInSlot(i).stackSize < inv.getStackInSlot(i).getMaxStackSize() && inv.getStackInSlot(i).stackSize < inv.getInventoryStackLimit() && (!inv.getStackInSlot(i).getHasSubtypes() || inv.getStackInSlot(i).getItemDamage() == p_70432_1_.getItemDamage()) && ItemStack.areItemStackTagsEqual(inv.getStackInSlot(i), p_70432_1_))
            {
                return i;
            }
        }

        return -1;
    }

}