package nukeduck.coinage.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nukeduck.coinage.item.ItemCoin;
import nukeduck.coinage.item.ItemCoinBag;
import nukeduck.coinage.item.ItemCoinBagLockable;
import nukeduck.coinage.util.BlockItemName;
import nukeduck.coinage.util.FormatUtil;

public class ItemRegister implements IRegister {
	private ItemModelMesher modelMesher;
	
	public static Item coinBag;
	public static Item coinBagLockable;
	public static Item coin;
	public static Item ironPadlock;
	public static Item merchantHat;
	
	private ModelResourceLocation empty, copper, silver, gold,
		emptyUnlocked, copperUnlocked, silverUnlocked, goldUnlocked,
		emptyLocked, copperLocked, silverLocked, goldLocked;
	
	public static ArmorMaterial MERCHANT;
	
	public void init() {
		modelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		MERCHANT = EnumHelper.addArmorMaterial("merchant", "iron", 5, new int[] {1, 1, 1, 1}, 15);
		registerItem(merchantHat = new ItemMerchantHat(MERCHANT, 0, 0), new BlockItemName("merchant", "hat"));
		
		registerItem(ironPadlock = new Item().setCreativeTab(CreativeTabs.tabMaterials), new BlockItemName("iron", "padlock"));
		
		registerItem(coinBag = new ItemCoinBag(), new BlockItemName("coin", "bag"), false);
		ModelBakery.addVariantName(coinBag,
			FormatUtil.prefix("coin_bag"),
			FormatUtil.prefix("coin_bag_copper"),
			FormatUtil.prefix("coin_bag_silver"),
			FormatUtil.prefix("coin_bag_gold"));
		
		registerItem(coinBagLockable = new ItemCoinBagLockable(), new BlockItemName("coin", "bag", "lockable"), false);
		ModelBakery.addVariantName(coinBagLockable,
			FormatUtil.prefix("coin_bag_unlocked"),
			FormatUtil.prefix("coin_bag_copper_unlocked"),
			FormatUtil.prefix("coin_bag_silver_unlocked"),
			FormatUtil.prefix("coin_bag_gold_unlocked"),
			FormatUtil.prefix("coin_bag_locked"),
			FormatUtil.prefix("coin_bag_copper_locked"),
			FormatUtil.prefix("coin_bag_silver_locked"),
			FormatUtil.prefix("coin_bag_gold_locked"));
		
		empty = new ModelResourceLocation(FormatUtil.prefix("coin_bag"), "inventory");
		copper = new ModelResourceLocation(FormatUtil.prefix("coin_bag_copper"), "inventory");
		silver = new ModelResourceLocation(FormatUtil.prefix("coin_bag_silver"), "inventory");
		gold = new ModelResourceLocation(FormatUtil.prefix("coin_bag_gold"), "inventory");
		
		emptyUnlocked = new ModelResourceLocation(FormatUtil.prefix("coin_bag_unlocked"), "inventory");
		copperUnlocked = new ModelResourceLocation(FormatUtil.prefix("coin_bag_copper_unlocked"), "inventory");
		silverUnlocked = new ModelResourceLocation(FormatUtil.prefix("coin_bag_silver_unlocked"), "inventory");
		goldUnlocked = new ModelResourceLocation(FormatUtil.prefix("coin_bag_gold_unlocked"), "inventory");
		
		emptyLocked = new ModelResourceLocation(FormatUtil.prefix("coin_bag_locked"), "inventory");
		copperLocked = new ModelResourceLocation(FormatUtil.prefix("coin_bag_copper_locked"), "inventory");
		silverLocked = new ModelResourceLocation(FormatUtil.prefix("coin_bag_silver_locked"), "inventory");
		goldLocked = new ModelResourceLocation(FormatUtil.prefix("coin_bag_gold_locked"), "inventory");
		modelMesher.register(coinBag, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				int coins = ItemCoinBag.coinCount(stack);
				if(coins / ItemCoin.getCoinMultiplier(2) >= 1) return gold;
				else if(coins / ItemCoin.getCoinMultiplier(1) >= 1) return silver;
				else if(coins >= ItemCoin.getCoinMultiplier(0)) return copper;
				else return empty;
			}
		});
		modelMesher.register(coinBagLockable, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				boolean locked = ((ItemCoinBag) stack.getItem()).isLocked(stack);
				
				int coins = ItemCoinBag.coinCount(stack);
				if(coins / ItemCoin.getCoinMultiplier(2) >= 1) return locked ? goldLocked : goldUnlocked;
				else if(coins / ItemCoin.getCoinMultiplier(1) >= 1) return locked ? silverLocked : silverUnlocked;
				else if(coins >= ItemCoin.getCoinMultiplier(0)) return locked ? copperLocked : copperUnlocked;
				else return locked ? emptyLocked : emptyUnlocked;
			}
		});
		
		//registerItem(coin = new ItemCoin(BlockRegister.coinPile), new BlockItemName("coin"), false);
		//registerModel(coin, 0, "coin_copper");
		//registerModel(coin, 1, "coin_silver");
		//registerModel(coin, 2, "coin_gold");
	}
	
	private void registerItem(Item item, BlockItemName name) {
		registerItem(item, name, true);
	}
	
	private void registerItem(Item item, BlockItemName name, boolean addModel) {
		String codeName = name.getUnderscored();
		String unlocalizedName = name.getCamelCase();
		
		GameRegistry.registerItem(item.setUnlocalizedName(unlocalizedName), codeName);
		if(addModel) registerModel(item, 0, codeName);
	}
	
	protected void registerModel(Item item, int meta, String name) {
		String fullName = FormatUtil.prefix(name);
		modelMesher.register(item, meta, new ModelResourceLocation(fullName, "inventory"));
		if(item.getHasSubtypes()) ModelBakery.addVariantName(item, fullName);
	}
}
