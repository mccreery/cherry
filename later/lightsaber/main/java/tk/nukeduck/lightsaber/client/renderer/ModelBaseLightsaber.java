package tk.nukeduck.lightsaber.client.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBaseLightsaber extends ModelBase {
	/** Two cuboids which make up the blade. */
	protected ModelRenderer blade1, blade2;
	/** Offset to move the blade by. */
	public int bladeOffset = 16;
	/** Offset to move the second blade by. */
	public int doubledOffset = 40;
	
	public ModelBaseLightsaber() {
		
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		render(entity, f, f1, f2, f3, f4, f5, false);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, boolean blade) {
		//setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		super.render(entity, f, f1, f2, f3, f4, f5);
	}
	
	protected void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}