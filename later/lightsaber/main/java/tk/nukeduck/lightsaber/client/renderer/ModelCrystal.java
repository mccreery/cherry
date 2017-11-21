package tk.nukeduck.lightsaber.client.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCrystal extends ModelBase {
	ModelRenderer shape1;
	ModelRenderer shape2;
	ModelRenderer shape3;
	ModelRenderer shape4;
	ModelRenderer shape5;
	ModelRenderer shape6;
	ModelRenderer shape7;
	ModelRenderer shape8;
	ModelRenderer shape9;
	ModelRenderer shape10;
	ModelRenderer shape11;
	ModelRenderer shape12;
	ModelRenderer shape13;
	ModelRenderer shape14;
	
	public static ModelRenderer[] boxes;
	
	public ModelCrystal() {
		textureWidth = 16;
		textureHeight = 16;
		
		shape1 = new ModelRenderer(this, 0, 0);
		shape1.addBox(0F, 0F, 0F, 4, 5, 4);
		shape1.setRotationPoint(-1F, 20F, 5F);
		shape1.mirror = true;
		setRotation(shape1, -0.3717861F, 0.5948578F, 0F);
		shape2 = new ModelRenderer(this, 0, 0);
		shape2.addBox(0F, 0F, 0F, 3, 9, 3);
		shape2.setRotationPoint(-4F, 17F, 2F);
		shape2.mirror = true;
		setRotation(shape2, -0.0743572F, 0.2230717F, -0.2974289F);
		shape3 = new ModelRenderer(this, 0, 0);
		shape3.addBox(0F, 0F, 0F, 4, 9, 4);
		shape3.setRotationPoint(0F, 15F, -1F);
		shape3.mirror = true;
		setRotation(shape3, 0.0743572F, 0.1487144F, 0.0743572F);
		shape4 = new ModelRenderer(this, 0, 0);
		shape4.addBox(0F, 0F, 0F, 2, 4, 2);
		shape4.setRotationPoint(-3F, 20F, -2F);
		shape4.mirror = true;
		setRotation(shape4, 0.5948578F, 0.5948578F, 0.2230717F);
		shape5 = new ModelRenderer(this, 0, 0);
		shape5.addBox(0F, 0F, 0F, 5, 3, 5);
		shape5.setRotationPoint(-3F, 22F, -3F);
		shape5.mirror = true;
		setRotation(shape5, 0.2230717F, -0.5205006F, 0.2974289F);
		shape6 = new ModelRenderer(this, 0, 0);
		shape6.addBox(0F, 0F, 0F, 3, 7, 3);
		shape6.setRotationPoint(0F, 18F, -3F);
		shape6.mirror = true;
		setRotation(shape6, -0.2974289F, 0.4461433F, 0F);
		shape7 = new ModelRenderer(this, 0, 0);
		shape7.addBox(0F, 0F, 0F, 2, 4, 2);
		shape7.setRotationPoint(5F, 21F, -4F);
		shape7.mirror = true;
		setRotation(shape7, 0F, -0.2230717F, 0.5205006F);
		shape8 = new ModelRenderer(this, 0, 0);
		shape8.addBox(0F, 0F, 0F, 3, 4, 3);
		shape8.setRotationPoint(-5F, 21F, -5F);
		shape8.mirror = true;
		setRotation(shape8, 0.240525F, 0.2230717F, -0.2974289F);
		shape9 = new ModelRenderer(this, 0, 0);
		shape9.addBox(0F, 0F, 0F, 2, 4, 2);
		shape9.setRotationPoint(-3F, 20F, 5F);
		shape9.mirror = true;
		setRotation(shape9, -0.4461433F, 0.1487144F, -0.0743572F);
		shape10 = new ModelRenderer(this, 0, 0);
		shape10.addBox(0F, 0F, 0F, 2, 3, 2);
		shape10.setRotationPoint(-6F, 21F, 5F);
		shape10.mirror = true;
		setRotation(shape10, -0.2974289F, -0.2974289F, 0.2974289F);
		shape11 = new ModelRenderer(this, 0, 0);
		shape11.addBox(0F, 0F, 0F, 3, 4, 3);
		shape11.setRotationPoint(5F, 20F, 1F);
		shape11.mirror = true;
		setRotation(shape11, -0.4461433F, -0.2974289F, 0.5205006F);
		shape12 = new ModelRenderer(this, 0, 0);
		shape12.addBox(0F, 0F, 0F, 2, 2, 2);
		shape12.setRotationPoint(-2F, 22F, -6F);
		shape12.mirror = true;
		setRotation(shape12, 0.2974289F, 0.5205006F, 0.5948578F);
		shape13 = new ModelRenderer(this, 0, 0);
		shape13.addBox(0F, 0F, 0F, 2, 2, 2);
		shape13.setRotationPoint(5F, 22F, 4.666667F);
		shape13.mirror = true;
		setRotation(shape13, 0.2974289F, 0.5205006F, 0.5948578F);
		shape14 = new ModelRenderer(this, 0, 0);
		shape14.addBox(0F, 0F, 0F, 2, 5, 2);
		shape14.setRotationPoint(0F, 18F, 3F);
		shape14.mirror = true;
		setRotation(shape14, -0.5205006F, -0.2230717F, -0.2974289F);

		boxes = new ModelRenderer[] {
			shape1, shape2, shape3, shape4, shape5, shape6, shape7, shape8, shape9, shape10, shape11, shape12, shape13, shape14
		};

		for(ModelRenderer box : boxes) {
			box.setTextureSize(32, 32);
		}
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, byte density) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		for(int i = 0; i < density; i++) {
			boxes[i].render(f5);
		}
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}
