package tk.nukeduck.walljump;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

import org.lwjgl.input.Keyboard;

public class KeyHandlerMenu {
	public static KeyBinding settingsMenu = new KeyBinding("key.walljump_menu.desc", Keyboard.KEY_J, "key.walljump.category");
	public static KeyBinding wallJump = new KeyBinding("key.walljump.desc", Keyboard.KEY_F, "key.walljump.category");
	
	public static KeyBinding[] arrayOfKeys = new KeyBinding[] {settingsMenu, wallJump};
	public static boolean[] areRepeating = new boolean[] {false, true};
	
	public KeyHandlerMenu() {
		ClientRegistry.registerKeyBinding(settingsMenu);
		ClientRegistry.registerKeyBinding(wallJump);
	}
	
	Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if(mc.currentScreen == null && settingsMenu.isPressed()) {
			mc.displayGuiScreen(new GuiWallJumpMenu());
		}
	}
}