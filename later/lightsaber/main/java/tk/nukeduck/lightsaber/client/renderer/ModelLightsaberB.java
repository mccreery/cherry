package tk.nukeduck.lightsaber.client.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLightsaberB extends ModelBaseLightsaber {
	ModelRenderer baseRing;
	ModelRenderer base;
	ModelRenderer upperButton;
	ModelRenderer upperRing;
	ModelRenderer lowerButton;
	ModelRenderer emitter1;
	ModelRenderer emitter2;
	ModelRenderer cone1;
	ModelRenderer cone2;
	ModelRenderer cone3;
	ModelRenderer cone4;
	ModelRenderer sidePanel;

	public ModelLightsaberB() {
		this.bladeOffset = 12;
		this.doubledOffset = 36;
		
		textureWidth = 32;
		textureHeight = 64;

		blade1 = new ModelRenderer(this, 10, 29);
		blade1.addBox(0F, 0F, 0F, 3, 28, 2);
		blade1.setRotationPoint(-1.5F, -16F, -1F);
		blade1.setTextureSize(32, 64);
		blade1.mirror = true;
		setRotation(blade1, 0F, 0F, 0F);
		blade2 = new ModelRenderer(this, 0, 12);
		blade2.addBox(0F, 0F, 0F, 2, 28, 3);
		blade2.setRotationPoint(-1F, -16F, -1.5F);
		blade2.setTextureSize(32, 64);
		blade2.mirror = true;
		setRotation(blade2, 0F, 0F, 0F);
		baseRing = new ModelRenderer(this, 0, 0);
		baseRing.addBox(0F, 0F, 0F, 4, 2, 4);
		baseRing.setRotationPoint(-2F, 21.5F, -2F);
		baseRing.setTextureSize(32, 64);
		baseRing.mirror = true;
		setRotation(baseRing, 0F, 0F, 0F);
		base = new ModelRenderer(this, 20, 0);
		base.addBox(0F, 0F, 0F, 3, 5, 3);
		base.setRotationPoint(-1.5F, 19F, -1.5F);
		base.setTextureSize(32, 64);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		upperButton = new ModelRenderer(this, 0, 6);
		upperButton.addBox(0F, 0F, 0F, 1, 1, 1);
		upperButton.setRotationPoint(1.5F, 19.5F, -0.5F);
		upperButton.setTextureSize(32, 64);
		upperButton.mirror = true;
		setRotation(upperButton, 0F, 0F, 0F);
		upperRing = new ModelRenderer(this, 16, 8);
		upperRing.addBox(0F, 0F, 0F, 4, 3, 4);
		upperRing.setRotationPoint(-2F, 16.5F, -2F);
		upperRing.setTextureSize(32, 64);
		upperRing.mirror = true;
		setRotation(upperRing, 0F, 0F, 0F);
		lowerButton = new ModelRenderer(this, 0, 6);
		lowerButton.addBox(0F, 0F, 0F, 1, 1, 1);
		lowerButton.setRotationPoint(1.5F, 21F, -0.5F);
		lowerButton.setTextureSize(32, 64);
		lowerButton.mirror = true;
		setRotation(lowerButton, 0F, 0F, 0F);
		emitter1 = new ModelRenderer(this, 4, 6);
		emitter1.addBox(0F, 0F, 0F, 2, 1, 2);
		emitter1.setRotationPoint(-1F, 15.5F, -1F);
		emitter1.setTextureSize(32, 64);
		emitter1.mirror = true;
		setRotation(emitter1, 0F, 0F, 0F);
		emitter2 = new ModelRenderer(this, 20, 15);
		emitter2.addBox(0F, 0F, 0F, 3, 1, 3);
		emitter2.setRotationPoint(-1.5F, 14.5F, -1.5F);
		emitter2.setTextureSize(32, 64);
		emitter2.mirror = true;
		setRotation(emitter2, 0F, 0F, 0F);
		cone1 = new ModelRenderer(this, 28, 32);
		cone1.addBox(0F, 0F, 0F, 1, 1, 1);
		cone1.setRotationPoint(-0.5F, 14F, -0.5F);
		cone1.setTextureSize(32, 64);
		cone1.mirror = true;
		setRotation(cone1, 0F, 0F, 0F);
		cone2 = new ModelRenderer(this, 24, 29);
		cone2.addBox(0F, 0F, 0F, 2, 1, 2);
		cone2.setRotationPoint(-1F, 13F, -1F);
		cone2.setTextureSize(32, 64);
		cone2.mirror = true;
		setRotation(cone2, 0F, 0F, 0F);
		cone3 = new ModelRenderer(this, 20, 25);
		cone3.addBox(0F, 0F, 0F, 3, 1, 3);
		cone3.setRotationPoint(-1.5F, 12.5F, -1.5F);
		cone3.setTextureSize(32, 64);
		cone3.mirror = true;
		setRotation(cone3, 0F, 0F, 0F);
		cone4 = new ModelRenderer(this, 16, 20);
		cone4.addBox(0F, 0F, 0F, 4, 1, 4);
		cone4.setRotationPoint(-2F, 12F, -2F);
		cone4.setTextureSize(32, 64);
		cone4.mirror = true;
		setRotation(cone4, 0F, 0F, 0F);
		sidePanel = new ModelRenderer(this, 14, 15);
		sidePanel.addBox(0F, 0F, 0F, 1, 3, 2);
		sidePanel.setRotationPoint(-2.5F, 19F, -1F);
		sidePanel.setTextureSize(32, 64);
		sidePanel.mirror = true;
		setRotation(sidePanel, 0F, 0F, 0F);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, boolean blade) {
		//setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if(blade) {
			blade1.render(f5);
			blade2.render(f5);
		} else {
			baseRing.render(f5);
			base.render(f5);
			upperButton.render(f5);
			lowerButton.render(f5);
			upperRing.render(f5);
			emitter1.render(f5);
			emitter2.render(f5);
			cone1.render(f5);
			cone2.render(f5);
			cone3.render(f5);
			cone4.render(f5);
			sidePanel.render(f5);
		}
	}
}
