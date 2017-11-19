package nukeduck.coinage.registry;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nukeduck.coinage.client.render.ModelMerchantHat;
import nukeduck.coinage.util.FormatUtil;

public class ItemMerchantHat extends ItemArmor {
	public ItemMerchantHat(ArmorMaterial material, int renderIndex, int armorType) {
		super(material, renderIndex, armorType);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return FormatUtil.prefix("textures/entity/villager_merchant.png");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		ModelBiped model = new ModelMerchantHat();
		model.bipedHead.showModel = armorSlot == 0;
		model.bipedHeadwear.showModel = armorSlot == 0;

		model.bipedBody.showModel = armorSlot == 1;
		model.bipedRightArm.showModel = armorSlot == 1;
		model.bipedLeftArm.showModel = armorSlot == 1;

		model.bipedRightLeg.showModel = armorSlot == 2;
		model.bipedLeftLeg.showModel = armorSlot == 2;

		model.isSneak = entityLiving.isSneaking();
		model.isRiding = entityLiving.isRiding();
		model.isChild = entityLiving.isChild();
		model.heldItemRight = entityLiving.getHeldItem() != null ? 1 :0;
		if(entityLiving instanceof EntityPlayer){
			model.aimedBow =((EntityPlayer) entityLiving).getItemInUseDuration() > 2;
		}
		return model;
	}
}
