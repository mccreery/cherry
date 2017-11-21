package tk.nukeduck.lightsaber.client.renderer;

import static org.lwjgl.opengl.GL11.GL_ALL_ATTRIB_BITS;
import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import tk.nukeduck.lightsaber.Lightsaber;
import tk.nukeduck.lightsaber.client.ClientEvents;
import tk.nukeduck.lightsaber.item.ItemLightsaber;
import tk.nukeduck.lightsaber.util.Strings;
import cpw.mods.fml.client.FMLClientHandler;

public class ItemRenderLightsaber implements IItemRenderer {
	/** {@code RenderItem} instance for rendering items. */
	private static RenderItem renderItem = new RenderItem();
	
	private static ModelBaseLightsaber b = new ModelLightsaberBDouble();
	private static ModelBaseLightsaber bDouble = new ModelLightsaberBDouble();
	
	/** Models for rendering lightsabers. */
	private static ModelBaseLightsaber[] models = {
		new ModelLightsaberA(),
		b,
		b,
		new ModelLightsaberC()
	};
	/** Models for rendering double lightsabers. */
	private static ModelBaseLightsaber[] modelsDoubled = {
		new ModelLightsaberADouble(),
		bDouble,
		bDouble,
		new ModelLightsaberCDouble()
	};
	/** Hilt textures. */
	private static ResourceLocation[] hilts = new ResourceLocation[models.length];
	/** Blade textures. */
	public static ResourceLocation[] blades = new ResourceLocation[Strings.COLORS.length + 1];
	
	/** Instance of this used in {@code RefillUnitRenderer}. */
	public static final ItemRenderLightsaber instance = new ItemRenderLightsaber();
	
	/** Constructor.<br/>
	 * Initialises blade and hilt textures. */
	public ItemRenderLightsaber() {
		for(int i = 0; i < Strings.COLORS.length; i++) {
			blades[i] = new ResourceLocation(Strings.MOD_ID, "textures/models/items/lightsaber_blade_" + Strings.COLORS[i] + ".png");
		}
		blades[blades.length - 1] = new ResourceLocation(Strings.MOD_ID, "textures/models/items/lightsaber_blade_bright.png");
		for(int i = 0; i < Strings.HILTS.length; i++) {
			hilts[i] = new ResourceLocation(Strings.MOD_ID, "textures/models/items/lightsaber_empty_" + Strings.HILTS[i] + ".png");
		}
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,ItemRendererHelper helper) {
		return false;
	}
	
	private static TextureManager tm = FMLClientHandler.instance().getClient().renderEngine;
	
	/** Render the item given for use in Refill Units.
	 * @param type The type of render to use.
	 * @param item The item to render.
	 * @param rotationAmount Rotation of the item. */
	public void renderItemForCharger(ItemRenderType type, ItemStack item, int rotationAmount) {
		boolean doubled = ((ItemLightsaber) item.getItem()).getDoubled();
		glPushMatrix(); {
			glScalef(0.5f, 0.5f, 0.5f);
			glRotatef(rotationAmount, 0, 1, 0);
			glRotatef(35, 1, 0, 1);
			
			int hiltIndex = ItemLightsaber.getHiltIndex(item);
			tm.bindTexture(hilts[hiltIndex]);
			(doubled ? modelsDoubled : models)[hiltIndex].render(null, 0, 0, 0, 0, 0, 0.0625F);
			
			glPushMatrix(); {
				glScalef(0.5F, 1.0F, 0.5F);
				
				renderGlow(item, null, false);
			}
			glPopMatrix();
			
			if(doubled) {
				glPushMatrix(); {
					glScalef(0.5F, -1.0F, 0.5F);
					glTranslatef(0F, -0.0625F * (float) (doubled ? modelsDoubled : models)[hiltIndex].doubledOffset, 0F);
					
					renderGlow(item, null, false);
				}
				glPopMatrix();
			}
		}
		glPopMatrix();
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		Lightsaber.mc.mcProfiler.startSection("lightsaber");
		
		switch(type) {
			case INVENTORY:
				glEnable(GL_ALPHA_TEST);
				if(ItemLightsaber.isEnabled(item)) renderItem.renderIcon(0, 0, item.getIconIndex(), 16, 16);
				renderItem.renderIcon(0, 0, ((ItemLightsaber) item.getItem()).getEmptyIconIndex(item), 16, 16);
				break;
			case EQUIPPED:
			case EQUIPPED_FIRST_PERSON:
				glPushMatrix(); {
					if(data[1] != null && data[1] instanceof EntityPlayer) {
						if(!(data[1] == Lightsaber.mc.renderViewEntity && Lightsaber.mc.gameSettings.thirdPersonView == 0 && !((Lightsaber.mc.currentScreen instanceof GuiInventory || Lightsaber.mc.currentScreen instanceof GuiContainerCreative) && RenderManager.instance.playerViewY == 180.0f))) {
							glScalef(0.5F, 0.5F, 0.5F);
							glTranslatef(0.95F, 1.75F, 0);
							glRotatef(-150, 0, 0, 1);
						} else {
							glScalef(0.75F, 0.75F, 0.75F);
							glTranslatef(1, 1.5F, 0);
							glRotatef(-160, 0, 0, 1);
							glRotatef(-15, 1, 0, 0);
						}
					}
					
					int hiltIndex = ItemLightsaber.getHiltIndex(item);
					if(item != null) {
						boolean doubled = ((ItemLightsaber) item.getItem()).getDoubled();
						
						Entity entity = (Entity) data[1];
						
						FMLClientHandler.instance().getClient().renderEngine.bindTexture(hilts[hiltIndex]);
						(doubled ? modelsDoubled : models)[hiltIndex].render(entity, 0, 0, 0, 0, 0, 0.0625F);
						
						if(ItemLightsaber.isEnabled(item)) {
							glPushMatrix(); {
								glScalef(0.5F, 1.0F, 0.5F);
								
								renderGlow(item, entity, true);
							}
							glPopMatrix();
							
							if(((ItemLightsaber) item.getItem()).getDoubled()) {
								glPushMatrix(); {
									glScalef(0.5F, -1.0F, 0.5F);
									glTranslatef(0F, -0.0625F * (float) (doubled ? modelsDoubled : models)[hiltIndex].doubledOffset, 0F);
									
									renderGlow(item, entity, true);
								}
								glPopMatrix();
							}
						}
					} else {
						FMLClientHandler.instance().getClient().renderEngine.bindTexture(hilts[hiltIndex]);
						models[hiltIndex].render((Entity)data[1], 0, 0, 0, 0, 0, 0.0625F);
					}
				}
				glPopMatrix();
				break;
			default: break;
		}
		
		Lightsaber.mc.mcProfiler.endSection();
	}
	
	/** Render the glow around the given item.
	 * @param item The item to render a glow for.
	 * @param entity The entity holding the item.
	 * @param hand Whether or not the item is in the hand. */
	public void renderGlow(ItemStack item, Entity entity, boolean hand) {
		glPushAttrib(GL_ALL_ATTRIB_BITS);
		int hiltIndex = ItemLightsaber.getHiltIndex(item);
		
		if(hand) {
			glTranslatef(0, 0.0625F * (float) models[hiltIndex].bladeOffset, 0);
			if(item.getTagCompound().getInteger("Animation") < 8) {
				glScalef(1, ((float) item.getTagCompound().getInteger("Animation")) / 8, 1);
			}
			glTranslatef(0, -0.0625F * (float) models[hiltIndex].bladeOffset, 0);
		}
		
		glDisable(GL_LIGHTING);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		boolean black = Strings.colorIndex(Strings.BLACK) == ItemLightsaber.getColor(item);
		
		ResourceLocation white = blades[blades.length - 1];
		ResourceLocation color = blades[ItemLightsaber.getColor(item)];
		
		tm.bindTexture(black ? color : white);
		
		for(float i = 1; i < 4/*6*/; i++) {
			if(i == 2) {
				tm.bindTexture(black ? white : color);
				//glCullFace(GL_FRONT);
			} else if(i == 4) {
				//glCullFace(GL_BACK);
			}
			
			glTranslatef(0, 1.1F, 0);
			glScalef(1.4F, 1.01F, 1.4F);
			glTranslatef(0, -1.1F, 0);
			
			float val = (float) Math.sin(ClientEvents.tickCounter) / 15F;
			
			if(i == 1) glColor4f(1, 1, 1, 1);
			else glColor4f(1, 1, 1, (black ? 0.6F : 0.9F) - i / 5/*6*/ + val);
			
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 0F);
			models[hiltIndex].render(entity, 0, 0, 0, 0, 0, 0.0625F, true);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, OpenGlHelper.lastBrightnessX, OpenGlHelper.lastBrightnessY);
		}
		glPopAttrib();
	}
}
