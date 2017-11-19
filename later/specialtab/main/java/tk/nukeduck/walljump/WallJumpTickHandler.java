package tk.nukeduck.walljump;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WallJumpTickHandler {
	private static final ResourceLocation icons = new ResourceLocation("walljump", "textures/gui/wallJumpIcons.png");
	
	/** Indicates how many degrees are still to be turned by the player. */
	private static int rotationRemaining;
	/** Dictates whether the player should be turning to the left or the right. {@code true} means left, {@code false} means right. */
	private static boolean left;
	/** Keeps track of how many wall jumps the player has left before they run out of energy. */
	private static int jumpsLeft = 3;
	/** If this is {@code true} it means the player is in the air and has wall jumped since becoming air-bound. */
	public static boolean isWallJumping;
	/**  Custom fall distance used in order to attempt fixing the fall damage bug. */
	public static double fallDistance = 0;
	
	/** What the player's Y motion should be set to as a bounce-back. */
	private static final double bounceBack = 0.6;
	
	private boolean shouldSprint;
	private Minecraft mc;
	
	public static enum Boots {
		bootsCloth, bootsIron, bootsGold, bootsDiamond, bootsChain
	}
	
	public WallJumpTickHandler() {
		mc = Minecraft.getMinecraft();
	}
	
	public boolean isJumpingOn() {
		String[] blocks = WallJump.getString("Exceptions").split(";");
		for(String block : blocks) {
			if(block.contains("!")) {
				Block b;
				
				String[] a = block.split("!");
				
				if(a[0].contains(":")) b = GameRegistry.findBlock(a[0].split(":")[0], a[0].split(":")[1]);
				else b = GameRegistry.findBlock("minecraft", block);
				
				if(getBlockInfrontofPlayer(mc) == b && getBlockDataInfrontofPlayer(mc) == (GuiWallJumpMenu.isInteger(a[1]) ? Integer.parseInt(a[1]) : 0)) return true;
			} else {
				Block b;
				
				if(block.contains(":")) b = GameRegistry.findBlock(block.split(":")[0], block.split(":")[1]);
				else b = GameRegistry.findBlock("minecraft", block);
				
				if(getBlockInfrontofPlayer(mc) == b) return true;
			}
		}
		
		return false;
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) {
		Items.command_block_minecart.setCreativeTab(CreativeTabs.tabBlock);
		
		if(e.phase == Phase.END) {
			if(mc.thePlayer != null && !mc.thePlayer.isCollidedHorizontally) {
				if(mc.thePlayer.isSprinting()) shouldSprint = true;
				else shouldSprint = false;
			}
			
			WallJump.loadSettings();
			
			if(getBlockInfrontofPlayer(mc) != null && !isJumpingOn()) {
				if(KeyHandlerMenu.wallJump.isKeyDown() || (WallJump.getBool("UseSpace") && mc.gameSettings.keyBindJump.isKeyDown())) {
					if(rotationRemaining < 80 && mc.thePlayer.isCollidedHorizontally && mc.thePlayer.motionY < 0 && mc.thePlayer.fallDistance > 0 && jumpsLeft > 0 && !mc.thePlayer.isInWater() && WallJump.getBool("WallJumping")) {
						if(WallJump.getBool("FlyWallJump")) {
							playCurrentStepSound(mc);
							if(!mc.thePlayer.capabilities.isCreativeMode && WallJump.getString("JumpType") != "2") jumpsLeft -= 1;
							if(!Boolean.parseBoolean(WallJump.getString("CircleMode"))) left = !left;
							mc.thePlayer.motionY = bounceBack;
							
							double a = Math.toRadians(mc.thePlayer.rotationYaw - 180);
							double dx = -Math.sin(a);
							double dz = Math.cos(a);
							
							mc.thePlayer.motionX = dx / 5;
							mc.thePlayer.motionZ = dz / 5;
							rotationRemaining = 180;
							fallDistance = 0;
							isWallJumping = true;
							
							mc.thePlayer.fallDistance = 0;
						} else if(!mc.thePlayer.capabilities.isFlying) {
							playCurrentStepSound(mc);
							if(!mc.thePlayer.capabilities.isCreativeMode) jumpsLeft -= 1;
							
							if(!WallJump.getBool("CircleMode")) left = !left;
							mc.thePlayer.motionY = bounceBack;
							
							double a = left ? Math.toRadians(mc.thePlayer.rotationYaw - 180) : Math.toRadians(mc.thePlayer.rotationYaw + 180);
							double dx = -Math.sin(a);
							double dz = Math.cos(a);
							
							mc.thePlayer.motionX = dx / 5;
							mc.thePlayer.motionZ = dz / 5;
							rotationRemaining = 180;
							mc.thePlayer.fallDistance = 0;
						}
					}
				}
				
				if(mc.currentScreen == null && rotationRemaining > 0) {
					if(WallJump.getBool("AutoTurn")) {
						if(left) {
							if(rotationRemaining >= 25) {
								mc.thePlayer.rotationYaw -= 25;
								rotationRemaining -= 25;
							} else {
								mc.thePlayer.rotationYaw -= rotationRemaining;
								rotationRemaining = 0;
							}
						} else {
							if(rotationRemaining >= 25) {
								mc.thePlayer.rotationYaw += 25;
								rotationRemaining -= 25;
							} else {
								mc.thePlayer.rotationYaw += rotationRemaining;
								rotationRemaining = 0;
							}
						}
					} else {
						rotationRemaining = 0;
					}
				}
			}
		}
		
		int jumpType = WallJump.getInt("JumpType");
		// Break point between new and old code positioning
		
		if(mc.currentScreen == null && mc.thePlayer.motionY < 0 && mc.theWorld != null) {
			fallDistance -= mc.thePlayer.motionY;
		}
		//mc.entityRenderer.setupOverlayRendering();
		
		//Refill wall jump bar according to boots worn
		if(mc.theWorld != null && mc.thePlayer.onGround)
		{
			if(jumpType != 1) {
				jumpsLeft = 10;
				if(jumpType == 2) {
					jumpsLeft = Integer.MAX_VALUE;
				}
			} else {
				if(mc.thePlayer.inventory.armorItemInSlot(0) != null) {
					try {
						switch(Boots.valueOf(mc.thePlayer.inventory.armorItemInSlot(0).getUnlocalizedName().replace("item.", ""))) {
							case bootsCloth: //Leather Boots
								jumpsLeft = 4;
								break;
							case bootsIron: //Iron Boots
								jumpsLeft = 6;
								break;
							case bootsGold: //Golden Boots
								jumpsLeft = 8;
								break;
							case bootsDiamond: //Diamond Boots
								jumpsLeft = 12;
								break;
							case bootsChain: //Chain-mail Boots
								jumpsLeft = 10;
								break;
							default: //Any other boots from mods
								jumpsLeft = 3;
						}
					} catch(IllegalArgumentException ex) {
						jumpsLeft = 3;
					}
				} else {
					jumpsLeft = 3; //Barefoot
				}
			}
		}
		
		if(mc.thePlayer != null && mc.thePlayer.onGround) {
			isWallJumping = false;
		}
		
		if(shouldSprint && !mc.thePlayer.isSprinting()) {
			mc.thePlayer.setSprinting(true);
		}
	}
	
	/**
	 * Renders the Wall Jump bar (or text) into the player's in-game GUI, unless they are in Creative Mode, do not have any jumps left or have their GUI toggled off using F1.
	 */
	@SubscribeEvent
	public void onRenderOverlayTick(RenderGameOverlayEvent.Post e) {
		if(e.type == RenderGameOverlayEvent.ElementType.ALL && mc != null && mc.theWorld != null && !mc.thePlayer.capabilities.isCreativeMode) {
			glPushMatrix(); {
				WallJump.loadSettings();
				
				GuiIngame gui = mc.ingameGUI;
				mc.renderEngine.bindTexture(icons);
				glColor3f(1.0F, 1.0F, 1.0F);
				
				// Variables used to greatly shorten the following lines.
				ScaledResolution scaledResolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
				
				int displayType = WallJump.getInt("DisplayType");
				int width = scaledResolution.getScaledWidth(), height = scaledResolution.getScaledHeight();
				
				int offsetX, offsetY;
				offsetX = offsetY = 5;
				
				switch(displayType) {
					case 0:
						offsetX = width / 2 + 10;
						offsetY = mc.thePlayer.isInsideOfMaterial(Material.water) ? height - 59 : height - 49;
						break;
					case 1:
						offsetX = width / 2 - 90;
						offsetY = (ForgeHooks.getTotalArmorValue(mc.thePlayer) > 0 ? height - 59 : height - 49);
						break;
					case 2:
						break; // Keep defaults
					case 3:
						offsetX = width - 85;
						// Keep default Y
						break;
					case 4:
						// Keep default X
						offsetY = height - 15;
						break;
					case 5:
						offsetX = width - 85;
						offsetY = height - 15;
						break;
					case 6:
						break; // Keep defaults
					case 7:
						offsetX = width - mc.fontRendererObj.getStringWidth("Wall Jumps Remaining: " + (WallJump.getInt("JumpType") != 2 ? jumpsLeft : "Infinity")) - 5;
						// Keep default Y
						break;
					case 8:
						// Keep default X
						offsetY = height - 15;
						break;
					case 9:
						offsetX = width - mc.fontRendererObj.getStringWidth("Wall Jumps Remaining: " + (WallJump.getInt("JumpType") != 2 ? jumpsLeft : "Infinity")) - 5;
						offsetY = height - 15;
						break;
				}
				
				if(jumpsLeft > 0 && WallJump.getBool("WallJumping")) {
					if(displayType <= 5) {
						String barIcons = WallJump.getString("BarIcons");
						for(int i = 0; i < jumpsLeft && i < 10; i++) {
							if(barIcons.equals("Feathers"))
								gui.drawTexturedModalRect(offsetX + 8 * i, offsetY, 9, 0, 9, 9);
							else if(barIcons.equals("Boots"))
								gui.drawTexturedModalRect(offsetX + 8 * i, offsetY, 0, 0, 9, 9);
							else if(barIcons.equals("Players"))
								gui.drawTexturedModalRect(offsetX + 8 * i, offsetY, 18, 0, 9, 9);
							else
								gui.drawTexturedModalRect(offsetX + 8 * i, offsetY, 27, 0, 9, 9);
						}
					} else {
						gui.drawString(mc.fontRendererObj, "Wall Jumps Remaining: " + (WallJump.getInt("JumpType") != 2 ? jumpsLeft : "Infinity"), offsetX, offsetY, 0xffffff);
					}
				}
			}
			glPopMatrix();
		}
	}
	
	/**
	 * Plays the sound of the block directly in front of the player, and one block down.
	 */
	public static void playCurrentStepSound(Minecraft mc)
	{
		int direction = MathHelper.floor_double((double)((mc.thePlayer.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		Block block = null;
		
		switch(direction)
		{
			case 0: //Direction 0 = +Z
			{
				block = mc.theWorld.getBlockState(new BlockPos((int)Math.floor(mc.thePlayer.posX), (int)Math.floor(mc.thePlayer.posY - 1), (int)Math.floor(mc.thePlayer.posZ + 1))).getBlock();
				break;
			}
			case 1: //Direction 1 = -X (issues)
			{
				block = mc.theWorld.getBlockState(new BlockPos((int)Math.floor(mc.thePlayer.posX - 1), (int)Math.floor(mc.thePlayer.posY - 1), (int)Math.floor(mc.thePlayer.posZ))).getBlock();
				break;
			}
			case 2: //Direction 2 = -Z
			{
				block = mc.theWorld.getBlockState(new BlockPos((int)Math.floor(mc.thePlayer.posX), (int)Math.floor(mc.thePlayer.posY - 1), (int)Math.floor(mc.thePlayer.posZ - 1))).getBlock();
				break;
			}
			case 3: //Direction 3 = +X
			{
				block = mc.theWorld.getBlockState(new BlockPos((int)Math.floor(mc.thePlayer.posX + 1), (int)Math.floor(mc.thePlayer.posY - 1), (int)Math.floor(mc.thePlayer.posZ))).getBlock();
				break;
			}
		}
		
        if (block != null)
        {
            SoundType stepsound = block.stepSound;
            mc.thePlayer.playSound(stepsound.soundName, stepsound.getVolume() * 0.5F, stepsound.frequency * 0.75F);
        }
	}
	
	/**
	 * Checks the block directly in front of the player.
	 */
	public static Block getBlockInfrontofPlayer(Minecraft mc)
	{
		int direction = 0;
		try
		{
			direction = MathHelper.floor_double((double)((mc.thePlayer.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		} catch(Exception e)
		{
			return null;
		}
		
		Block block = null;
		
		switch(direction)
		{
			case 0: //Direction 0 = +Z
			{
				block = mc.theWorld.getBlockState(new BlockPos((int)Math.floor(mc.thePlayer.posX), (int)Math.floor(mc.thePlayer.posY - 1), (int)Math.floor(mc.thePlayer.posZ + 1))).getBlock();
				break;
			}
			case 1: //Direction 1 = -X (issues)
			{
				block = mc.theWorld.getBlockState(new BlockPos((int)Math.floor(mc.thePlayer.posX - 1), (int)Math.floor(mc.thePlayer.posY - 1), (int)Math.floor(mc.thePlayer.posZ))).getBlock();
				break;
			}
			case 2: //Direction 2 = -Z
			{
				block = mc.theWorld.getBlockState(new BlockPos((int)Math.floor(mc.thePlayer.posX), (int)Math.floor(mc.thePlayer.posY - 1), (int)Math.floor(mc.thePlayer.posZ - 1))).getBlock();
				break;
			}
			case 3: //Direction 3 = +X
			{
				block = mc.theWorld.getBlockState(new BlockPos((int)Math.floor(mc.thePlayer.posX + 1), (int)Math.floor(mc.thePlayer.posY - 1), (int)Math.floor(mc.thePlayer.posZ))).getBlock();
				break;
			}
		}
		return block;
	}
	
	/**
	 * Checks the block metadata value directly in front of the player.
	 */
	public static int getBlockDataInfrontofPlayer(Minecraft mc)
	{
		int direction = 0;
		try
		{
			direction = MathHelper.floor_double((double)((mc.thePlayer.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		} catch(Exception e)
		{
			return 0;
		}
		
		IBlockState block = null;
		
		switch(direction)
		{
			case 0: //Direction 0 = +Z
			{
				block = mc.theWorld.getBlockState(new BlockPos((int)Math.floor(mc.thePlayer.posX), (int)Math.floor(mc.thePlayer.posY - 1), (int)Math.floor(mc.thePlayer.posZ + 1)));
				break;
			}
			case 1: //Direction 1 = -X (issues)
			{
				block = mc.theWorld.getBlockState(new BlockPos((int)Math.floor(mc.thePlayer.posX - 1), (int)Math.floor(mc.thePlayer.posY - 1), (int)Math.floor(mc.thePlayer.posZ)));
				break;
			}
			case 2: //Direction 2 = -Z
			{
				block = mc.theWorld.getBlockState(new BlockPos((int)Math.floor(mc.thePlayer.posX), (int)Math.floor(mc.thePlayer.posY - 1), (int)Math.floor(mc.thePlayer.posZ - 1)));
				break;
			}
			case 3: //Direction 3 = +X
			{
				block = mc.theWorld.getBlockState(new BlockPos((int)Math.floor(mc.thePlayer.posX + 1), (int)Math.floor(mc.thePlayer.posY - 1), (int)Math.floor(mc.thePlayer.posZ)));
				break;
			}
		}
		return block != null ? block.getBlock().getMetaFromState(block) : null;
	}
}