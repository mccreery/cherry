package tk.nukeduck.hearts.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLantern extends ModelBase {
	//fields
	ModelRenderer body, lid, chainA, chainB;

	public ModelLantern() {
		this.textureWidth = 32;
		this.textureHeight = 32;

		body = new ModelRenderer(this, 0, 0);
		body.addBox(0F, 0F, 0F, 6, 8, 6);
		body.setRotationPoint(-3F, 16F, -3F);
		body.setTextureSize(32, 32);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		lid = new ModelRenderer(this, 0, 14);
		lid.addBox(0F, 0F, 0F, 4, 3, 4);
		lid.setRotationPoint(-2F, 14F, -2F);
		lid.setTextureSize(32, 32);
		lid.mirror = true;
		setRotation(lid, 0F, 0F, 0F);
		chainA = new ModelRenderer(this, 0, 18);
		chainA.addBox(0F, 0F, 0F, 0, 6, 3);
		chainA.setRotationPoint(0F, 8F, -1.5F);
		chainA.setTextureSize(32, 32);
		chainA.mirror = true;
		setRotation(chainA, 0F, 0F, 0F);
		chainB = new ModelRenderer(this, 6, 21);
		chainB.addBox(0F, 0F, 0F, 3, 6, 0);
		chainB.setRotationPoint(-1.5F, 8F, 0F);
		chainB.setTextureSize(32, 32);
		chainB.mirror = true;
		setRotation(chainB, 0F, 0F, 0F);
	}

	public void render(int metadata, float f5) {
		this.render(null, metadata, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f5);
	}
	public void render(Entity entity, int metadata, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		body.render(f5);
		lid.render(f5);
		if((int) (metadata / 4) == 1) {
			chainA.render(f5);
			chainB.render(f5);
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
