package nukeduck.functionaldecor.client.renderers.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelKeyboard extends ModelBase {
	public final ModelRenderer base;

	public ModelKeyboard() {
		this.textureWidth = 64;
		this.textureHeight = 16;

		this.base = new ModelRenderer(this, 0, 0);
		this.base.addBox(1.0F, 15.0F, 5.0F, 14, 1, 5);
	}

	public void render(Entity entity, float a, float b, float c, float d, float e, float f) {
		this.base.render(f);
	}
}
