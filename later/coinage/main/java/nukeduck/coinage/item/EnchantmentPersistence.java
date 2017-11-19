package nukeduck.coinage.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.EnumHelper;
import nukeduck.coinage.Constants;

public class EnchantmentPersistence extends Enchantment {
	public EnchantmentPersistence(int enchID, ResourceLocation enchName, int enchWeight, EnumEnchantmentType enchType) {
		super(enchID, enchName, enchWeight, enchType);
		this.setName("persistence");
		Enchantment.addToBookList(this);
	}
    
	private static final float[] MULTIPLIERS = {
		0.25f, 0.5f, 0.75f
	};
	
    // 25%, 50%, 75%
    public int getMaxLevel() {
        return MULTIPLIERS.length;
    }
    
	/** @return The amount of copper coins kept due to the Persistence enchantment, if any. */
	public static int getCoinsPersisted(ItemStack stack) {
		int total = ItemCoinBag.coinCount(stack);
		NBTTagList ench = stack.getEnchantmentTagList();
		for(int i = 0; i < ench.tagCount(); i++) {
			NBTTagCompound thisEnch = ench.getCompoundTagAt(i);
			if(thisEnch.getShort("id") == Constants.PERSISTENCE_ID) {
				short level = thisEnch.getShort("lvl");
				return (int) Math.round(total * EnchantmentPersistence.MULTIPLIERS[level - 1]);
			}
		}
		return 0;
	}
    
    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ItemCoinBag;
    }
    
    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 1;//1 + enchantmentLevel * 5;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 500;
    }
}
