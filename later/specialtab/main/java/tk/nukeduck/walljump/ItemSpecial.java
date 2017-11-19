package tk.nukeduck.walljump;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemSpecial {
	protected final Item baseItem;
	
	public ItemSpecial(Item item) {
		this.baseItem = item;
	}
	
	public ItemStack item() {return null;}
	
    public static ItemStack addEnchantment(ItemStack itemStack, Enchantment ench, int level) {
    	NBTTagCompound nbt = addTagCompound(itemStack);
        if(!nbt.hasKey("ench", 9)) {
            nbt.setTag("ench", new NBTTagList());
        }
        
        NBTTagList enchList = nbt.getTagList("ench", 10);
        NBTTagCompound newEnch = new NBTTagCompound();
        newEnch.setShort("id", (short) ench.effectId);
        newEnch.setShort("lvl", (short) level);
        enchList.appendTag(newEnch);
        
        return itemStack;
    }
    
    public static ItemStack setUnbreakable(ItemStack itemStack, boolean enabled) {
        addTagCompound(itemStack).setByte("Unbreakable", (byte) (enabled ? 1 : 0));
		return itemStack;
    }
    
    public static ItemStack addModifier(ItemStack itemStack, String name, String title, double amount, int operation) {
    	NBTTagCompound nbt = addTagCompound(itemStack);
		NBTTagList a = nbt.getTagList("AttributeModifiers", 10);
		
		NBTTagCompound attribute = new NBTTagCompound();
		attribute.setString("AttributeName", name);
		attribute.setString("Name", title);
		attribute.setDouble("Amount", amount);
		attribute.setInteger("Operation", operation);
		attribute.setLong("UUIDLeast", 587063109);
		attribute.setLong("UUIDMost", 140344514);
		
		a.appendTag(attribute);
		return itemStack;
    }
    
    public static NBTTagCompound addTagCompound(ItemStack itemStack) {
        if(!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        return itemStack.getTagCompound();
    }
}