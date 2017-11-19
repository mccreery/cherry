package nukeduck.coinage;

import java.util.HashMap;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import nukeduck.coinage.registry.IRegister;

public class CoinDropRegister implements IRegister {
	public final HashMap<String, CoinDrop> entityDrops = new HashMap<String, CoinDrop>(); 
	
	public void init() {
		add(EntityZombie.class, new CoinDrop(10, 6));
		add(EntitySkeleton.class, new CoinDrop(27, 10));
		add(EntitySpider.class, new CoinDrop(20, 4));
		add(EntityCreeper.class, new CoinDrop(25, 8) {
			@Override
			public int getValue(EntityLivingBase entity) {
				if(((EntityCreeper) entity).getPowered()) {
					int count = 10500 + Coinage.random.nextInt(1000) - 500;
					if(count < 0) count = 0;
					return count;
				}
				return getValue();
			}
		});
	}
	
	public void add(Class<? extends EntityLivingBase> entity, CoinDrop drop) {
		entityDrops.put((String) EntityList.classToStringMapping.get(entity), drop);
	}
	
	public int get(EntityLivingBase entity) {
		String entityName = EntityList.getEntityString(entity);
		if(!entityDrops.containsKey(entityName)) return 0;
		return entityDrops.get(entityName).getValue(entity);
	}
}

class CoinDrop {
	int baseCount;
	int variation;
	
	public CoinDrop(int baseCount, int variation) {
		this.baseCount = baseCount;
		this.variation = variation;
	}
	
	public int getValue() {
		int count = baseCount + Coinage.random.nextInt(variation) - variation / 2;
		if(count < 0) count = 0;
		
		return count;
	}
	
	public int getValue(EntityLivingBase entity) {
		return getValue();
	}
}