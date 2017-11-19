package nukeduck.coinage.registry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nukeduck.coinage.block.BlockCoinPile;
import nukeduck.coinage.block.BlockPot;
import nukeduck.coinage.block.tileentity.TileEntityCoinPile;
import nukeduck.coinage.item.ItemCoin;
import nukeduck.coinage.item.ItemColored;
import nukeduck.coinage.util.BlockItemName;

public class BlockRegister implements IRegister {
	public static Block pot;
	public static Block coinPile;
	
	private static ItemRegister itemRegister;
	public BlockRegister(ItemRegister itemRegister) {
		this.itemRegister = itemRegister;
	}
	
	@Override
	public void init() {
		pot = new BlockPot().setHardness(0.0f).setStepSound(Block.soundTypeStone).setResistance(0.0f);
		registerBlock(pot, new BlockItemName("clay", "pot"), true, ItemColored.class);
		registerBlock(coinPile = new BlockCoinPile().setHardness(0.1f).setResistance(0.1f).setCreativeTab(CreativeTabs.tabMisc), new BlockItemName("coin"), false, ItemCoin.class);
		GameRegistry.registerTileEntity(TileEntityCoinPile.class, "coin_pile");
		
		ItemRegister.coin = Item.getItemFromBlock(coinPile);
		//ItemRegister.coin.setCreativeTab(CreativeTabs.tabMisc);
		this.itemRegister.registerModel(ItemRegister.coin, 0, "coin_copper");
		this.itemRegister.registerModel(ItemRegister.coin, 1, "coin_silver");
		this.itemRegister.registerModel(ItemRegister.coin, 2, "coin_gold");
	}
	
	private void registerBlock(Block block, BlockItemName name) {
		registerBlock(block, name, true);
	}
	
	private void registerBlock(Block block, BlockItemName name, boolean addModel) {
		registerBlock(block, name, addModel, ItemBlock.class, new Object[0]);
	}
	
	private void registerBlock(Block block, BlockItemName name, boolean addModel, Class<? extends ItemBlock> itemClass, Object... itemBlockParams) {
		block.setUnlocalizedName(name.getCamelCase());
		String codeName = name.getUnderscored();
		GameRegistry.registerBlock(block, itemClass, codeName, itemBlockParams);
		if(addModel) this.itemRegister.registerModel(Item.getItemFromBlock(block), 0, codeName);
	}
}
