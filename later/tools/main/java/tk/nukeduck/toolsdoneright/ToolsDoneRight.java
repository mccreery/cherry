package tk.nukeduck.toolsdoneright;

import java.util.Dictionary;
import java.util.Random;
import java.util.concurrent.Callable;

import tk.nukeduck.toolsdoneright.block.*;
import tk.nukeduck.toolsdoneright.item.*;
import tk.nukeduck.toolsdoneright.util.InventoryUtils;
import tk.nukeduck.toolsdoneright.world.WorldGenOre;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;

import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.*;

@Mod(modid = ToolsDoneRight.modid, version = ToolsDoneRight.version, name = "Tools Done Right")

public class ToolsDoneRight
{
	public static final Random random = new Random();
	
    public static final String modid = "toolsdoneright", version = "1.1.4";
    
    public static Item emeraldSword, emeraldPickaxe, emeraldAxe, emeraldShovel, emeraldHoe;
    public static Item rubySword, rubyPickaxe, rubyAxe, rubyShovel, rubyHoe;
    public static Item topazSword, topazPickaxe, topazAxe, topazShovel, topazHoe;
    public static Item sapphireSword, sapphirePickaxe, sapphireAxe, sapphireShovel, sapphireHoe;
    public static Item obsidianSword, obsidianPickaxe, obsidianAxe, obsidianShovel, obsidianHoe;
    public static Item endSword, endPickaxe, endAxe, endShovel, endHoe;
    
    public static Block rubyOre, topazOre, sapphireOre;
    public static Block rubyBlock, topazBlock, sapphireBlock;
    public static Item rubyItem, topazItem, sapphireItem;
    
    public static void spawnParticlesAt(World world, double x, double y, double z, int amount, float volume) {
    	if(world.isRemote) {
	    	for (int l = 0; l < amount; ++l) {
	            double d6 = (double)((float)x + random.nextFloat());
	            double d1 = (double)((float)y + random.nextFloat());
	            d6 = (double)((float)z + random.nextFloat());
	            double d3 = 0.0D;
	            double d4 = 0.0D;
	            double d5 = 0.0D;
	            int i1 = random.nextInt(2) * 2 - 1;
	            int j1 = random.nextInt(2) * 2 - 1;
	            d3 = ((double)random.nextFloat() - 0.5D) * 0.125D;
	            d4 = ((double)random.nextFloat() - 0.5D) * 0.125D;
	            d5 = ((double)random.nextFloat() - 0.5D) * 0.125D;
	            double d2 = (double)z + 0.5D + 0.25D * (double)j1;
	            d5 = (double)(random.nextFloat() * 1.0F * (float)j1);
	            double d0 = (double)x + 0.5D + 0.25D * (double)i1;
	            d3 = (double)(random.nextFloat() * 1.0F * (float)i1);
	            world.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
	        }
    	}
    }
    
    @SubscribeEvent
    public void onBlockBreak(HarvestDropsEvent e) {
    	if(e.harvester != null &&
    			e.harvester.getHeldItem() != null &&
    			(e.harvester.getHeldItem().getItem() instanceof ItemEndPickaxe || 
    			e.harvester.getHeldItem().getItem() instanceof ItemEndAxe || 
    			e.harvester.getHeldItem().getItem() instanceof ItemEndShovel)) {
    		InventoryBasic enderChest = e.harvester.getInventoryEnderChest();
    		for(ItemStack item : e.drops) {
    			InventoryUtils.addItemStackToInventory(enderChest, item);
    		}
    		
    		spawnParticlesAt(e.world, e.x, e.y, e.z, 10, 0.3F);
    	}
    }
    
    @SubscribeEvent
    public void onEntityRightClick(EntityInteractEvent e) {
    	Entity ent = e.target;
    	String id = EntityList.getEntityString(ent);
    	if(e.entityPlayer != null && ent != null && ent instanceof EntityLiving && id != null && !(ent instanceof EntityPlayer)) {
			EntityPlayer player = e.entityPlayer;
			if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemEndSword && player.getHeldItem().stackTagCompound != null) {
				if(!player.getHeldItem().stackTagCompound.hasKey("Entity")) {
		    		NBTTagCompound c = new NBTTagCompound();
		    		ent.writeToNBT(c);
		    		c.setString("id", id);
		    		
		    		player.getHeldItem().stackTagCompound.setTag("Entity", c);
		    		
		    		spawnParticlesAt(player.worldObj, ent.posX - 0.5D, ent.posY, ent.posZ - 0.5D, 30, 1.0F);
		    		
		    		if(!player.worldObj.isRemote) player.worldObj.removeEntity(player.worldObj.getEntityByID(ent.getEntityId()));
		    		if(!player.capabilities.isCreativeMode) player.getHeldItem().damageItem(1, player);
				}
			}
    	}
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(new ToolsDoneRight());
    	
    	rubyOre = new BlockTDROre(Material.rock).setBlockName("ruby_ore").setBlockTextureName(modid + ":ruby_ore").setCreativeTab(CreativeTabs.tabBlock).setHardness(3.0F).setResistance(5.0F);
    	topazOre = new BlockTDROre(Material.rock).setBlockName("topaz_ore").setBlockTextureName(modid + ":topaz_ore").setCreativeTab(CreativeTabs.tabBlock).setHardness(3.0F).setResistance(5.0F);
    	sapphireOre = new BlockTDROre(Material.rock).setBlockName("sapphire_ore").setBlockTextureName(modid + ":sapphire_ore").setCreativeTab(CreativeTabs.tabBlock).setHardness(3.0F).setResistance(5.0F);
    	GameRegistry.registerBlock(rubyOre, rubyOre.getUnlocalizedName());
    	GameRegistry.registerBlock(topazOre, topazOre.getUnlocalizedName());
    	GameRegistry.registerBlock(sapphireOre, sapphireOre.getUnlocalizedName());
    	
    	rubyBlock = new BlockTDR(Material.iron).setBlockName("ruby_block").setBlockTextureName(modid + ":ruby_block").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F);
    	topazBlock = new BlockTDR(Material.iron).setBlockName("topaz_block").setBlockTextureName(modid + ":topaz_block").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F);
    	sapphireBlock = new BlockTDR(Material.iron).setBlockName("sapphire_block").setBlockTextureName(modid + ":sapphire_block").setCreativeTab(CreativeTabs.tabBlock).setHardness(5.0F).setResistance(10.0F);
    	GameRegistry.registerBlock(rubyBlock, rubyBlock.getUnlocalizedName());
    	GameRegistry.registerBlock(topazBlock, topazBlock.getUnlocalizedName());
    	GameRegistry.registerBlock(sapphireBlock, sapphireBlock.getUnlocalizedName());
    	
    	rubyItem = new Item().setUnlocalizedName("ruby").setTextureName(modid + ":ruby").setCreativeTab(CreativeTabs.tabMaterials);
    	topazItem = new Item().setUnlocalizedName("topaz").setTextureName(modid + ":topaz").setCreativeTab(CreativeTabs.tabMaterials);
    	sapphireItem = new Item().setUnlocalizedName("sapphire").setTextureName(modid + ":sapphire").setCreativeTab(CreativeTabs.tabMaterials);
    	
    	GameRegistry.addSmelting(rubyOre, new ItemStack(rubyItem), 0.8F);
    	GameRegistry.addSmelting(topazOre, new ItemStack(topazItem), 0.8F);
    	GameRegistry.addSmelting(sapphireOre, new ItemStack(sapphireItem), 0.8F);
    	
    	GameRegistry.addRecipe(new ItemStack(rubyBlock, 1), new Object[] {"XXX", "XXX", "XXX", 'X', rubyItem});
    	GameRegistry.addShapelessRecipe(new ItemStack(rubyItem, 9), rubyBlock);
    	
    	GameRegistry.addRecipe(new ItemStack(topazBlock, 1), new Object[] {"XXX", "XXX", "XXX", 'X', topazItem});
    	GameRegistry.addShapelessRecipe(new ItemStack(topazItem, 9), topazBlock);
    	
    	GameRegistry.addRecipe(new ItemStack(sapphireBlock, 1), new Object[] {"XXX", "XXX", "XXX", 'X', sapphireItem});
    	GameRegistry.addShapelessRecipe(new ItemStack(sapphireItem, 9), sapphireBlock);
    	
    	GameRegistry.registerWorldGenerator(new WorldGenOre(), 0);
    	
		ToolMaterial gem = EnumHelper.addToolMaterial("gem", 2, 200, 10.0F, 2.5F, 30);
		ToolMaterial obsidian = EnumHelper.addToolMaterial("obsidian", 3, 4000, 3.5F, 2.0F, 5);
		ToolMaterial end = EnumHelper.addToolMaterial("end", 3, 1000, 8.0F, 3.0F, 20);
		
		emeraldSword = new ItemTDRSword(gem).setUnlocalizedName("emerald_sword").setTextureName(modid + ":swordEmerald");
		rubySword = new ItemTDRSword(gem).setUnlocalizedName("ruby_sword").setTextureName(modid + ":swordRuby");
		topazSword = new ItemTDRSword(gem).setUnlocalizedName("topaz_sword").setTextureName(modid + ":swordTopaz");
		sapphireSword = new ItemTDRSword(gem).setUnlocalizedName("sapphire_sword").setTextureName(modid + ":swordSapphire");
		obsidianSword = new ItemTDRSword(obsidian).setUnlocalizedName("obsidian_sword").setTextureName(modid + ":swordObsidian");
		endSword = new ItemEndSword(end).setUnlocalizedName("end_sword").setTextureName(modid + ":swordEnd");
		
		emeraldShovel = new ItemTDRShovel(gem).setUnlocalizedName("emerald_shovel").setTextureName(modid + ":shovelEmerald");
		emeraldPickaxe = new ItemTDRPickaxe(gem).setUnlocalizedName("emerald_pickaxe").setTextureName(modid + ":pickaxeEmerald");
		emeraldAxe = new ItemTDRAxe(gem).setUnlocalizedName("emerald_axe").setTextureName(modid + ":hatchetEmerald");
		
		rubyShovel = new ItemTDRShovel(gem).setUnlocalizedName("ruby_shovel").setTextureName(modid + ":shovelRuby");
		rubyPickaxe = new ItemTDRPickaxe(gem).setUnlocalizedName("ruby_pickaxe").setTextureName(modid + ":pickaxeRuby");
		rubyAxe = new ItemTDRAxe(gem).setUnlocalizedName("ruby_axe").setTextureName(modid + ":hatchetRuby");

		topazShovel = new ItemTDRShovel(gem).setUnlocalizedName("topaz_shovel").setTextureName(modid + ":shovelTopaz");
		topazPickaxe = new ItemTDRPickaxe(gem).setUnlocalizedName("topaz_pickaxe").setTextureName(modid + ":pickaxeTopaz");
		topazAxe = new ItemTDRAxe(gem).setUnlocalizedName("topaz_axe").setTextureName(modid + ":hatchetTopaz");
		
		sapphireShovel = new ItemTDRShovel(gem).setUnlocalizedName("sapphire_shovel").setTextureName(modid + ":shovelSapphire");
		sapphirePickaxe = new ItemTDRPickaxe(gem).setUnlocalizedName("sapphire_pickaxe").setTextureName(modid + ":pickaxeSapphire");
		sapphireAxe = new ItemTDRAxe(gem).setUnlocalizedName("sapphire_axe").setTextureName(modid + ":hatchetSapphire");

		obsidianShovel = new ItemTDRShovel(obsidian).setUnlocalizedName("obsidian_shovel").setTextureName(modid + ":shovelObsidian");
		obsidianPickaxe = new ItemTDRPickaxe(obsidian).setUnlocalizedName("obsidian_pickaxe").setTextureName(modid + ":pickaxeObsidian");
		obsidianAxe = new ItemTDRAxe(obsidian).setUnlocalizedName("obsidian_axe").setTextureName(modid + ":hatchetObsidian");

		endShovel = new ItemEndShovel(end).setUnlocalizedName("end_shovel").setTextureName(modid + ":shovelEnd");
		endPickaxe = new ItemEndPickaxe(end).setUnlocalizedName("end_pickaxe").setTextureName(modid + ":pickaxeEnd");
		endAxe = new ItemEndAxe(end).setUnlocalizedName("end_axe").setTextureName(modid + ":hatchetEnd");
		
		emeraldHoe = new ItemTDRHoe(gem).setUnlocalizedName("emerald_hoe").setTextureName(modid + ":hoeEmerald");
		rubyHoe = new ItemTDRHoe(gem).setUnlocalizedName("ruby_hoe").setTextureName(modid + ":hoeRuby");
		topazHoe = new ItemTDRHoe(gem).setUnlocalizedName("topaz_hoe").setTextureName(modid + ":hoeTopaz");
		sapphireHoe = new ItemTDRHoe(gem).setUnlocalizedName("sapphire_hoe").setTextureName(modid + ":hoeSapphire");
		obsidianHoe = new ItemTDRHoe(obsidian).setUnlocalizedName("obsidian_hoe").setTextureName(modid + ":hoeObsidian");
		endHoe = new ItemEndHoe(end).setUnlocalizedName("end_hoe").setTextureName(modid + ":hoeEnd");
		
		for(Item i : new Item[] {
			// Items
			rubyItem, topazItem, sapphireItem,
			
			// Swords
			emeraldSword, rubySword, topazSword, sapphireSword, obsidianSword, endSword,
			
			// Tools
			emeraldShovel, emeraldPickaxe, emeraldAxe,
			rubyShovel, rubyPickaxe, rubyAxe,
			topazShovel, topazPickaxe, topazAxe,
			sapphireShovel, sapphirePickaxe, sapphireAxe,
			obsidianShovel, obsidianPickaxe, obsidianAxe,
			endShovel, endPickaxe, endAxe,
			
			// Hoes
			emeraldHoe, rubyHoe, topazHoe, sapphireHoe, obsidianHoe, endHoe
    	}) {
			GameRegistry.registerItem(i, i.getUnlocalizedName());
		}
		
		Object[] materials = new Object[] {Items.emerald, rubyItem, topazItem, sapphireItem, Blocks.obsidian};
		
		ItemStack[] items = new ItemStack[] {addEnchantment(new ItemStack(emeraldSword, 1), 21, 2), new ItemStack(rubySword, 1), new ItemStack(topazSword, 1), new ItemStack(sapphireSword, 1), new ItemStack(obsidianSword, 1)};
		for(int i = 0; i < items.length; i++) {
			GameRegistry.addRecipe(items[i], new Object[] {
				"X", "X", "S", 'X', materials[i], 'S', Items.stick
			});
		}
		
		items = new ItemStack[] {addEnchantment(new ItemStack(emeraldPickaxe, 1), 35, 2), new ItemStack(rubyPickaxe, 1), new ItemStack(topazPickaxe, 1), new ItemStack(sapphirePickaxe, 1), new ItemStack(obsidianPickaxe, 1)};
		for(int i = 0; i < items.length; i++) {
			GameRegistry.addRecipe(items[i], new Object[] {
				"XXX", " S ", " S ", 'X', materials[i], 'S', Items.stick
			});
		}
		
		items = new ItemStack[] {addEnchantment(new ItemStack(emeraldAxe, 1), 35, 2), new ItemStack(rubyAxe, 1), new ItemStack(topazAxe, 1), new ItemStack(sapphireAxe, 1), new ItemStack(obsidianAxe, 1)};
		for(int i = 0; i < items.length; i++) {
			GameRegistry.addRecipe(items[i], new Object[] {
				"XX", "XS", " S", 'X', materials[i], 'S', Items.stick
			});
		}
		
		items = new ItemStack[] {addEnchantment(new ItemStack(emeraldShovel, 1), 35, 2), new ItemStack(rubyShovel, 1), new ItemStack(topazShovel, 1), new ItemStack(sapphireShovel, 1), new ItemStack(obsidianShovel, 1)};
		for(int i = 0; i < items.length; i++) {
			GameRegistry.addRecipe(items[i], new Object[] {
				"X", "S", "S", 'X', materials[i], 'S', Items.stick
			});
		}
		
		items = new ItemStack[] {new ItemStack(emeraldHoe, 1), new ItemStack(rubyHoe, 1), new ItemStack(topazHoe, 1), new ItemStack(sapphireHoe, 1), new ItemStack(obsidianHoe, 1)};
		for(int i = 0; i < items.length; i++) {
			GameRegistry.addRecipe(items[i], new Object[] {
				"XX", " S", " S", 'X', materials[i], 'S', Items.stick
			});
		}
		
		// Special case crafting for end items
		GameRegistry.addRecipe(new ItemStack(endSword, 1), new Object[] {" X ", " X ", "ESE", 'X', Blocks.end_stone, 'S', Items.stick, 'E', Items.ender_eye});
		GameRegistry.addRecipe(new ItemStack(endPickaxe, 1), new Object[] {"XXX", " E ", " S ", 'X', Blocks.end_stone, 'S', Items.stick, 'E', Items.ender_eye});
		GameRegistry.addRecipe(new ItemStack(endAxe, 1), new Object[] {"XX", "XE", " S", 'X', Blocks.end_stone, 'S', Items.stick, 'E', Items.ender_eye});
		GameRegistry.addRecipe(new ItemStack(endShovel, 1), new Object[] {"X", "E", "S", 'X', Blocks.end_stone, 'S', Items.stick, 'E', Items.ender_eye});
		GameRegistry.addRecipe(new ItemStack(endHoe, 1), new Object[] {"XX", " E", " S", 'X', Blocks.end_stone, 'S', Items.stick, 'E', Items.ender_eye});
    }
    
    public static ItemStack addEnchantment(ItemStack stack, int id, int lvl) {
		NBTTagCompound enchantedData = new NBTTagCompound();
		NBTTagList enchantments = new NBTTagList();
		NBTTagCompound enchantment = new NBTTagCompound();
		enchantment.setInteger("id", id);
		enchantment.setInteger("lvl", lvl);
		enchantments.appendTag(enchantment);
		enchantedData.setTag("ench", enchantments);
		stack.setTagCompound(enchantedData);
		return stack;
    }
}