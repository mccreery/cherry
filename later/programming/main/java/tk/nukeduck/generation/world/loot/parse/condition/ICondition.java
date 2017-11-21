package tk.nukeduck.generation.world.loot.parse.condition;

import net.minecraft.world.World;
import tk.nukeduck.generation.util.BlockPos;

public interface ICondition {
	public boolean isMet(World world, BlockPos pos);
}
