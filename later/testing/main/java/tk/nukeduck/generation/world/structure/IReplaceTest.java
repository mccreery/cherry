package tk.nukeduck.generation.world.structure;

import net.minecraft.world.World;

public interface IReplaceTest {
	public boolean canReplace(World world, int x, int y, int z);
}
