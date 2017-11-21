package tk.nukeduck.lightsaber.client;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.GL_ALL_ATTRIB_BITS;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.client.renderer.CrystalRenderer;
import tk.nukeduck.lightsaber.client.renderer.EntityLightFX;
import tk.nukeduck.lightsaber.client.renderer.RenderUtil;
import tk.nukeduck.lightsaber.entity.ExtendedPropertiesForceSkills;
import tk.nukeduck.lightsaber.entity.skills.SkillHandler;
import tk.nukeduck.lightsaber.item.ItemLightsaber;
import tk.nukeduck.lightsaber.registry.crafting.EnergyCapsuleRefillHandler;
import tk.nukeduck.lightsaber.util.Strings;

import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientEvents {
	/** Overlay bar texture. */
	private static final ResourceLocation overlay = new ResourceLocation(Strings.MOD_ID, "textures/gui/ingame_overlay.png");
	/** Instance for use in registering events. */
	public static final ClientEvents instance = new ClientEvents();
	
	public static int disableCountdown = 0;
	public static float tickCounter = 0;
	
	/** Force Body overlay texture. */
	private static final ResourceLocation overlayBody = new ResourceLocation(Strings.MOD_ID, "textures/gui/body_overlay.png");
	public static float overlayBodyOpacity = 0.0F;
	
	/** Ticks counters and updates skill handlers. */
	@SubscribeEvent
	public void clientTick(TickEvent.ClientTickEvent e) {
		overlayBodyOpacity = (float) Math.max(0.0D, overlayBodyOpacity - 0.01D);
		
		tickCounter += 0.1F;
		if(tickCounter >= 360) tickCounter = 0;
		
		if(e.phase != Phase.START) return;
		if(disableCountdown > 0) disableCountdown--;
		
		if(Lightsaber.mc.thePlayer != null) SkillHandler.update(Lightsaber.mc.thePlayer);
	}
	
	/** Draws aura on mobs. */
	@SubscribeEvent
	public void onEntityRender(RenderLivingEvent.Specials.Pre e) {
		if(SkillHandler.AURA.getIsActive()) {
			EntityLivingBase entity = e.entity;
			EntityLivingBase player = Lightsaber.mc.thePlayer;
			
			glPushAttrib(GL_ALL_ATTRIB_BITS);
			glPushMatrix();
				glDisable(GL_DEPTH_TEST);
				glDisable(GL_LIGHTING);
				
				glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				
				double dx = (entity.prevPosX + (entity.posX - entity.prevPosX) * this.currentPartialTicks) - (player.prevPosX + (player.posX - player.prevPosX) * this.currentPartialTicks); 
				double dy = (entity.prevPosY + (entity.posY - entity.prevPosY) * this.currentPartialTicks) - (player.prevPosY + (player.posY - player.prevPosY) * this.currentPartialTicks); 
				double dz = (entity.prevPosZ + (entity.posZ - entity.prevPosZ) * this.currentPartialTicks) - (player.prevPosZ + (player.posZ - player.prevPosZ) * this.currentPartialTicks);
				
				glDisable(GL_LIGHTING);
				
				glTranslated(dx + 0.5F, dy + entity.height / 2, dz + 0.5F);
				glRotatef(-player.rotationYaw, 0.0F, 1.0F, 0.0F);
				glRotatef(player.rotationPitch, 1.0F, 0.0F, 0.0F);
				glRotatef(180, 0, 0, 1);
				glTranslatef(-0.5F, -0.5F, 0.0F);
				
				glScalef(0.0625F, 0.0625F, 0.0625F);
				Lightsaber.mc.ingameGUI.drawTexturedModalRect(0, 0, 0, 0, 32, 32);
				//Lightsaber.mc.ingameGUI.drawString(Lightsaber.mc.fontRenderer, entity.getCommandSenderName(), 0, 0, 0xffffff);
			glPopMatrix();
			glPopAttrib();
		}
	}
	
	/** Renders the mana bar on the in-game HUD. */
	@SubscribeEvent
	public void onOverlayRender(RenderGameOverlayEvent.Post e) {
		if(e.type == RenderGameOverlayEvent.ElementType.AIR && Lightsaber.mc != null && Lightsaber.mc.theWorld != null && !Lightsaber.mc.thePlayer.capabilities.isCreativeMode) {
			glPushMatrix();
				Lightsaber.mc.getTextureManager().bindTexture(overlay);
				
				int width = Lightsaber.mc.displayWidth;
				int height = Lightsaber.mc.displayHeight;
				ScaledResolution scale = new ScaledResolution(Lightsaber.mc, width, height);
				width = scale.getScaledWidth();
				height = scale.getScaledHeight();
				
				if(Lightsaber.mc.thePlayer != null) {
					int yOffset = Lightsaber.mc.thePlayer.isInsideOfMaterial(Material.water) ? 59 : 49;
					
					Lightsaber.mc.ingameGUI.drawTexturedModalRect(width / 2 + 10, height - yOffset, 0, 0, 81, 9);
					ExtendedPropertiesForceSkills skills = ExtendedPropertiesForceSkills.get(Lightsaber.mc.thePlayer);
					if(skills != null) {
						Lightsaber.mc.ingameGUI.drawTexturedModalRect(width / 2 + 10, height - yOffset, 0, 9, Math.round(((float) skills.currentMana / (float) skills.maxMana) * 81F), 9);
					}
				}
				
				Lightsaber.mc.getTextureManager().bindTexture(overlayBody);
				glEnable(GL_BLEND);
				glColor4f(1, 1, 1, overlayBodyOpacity);
				Tessellator t = Tessellator.instance;
				t.startDrawingQuads();
				
				t.addVertexWithUV(0, 0, 0, 0, 0);
				t.addVertexWithUV(0, height, 0, 0, 1);
				t.addVertexWithUV(width, height, 0, 1, 1);
				t.addVertexWithUV(width, 0, 0, 1, 0);
				
				t.draw();
			glPopMatrix();
		}
	}
	
	public float currentPartialTicks = 0.0F;
	
	/** Attempts to prevent switching animation, if applicable. */
	@SubscribeEvent
	public void lastWorldRender(RenderWorldLastEvent e) {
		this.currentPartialTicks = e.partialTicks;
		if(disableCountdown > 0) {
			if(!RenderUtil.preventSwitching(Lightsaber.mc)) System.err.println("Reflection error: Could not prevent equip animation in hand.");
		}
	}
	
	/** Resets switching animation counter when a lightsaber is dropped. */
	@SubscribeEvent
	public void itemDrop(ItemTossEvent e) {
		if(e.entityItem.getEntityItem().getItem() instanceof ItemLightsaber && e.player.getHeldItem() == null) {
			disableCountdown = 0;
		}
	}
	
	/** Adds energy levels to items when shift is pressed. */
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onTooltip(ItemTooltipEvent e) {
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			if(ArrayUtils.contains(EnergyCapsuleRefillHandler.charges.keySet().toArray(), e.itemStack.getItem())) {
				double charge = EnergyCapsuleRefillHandler.charges.get(e.itemStack.getItem());
				String text = Strings.translate(Strings.ENERGY_LITERAL) + ChatFormatting.BLUE + (Math.abs(charge % 1.0) > 0.01 ? charge : (int) charge) + Strings.translate(Strings.ENERGY_SYMBOL);
				if(!e.toolTip.contains(text)) e.toolTip.add(text);
			}
		}
	}
}