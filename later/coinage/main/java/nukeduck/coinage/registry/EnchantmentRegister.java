package nukeduck.coinage.registry;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;
import nukeduck.coinage.Constants;
import nukeduck.coinage.item.EnchantmentGreed;
import nukeduck.coinage.item.EnchantmentPersistence;

public class EnchantmentRegister implements IRegister {
	public EnchantmentPersistence persistence;
	public EnchantmentGreed greed;
	
	@Override
	public void init() {
		persistence = new EnchantmentPersistence(Constants.ENCHANTMENT_ID, new ResourceLocation("persistence"), 7, EnumEnchantmentType.ALL);
		Constants.PERSISTENCE_ID = Constants.ENCHANTMENT_ID++;
		greed = new EnchantmentGreed(Constants.ENCHANTMENT_ID, new ResourceLocation("greed"), 7, EnumEnchantmentType.ALL);
		Constants.GREED_ID = Constants.ENCHANTMENT_ID++;
	}
}
