package tk.nukeduck.alchemy;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

public class ModelAlchemyPot extends ModelBase {
	/** Base */
		ModelRenderer cauldronBase, base;
	/** Sides */
		ModelRenderer frontSide, backSide, leftSide, rightSide;
	/** Embossed Sides */
		ModelRenderer sideEmbossFrontTop, sideEmbossFrontMiddle, sideEmbossFrontBottom, sideEmbossLeft, sideEmbossRight, sideEmbossBack;
	/** Rims */
		ModelRenderer rimFront, rimBack, rimLeft, rimRight;
	/** Pipes */
		ModelRenderer pipeLeft, pipeRight;
	/** Handle */
		ModelRenderer handleTop, handleLeft, handleRight, hingeLeft, hingeRight;
	/** Feet */
		ModelRenderer footFrontLeft, footFrontRight, footBackLeft, footBackRight;
		
	/** Decorations */
		ModelRenderer buckleLeft, buckleRight;
		ModelRenderer skullRing, skullTall, skullWide;
		ModelRenderer ringFront, ringBack, ringLeft, ringRight;
	
	public ModelAlchemyPot() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		
		cauldronBase = new ModelRenderer(this, 0, 15);
		cauldronBase.addBox(0F, 0F, 0F, 12, 3, 12);
		cauldronBase.setRotationPoint(-6F, 19F, -6F);
		cauldronBase.setTextureSize(128, 64);
		cauldronBase.mirror = true;
		setRotation(cauldronBase, 0F, 0F, 0F);
		
		base = new ModelRenderer(this, 0, 0);
		base.addBox(0F, 0F, 0F, 10, 1, 10);
		base.setRotationPoint(-5F, 22.5F, -5F);
		base.setTextureSize(128, 64);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		
		sideEmbossFrontTop = new ModelRenderer(this, 56, 0);
		sideEmbossFrontTop.addBox(0F, 0F, 0F, 5, 1, 1);
		sideEmbossFrontTop.setRotationPoint(-2.5F, 13F, -6.5F);
		sideEmbossFrontTop.setTextureSize(128, 64);
		sideEmbossFrontTop.mirror = true;
		setRotation(sideEmbossFrontTop, 0F, 0F, 0F);
		
		sideEmbossFrontMiddle = new ModelRenderer(this, 56, 2);
		sideEmbossFrontMiddle.addBox(0F, 0F, 0F, 6, 2, 1);
		sideEmbossFrontMiddle.setRotationPoint(-3F, 14F, -6.5F);
		sideEmbossFrontMiddle.setTextureSize(128, 64);
		sideEmbossFrontMiddle.mirror = true;
		setRotation(sideEmbossFrontMiddle, 0F, 0F, 0F);
		
		sideEmbossFrontBottom = new ModelRenderer(this, 30, 0);
		sideEmbossFrontBottom.addBox(0F, 0F, 0F, 11, 5, 2);
		sideEmbossFrontBottom.setRotationPoint(-5.5F, 16F, -6.5F);
		sideEmbossFrontBottom.setTextureSize(128, 64);
		sideEmbossFrontBottom.mirror = true;
		setRotation(sideEmbossFrontBottom, 0F, 0F, 0F);
		
		sideEmbossLeft = new ModelRenderer(this, 36, 7);
		sideEmbossLeft.addBox(0F, 0F, 0F, 2, 6, 11);
		sideEmbossLeft.setRotationPoint(-6.5F, 15F, -5.5F);
		sideEmbossLeft.setTextureSize(128, 64);
		sideEmbossLeft.mirror = true;
		setRotation(sideEmbossLeft, 0F, 0F, 0F);
		
		sideEmbossRight = new ModelRenderer(this, 36, 7);
		sideEmbossRight.addBox(0F, 0F, 0F, 2, 6, 11);
		sideEmbossRight.setRotationPoint(4.5F, 15F, -5.5F);
		sideEmbossRight.setTextureSize(128, 64);
		sideEmbossRight.mirror = true;
		setRotation(sideEmbossRight, 0F, 0F, 0F);
		
		sideEmbossBack = new ModelRenderer(this, 30, 0);
		sideEmbossBack.addBox(0F, 0F, 0F, 11, 5, 2);
		sideEmbossBack.setRotationPoint(-5.5F, 16F, 4.5F);
		sideEmbossBack.setTextureSize(128, 64);
		sideEmbossBack.mirror = true;
		setRotation(sideEmbossBack, 0F, 0F, 0F);
		
		frontSide = new ModelRenderer(this, 0, 32);
		frontSide.addBox(0F, 0F, 0F, 10, 9, 1);
		frontSide.setRotationPoint(-5F, 12F, -6F);
		frontSide.setTextureSize(128, 64);
		frontSide.mirror = true;
		setRotation(frontSide, 0F, 0F, 0F);
		
		backSide = new ModelRenderer(this, 0, 32);
		backSide.addBox(0F, 0F, 0F, 10, 9, 1);
		backSide.setRotationPoint(-5F, 12F, 5F);
		backSide.setTextureSize(128, 64);
		backSide.mirror = true;
		setRotation(backSide, 0F, 0F, 0F);
		
		leftSide = new ModelRenderer(this, 12, 32);
		leftSide.addBox(0F, 0F, 0F, 1, 9, 10);
		leftSide.setRotationPoint(-6F, 12F, -5F);
		leftSide.setTextureSize(128, 64);
		leftSide.mirror = true;
		setRotation(leftSide, 0F, 0F, 0F);
		
		rightSide = new ModelRenderer(this, 12, 32);
		rightSide.addBox(0F, 0F, 0F, 1, 9, 10);
		rightSide.setRotationPoint(5F, 12F, -5F);
		rightSide.setTextureSize(128, 64);
		rightSide.mirror = true;
		setRotation(rightSide, 0F, 0F, 0F);
		
		rimFront = new ModelRenderer(this, 20, 11);
		rimFront.addBox(0F, 0F, 0F, 10, 1, 1);
		rimFront.setRotationPoint(-5F, 11F, -7F);
		rimFront.setTextureSize(128, 64);
		rimFront.mirror = true;
		setRotation(rimFront, 0F, 0F, 0F);
		
		rimBack = new ModelRenderer(this, 20, 11);
		rimBack.addBox(0F, 0F, 0F, 10, 1, 1);
		rimBack.setRotationPoint(-5F, 11F, 6F);
		rimBack.setTextureSize(128, 64);
		rimBack.mirror = true;
		setRotation(rimBack, 0F, 0F, 0F);
		
		rimLeft = new ModelRenderer(this, 34, 32);
		rimLeft.addBox(0F, 0F, 0F, 1, 1, 10);
		rimLeft.setRotationPoint(-7F, 11F, -5F);
		rimLeft.setTextureSize(128, 64);
		rimLeft.mirror = true;
		setRotation(rimLeft, 0F, 0F, 0F);
		
		rimRight = new ModelRenderer(this, 34, 32);
		rimRight.addBox(0F, 0F, 0F, 1, 1, 10);
		rimRight.setRotationPoint(6F, 11F, -5F);
		rimRight.setTextureSize(128, 64);
		rimRight.mirror = true;
		setRotation(rimRight, 0F, 0F, 0F);
		
		pipeLeft = new ModelRenderer(this, 26, 43);
		pipeLeft.addBox(0F, 0.01F, 0F, 2, 8, 8);
		pipeLeft.setRotationPoint(-8F, 13F, -4F);
		pipeLeft.setTextureSize(128, 64);
		pipeLeft.mirror = true;
		setRotation(pipeLeft, 0F, 0F, 0F);
		
		pipeRight = new ModelRenderer(this, 26, 43);
		pipeRight.addBox(0F, 0.01F, 0F, 2, 8, 8);
		pipeRight.setRotationPoint(6F, 13F, -4F);
		pipeRight.setTextureSize(128, 64);
		pipeRight.mirror = true;
		setRotation(pipeRight, 0F, 0F, 0F);
		
		handleTop = new ModelRenderer(this, 0, 13);
		handleTop.addBox(-11.5F, -7.5F, -1.1F, 12, 1, 1);
		handleTop.setRotationPoint(5.5F, 12F, 0.5F);
		handleTop.setTextureSize(128, 64);
		handleTop.mirror = true;
		setRotation(handleTop, -1.396263F, 0F, 0F);
		
		handleLeft = new ModelRenderer(this, 2, 0);
		handleLeft.addBox(-12F, -7F, -1F, 1, 7, 1);
		handleLeft.setRotationPoint(5.5F, 12F, 0.5F);
		handleLeft.setTextureSize(128, 64);
		handleLeft.mirror = true;
		setRotation(handleLeft, -1.396263F, 0F, 0F);
		
		handleRight = new ModelRenderer(this, 2, 0);
		handleRight.addBox(0F, -7F, -1F, 1, 7, 1);
		handleRight.setRotationPoint(5.5F, 12F, 0.5F);
		handleRight.setTextureSize(128, 64);
		handleRight.mirror = true;
		setRotation(handleRight, -1.396263F, 0F, 0F);
		
		hingeLeft = new ModelRenderer(this, 0, 0);
		hingeLeft.addBox(0F, 0F, 0F, 2, 1, 2);
		hingeLeft.setRotationPoint(-7.1F, 11.5F, -1F);
		hingeLeft.setTextureSize(128, 64);
		hingeLeft.mirror = true;
		setRotation(hingeLeft, 0F, 0F, 0F);
		
		hingeRight = new ModelRenderer(this, 0, 0);
		hingeRight.addBox(0F, 0F, 0F, 2, 1, 2);
		hingeRight.setRotationPoint(5.1F, 11.5F, -1F);
		hingeRight.setTextureSize(128, 64);
		hingeRight.mirror = true;
		setRotation(hingeRight, 0F, 0F, 0F);
		
		footFrontLeft = new ModelRenderer(this, 48, 24);
		footFrontLeft.addBox(0F, 0F, 0F, 3, 2, 3);
		footFrontLeft.setRotationPoint(-7F, 22F, -6.5F);
		footFrontLeft.setTextureSize(128, 64);
		footFrontLeft.mirror = true;
		setRotation(footFrontLeft, 0F, 0F, 0F);
		
		footFrontRight = new ModelRenderer(this, 48, 24);
		footFrontRight.addBox(0F, 0F, 0F, 3, 2, 3);
		footFrontRight.setRotationPoint(4F, 22F, -6.5F);
		footFrontRight.setTextureSize(128, 64);
		footFrontRight.mirror = true;
		setRotation(footFrontRight, 0F, 0F, 0F);
		
		footBackLeft = new ModelRenderer(this, 48, 24);
		footBackLeft.addBox(0F, 0F, 0F, 3, 2, 3);
		footBackLeft.setRotationPoint(-7F, 22F, 3.5F);
		footBackLeft.setTextureSize(128, 64);
		footBackLeft.mirror = true;
		setRotation(footBackLeft, 0F, 0F, 0F);
		
		footBackRight = new ModelRenderer(this, 48, 24);
		footBackRight.addBox(0F, 0F, 0F, 3, 2, 3);
		footBackRight.setRotationPoint(4F, 22F, 3.5F);
		footBackRight.setTextureSize(128, 64);
		footBackRight.mirror = true;
		setRotation(footBackRight, 0F, 0F, 0F);
		
		buckleLeft = new ModelRenderer(this, 0, 42);
		buckleLeft.addBox(0F, 0F, 0F, 1, 2, 1);
		buckleLeft.setRotationPoint(-4.5F, 15F, -6.5F);
		buckleLeft.setTextureSize(128, 64);
		buckleLeft.mirror = true;
		setRotation(buckleLeft, -0.2617994F, 0F, 0F);
		
		buckleRight = new ModelRenderer(this, 0, 42);
		buckleRight.addBox(0F, 0F, 0F, 1, 2, 1);
		buckleRight.setRotationPoint(3.5F, 15F, -6.5F);
		buckleRight.setTextureSize(128, 64);
		buckleRight.mirror = true;
		setRotation(buckleRight, -0.2617994F, 0F, 0F);
		
		skullRing = new ModelRenderer(this, 0, 15);
		skullRing.addBox(0F, 0F, 0F, 3, 3, 0);
		skullRing.setRotationPoint(-1.5F, 17.5F, -6.75F);
		skullRing.setTextureSize(128, 64);
		skullRing.mirror = true;
		setRotation(skullRing, -0.3490659F, 0F, 0F);
		
		skullTall = new ModelRenderer(this, 6, 15);
		skullTall.addBox(0F, 0F, 0F, 2, 4, 1);
		skullTall.setRotationPoint(-1F, 14.5F, -7F);
		skullTall.setTextureSize(128, 64);
		skullTall.mirror = true;
		setRotation(skullTall, 0F, 0F, 0F);
		
		skullWide = new ModelRenderer(this, 0, 20);
		skullWide.addBox(0F, 0F, 0F, 3, 2, 1);
		skullWide.setRotationPoint(-1.5F, 15F, -7.25F);
		skullWide.setTextureSize(128, 64);
		skullWide.mirror = true;
		setRotation(skullWide, 0F, 0F, 0F);
		
		ringFront = new ModelRenderer(this, 62, 7);
		ringFront.addBox(0F, 0F, 0F, 12, 1, 1);
		ringFront.setRotationPoint(-6F, 19F, -7F);
		ringFront.setTextureSize(128, 64);
		ringFront.mirror = true;
		setRotation(ringFront, 0F, 0F, 0F);
		
		ringBack = new ModelRenderer(this, 62, 7);
		ringBack.addBox(0F, 0F, 0F, 12, 1, 1);
		ringBack.setRotationPoint(-6F, 19F, 6F);
		ringBack.setTextureSize(128, 64);
		ringBack.mirror = true;
		setRotation(ringBack, 0F, 0F, 0F);
		
		ringLeft = new ModelRenderer(this, 62, 9);
		ringLeft.addBox(0F, 0F, 0F, 1, 1, 12);
		ringLeft.setRotationPoint(-7F, 18.5F, -6F);
		ringLeft.setTextureSize(128, 64);
		ringLeft.mirror = true;
		setRotation(ringLeft, 0F, 0F, 0F);
		
		ringRight = new ModelRenderer(this, 62, 9);
		ringRight.addBox(0F, 0F, 0F, 1, 1, 12);
		ringRight.setRotationPoint(6F, 18.5F, -6F);
		ringRight.setTextureSize(128, 64);
		ringRight.mirror = true;
		setRotation(ringRight, 0F, 0F, 0F);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		cauldronBase.render(f5);
		base.render(f5);
		sideEmbossFrontTop.render(f5);
		sideEmbossFrontMiddle.render(f5);
		sideEmbossFrontBottom.render(f5);
		sideEmbossLeft.render(f5);
		sideEmbossRight.render(f5);
		sideEmbossBack.render(f5);
		frontSide.render(f5);
		backSide.render(f5);
		leftSide.render(f5);
		rightSide.render(f5);
		rimFront.render(f5);
		rimBack.render(f5);
		rimLeft.render(f5);
		rimRight.render(f5);
		pipeLeft.render(f5);
		pipeRight.render(f5);
		handleTop.render(f5);
		handleLeft.render(f5);
		handleRight.render(f5);
		hingeLeft.render(f5);
		hingeRight.render(f5);
		footFrontLeft.render(f5);
		footFrontRight.render(f5);
		footBackLeft.render(f5);
		footBackRight.render(f5);
		buckleLeft.render(f5);
		buckleRight.render(f5);
		skullRing.render(f5);
		skullTall.render(f5);
		skullWide.render(f5);
		ringFront.render(f5);
		ringBack.render(f5);
		ringLeft.render(f5);
		ringRight.render(f5);
	}
	
	public void renderModel(float f5) {
		cauldronBase.render(f5);
		base.render(f5);
		sideEmbossFrontTop.render(f5);
		sideEmbossFrontMiddle.render(f5);
		sideEmbossFrontBottom.render(f5);
		sideEmbossLeft.render(f5);
		sideEmbossRight.render(f5);
		sideEmbossBack.render(f5);
		frontSide.render(f5);
		backSide.render(f5);
		leftSide.render(f5);
		rightSide.render(f5);
		rimFront.render(f5);
		rimBack.render(f5);
		rimLeft.render(f5);
		rimRight.render(f5);
		pipeLeft.render(f5);
		pipeRight.render(f5);
		handleTop.render(f5);
		handleLeft.render(f5);
		handleRight.render(f5);
		hingeLeft.render(f5);
		hingeRight.render(f5);
		footFrontLeft.render(f5);
		footFrontRight.render(f5);
		footBackLeft.render(f5);
		footBackRight.render(f5);
		buckleLeft.render(f5);
		buckleRight.render(f5);
		skullRing.render(f5);
		skullTall.render(f5);
		skullWide.render(f5);
		ringFront.render(f5);
		ringBack.render(f5);
		ringLeft.render(f5);
		ringRight.render(f5);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}