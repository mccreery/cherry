package tk.nukeduck.generation.util;

import java.io.IOException;
import java.io.InputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.IOUtils;

public class IOHelper {
	public static final String getResource(ResourceLocation resource) {
		try {
			InputStream stream = Minecraft.getMinecraft().getResourceManager().getResource(resource).getInputStream();
			return IOUtils.toString(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
