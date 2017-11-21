package tk.nukeduck.lightsaber.client.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLightsaberA extends ModelBaseLightsaber {
	ModelRenderer upperRing;
	ModelRenderer lowerRing;
	ModelRenderer sideBox;
	ModelRenderer mainBody;
	ModelRenderer upperButton;
	ModelRenderer lowerButton;
	ModelRenderer backHolder;
	ModelRenderer leftHolder1;
	ModelRenderer rightHolder1;
	ModelRenderer leftHolder2;
	ModelRenderer rightHolder2;
	ModelRenderer frontHolder;
	ModelRenderer rightHolder3;
	ModelRenderer leftHolder3;
	ModelRenderer topHolder;
	
	public static ModelRenderer[] boxes;
	
	public ModelLightsaberA() {
		this.bladeOffset = 16;
		this.doubledOffset = 40;
		
		textureWidth = 32;
		textureHeight = 64;
		
		upperRing = new ModelRenderer(this, 10, 12);
		upperRing.addBox(0F, 0F, 0F, 5, 1, 5);
		upperRing.setRotationPoint(-2.5F, 18F, -2.5F);
		upperRing.mirror = true;
		setRotation(upperRing, 0F, 0F, 0F);
		lowerRing = new ModelRenderer(this, 10, 12);
		lowerRing.addBox(0F, 0F, 0F, 5, 1, 5);
		lowerRing.setRotationPoint(-2.5F, 20F, -2.5F);
		lowerRing.mirror = true;
		setRotation(lowerRing, 0F, 0F, 0F);
		sideBox = new ModelRenderer(this, 16, 4);
		sideBox.addBox(0F, 0F, 0F, 1, 4, 3);
		sideBox.setRotationPoint(-3.5F, 17.5F, -1.5F);
		sideBox.mirror = true;
		setRotation(sideBox, 0F, 0F, 0F);
		mainBody = new ModelRenderer(this, 0, 0);
		mainBody.addBox(0F, 0F, 0F, 4, 8, 4);
		mainBody.setRotationPoint(-2F, 16F, -2F);
		mainBody.mirror = true;
		setRotation(mainBody, 0F, 0F, 0F);
		upperButton = new ModelRenderer(this, 24, 7);
		upperButton.addBox(0F, 0F, 0F, 1, 1, 1);
		upperButton.setRotationPoint(2.5F, 18F, -0.5F);
		upperButton.mirror = true;
		setRotation(upperButton, 0F, 0F, 0F);
		lowerButton = new ModelRenderer(this, 24, 7);
		lowerButton.addBox(0F, 0F, 0F, 1, 1, 1);
		lowerButton.setRotationPoint(2.5F, 20F, -0.5F);
		lowerButton.mirror = true;
		setRotation(lowerButton, 0F, 0F, 0F);
		backHolder = new ModelRenderer(this, 10, 18);
		backHolder.addBox(0F, 0F, 0F, 1, 4, 4);
		backHolder.setRotationPoint(1.5F, 12.5F, -2F);
		backHolder.mirror = true;
		setRotation(backHolder, 0F, 0F, 0F);
		leftHolder1 = new ModelRenderer(this, 15, 0);
		leftHolder1.addBox(0F, 0F, 0F, 2, 3, 1);
		leftHolder1.setRotationPoint(0F, 13.5F, -2.5F);
		leftHolder1.mirror = true;
		setRotation(leftHolder1, 0F, 0F, 0F);
		rightHolder1 = new ModelRenderer(this, 15, 0);
		rightHolder1.addBox(0F, 0F, 0F, 2, 3, 1);
		rightHolder1.setRotationPoint(0F, 13.5F, 1.5F);
		rightHolder1.mirror = true;
		setRotation(rightHolder1, 0F, 0F, 0F);
		leftHolder2 = new ModelRenderer(this, 0, 0);
		leftHolder2.addBox(0F, 0F, 0F, 1, 2, 1);
		leftHolder2.setRotationPoint(-1F, 14.5F, -2.5F);
		leftHolder2.mirror = true;
		setRotation(leftHolder2, 0F, 0F, 0F);
		rightHolder2 = new ModelRenderer(this, 0, 0);
		rightHolder2.addBox(0F, 0F, 0F, 1, 2, 1);
		rightHolder2.setRotationPoint(-1F, 14.5F, 1.5F);
		rightHolder2.mirror = true;
		setRotation(rightHolder2, 0F, 0F, 0F);
		frontHolder = new ModelRenderer(this, 21, 0);
		frontHolder.addBox(0F, 0F, 0F, 1, 1, 4);
		frontHolder.setRotationPoint(-2.5F, 15.5F, -2F);
		frontHolder.mirror = true;
		setRotation(frontHolder, 0F, 0F, 0F);
		rightHolder3 = new ModelRenderer(this, 24, 5);
		rightHolder3.addBox(0F, 0F, 0F, 1, 1, 1);
		rightHolder3.setRotationPoint(-2F, 15.5F, 1.5F);
		rightHolder3.mirror = true;
		setRotation(rightHolder3, 0F, 0F, 0F);
		leftHolder3 = new ModelRenderer(this, 24, 5);
		leftHolder3.addBox(0F, 0F, 0F, 1, 1, 1);
		leftHolder3.setRotationPoint(-2F, 15.5F, -2.5F);
		leftHolder3.mirror = true;
		setRotation(leftHolder3, 0F, 0F, 0F);
		blade1 = new ModelRenderer(this, 10, 29);
		blade1.addBox(0F, 0F, 0F, 3, 32, 2);
		blade1.setRotationPoint(-1.5F, -16F, -1F);
		blade1.mirror = true;
		setRotation(blade1, 0F, 0F, 0F);
		blade2 = new ModelRenderer(this, 0, 12);
		blade2.addBox(0F, 0F, 0F, 2, 32, 3);
		blade2.setRotationPoint(-1F, -16F, -1.5F);
		blade2.mirror = true;
		setRotation(blade2, 0F, 0F, 0F);
		topHolder = new ModelRenderer(this, 10, 26);
		topHolder.addBox(0F, 0F, 0F, 1, 1, 2);
		topHolder.setRotationPoint(1.5F, 11.5F, -1F);
		topHolder.mirror = true;
		setRotation(topHolder, 0F, 0F, 0F);
		
		boxes = new ModelRenderer[] {
			lowerRing, sideBox, mainBody, upperButton, lowerButton, backHolder, leftHolder2, rightHolder1, leftHolder2, rightHolder2, frontHolder, rightHolder3, leftHolder3, blade1, blade2, topHolder
		};
		
		for(ModelRenderer box : boxes) {
			box.setTextureSize(32,  64);
		}
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, boolean blade) {
		if(blade) {
			blade1.render(f5);
			blade2.render(f5);
		} else {
			upperRing.render(f5);
			lowerRing.render(f5);
			sideBox.render(f5);
			mainBody.render(f5);
			upperButton.render(f5);
			lowerButton.render(f5);
			backHolder.render(f5);
			leftHolder1.render(f5);
			rightHolder1.render(f5);
			leftHolder2.render(f5);
			rightHolder2.render(f5);
			frontHolder.render(f5);
			rightHolder3.render(f5);
			leftHolder3.render(f5);
			topHolder.render(f5);
		}
	}
}