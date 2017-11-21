package tk.nukeduck.generation.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import tk.nukeduck.generation.entity.EntityMimic;

/**
 * Mimic - Undefined
 * Created using Tabula 5.1.0
 */
public class ModelMimic extends ModelBase {
	public ModelRenderer body;
	public ModelRenderer lid;
	public ModelRenderer latch;
	public ModelRenderer tongueMain;
	public ModelRenderer tongueNeck;
	public ModelRenderer tongueTip;
	public ModelRenderer LT;
	public ModelRenderer RT;
	public ModelRenderer RT2;
	public ModelRenderer RT3;
	public ModelRenderer LT3;
	public ModelRenderer LT2;
	public ModelRenderer LTa;
	public ModelRenderer RTa;

	public ModelMimic() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.lid = new ModelRenderer(this, 0, 0);
		this.lid.setRotationPoint(0.0F, 15.0F, 7.0F);
		this.lid.addBox(-7.0F, -5.0F, -14.0F, 14, 5, 14, 0.0F);
		this.setRotation(lid, -0.5235987755982988F, 0.0F, 0.0F);
		this.LT2 = new ModelRenderer(this, 30, 48);
		this.LT2.setRotationPoint(3.5F, 14.0F, -6.0F);
		this.LT2.addBox(-2.0F, -1.0F, 0.0F, 2, 1, 1, 0.0F);
		this.setRotation(LT2, 0.0F, -0.08726646259971647F, -0.17453292519943295F);
		this.tongueTip = new ModelRenderer(this, 0, 57);
		this.tongueTip.setRotationPoint(0.0F, 0.0F, -4.0F);
		this.tongueTip.addBox(-1.5F, 0.0F, -2.0F, 3, 1, 2, 0.0F);
		this.setRotation(tongueTip, 0.7285004297824331F, 0.0F, 0.0F);
		this.RT3 = new ModelRenderer(this, 30, 50);
		this.RT3.mirror = true;
		this.RT3.setRotationPoint(-5.3F, 14.5F, -2.5F);
		this.RT3.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotation(RT3, 0.3490658503988659F, -0.17453292519943295F, 0.17453292519943295F);
		this.LT3 = new ModelRenderer(this, 30, 50);
		this.LT3.setRotationPoint(5.3F, 14.5F, -2.5F);
		this.LT3.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
		this.setRotation(LT3, 0.3490658503988659F, 0.17453292519943295F, -0.17453292519943295F);
		this.tongueNeck = new ModelRenderer(this, 0, 52);
		this.tongueNeck.setRotationPoint(0.0F, -0.5F, -8.0F);
		this.tongueNeck.addBox(-2.5F, 0.0F, -4.0F, 5, 1, 4, 0.0F);
		this.setRotation(tongueNeck, 0.8726646259971648F, 0.0F, 0.0F);
		this.LTa = new ModelRenderer(this, 38, 43);
		this.LTa.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.LTa.addBox(-0.5F, -2.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotation(LTa, -0.4363323129985824F, 0.0F, -0.17453292519943295F);
		this.latch = new ModelRenderer(this, 0, 0);
		this.latch.setRotationPoint(-1.0F, -1.0F, -15.0F);
		this.latch.addBox(0.0F, -1.0F, 0.0F, 2, 4, 1, 0.0F);
		this.tongueMain = new ModelRenderer(this, 0, 43);
		this.tongueMain.setRotationPoint(0.0F, 15.0F, 1.5F);
		this.tongueMain.addBox(-3.5F, -0.5F, -8.0F, 7, 1, 8, 0.0F);
		this.setRotation(tongueMain, -0.3490658503988659F, 0.0F, 0.0F);
		this.RTa = new ModelRenderer(this, 38, 43);
		this.RTa.mirror = true;
		this.RTa.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.RTa.addBox(-0.5F, -2.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotation(RTa, -0.4363323129985824F, 0.0F, 0.17453292519943295F);
		this.body = new ModelRenderer(this, 0, 19);
		this.body.setRotationPoint(-7.0F, 14.0F, -7.0F);
		this.body.addBox(0.0F, 0.0F, 0.0F, 14, 10, 14, 0.0F);
		this.RT2 = new ModelRenderer(this, 30, 48);
		this.RT2.mirror = true;
		this.RT2.setRotationPoint(-3.5F, 14.0F, -6.0F);
		this.RT2.addBox(0.0F, -1.0F, 0.0F, 2, 1, 1, 0.0F);
		this.setRotation(RT2, 0.0F, 0.08726646259971647F, 0.17453292519943295F);
		this.RT = new ModelRenderer(this, 30, 43);
		this.RT.mirror = true;
		this.RT.setRotationPoint(-4.5F, 15.5F, -5.0F);
		this.RT.addBox(-1.0F, -4.0F, -1.0F, 2, 3, 2, 0.0F);
		this.setRotation(RT, 0.2617993877991494F, 0.0F, -0.17453292519943295F);
		this.LT = new ModelRenderer(this, 30, 43);
		this.LT.setRotationPoint(4.5F, 15.5F, -5.0F);
		this.LT.addBox(-1.0F, -4.0F, -1.0F, 2, 3, 2, 0.0F);
		this.setRotation(LT, 0.2617993877991494F, 0.0F, 0.17453292519943295F);
		this.tongueNeck.addChild(this.tongueTip);
		this.tongueMain.addChild(this.tongueNeck);
		this.LT.addChild(this.LTa);
		this.lid.addChild(this.latch);
		this.RT.addChild(this.RTa);
	}

	@Override
	public void setRotationAngles(float a, float b, float c, float d, float e, float f, Entity g) {
		float time = c / 3.5F;

		float lidAngle;
		if(time <= Math.PI) {
			lidAngle = (float) -Math.cos(time) + 1.0F;
		} else {
			lidAngle = (float) -Math.cos(time) * 0.2F + 1.8F;
		}
		this.lid.rotateAngleX = -lidAngle / 3.0F;

		float easeFactor = (float) Math.min(1.0F, time / Math.PI);
		float rotateFactor = MathHelper.sin(time) / 20.0F;
		this.tongueMain.rotateAngleX = rotateFactor - 0.4F * easeFactor;
		this.tongueNeck.rotateAngleX = rotateFactor * 2F + 0.5F * easeFactor;
		this.tongueTip.rotateAngleX = rotateFactor * 3F + 0.8F * easeFactor;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		this.body.render(f5);
		this.lid.render(f5);

		this.LT2.render(f5);
		this.RT3.render(f5);
		this.LT3.render(f5);
		this.RT2.render(f5);
		this.RT.render(f5);
		this.LT.render(f5);

		this.tongueMain.render(f5);
	}

	public void setRotation(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
