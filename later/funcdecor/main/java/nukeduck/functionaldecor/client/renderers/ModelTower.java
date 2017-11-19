package nukeduck.functionaldecor.client.renderers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTower extends ModelBase {
	public final ModelRenderer base;

	public ModelTower() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.base = new ModelRenderer(this, 0, 0);
		this.base.addBox(5.0F, 4.0F, 1.0F, 6, 12, 14);
	}

	public void render(Entity entity, float a, float b, float c, float d, float e, float f) {
		this.base.render(f);
	}
}
