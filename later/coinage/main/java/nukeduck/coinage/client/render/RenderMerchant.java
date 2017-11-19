package nukeduck.coinage.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import nukeduck.coinage.Coinage;
import nukeduck.coinage.Constants;

public class RenderMerchant extends RenderBiped {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/villager_merchant.png");
	public RenderMerchant(RenderManager p_i46168_1_, ModelBiped p_i46168_2_, float p_i46168_3_) {
		super(p_i46168_1_, p_i46168_2_, p_i46168_3_);
		this.addLayer(new LayerPocketItems());
	}
	
    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return TEXTURE;
    }
    
    @Override
    public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
    	super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
    }
}
