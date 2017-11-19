package nukeduck.functionaldecor.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import nukeduck.functionaldecor.FunctionalDecor;
import nukeduck.functionaldecor.item.ItemBlockDecor;
import nukeduck.functionaldecor.util.BlockItemName;
import nukeduck.functionaldecor.util.BlockItemName.FormatType;

public class DecorBlocks {
	public static final Material IRON_HAND = new Material(MapColor.ironColor) {
		public boolean getCanBlockGrass() {
			return false;
		}
	};
	public static Block alarmClock, lamp, peripheral, recycleBin;

	public static final void init() {
		registerBlock(alarmClock = new BlockAlarmClock().setHardness(0.2F), ItemBlockDecor.class, new BlockItemName("alarm", "clock"));
		registerBlock(lamp = new BlockLamp().setHardness(0.2F), ItemBlockDecor.class, new BlockItemName("lamp"));
		registerBlock(peripheral = new BlockPeripheral().setHardness(0.2F), ItemBlockDecor.class, new BlockItemName("peripheral"));
		registerBlock(recycleBin = new BlockRecycleBin().setHardness(0.2F), ItemBlockDecor.class, new BlockItemName("recycle", "bin"));
	}

	private static final void registerBlock(Block block, BlockItemName name) {
		registerBlock(block, ItemBlock.class, name);
	}
	private static final void registerBlock(Block block, Class<? extends ItemBlock> itemBlock, BlockItemName name) {
		block.setCreativeTab(FunctionalDecor.TAB);
		block.setBlockName(name.toString(FormatType.HEADLESS_CAMELCASE));
		//block.setBlockTextureName(name.toNamespacedString(FormatType.LOWERCASE_UNDERSCORED));
		GameRegistry.registerBlock(block, itemBlock, name.toString(FormatType.LOWERCASE_UNDERSCORED));
	}
}
