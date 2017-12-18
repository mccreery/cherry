package tk.nukeduck.generation.util;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import tk.nukeduck.generation.registry.ObjectName;
import tk.nukeduck.generation.registry.ObjectName.Format;

public class BlockWrapper {
	public final Block block;
	public final ObjectName name;

	public BlockWrapper(Block block, ObjectName name) {
		this.block = block;
		this.name = name;
	}

	public BlockWrapper init() {
		this.block.setBlockName(this.name.toString(Format.CAMEL_CASE_HEADLESS));
		GameRegistry.registerBlock(this.block, this.name.toString(Format.UNDERSCORED_LOWER));
		return this;
	}
}
