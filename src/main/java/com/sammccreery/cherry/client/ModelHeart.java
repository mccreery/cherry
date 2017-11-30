package com.sammccreery.cherry.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHeart extends ModelBase {
	ModelRenderer shape1, shape2, shape3, shape4,
		shape5, shape6, shape7, shape8,
		shape9, shape10, shape11, shape12,
		shape13, shape14, shape15, shape16,
		shape17, shape18, shape19, shape20;

	public ModelHeart() {
		shape1 = new ModelRenderer(this, 0, 0);
		shape1.addBox(0F, 0F, 0F, 4, 1, 2);
		shape1.setRotationPoint(-2F, 23F, -1F);
		shape1.setTextureSize(64, 32);
		shape1.mirror = true;
		this.setRotation(shape1, 0F, 0F, 0F);

		shape2 = new ModelRenderer(this, 0, 0);
		shape2.addBox(0F, 0F, 0F, 8, 8, 6);
		shape2.setRotationPoint(-4F, 12F, -3F);
		shape2.setTextureSize(64, 32);
		shape2.mirror = true;
		this.setRotation(shape2, 0F, 0F, 0F);

		shape3 = new ModelRenderer(this, 0, 0);
		shape3.addBox(0F, 0F, 0F, 12, 6, 4);
		shape3.setRotationPoint(-6F, 13F, -2F);
		shape3.setTextureSize(64, 32);
		shape3.mirror = true;
		this.setRotation(shape3, 0F, 0F, 0F);

		shape4 = new ModelRenderer(this, 0, 0);
		shape4.addBox(0F, 0F, 0F, 10, 5, 6);
		shape4.setRotationPoint(-5F, 13F, -3F);
		shape4.setTextureSize(64, 32);
		shape4.mirror = true;
		this.setRotation(shape4, 0F, 0F, 0F);

		shape5 = new ModelRenderer(this, 0, 0);
		shape5.addBox(0F, 0F, 0F, 3, 3, 4);
		shape5.setRotationPoint(2F, 10F, -2F);
		shape5.setTextureSize(64, 32);
		shape5.mirror = true;
		this.setRotation(shape5, 0F, 0F, 0F);

		shape6 = new ModelRenderer(this, 0, 0);
		shape6.addBox(0F, 0F, 0F, 3, 3, 4);
		shape6.setRotationPoint(-5F, 10F, -2F);
		shape6.setTextureSize(64, 32);
		shape6.mirror = true;
		this.setRotation(shape6, 0F, 0F, 0F);

		shape7 = new ModelRenderer(this, 0, 0);
		shape7.addBox(0F, 0F, 0F, 6, 4, 8);
		shape7.setRotationPoint(-3F, 14F, -4F);
		shape7.setTextureSize(64, 32);
		shape7.mirror = true;
		this.setRotation(shape7, 0F, 0F, 0F);

		shape8 = new ModelRenderer(this, 0, 0);
		shape8.addBox(0F, 0F, 0F, 4, 6, 8);
		shape8.setRotationPoint(-2F, 13F, -4F);
		shape8.setTextureSize(64, 32);
		shape8.mirror = true;
		this.setRotation(shape8, 0F, 0F, 0F);

		shape9 = new ModelRenderer(this, 0, 0);
		shape9.addBox(0F, 0F, 0F, 10, 2, 2);
		shape9.setRotationPoint(-5F, 19F, -1F);
		shape9.setTextureSize(64, 32);
		shape9.mirror = true;
		this.setRotation(shape9, 0F, 0F, 0F);

		shape10 = new ModelRenderer(this, 0, 0);
		shape10.addBox(0F, 0F, 0F, 12, 2, 2);
		shape10.setRotationPoint(-6F, 11F, -1F);
		shape10.setTextureSize(64, 32);
		shape10.mirror = true;
		this.setRotation(shape10, 0F, 0F, 0F);

		shape11 = new ModelRenderer(this, 0, 0);
		shape11.addBox(0F, 0F, 0F, 2, 1, 2);
		shape11.setRotationPoint(2F, 9F, -1F);
		shape11.setTextureSize(64, 32);
		shape11.mirror = true;
		this.setRotation(shape11, 0F, 0F, 0F);

		shape12 = new ModelRenderer(this, 0, 0);
		shape12.addBox(0F, 0F, 0F, 2, 1, 2);
		shape12.setRotationPoint(-4F, 9F, -1F);
		shape12.setTextureSize(64, 32);
		shape12.mirror = true;
		this.setRotation(shape12, 0F, 0F, 0F);

		shape13 = new ModelRenderer(this, 0, 0);
		shape13.addBox(0F, 0F, 0F, 1, 2, 2);
		shape13.setRotationPoint(1F, 10F, -1F);
		shape13.setTextureSize(64, 32);
		shape13.mirror = true;
		this.setRotation(shape13, 0F, 0F, 0F);

		shape14 = new ModelRenderer(this, 0, 0);
		shape14.addBox(0F, 0F, 0F, 1, 2, 2);
		shape14.setRotationPoint(-2F, 10F, -1F);
		shape14.setTextureSize(64, 32);
		shape14.mirror = true;
		this.setRotation(shape14, 0F, 0F, 0F);

		shape15 = new ModelRenderer(this, 0, 0);
		shape15.addBox(0F, 0F, 0F, 1, 1, 4);
		shape15.setRotationPoint(1F, 11F, -2F);
		shape15.setTextureSize(64, 32);
		shape15.mirror = true;
		this.setRotation(shape15, 0F, 0F, 0F);

		shape16 = new ModelRenderer(this, 0, 0);
		shape16.addBox(0F, 0F, 0F, 1, 1, 4);
		shape16.setRotationPoint(-2F, 11F, -2F);
		shape16.setTextureSize(64, 32);
		shape16.mirror = true;
		this.setRotation(shape16, 0F, 0F, 0F);

		shape17 = new ModelRenderer(this, 0, 0);
		shape17.addBox(0F, 0F, 0F, 6, 4, 4);
		shape17.setRotationPoint(-3F, 19F, -2F);
		shape17.setTextureSize(64, 32);
		shape17.mirror = true;
		this.setRotation(shape17, 0F, 0F, 0F);

		shape18 = new ModelRenderer(this, 0, 0);
		shape18.addBox(0F, 0F, 0F, 4, 2, 6);
		shape18.setRotationPoint(-2F, 20F, -3F);
		shape18.setTextureSize(64, 32);
		shape18.mirror = true;
		this.setRotation(shape18, 0F, 0F, 0F);

		shape19 = new ModelRenderer(this, 0, 0);
		shape19.addBox(0F, 0F, 0F, 2, 1, 6);
		shape19.setRotationPoint(-4F, 11F, -3F);
		shape19.setTextureSize(64, 32);
		shape19.mirror = true;
		this.setRotation(shape19, 0F, 0F, 0F);

		shape20 = new ModelRenderer(this, 0, 0);
		shape20.addBox(0F, 0F, 0F, 2, 1, 6);
		shape20.setRotationPoint(2F, 11F, -3F);
		shape20.setTextureSize(64, 32);
		shape20.mirror = true;
		this.setRotation(shape20, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		//super.render(entity, f, f1, f2, f3, f4, f5);
		//this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		shape1.render(f5);
		shape2.render(f5);
		shape3.render(f5);
		shape4.render(f5);
		shape5.render(f5);
		shape6.render(f5);
		shape7.render(f5);
		shape8.render(f5);
		shape9.render(f5);
		shape10.render(f5);
		shape11.render(f5);
		shape12.render(f5);
		shape13.render(f5);
		shape14.render(f5);
		shape15.render(f5);
		shape16.render(f5);
		shape17.render(f5);
		shape18.render(f5);
		shape19.render(f5);
		shape20.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
