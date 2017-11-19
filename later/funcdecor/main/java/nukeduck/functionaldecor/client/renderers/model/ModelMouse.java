package nukeduck.functionaldecor.client.renderers.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMouse extends ModelBase {
	private final ModelRenderer base, wheel;

	public ModelMouse() {
		this.textureWidth = 16;
		this.textureHeight = 16;

		this.base = new ModelRenderer(this, 0, 0);
		this.base.addBox(6.5F, 14.0F, 5.0F, 3, 2, 5);
		this.wheel = new ModelRenderer(this, 0, 7);
		this.wheel.addBox(7.5F, 13.5F, 7.5F, 1, 1, 2);
	}

	public void render(Entity entity, float a, float b, float c, float d, float e, float f) {
		this.base.render(f);
		this.wheel.render(f);
	}
}
