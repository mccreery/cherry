package tk.nukeduck.heartdrops;

import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "heartdrops", name = "Heart Drops", version = "1.0")

public class HeartDrops {
    public static final Item heart = new Item().setUnlocalizedName("heartDrop").setTextureName("heartdrops:heart").setCreativeTab(CreativeTabs.tabMisc);
    public static Random random = new Random();
    
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		GameRegistry.registerItem(heart, "heart");
		MinecraftForge.EVENT_BUS.register(new HeartDrops());
	}
	
	@SubscribeEvent
	public void onEntityDrops(LivingDropsEvent event) {
		if(random.nextBoolean() && event.recentlyHit) event.entityLiving.dropItem(heart, (int) ((event.entityLiving.getMaxHealth() / 5) * (random.nextFloat() * 2.0F)));
	}
	
	@SubscribeEvent
	public void onEntityPickup(EntityItemPickupEvent event) {
		if(event.item.getEntityItem().getItem().equals(heart)) {
			event.entityPlayer.heal(event.item.getEntityItem().stackSize);
			event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.levelup", 1.0F, 2.0F);
			event.setCanceled(true);
			event.item.setDead();
		}
	}
}