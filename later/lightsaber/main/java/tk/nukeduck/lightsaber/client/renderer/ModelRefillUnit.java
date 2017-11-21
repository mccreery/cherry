package tk.nukeduck.lightsaber.client.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelRefillUnit extends ModelBase {
	public static ModelRenderer base1, base2,
		side1, side2, side3, side4,
		middle, top,
		arm1, arm2, arm3, arm4,
		bolt1, bolt2, bolt3, bolt4,
		hand1, hand2, hand3, hand4,
		screenArm1, screenArm2, screenBar, screenLeft, screenRight, screen;
	
	public ModelRefillUnit() {
		textureWidth = 64;
		textureHeight = 32;
		
		base1 = new ModelRenderer(this, 0, 0).addBox(0F, 0F, 0F, 8, 2, 6);
		base1.setRotationPoint(-4F, 22F, -3F);
		base1.mirror = true;
		setRotation(base1, 0F, 0F, 0F);
		base2 = new ModelRenderer(this, 0, 8).addBox(0F, 0.001F, 0F, 6, 2, 8);
		base2.setRotationPoint(-3F, 22F, -4F);
		base2.mirror = true;
		setRotation(base2, 0F, 0F, 0F);
		side1 = new ModelRenderer(this, 28, 4).addBox(0F, 0F, 0F, 2, 2, 2);
		side1.setRotationPoint(4F, 21F, -1F);
		side1.mirror = true;
		setRotation(side1, 0F, 0F, 0.7853982F);
		side2 = new ModelRenderer(this, 28, 4).addBox(0F, 0F, 0F, 2, 2, 2);
		side2.setRotationPoint(-4F, 21F, -1F);
		side2.mirror = true;
		setRotation(side2, 0F, 0F, 0.7853982F);
		side3 = new ModelRenderer(this, 28, 4).addBox(0F, 0F, 0F, 2, 2, 2);
		side3.setRotationPoint(-1F, 21F, 4F);
		side3.mirror = true;
		setRotation(side3, -0.7853982F, 0F, 0F);
		side4 = new ModelRenderer(this, 28, 4).addBox(0F, 0F, 0F, 2, 2, 2);
		side4.setRotationPoint(-1F, 21F, -4F);
		side4.mirror = true;
		setRotation(side4, -0.7853982F, 0F, 0F);
		middle = new ModelRenderer(this, 20, 8).addBox(0F, 0F, 0F, 4, 2, 4);
		middle.setRotationPoint(-2F, 20F, -2F);
		middle.mirror = true;
		setRotation(middle, 0F, 0F, 0F);
		top = new ModelRenderer(this, 28, 0).addBox(0F, 0F, 0F, 3, 1, 3);
		top.setRotationPoint(-1.5F, 19F, -1.5F);
		top.mirror = true;
		setRotation(top, 0F, 0F, 0F);
		arm1 = new ModelRenderer(this, 0, 8).addBox(0F, 0F, 0F, 1, 6, 1);
		arm1.setRotationPoint(-7F, 16.3F, -0.5F);
		arm1.mirror = true;
		setRotation(arm1, 0F, 0F, -0.3490659F);
		arm2 = new ModelRenderer(this, 0, 8).addBox(0F, 0F, 0F, 1, 6, 1);
		arm2.setRotationPoint(-0.5F, 16.3F, -7F);
		arm2.mirror = true;
		setRotation(arm2, 0.3490659F, 0F, 0F);
		arm3 = new ModelRenderer(this, 0, 8).addBox(0F, 0F, 0F, 1, 6, 1);
		arm3.setRotationPoint(6F, 16F, -0.5F);
		arm3.mirror = true;
		setRotation(arm3, 0F, 0F, 0.3490659F);
		arm4 = new ModelRenderer(this, 0, 8).addBox(0F, 0F, 0F, 1, 6, 1);
		arm4.setRotationPoint(-0.5F, 16F, 6F);
		arm4.mirror = true;
		setRotation(arm4, -0.3490659F, 0F, 0F);
		bolt1 = new ModelRenderer(this, 4, 8).addBox(0F, 0F, 0F, 1, 1, 1);
		bolt1.setRotationPoint(-3F, 21.5F, 2F);
		bolt1.mirror = true;
		setRotation(bolt1, 0F, 0F, 0F);
		bolt2 = new ModelRenderer(this, 4, 10).addBox(0F, 0F, 0F, 1, 1, 1);
		bolt2.setRotationPoint(2F, 21.5F, 2F);
		bolt2.mirror = true;
		setRotation(bolt2, 0F, 0F, 0F);
		bolt3 = new ModelRenderer(this, 4, 12).addBox(0F, 0F, 0F, 1, 1, 1);
		bolt3.setRotationPoint(2F, 21.5F, -3F);
		bolt3.mirror = true;
		setRotation(bolt3, 0F, 0F, 0F);
		bolt4 = new ModelRenderer(this, 4, 14).addBox(0F, 0F, 0F, 1, 1, 1);
		bolt4.setRotationPoint(-3F, 21.5F, -3F);
		bolt4.mirror = true;
		setRotation(bolt4, 0F, 0F, 0F);
		hand1 = new ModelRenderer(this, 0, 0).addBox(0F, 0F, 0F, 1, 4, 1);
		hand1.setRotationPoint(-0.5F, 12.8F, -5F);
		hand1.mirror = true;
		setRotation(hand1, -0.5235988F, 0F, 0F);
		hand2 = new ModelRenderer(this, 0, 0).addBox(0F, 0F, 0F, 1, 4, 1);
		hand2.setRotationPoint(-5F, 12.8F, -0.5F);
		hand2.mirror = true;
		setRotation(hand2, 0F, 0F, 0.5235988F);
		hand3 = new ModelRenderer(this, 0, 0).addBox(0F, 0F, 0F, 1, 4, 1);
		hand3.setRotationPoint(-0.5F, 13.4F, 4.08F);
		hand3.mirror = true;
		setRotation(hand3, 0.5235988F, 0F, 0F);
		hand4 = new ModelRenderer(this, 0, 0).addBox(0F, 0F, 0F, 1, 4, 1);
		hand4.setRotationPoint(4.1F, 13.4F, -0.5F);
		hand4.mirror = true;
		setRotation(hand4, 0F, 0F, -0.5235988F);
		
		screenArm1 = new ModelRenderer(this, 0, 27).addBox(0F, 0F, 0F, 1, 1, 4);
		screenArm1.setRotationPoint(-0.5F, 23F, -8F);
		screenArm1.mirror = true;
		setRotation(screenArm1, 0F, 0F, 0F);
		screenArm2 = new ModelRenderer(this, 0, 26).addBox(0F, 0F, 0F, 1, 4, 1);
		screenArm2.setRotationPoint(-0.5F, 19F, -8F);
		screenArm2.mirror = true;
		setRotation(screenArm2, 0F, 0F, 0F);
		screenBar = new ModelRenderer(this, 10, 25).addBox(0F, 0F, 0F, 5, 1, 1);
		screenBar.setRotationPoint(-2.5F, 18F, -8F);
		screenBar.mirror = true;
		setRotation(screenBar, 0F, 0F, 0F);
		screenLeft = new ModelRenderer(this, 6, 26).addBox(0F, -4F, 0F, 1, 4, 1);
		screenLeft.setRotationPoint(1.5F, 18F, -8F);
		screenLeft.mirror = true;
		setRotation(screenLeft, -0.0872665F, 0F, 0F);
		screenRight = new ModelRenderer(this, 6, 26).addBox(0F, -4F, 0F, 1, 4, 1);
		screenRight.setRotationPoint(-2.5F, 18F, -8F);
		screenRight.mirror = true;
		setRotation(screenRight, -0.0872665F, 0F, 0F);
		screen = new ModelRenderer(this, 10, 27).addBox(0F, -4F, 0F, 7, 4, 1);
		screen.setRotationPoint(-3.5F, 15F, -8.5F);
		screen.mirror = true;
		setRotation(screen, -0.424146F, 0F, 0F);
		
		for(ModelRenderer m : new ModelRenderer[] {base1, base2, side1, side2, side3, side4, middle, top,
				arm1, arm2, arm3, arm4, bolt1, bolt2, bolt3, bolt4,
				hand1, hand2, hand3, hand4, screenArm1, screenArm2, screenBar, screenLeft,
				screenRight, screen}) {
			m.setTextureSize(textureWidth, textureHeight);
		}
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, int rotation) {
		//super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		base1.render(f5);
		base2.render(f5);
		middle.render(f5);
		
		GL11.glPushMatrix();
		GL11.glRotatef((float) Math.sin((rotation * 0.01F)) * 360F, 0, 1, 0);
		
		top.render(f5);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glRotatef(rotation, 0, 1, 0);
		arm1.render(f5);
		arm2.render(f5);
		arm3.render(f5);
		arm4.render(f5);
		hand1.render(f5);
		hand2.render(f5);
		hand3.render(f5);
		hand4.render(f5);
		side1.render(f5);
		side2.render(f5);
		side3.render(f5);
		side4.render(f5);
		GL11.glPopMatrix();
		
		bolt1.render(f5);
		bolt2.render(f5);
		bolt3.render(f5);
		bolt4.render(f5);
		
		screenArm1.render(f5);
		screenArm2.render(f5);
		screenBar.render(f5);
		screenLeft.render(f5);
		screenRight.render(f5);
		screen.render(f5);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
	}
	
	public void renderModel(float f) {
		base1.render(f);
		base2.render(f);
		side1.render(f);
		side2.render(f);
		side3.render(f);
		side4.render(f);
		middle.render(f);
		arm1.render(f);
		arm2.render(f);
		arm3.render(f);
		arm4.render(f);
		bolt1.render(f);
		bolt2.render(f);
		bolt3.render(f);
		bolt4.render(f);
		hand1.render(f);
		hand2.render(f);
		hand3.render(f);
		hand4.render(f);
	}
}