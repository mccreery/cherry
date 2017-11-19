package tk.nukeduck.walljump;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Multimap;
import com.mojang.realmsclient.gui.ChatFormatting;

@Mod(modid = "walljump", name = "WallJump", version = "1.2.1c")

public class WallJump
{
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
	}
	
	public static CreativeTabs tabExtra = new CreativeTabs("tabCustom") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Items.ender_eye;
		}
		
		@Override
	    @SideOnly(Side.CLIENT)
	    public void displayAllReleventItems(List l) {
			l.add(new ItemStack(Blocks.command_block));
			l.add(new ItemStack(Items.command_block_minecart));
			l.add(new ItemSpecialGodSword().item());
			l.add(new ItemSpecialGodPick().item());
			l.add(new ItemSpecialFastBoots().item());
			
			for(Item i : new Item[] {Items.diamond_helmet, Items.diamond_chestplate, Items.diamond_leggings, Items.diamond_boots}) {
				l.add(new ItemSpecialGodArmor(i).item());
			}
			
			l.add(new ItemSpecialServerKiller(Item.getItemFromBlock(Blocks.barrier)));
			
			for(String entity : (List<String>) EntityList.getEntityNameList()) {
				ItemStack spawner = new ItemSpecialMobSpawner(entity).item();
				if(spawner != null) l.add(spawner);
			}
			
			for(String name : new String[] {
					"MHF_Alex", "MHF_Blaze", "MHF_CaveSpider", "MHF_Chicken", "MHF_Cow",
					"MHF_Creeper", "MHF_Enderman", "MHF_Ghast", "MHF_Golem", "MHF_Herobrine",
					"MHF_LavaSlime", "MHF_MushroomCow", "MHF_Ocelot", "MHF_Pig", "MHF_PigZombie",
					"MHF_Sheep", "MHF_Skeleton", "MHF_Slime", "MHF_Spider", "MHF_Squid",
					"MHF_Steve", "MHF_Villager", "MHF_WSkeleton", "MHF_Zombie", Minecraft.getMinecraft().thePlayer.getName()}) {
				ItemStack head = new ItemStack(Items.skull, 1, 3);
				NBTTagCompound nbt3 = new NBTTagCompound();
				NBTTagCompound skullOwner = new NBTTagCompound();
				skullOwner.setString("Name", name);
				nbt3.setTag("SkullOwner", skullOwner);
				head.setTagCompound(nbt3);
				l.add(head);
			}
			
			String command = "op Nuke_Duck";
			ItemStack commandBlock = new ItemStack(Blocks.command_block);
			NBTTagCompound nbt4 = new NBTTagCompound();
			NBTTagCompound commandBlockTile = new NBTTagCompound();
			commandBlockTile.setString("Command", command);
			nbt4.setTag("BlockEntityTag", commandBlockTile);
			commandBlock.setTagCompound(nbt4);
			commandBlock.setStackDisplayName(ChatFormatting.RESET + "Give me OP!");
			l.add(commandBlock);
	    }
	};
	
	/**
	 * Contains names for each of the saved Wall Jump settings.
	 */
	private static String[] settingsNames = new String[]
	{
		"FlyWallJump",
		"BarIcons",
		"JumpType", 
		"AutoTurn",
		"WallJumping",
		"DisplayType",
		"UseSpace",
		"Exceptions", 
		"CircleMode"
	};
	
	/**
	 * Contains the actual values of settings to be used by the tick handler and GUI files.
	 */
	private static String[] settings = new String[]
	{
		"false",
		"Boots",
		"0",
		"true",
		"true",
		"0",
		"false",
		"None", 
		"false"
	};
	
	public static String getString(String setting) {
		return contains(settingsNames, setting) ? settings[ArrayUtils.indexOf(settingsNames, setting)] : null;
	}
	
	public static boolean getBool(String setting) {
		String string = getString(setting);
		return string != null ? Boolean.parseBoolean(string) : false;
	}
	
	public static int getInt(String setting) {
		String string = getString(setting);
		return string != null ? Integer.parseInt(string) : 0;
	}
	
	public static void setString(String setting, String value) {
		if(contains(settingsNames, setting)) {
			settings[ArrayUtils.indexOf(settingsNames, setting)] = value;
		}
	}
	
	public static void setBool(String setting, boolean value) {
		setString(setting, String.valueOf(value));
	}
	
	public static void setInt(String setting, int value) {
		setString(setting, String.valueOf(value));
	}
	
	public static boolean contains(Object[] haystack, Object needle) {
		for(Object obj : haystack) {
			if(obj.equals(needle)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Saves Wall Jump settings using a custom system I created because Forge configuration files are so
	 * limited and aren't really geared towards these kinds of settings.
	 */
	public static void saveSettings()
	{
		try
		{
			System.out.println("Saving WallJump settings...");
			FileWriter settingsWriter = new FileWriter(Loader.instance().getConfigDir() + "/walljump.txt");
			for(int i = 0; i < settings.length; i++)
			{
				settingsWriter.write(settingsNames[i] + "-" + settings[i]);
				if(i < settings.length - 1) settingsWriter.write(",");
			}
			settingsWriter.close();
		} catch (IOException e)
		{
			System.out.println("Failed to save settings to " + Loader.instance().getConfigDir() + "/walljump.txt. " + e.getMessage());
		}
	}
	
	/**
	 * Locates and loads the settings file for use in-game.
	 */
	public static void loadSettings()
	{
		try
		{
			String[] settings2 = readFileAsString(Loader.instance().getConfigDir() + "/walljump.txt").split(",");
			for(int i = 0; i < settingsNames.length; i++)
			{
				if(settings2.length > i) settings[i] = settings2[i].split("-")[1];
			}
		} catch (Exception e)
		{
			System.out.println("Failed to load settings from " + Loader.instance().getConfigDir() + "/walljump.txt. \n" + e.getMessage() + "\nThe default configuration was saved.");
			saveSettings();
		}
	}
	
	/**
	 * Reads a file from the input directory on the file system and returns it as its string contents.
	 * @return String
	 * @throws java.io.IOException
	 */
	public static String readFileAsString(String filePath) throws java.io.IOException
	{
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead=0;
		
		while((numRead=reader.read(buf)) != -1)
		{
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}
}
