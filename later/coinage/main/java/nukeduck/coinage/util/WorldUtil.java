package nukeduck.coinage.util;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nukeduck.coinage.Coinage;
import nukeduck.coinage.entity.EntityItemCoin;
import scala.util.Random;

public class WorldUtil {
	public static final int[] getCoins(int coins) {
		int golds, silvers;
		golds = silvers = 0;
		while(coins >= 10000) {
			golds++;
			coins -= 10000;
		}
		while(coins >= 100) {
			silvers++;
			coins -= 100;
		}
		return new int[] {coins, silvers, golds};
	}

	public static final void createCoins(int coins, World world, double x, double y, double z, List<EntityItem> drops, boolean allowGreed) {
		int[] coinCounts = getCoins(coins);
		
		Random random = new Random();
		
		while(coinCounts[2] > 0) {
			EntityItemCoin coin = new EntityItemCoin(world, x, y + 1, z, new ItemStack(Coinage.instance.itemRegister.coin, Math.min(64, coinCounts[2]), 2), allowGreed);
			coin.setDefaultPickupDelay();
			coin.setVelocity((random.nextDouble() - 0.5) * 0.15, random.nextDouble() * 0.2, (random.nextDouble() - 0.5) * 0.15);
			drops.add(coin);
			coinCounts[2] -= 64;
		}
		while(coinCounts[1] > 0) {
			EntityItemCoin coin = new EntityItemCoin(world, x, y + 1, z, new ItemStack(Coinage.instance.itemRegister.coin, Math.min(64,coinCounts[1]), 1), allowGreed);
			coin.setDefaultPickupDelay();
			coin.setVelocity((random.nextDouble() - 0.5) * 0.15, random.nextDouble() * 0.2, (random.nextDouble() - 0.5) * 0.15);
			drops.add(coin);
			coinCounts[1] -= 64;
		}
		while(coinCounts[0] > 0) {
			EntityItemCoin coin = new EntityItemCoin(world, x, y + 1, z, new ItemStack(Coinage.instance.itemRegister.coin, Math.min(64, coinCounts[0]), 0), allowGreed);
			coin.setDefaultPickupDelay();
			coin.setVelocity((random.nextDouble() - 0.5) * 0.15, random.nextDouble() * 0.2, (random.nextDouble() - 0.5) * 0.15);
			drops.add(coin);
			coinCounts[0] -= 64;
		}
	}
}
