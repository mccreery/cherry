package tk.nukeduck.walljump;

import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class GuiWallJumpMenu extends GuiScreen
{
    /** Counts the number of screen updates. */
	private int updateCounter = 0;
    
    private static GuiButton flyWallJump;
    private static GuiButton wallJumping;
    private static GuiButton barIcons;
    private static GuiButton jumpType;
    private static GuiButton autoTurn;
    private static GuiButton displayType;
    private static GuiButton shouldUseSpace;
    private static GuiButton circleMode;
    
    /** A text box containing all block IDs which cannot be wall jumped from */
    private static GuiTextField exceptions;
    
    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
    	Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        byte b0 = -16;
        
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 24 + b0, StatCollector.translateToLocal("menu.returnToGame")));
        this.buttonList.add(wallJumping = new GuiButton(6, this.width / 2 - 100, this.height / 4 + 48 + b0, "Wall Jumping"));
        this.buttonList.add(flyWallJump = new GuiButton(2, this.width / 2 - 152, this.height / 4 + 72 + b0, 150, 20, "Flying Wall Jump"));
        this.buttonList.add(barIcons = new GuiButton(3, this.width / 2 + 2, this.height / 4 + 72 + b0, 150, 20, ""));
        this.buttonList.add(jumpType = new GuiButton(4, this.width / 2 - 152, this.height / 4 + 96 + b0, 150, 20, ""));
        this.buttonList.add(autoTurn = new GuiButton(5, this.width / 2 + 2, this.height / 4 + 96 + b0, 150, 20, "Auto-Turn"));
        //
        this.buttonList.add(displayType = new GuiButton(7, this.width / 2 - 152, this.height / 4 + 120 + b0, 150, 20, "Display"));
        this.buttonList.add(shouldUseSpace = new GuiButton(8, this.width / 2 + 2, this.height / 4 + 120 + b0, 150, 20, "Use jump key"));
        //
        this.buttonList.add(circleMode = new GuiButton(9, this.width / 2 - 152, this.height / 4 + 144 + b0, 150, 20, "Circle Mode"));
        exceptions = new GuiTextField(10, mc.fontRendererObj, this.width / 2 - 75, this.height / 4 + 184 + b0, 150, 20);
        exceptions.setMaxStringLength(200);
        exceptions.setEnabled(true);
        
        WallJump.loadSettings();
        
        barIcons.displayString = "Bar Icons: " + WallJump.getString("BarIcons");
        jumpType.displayString = "Jumping Type: " + jumpTypes[WallJump.getInt("JumpType")];
        displayType.displayString = "Display: " + displayTypes[WallJump.getInt("DisplayType")];
        
        if(WallJump.getBool("FlyWallJump")) flyWallJump.displayString = "Flying wall Jump: " + StatCollector.translateToLocal("options.on");
        else flyWallJump.displayString = "Flying Wall Jump: " + StatCollector.translateToLocal("options.off");
        
        if(WallJump.getBool("AutoTurn")) autoTurn.displayString = "Auto-Turn: " + StatCollector.translateToLocal("options.on");
        else autoTurn.displayString = "Auto-Turn: " + StatCollector.translateToLocal("options.off");
        
        if(WallJump.getBool("WallJumping")) wallJumping.displayString = "Wall Jumping: " + StatCollector.translateToLocal("options.on");
        else wallJumping.displayString = "Wall Jumping: " + StatCollector.translateToLocal("options.off");
        
        if(WallJump.getBool("UseSpace")) shouldUseSpace.displayString = "Use jump key: " + StatCollector.translateToLocal("options.on");
        else shouldUseSpace.displayString = "Use jump key: " + StatCollector.translateToLocal("options.off");
        
        if(WallJump.getBool("CircleMode")) circleMode.displayString = "Circle Mode: " + StatCollector.translateToLocal("options.on");
        else circleMode.displayString = "Circle Mode: " + StatCollector.translateToLocal("options.off");
        
        exceptions.setText(WallJump.getString("Exceptions"));
        
        if(!WallJump.getBool("WallJumping")) {
        	barIcons.enabled = false;
        	jumpType.enabled = false;
        	autoTurn.enabled = false;
        	flyWallJump.enabled = false;
        	displayType.enabled = false;
        	shouldUseSpace.enabled = false;
        }
    }

    /**
     * Called when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
    	WallJump.loadSettings();
        switch (par1GuiButton.id)
        {
        	case 1:
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                this.mc.getSoundHandler().resumeSounds();
        		break;
            case 2:
            	Boolean flag = !WallJump.getBool("FlyWallJump");
            	flyWallJump.displayString = flag ? "Flying wall Jump: " + StatCollector.translateToLocal("options.on") : "Flying Wall Jump: " + StatCollector.translateToLocal("options.off");
            	WallJump.setBool("FlyWallJump", flag);
                break;
            case 3:
            	String barIconsValue = WallJump.getString("BarIcons");
            	if(barIconsValue.equals("Boots")) {
            		WallJump.setString("BarIcons", "Feathers");
            	} else if(barIconsValue.equals("Feathers")) {
            		WallJump.setString("BarIcons", "Players");
            	} else if(barIconsValue.equals("Players")) {
            		WallJump.setString("BarIcons", "Smoke");
            	} else {
            		WallJump.setString("BarIcons", "Boots");
            	}
                barIcons.displayString = "Bar Icons: " + WallJump.getString("BarIcons");
                break;
            case 4:
            	Integer i = WallJump.getInt("JumpType") + 1;
            	if(i == 3) i = 0;
            	WallJump.setInt("JumpType", i);
            	jumpType.displayString = "Jumping Type: " + jumpTypes[i];
            	break;
            case 5:
            	Boolean flag2 = !WallJump.getBool("AutoTurn");
            	autoTurn.displayString = flag2 ? "Auto-Turn: " + StatCollector.translateToLocal("options.on") : "Auto-Turn: " + StatCollector.translateToLocal("options.off");
            	WallJump.setBool("AutoTurn", flag2);
            	break;
            case 6:
            	Boolean flag3 = !WallJump.getBool("WallJumping");
            	if(flag3) wallJumping.displayString = "Wall Jumping: " + StatCollector.translateToLocal("options.on");
                else wallJumping.displayString = "Wall Jumping: " + StatCollector.translateToLocal("options.off");
            	WallJump.setBool("WallJumping", flag3);
            	
            	if(flag3) {
            		flyWallJump.enabled = true; barIcons.enabled = true; jumpType.enabled = true; autoTurn.enabled = true; displayType.enabled = true; shouldUseSpace.enabled = true;
            	} else {
            		flyWallJump.enabled = false; barIcons.enabled = false; jumpType.enabled = false; autoTurn.enabled = false; displayType.enabled = false; shouldUseSpace.enabled = false;
            	}
            	break;
            case 7:
            	Integer j = WallJump.getInt("DisplayType") + 1;
            	if(j == 10) j = 0;
            	WallJump.setInt("DisplayType", j);
            	displayType.displayString = "Display: " + displayTypes[j];
            	break;
            case 8:
            	Boolean flag4 = !WallJump.getBool("UseSpace");
            	shouldUseSpace.displayString = flag4 ? "Use jump key: " + StatCollector.translateToLocal("options.on") : "Use jump key: " + StatCollector.translateToLocal("options.off");
            	WallJump.setBool("UseSpace", flag4);
            	break;
            case 9:
            	Boolean flag5 = !WallJump.getBool("CircleMode");
            	circleMode.displayString = flag5 ? "Circle Mode: " + StatCollector.translateToLocal("options.on") : "Circle Mode: " + StatCollector.translateToLocal("options.off");
            	WallJump.setBool("CircleMode", flag5);
            	break;
        }
        WallJump.saveSettings();
    }
    
    /**
     * Holds the names of each wall jumping type, used in the GUI menu button.
     */
    private static String[] jumpTypes = new String[]
    {
    	"Fixed",
    	"Limited",
    	"Unlimited"
    };
    
    /**
     * Holds the names of each remaining jumps Display Type, used in the GUI menu button.
     */
    private static String[] displayTypes = new String[]
    {
    	"Bar Above Food",
    	"Bar Above Armor",
    	"Bar Top Left",
    	"Bar Top Right",
    	"Bar Bottom Left",
    	"Bar Bottom Right",
    	"Text Top Left",
    	"Text Top Right",
    	"Text Bottom Left",
    	"Text Bottom Right"
    };
    
    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
    	super.updateScreen();
    	++this.updateCounter;
    }
    
    public void onGuiClosed()
    {
            Keyboard.enableRepeatEvents(false);
    }
    
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }
    
    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(mc.fontRendererObj, "Wall Jump Settings", this.width / 2, 40, 16777215);
        super.drawScreen(par1, par2, par3);
        this.drawCenteredString(mc.fontRendererObj, "Names of blocks which cannot be wall jumped off of, seperated by a semicolon (;)", this.width / 2, this.height / 4 + 156, 16777215);
        this.drawString(mc.fontRendererObj, "< (More stable)", this.width / 2 + 10, this.height / 4 + 134, 0xffffffff);
        exceptions.drawTextBox();
        boolean isInputValid = true;
        try
        {	
        	String[] i = exceptions.getText().split(";");
        	for(int x = 0; x < i.length; x++)
        	{
    			String working = i[x];
    			int metadata = 0;
    			
    			if(working.contains("!")) {
    				String[] parts = working.split("!");
    				working = parts[0];
    				metadata = isInteger(parts[1]) ? Integer.parseInt(parts[1]) : 0;
    			}
    			
    			Block block = null;
    			if(i[x].contains(":") && !working.contains("minecraft:")) {
    				String[] names = working.split(":");
    				block = GameRegistry.findBlock(names[0], names[1]);
    			} else {
    				block = Block.getBlockFromName(working);
    			}
    			
    			if(block != null) {
    				mc.getRenderItem().renderItemIntoGUI(new ItemStack(block, 1, metadata), this.width / 2 -75 + ((x % 8) * 19), this.height / 4 + 209 - 16 + ((x / 8) * 20));
    			}
        	}
        } catch(Exception e) {}
    }
    
	protected void keyTyped(char c, int i) throws IOException {
		super.keyTyped(c, i);
		exceptions.textboxKeyTyped(c, i);
		WallJump.setString("Exceptions", exceptions.getText().length() == 0 ? "None" : exceptions.getText());
		WallJump.saveSettings();
	}
	
	protected void mouseClicked(int i, int j, int k) throws IOException {
		super.mouseClicked(i, j, k);
		exceptions.mouseClicked(i, j, k);
    }
}