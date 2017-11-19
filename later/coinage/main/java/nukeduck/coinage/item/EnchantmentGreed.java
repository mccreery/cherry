package nukeduck.coinage.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.EnumHelper;

public class EnchantmentGreed extends Enchantment {
	public EnchantmentGreed(int enchID, ResourceLocation enchName, int enchWeight, EnumEnchantmentType enchType) {
		super(enchID, enchName, enchWeight, enchType);
		this.setName("greed");
		Enchantment.addToBookList(this);
	}
    
	public static final float[] CHANCES = {
		0.1f, 0.2f, 0.4f, 0.3f
	};
	
	public static final int[] MULTIPLIERS = {
		2, 2, 2, 3
	};
	
    public int getMaxLevel() {
        return CHANCES.length;
    }
    
    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ItemCoinBag;
    }
    
    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel) {
        return 1;//1 + enchantmentLevel * 5;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 500;
    }
}
