package nukeduck.coinage.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMerchantHat extends ModelBiped {
	ModelRenderer hat;

	public ModelMerchantHat() {
		super();
		textureWidth = 64;
		textureHeight = 64;
		
		hat = new ModelRenderer(this, 26, 51);
		hat.addBox(-4.5F, -8.5F, -6F, 9, 3, 10);
		hat.setRotationPoint(0F, 0F, 0F);
		hat.setTextureSize(64, 64);
		hat.mirror = true;
		setRotation(hat, -0.0872665F, 0F, 0F);
		bipedHead.addChild(hat);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		//super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		hat.render(f5 * 1.1f);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
		hat.rotateAngleX = bipedHead.rotateAngleX - 0.174533F;
		hat.rotateAngleY = bipedHead.rotateAngleY;
		hat.rotateAngleZ = bipedHead.rotateAngleZ;
	}
}
