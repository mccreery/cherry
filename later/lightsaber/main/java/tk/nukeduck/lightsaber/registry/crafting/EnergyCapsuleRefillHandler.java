package tk.nukeduck.lightsaber.registry.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;
import tk.nukeduck.lightsaber.item.ItemEnergyCapsule;

public class EnergyCapsuleRefillHandler implements IRecipe {
	/** Map containing all the energy levels certain items hold. */
	public static Map<Item, Double> charges = new HashMap<Item, Double>() {
		{
			put(Items.coal, 3.0);
			put(Item.getItemFromBlock(Blocks.coal_block), 30.0);
			put(Items.iron_ingot, 7.0);
			put(Item.getItemFromBlock(Blocks.iron_block), 70.0);
			put(Items.gold_ingot, 8.0);
			put(Item.getItemFromBlock(Blocks.gold_block), 80.0);
			put(Items.redstone, 4.0);
			put(Item.getItemFromBlock(Blocks.redstone_block), 40.0);
			put(Items.emerald, 30.0);
			put(Item.getItemFromBlock(Blocks.emerald_block), 300.0);
			put(Items.diamond, 100.0);
			put(Item.getItemFromBlock(Blocks.diamond_block), 1000.0);
			put(Items.nether_star, 1000.0);
			
			put(Items.compass, 35.0);
			put(Items.clock, 45.0);
			
			put(Items.golden_apple, 5.0);
			put(Items.golden_carrot, 3.0);
			put(Items.magma_cream, 10.0);
			put(Items.speckled_melon, 8.0);
			
			put(Items.fire_charge, 5.0);
			put(Items.skull, 80.0);
			
			put(Items.repeater, 20.0);
			put(Item.getItemFromBlock(Blocks.redstone_torch), 4.2);
			put(Item.getItemFromBlock(Blocks.redstone_lamp), 30.0);
			
			put(Item.getItemFromBlock(Blocks.glowstone), 10.0);
			put(Items.blaze_rod, 8.0);
			put(Items.blaze_powder, 3.5);
			put(Items.ender_pearl, 5.0);
			put(Items.glowstone_dust, 2.5);
			put(Items.gunpowder, 2.0);
			
			put(Item.getItemFromBlock(Blocks.sapling), 6.0);
			put(Item.getItemFromBlock(Blocks.tnt), 20.0);
			
			put(Items.lava_bucket, 35.0);
			put(Items.experience_bottle, 50.0);
			put(Item.getItemFromBlock(Blocks.enchanting_table), 1000.0);
			put(Item.getItemFromBlock(Blocks.brewing_stand), 9.0);
			put(Item.getItemFromBlock(Blocks.beacon), 1500.0);
		}
	};
	
	public EnergyCapsuleRefillHandler() {}
	
	/** {@inheritDoc}<br/>
	 * Checks whether or not the recipe contains both a capsule and some items which have energy. */
	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		powerSources.clear();
		capsule = null;
		
		if(!inv.getInventoryName().equals("container.crafting")) return false;
		
		boolean a, b;
		a = b = false;
		
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack d = inv.getStackInSlot(i);
			if(d == null) continue;
			else if(d.getItem() instanceof ItemEnergyCapsule) {
				capsule = d;
				if(a || d.getItemDamage() < 2) return false;
				a = true;
			} else if(ArrayUtils.contains(charges.keySet().toArray(), d.getItem())) {
				powerSources.add(d.getItem());
				b = true;
			}
		}
		return a && b;
	}
	
	/** The current energy capsule in the recipe. */
	public ItemStack capsule;
	/** The current list of power sources in the recipe. */
	public ArrayList<Item> powerSources = new ArrayList<Item>();
	
	/** Just a wrapper for {@code getRecipeOutput()}.
	 * @see #getRecipeOutput() */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return getRecipeOutput();
	}
	
	/** This recipe can take up the whole crafting table. */
	@Override
	public int getRecipeSize() {
		return 9;
	}
	
	/** Adds up the total of charges in all the items of the current recipe. */
	@Override
	public ItemStack getRecipeOutput() {
		if(powerSources.size() == 0 || capsule == null) return null;
		
		double totalAdd = 0;
		for(Item i : powerSources) {
			totalAdd += charges.get(i);
		}
		
		ItemStack capsuleResult = capsule.copy();
		capsuleResult.setItemDamage(Math.max(1, capsuleResult.getItemDamage() - (int) Math.round(totalAdd)));
		
		return capsuleResult;
	}
}