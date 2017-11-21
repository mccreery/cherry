package tk.nukeduck.lightsaber.input;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import tk.nukeduck.lightsaber.util.Constants;

public class KeyBindings {
	// NO LONGER USED: Tome of the Force handles skills menu.
	///** Key binding to open the skills menu. */
	//public static final KeyBinding openSkills = new KeyBinding("key.openSkills", Keyboard.KEY_Y, "key.categories.lightsaber");
	
	/** An array of {@code KeyBinding} instances used to trigger all of the selected skills for the current user. */
	public static final KeyBinding[] useSkills = new KeyBinding[Constants.MAX_SELECTED]; // TODO This should be changed, since keys are hard to use to select skills.
	
	/** Initialise key bindings. */
	public static void init() {
		//ClientRegistry.registerKeyBinding(openSkills);
		
		int[] keys = {Keyboard.KEY_G, Keyboard.KEY_H, Keyboard.KEY_J, Keyboard.KEY_K, Keyboard.KEY_L, Keyboard.KEY_C, Keyboard.KEY_V, Keyboard.KEY_B, Keyboard.KEY_N, Keyboard.KEY_M};
		for(int i = 0; i < Constants.MAX_SELECTED; i++) {
			useSkills[i] = new KeyBinding("key.skill" + (i + 1), keys[i], "key.categories.lightsaber");
			ClientRegistry.registerKeyBinding(useSkills[i]);
		}
	}
}