package tk.nukeduck.generation.world.structure;

import net.minecraft.world.World;
import tk.nukeduck.generation.world.BoundingBox;
import tk.nukeduck.generation.world.WorldUtils;

public abstract class StructureBoxed extends IStructure implements IReplaceTest {
	protected BoundingBox boundingBox;
	protected float replaceThreshold;

	public StructureBoxed(Dimension dimension) {
		this(dimension, BoundingBox.EMPTY);
	}
	public StructureBoxed(Dimension dimension, BoundingBox boundingBox) {
		this(dimension, boundingBox, 1.0F);
	}
	public StructureBoxed(Dimension dimension, BoundingBox boundingBox, float replaceThreshold) {
		super(dimension);
		this.setBoundingBox(boundingBox);
		this.replaceThreshold = replaceThreshold;
	}

	public StructureBoxed setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
		return this;
	}
	public BoundingBox getBoundingBox() {
		return this.boundingBox;
	}

	@Override
	public boolean canGenerate(World world, int x, int y, int z) {
		return WorldUtils.getReplaceFactor(world, this.getBoundingBox().offset(x, y, z), this) >= this.replaceThreshold;
	}

	@Override
	public boolean canReplace(World world, int x, int y, int z) {
		return WorldUtils.REPLACE_TEST_DEFAULT.canReplace(world, x, y, z);
	}
}
