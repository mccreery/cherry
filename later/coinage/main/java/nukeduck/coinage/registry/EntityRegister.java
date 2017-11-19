package nukeduck.coinage.registry;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import nukeduck.coinage.Coinage;
import nukeduck.coinage.entity.EntityItemCoin;
import nukeduck.coinage.entity.EntityMerchant;

public class EntityRegister implements IRegister {
	private static int id;
	@Override
	public void init() {
		EntityRegistry.registerModEntity(EntityItemCoin.class, "ItemCoin", id++, Coinage.instance, 64, 20, true);
		EntityRegistry.registerModEntity(EntityMerchant.class, "Merchant", id++, Coinage.instance, 80, 1, false);
	}
}
