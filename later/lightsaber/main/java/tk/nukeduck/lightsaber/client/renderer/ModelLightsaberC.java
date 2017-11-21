package tk.nukeduck.lightsaber.client.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelLightsaberC extends ModelBaseLightsaber {
	ModelRenderer base;
	ModelRenderer button;
	ModelRenderer ring;
	ModelRenderer sidePanel;
	ModelRenderer indent;
	ModelRenderer bar1;
	ModelRenderer bar2;
	ModelRenderer bar3;
	ModelRenderer bar4;
	ModelRenderer bar5;
	ModelRenderer bar6;
	ModelRenderer bar7;
	ModelRenderer bar8;
	ModelRenderer emitter;

	public ModelLightsaberC() {
		this.bladeOffset = 13;
		this.doubledOffset = 37;
		
		textureWidth = 32;
		textureHeight = 64;

		blade1 = new ModelRenderer(this, 10, 29);
		blade1.addBox(0F, 0F, 0F, 3, 29, 2);
		blade1.setRotationPoint(-1.5F, -16F, -1F);
		blade1.setTextureSize(32, 64);
		blade1.mirror = true;
		setRotation(blade1, 0F, 0F, 0F);
		blade2 = new ModelRenderer(this, 0, 12);
		blade2.addBox(0F, 0F, 0F, 2, 29, 3);
		blade2.setRotationPoint(-1F, -16F, -1.5F);
		blade2.setTextureSize(32, 64);
		blade2.mirror = true;
		setRotation(blade2, 0F, 0F, 0F);
		base = new ModelRenderer(this, 20, 0);
		base.addBox(0F, 0F, 0F, 3, 9, 3);
		base.setRotationPoint(-1.5F, 15F, -1.5F);
		base.setTextureSize(32, 64);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		button = new ModelRenderer(this, 0, 6);
		button.addBox(0F, 0F, 0F, 1, 1, 1);
		button.setRotationPoint(-0.5F, 22.5F, -2.5F);
		button.setTextureSize(32, 64);
		button.mirror = true;
		setRotation(button, 0F, 0F, 0F);
		ring = new ModelRenderer(this, 16, 12);
		ring.addBox(0F, 0F, 0F, 4, 4, 4);
		ring.setRotationPoint(-2F, 18.5F, -2F);
		ring.setTextureSize(32, 64);
		ring.mirror = true;
		setRotation(ring, 0F, 0F, 0F);
		sidePanel = new ModelRenderer(this, 10, 15);
		sidePanel.addBox(0F, 0F, 0F, 1, 2, 2);
		sidePanel.setRotationPoint(-2.5F, 15F, -1F);
		sidePanel.setTextureSize(32, 64);
		sidePanel.mirror = true;
		setRotation(sidePanel, 0F, 0F, 0F);
		indent = new ModelRenderer(this, 4, 0);
		indent.addBox(0F, 0F, 0F, 2, 1, 2);
		indent.setRotationPoint(-1F, 14F, -1F);
		indent.setTextureSize(32, 64);
		indent.mirror = true;
		setRotation(indent, 0F, 0F, 0F);
		bar1 = new ModelRenderer(this, 0, 0);
		bar1.addBox(0F, 0F, 0F, 1, 4, 1);
		bar1.setRotationPoint(0.5F, 18F, -2.5F);
		bar1.setTextureSize(32, 64);
		bar1.mirror = true;
		setRotation(bar1, 0F, 0F, 0F);
		bar2 = new ModelRenderer(this, 0, 0);
		bar2.addBox(0F, 0F, 0F, 1, 4, 1);
		bar2.setRotationPoint(-1.5F, 18F, -2.5F);
		bar2.setTextureSize(32, 64);
		bar2.mirror = true;
		setRotation(bar2, 0F, 0F, 0F);
		bar3 = new ModelRenderer(this, 0, 0);
		bar3.addBox(0F, 0F, 0F, 1, 4, 1);
		bar3.setRotationPoint(-2.5F, 18F, -1.5F);
		bar3.setTextureSize(32, 64);
		bar3.mirror = true;
		setRotation(bar3, 0F, 0F, 0F);
		bar4 = new ModelRenderer(this, 0, 0);
		bar4.addBox(0F, 0F, 0F, 1, 4, 1);
		bar4.setRotationPoint(-2.5F, 18F, 0.5F);
		bar4.setTextureSize(32, 64);
		bar4.mirror = true;
		setRotation(bar4, 0F, 0F, 0F);
		bar5 = new ModelRenderer(this, 0, 0);
		bar5.addBox(0F, 0F, 0F, 1, 4, 1);
		bar5.setRotationPoint(-1.5F, 18F, 1.5F);
		bar5.setTextureSize(32, 64);
		bar5.mirror = true;
		setRotation(bar5, 0F, 0F, 0F);
		bar6 = new ModelRenderer(this, 0, 0);
		bar6.addBox(0F, 0F, 0F, 1, 4, 1);
		bar6.setRotationPoint(0.5F, 18F, 1.5F);
		bar6.setTextureSize(32, 64);
		bar6.mirror = true;
		setRotation(bar6, 0F, 0F, 0F);
		bar7 = new ModelRenderer(this, 0, 0);
		bar7.addBox(0F, 0F, 0F, 1, 4, 1);
		bar7.setRotationPoint(1.5F, 18F, 0.5F);
		bar7.setTextureSize(32, 64);
		bar7.mirror = true;
		setRotation(bar7, 0F, 0F, 0F);
		bar8 = new ModelRenderer(this, 0, 0);
		bar8.addBox(0F, 0F, 0F, 1, 4, 1);
		bar8.setRotationPoint(1.5F, 18F, -1.5F);
		bar8.setTextureSize(32, 64);
		bar8.mirror = true;
		setRotation(bar8, 0F, 0F, 0F);
		emitter = new ModelRenderer(this, 0, 8);
		emitter.addBox(0F, 0F, 0F, 3, 1, 3);
		emitter.setRotationPoint(-1.5F, 13F, -1.5F);
		emitter.setTextureSize(32, 64);
		emitter.mirror = true;
		setRotation(emitter, 0F, 0F, 0F);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, boolean blade) {
		//setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		if(blade) {
			blade1.render(f5);
			blade2.render(f5);
		} else {
			base.render(f5);
			button.render(f5);
			ring.render(f5);
			sidePanel.render(f5);
			indent.render(f5);
			bar1.render(f5);
			bar2.render(f5);
			bar3.render(f5);
			bar4.render(f5);
			bar5.render(f5);
			bar6.render(f5);
			bar7.render(f5);
			bar8.render(f5);
			emitter.render(f5);
		}
	}
}
