package tk.nukeduck.lightsaber;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import tk.nukeduck.lightsaber.block.generation.WorldGeneratorCrystal;
import tk.nukeduck.lightsaber.block.tileentity.TileEntityCrystal;
import tk.nukeduck.lightsaber.block.tileentity.TileEntityRefillUnit;
import tk.nukeduck.lightsaber.client.ClientEvents;
import tk.nukeduck.lightsaber.client.renderer.CrystalRenderer;
import tk.nukeduck.lightsaber.client.renderer.RefillUnitRenderer;
import tk.nukeduck.lightsaber.client.renderer.RenderUtil;
import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import tk.nukeduck.lightsaber.entity.skills.ForceSkill;
import tk.nukeduck.lightsaber.entity.skills.ServerSkillHandler;
import tk.nukeduck.lightsaber.entity.skills.SkillHandler;
import tk.nukeduck.lightsaber.input.KeyBindings;
import tk.nukeduck.lightsaber.input.KeyHandlerGui;
import tk.nukeduck.lightsaber.network.BuySkillMessage;
import tk.nukeduck.lightsaber.network.BuySkillMessageHandler;
import tk.nukeduck.lightsaber.network.BuySkillReturnMessage;
import tk.nukeduck.lightsaber.network.BuySkillReturnMessageHandler;
import tk.nukeduck.lightsaber.network.ClientProxy;
import tk.nukeduck.lightsaber.network.CommonProxy;
import tk.nukeduck.lightsaber.network.IORuleMessage;
import tk.nukeduck.lightsaber.network.IORuleMessageHandler;
import tk.nukeduck.lightsaber.network.ManaMessage;
import tk.nukeduck.lightsaber.network.ManaMessageHandler;
import tk.nukeduck.lightsaber.network.PointsMessage;
import tk.nukeduck.lightsaber.network.PointsMessageHandler;
import tk.nukeduck.lightsaber.network.SetSelectedMessage;
import tk.nukeduck.lightsaber.network.SetSelectedMessageHandler;
import tk.nukeduck.lightsaber.network.SkillActiveMessage;
import tk.nukeduck.lightsaber.network.SkillActiveMessageHandler;
import tk.nukeduck.lightsaber.registry.LightsaberBlocks;
import tk.nukeduck.lightsaber.registry.LightsaberItems;
import tk.nukeduck.lightsaber.registry.crafting.LightsaberCrafting;
import tk.nukeduck.lightsaber.util.Constants;
import tk.nukeduck.lightsaber.util.Strings;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Strings.MOD_ID, name = "Lightsaber Mod", version = "1.0")

public class Lightsaber {
	@Instance
	public static Lightsaber instance;
	
	/** Instance of {@code Minecraft}. */
	public static Minecraft mc = Minecraft.getMinecraft();
	/** Instance of {@code Random}. */
	public static final Random random = new Random();
	
	/** Creative tab containing all the blocks and items of the mod. */
	public static CreativeTabs lightsaberTab = new CreativeTabs(Strings.MOD_ID) {
		public Item getTabIconItem() {
			return LightsaberItems.lightsabers[Strings.colorIndex(Strings.RED)];
		}
	};
	
	public static final DamageSource force = new DamageSource(Strings.MOD_ID + Strings.DAMAGE_FORCE_SUFFIX);
	
	@SidedProxy(clientSide="tk.nukeduck.lightsaber.network.ClientProxy", serverSide="tk.nukeduck.lightsaber.network.CommonProxy")
	public static CommonProxy proxy;
	public static ClientProxy clientProxy = new ClientProxy();
	
	/** Network wrapper for sending packets. */
	public static SimpleNetworkWrapper networkWrapper;
	
	/** Pre-initialisation event.<br/><ul>
	 * <li>Packet registration</li>
	 * <li>Setting up reflection</li>
	 * <li>Events registration</li>
	 * </ul> */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;
		
		RenderUtil.init();
		
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Strings.MOD_ID);
		networkWrapper.registerMessage(IORuleMessageHandler.class, IORuleMessage.class, 0, Side.SERVER);
		networkWrapper.registerMessage(BuySkillMessageHandler.class, BuySkillMessage.class, 1, Side.SERVER);
		networkWrapper.registerMessage(BuySkillReturnMessageHandler.class, BuySkillReturnMessage.class, 2, Side.CLIENT);
		networkWrapper.registerMessage(PointsMessageHandler.class, PointsMessage.class, 3, Side.CLIENT);
		
		networkWrapper.registerMessage(SetSelectedMessageHandler.class, SetSelectedMessage.class, 4, Side.CLIENT);
		networkWrapper.registerMessage(SetSelectedMessageHandler.class, SetSelectedMessage.class, 5, Side.SERVER);
		
		networkWrapper.registerMessage(SkillActiveMessageHandler.class, SkillActiveMessage.class, 6, Side.SERVER);
		
		networkWrapper.registerMessage(ManaMessageHandler.class, ManaMessage.class, 7, Side.CLIENT);
		
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		
		FMLCommonHandler.instance().bus().register(ClientEvents.instance);
		MinecraftForge.EVENT_BUS.register(ClientEvents.instance);
		
		KeyBindings.init();
		FMLCommonHandler.instance().bus().register(new KeyHandlerGui());
		FMLCommonHandler.instance().bus().register(ServerSkillHandler.instance);
	}
	
	/** Initialisation event.<br/><ul>
	 * <li>Item & block registration</li>
	 * <li>Tile entities</li>
	 * <li>GUI handlers</li>
	 * <li>Client-side rendering</li>
	 * </ul> */
	@EventHandler
	public void init(FMLInitializationEvent event) {
		LightsaberItems.registerItems();
		LightsaberBlocks.registerBlocks();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, this.proxy);
		
		GameRegistry.registerTileEntity(TileEntityRefillUnit.class, "LightsaberCharger");
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRefillUnit.class, new RefillUnitRenderer());
		GameRegistry.registerTileEntity(TileEntityCrystal.class, "Crystal");
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrystal.class, new CrystalRenderer());
		
		clientProxy.registerRenderers();
	}
	
	/** Post-initialisation event.<br/><ul>
	 * <li>Cafting recipes registration</li>
	 * <li>World generation</li>
	 * <li>Skills</li>
	 * </ul> */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		LightsaberCrafting.registerCrafting();
		
		MinecraftForge.EVENT_BUS.register(new Lightsaber());
		GameRegistry.registerWorldGenerator(new WorldGeneratorCrystal(), 1);
		
		SkillHandler.initHandlers();
	}
	
	/** Adds force skill properties to players. */
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if(event.entity instanceof EntityPlayer && ExtendedPropertiesForceSkills.get((EntityPlayer) event.entity) == null) {
			ExtendedPropertiesForceSkills.register((EntityPlayer) event.entity);
		}
	}
	
	/** Sends packets to newly-joining players telling them what skills they have. */
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if(event.entity instanceof EntityPlayerMP) {
			ExtendedPropertiesForceSkills skills = ExtendedPropertiesForceSkills.get((EntityPlayer) event.entity);
			for(ForceSkill skill : skills.skillsAttained.keySet()) {
				if(skills.skillsAttained.get(skill)) Lightsaber.networkWrapper.sendTo(new BuySkillReturnMessage(skill.getId(), skills.points), (EntityPlayerMP) event.entity);
			}
			for(int i = 0; i < Constants.MAX_SELECTED; i++) {
				if(skills.selected[i] != null) Lightsaber.networkWrapper.sendTo(new SetSelectedMessage(i, skills.selected[i].getId()), (EntityPlayerMP) event.entity);
			}
			Lightsaber.networkWrapper.sendTo(new PointsMessage(skills.progressCurrent, skills.points), (EntityPlayerMP) event.entity);
			skills.sendManaMessage((EntityPlayerMP) event.entity);
		}
	}
}