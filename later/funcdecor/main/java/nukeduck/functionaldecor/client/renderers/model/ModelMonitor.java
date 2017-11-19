package nukeduck.functionaldecor.client.renderers.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMonitor extends ModelBase {
	private final ModelRenderer base, stand, screen;

	public ModelMonitor() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.base = new ModelRenderer(this, 6, 12);
		this.base.addBox(5.0F, 15.0F, 6.0F, 6, 1, 4);

		this.stand = new ModelRenderer(this, 0, 12);
		this.stand.addBox(7.0F, 9.0F, 9.0F, 2, 6, 1);

		this.screen = new ModelRenderer(this, 0, 0);
		this.screen.addBox(0.0F, 3.0F, 7.0F, 16, 10, 2);
	}

	public void render(Entity entity, float a, float b, float c, float d, float e, float f) {
		this.base.render(f);
		this.stand.render(f);
		this.screen.render(f);
	}
}
