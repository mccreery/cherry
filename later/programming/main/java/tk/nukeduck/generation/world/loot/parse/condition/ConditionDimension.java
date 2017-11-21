package tk.nukeduck.generation.world.loot.parse.condition;

import net.minecraft.world.World;
import tk.nukeduck.generation.util.BlockPos;

public class ConditionDimension implements ICondition {
	private int dimensionId;
	public ConditionDimension(Double dimensionId) {
		this.dimensionId = dimensionId.intValue();
	}

	@Override
	public boolean isMet(World world, BlockPos pos) {
		return world.provider.dimensionId == this.dimensionId;
	}
}
