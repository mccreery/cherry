package nukeduck.functionaldecor.util;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import static org.lwjgl.opengl.GL11.*;

public class RenderUtils {
	public static final void billBoard(double posX, double posY, double posZ, Entity camera, float partialTicks) {
		//glDisable(GL_DEPTH_TEST);

		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		double dx = posX - (camera.prevPosX + (camera.posX - camera.prevPosX) * partialTicks); 
		double dy = posY - (camera.prevPosY + (camera.posY - camera.prevPosY) * partialTicks); 
		double dz = posZ - (camera.prevPosZ + (camera.posZ - camera.prevPosZ) * partialTicks);

		glDisable(GL_LIGHTING);

		double scale = Math.max(1, Math.sqrt(dx * dx + dy * dy + dz * dz) / 5);

		glTranslated(dx, dy + 0.5F + camera.height, dz);
		GL11.glScaled(scale, scale, scale);
		glRotatef(-camera.rotationYaw, 0.0F, 1.0F, 0.0F);
		glRotatef(camera.rotationPitch, 1.0F, 0.0F, 0.0F);
		glRotatef(180, 0, 0, 1);
		glTranslatef(-0.5F, -0.5F, 0.0F);

		glEnable(GL_BLEND);
	}
}
