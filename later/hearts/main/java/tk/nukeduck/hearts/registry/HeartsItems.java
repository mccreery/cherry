package tk.nukeduck.hearts.registry;

import tk.nukeduck.hearts.item.ItemHeartShard;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class HeartsItems {
	public static Item shard = new ItemHeartShard();

	public static void init() {
		GameRegistry.registerItem(shard = new ItemHeartShard().setUnlocalizedName("heartShard"), "heart_shard");
	}
}
