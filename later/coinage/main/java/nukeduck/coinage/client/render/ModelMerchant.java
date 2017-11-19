package nukeduck.coinage.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMerchant extends ModelBiped {
  //fields
    ModelRenderer backpack;
    ModelRenderer backpack2;
    ModelRenderer backpack3;
    ModelRenderer nose;
    ModelRenderer strap1;
    ModelRenderer strap2;
    ModelRenderer coat;
    ModelRenderer strap3;
    ModelRenderer hat;
    ModelRenderer leftforearm;
    ModelRenderer leftboot;
    ModelRenderer rightboot;
  
  public ModelMerchant()
  {
    textureWidth = 64;
    textureHeight = 64;
    
    bipedHeadwear = new ModelRenderer(this, 0, 0);
    
      backpack = new ModelRenderer(this, 48, 0);
      backpack.addBox(-2.5F, 1F, 2F, 5, 3, 3);
      backpack.setRotationPoint(0F, 0F, 0F);
      backpack.setTextureSize(64, 64);
      backpack.mirror = true;
      setRotation(backpack, 0F, 0F, 0F);
      backpack2 = new ModelRenderer(this, 40, 0);
      backpack2.addBox(-4F, 5.5F, 2.5F, 1, 3, 3);
      backpack2.setRotationPoint(0F, 0F, 0F);
      backpack2.setTextureSize(64, 64);
      backpack2.mirror = true;
      setRotation(backpack2, 0F, 0F, 0F);
      backpack3 = new ModelRenderer(this, 44, 6);
      backpack3.addBox(-3F, 4F, 2F, 6, 5, 4);
      backpack3.setRotationPoint(0F, 0F, 0F);
      backpack3.setTextureSize(64, 64);
      backpack3.mirror = true;
      setRotation(backpack3, 0F, 0F, 0F);
      bipedHead = new ModelRenderer(this, 0, 0);
      bipedHead.addBox(-4F, -9F, -5F, 8, 10, 8);
      bipedHead.setRotationPoint(0F, 0F, 0F);
      bipedHead.setTextureSize(64, 64);
      bipedHead.mirror = true;
      setRotation(bipedHead, 0.0872665F, 0F, 0F);
      bipedBody = new ModelRenderer(this, 16, 18);
      bipedBody.addBox(-4F, 0F, -2F, 8, 12, 4);
      bipedBody.setRotationPoint(0F, 0F, 0F);
      bipedBody.setTextureSize(64, 64);
      bipedBody.mirror = true;
      setRotation(bipedBody, 0F, 0F, 0F);
      bipedRightArm = new ModelRenderer(this, 40, 18);
      bipedRightArm.addBox(-3F, -2F, -2F, 4, 12, 4);
      bipedRightArm.setRotationPoint(-5F, 2F, 0F);
      bipedRightArm.setTextureSize(64, 64);
      bipedRightArm.mirror = true;
      setRotation(bipedRightArm, 0F, 0F, 0F);
      bipedRightLeg = new ModelRenderer(this, 0, 18);
      bipedRightLeg.addBox(-2F, 0F, -2F, 4, 10, 4);
      bipedRightLeg.setRotationPoint(-2F, 12F, 0F);
      bipedRightLeg.setTextureSize(64, 64);
      bipedRightLeg.mirror = true;
      setRotation(bipedRightLeg, 0F, 0F, 0F);
      bipedLeftLeg = new ModelRenderer(this, 0, 18);
      bipedLeftLeg.addBox(-2F, 0F, -2F, 4, 10, 4);
      bipedLeftLeg.setRotationPoint(2F, 12F, 0F);
      bipedLeftLeg.setTextureSize(64, 64);
      bipedLeftLeg.mirror = true;
      setRotation(bipedLeftLeg, 0F, 0F, 0F);
      nose = new ModelRenderer(this, 32, 0);
      nose.addBox(-1F, -2F, -7F, 2, 4, 2);
      nose.setRotationPoint(0F, 0F, 0F);
      nose.setTextureSize(64, 64);
      nose.mirror = true;
      setRotation(nose, 0.0872665F, 0F, 0F);
      strap1 = new ModelRenderer(this, 0, 34);
      strap1.addBox(-1F, 0F, -3F, 1, 5, 1);
      strap1.setRotationPoint(-2F, 12F, 0F);
      strap1.setTextureSize(64, 64);
      strap1.mirror = true;
      setRotation(strap1, 0F, 0F, 0.5235988F);
      bipedLeftArm = new ModelRenderer(this, 40, 18);
      bipedLeftArm.addBox(-1F, -2F, -2F, 4, 7, 4);
      bipedLeftArm.setRotationPoint(5F, 2F, 0F);
      bipedLeftArm.setTextureSize(64, 64);
      bipedLeftArm.mirror = true;
      setRotation(bipedLeftArm, -0.5235988F, 0F, 0F);
      strap2 = new ModelRenderer(this, 0, 34);
      strap2.addBox(-1F, 0F, 2F, 1, 5, 1);
      strap2.setRotationPoint(-2F, 12F, 0F);
      strap2.setTextureSize(64, 64);
      strap2.mirror = true;
      setRotation(strap2, 0F, 0F, 0.5235988F);
      coat = new ModelRenderer(this, 0, 41);
      coat.addBox(-4.5F, 0F, -2.5F, 9, 13, 5);
      coat.setRotationPoint(0F, 0F, 0F);
      coat.setTextureSize(64, 64);
      coat.mirror = true;
      setRotation(coat, 0F, 0F, 0F);
      strap3 = new ModelRenderer(this, 4, 34);
      strap3.addBox(-1F, 4F, -2F, 1, 1, 4);
      strap3.setRotationPoint(-2F, 12F, 0F);
      strap3.setTextureSize(64, 64);
      strap3.mirror = true;
      setRotation(strap3, 0F, 0F, 0.5235988F);
      hat = new ModelRenderer(this, 26, 51);
      hat.addBox(-4.5F, -10F, -8F, 9, 3, 10);
      hat.setRotationPoint(0F, 0F, 0F);
      hat.setTextureSize(64, 64);
      hat.mirror = true;
      setRotation(hat, -0.0872665F, 0F, 0F);
      leftforearm = new ModelRenderer(this, 40, 34);
      leftforearm.addBox(-2.2F, 3.3F, -2.25F, 7, 4, 4);
      leftforearm.setRotationPoint(5F, 2F, 0F);
      leftforearm.setTextureSize(64, 64);
      leftforearm.mirror = true;
      setRotation(leftforearm, -0.5235988F, 0.2094395F, 0.3490659F);
      leftboot = new ModelRenderer(this, 14, 34);
      leftboot.addBox(-2F, 10F, -3F, 4, 2, 5);
      leftboot.setRotationPoint(2F, 12F, 0F);
      leftboot.setTextureSize(64, 64);
      leftboot.mirror = true;
      setRotation(leftboot, 0F, 0F, 0F);
      rightboot = new ModelRenderer(this, 14, 34);
      rightboot.addBox(-2F, 10F, -3F, 4, 2, 5);
      rightboot.setRotationPoint(-2F, 12F, 0F);
      rightboot.setTextureSize(64, 64);
      rightboot.mirror = true;
      setRotation(rightboot, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    backpack.render(f5);
    backpack2.render(f5);
    backpack3.render(f5);
    nose.render(f5);
    strap1.render(f5);
    strap2.render(f5);
    coat.render(f5);
    strap3.render(f5);
    hat.render(f5);
    leftforearm.render(f5);
    leftboot.render(f5);
    rightboot.render(f5);
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
	  nose.rotateAngleX = bipedHead.rotateAngleX;
	  nose.rotateAngleY = bipedHead.rotateAngleY;
	  nose.rotateAngleZ = bipedHead.rotateAngleZ;
	  hat.rotateAngleX = bipedHead.rotateAngleX - 0.0872665F;
	  hat.rotateAngleY = bipedHead.rotateAngleY;
	  hat.rotateAngleZ = bipedHead.rotateAngleZ;
	  bipedHead.rotateAngleX += 0.0872665F;
	  setRotation(leftboot, bipedLeftLeg.rotateAngleX, bipedLeftLeg.rotateAngleY, bipedLeftLeg.rotateAngleZ);
	  setRotation(rightboot, bipedRightLeg.rotateAngleX, bipedRightLeg.rotateAngleY, bipedRightLeg.rotateAngleZ);
	  setRotation(bipedLeftArm, -0.5235988F, 0F, 0F);
	  setRotation(bipedRightArm, bipedRightArm.rotateAngleX - 0.8f, bipedRightArm.rotateAngleY, bipedRightArm.rotateAngleZ);
	  
	  for(ModelRenderer renderer : new ModelRenderer[] {strap1, strap2, strap3}) {
		  renderer.rotateAngleZ = 0.5235988F;
		  renderer.rotateAngleX = bipedRightLeg.rotateAngleX * 2f;
	  }
  }
}
