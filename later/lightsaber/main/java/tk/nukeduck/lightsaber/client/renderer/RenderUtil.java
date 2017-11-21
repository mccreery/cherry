package tk.nukeduck.lightsaber.client.renderer;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RenderUtil {
	private static Field current, previous;
	private static boolean success = false;
	
	/** Sets up reflection. */
	public static void init() {
		try {
			current = ItemRenderer.class.getDeclaredField("equippedProgress");
			current.setAccessible(true);
			
			previous = ItemRenderer.class.getDeclaredField("prevEquippedProgress");
			previous.setAccessible(true);
			
			success = true;
		} catch (Exception e) {}
	}
	
	/** Attempts to prevent the switching animation when a lightsaber loses charge. */
	@SideOnly(Side.CLIENT)
	public static boolean preventSwitching(Minecraft mc) {
		return preventSwitching(mc, 1);
	}
	
	/** Attempts to prevent the switching animation when a lightsaber loses charge for the specified amount of time. */
	@SideOnly(Side.CLIENT)
	public static boolean preventSwitching(Minecraft mc, float value) {
		ItemRenderer ir = mc.entityRenderer.itemRenderer;
		if(ir != null && success) {
			try{
				current.setFloat(ir, value);
				previous.setFloat(ir, value);
				return true;
			} catch (Exception e) {}
		} else {
			init();
		}
		return false;
	}
}