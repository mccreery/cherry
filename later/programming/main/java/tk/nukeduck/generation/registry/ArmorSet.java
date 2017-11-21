package tk.nukeduck.generation.registry;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import tk.nukeduck.generation.registry.ObjectName.Format;
import tk.nukeduck.generation.util.ItemWrapper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.registry.GameRegistry;

public class ArmorSet {
	private final Mod mod;

	public final ObjectName baseName;

	public final ArmorMaterial material;
	public final ItemStack resource;

	public final ItemWrapper<ItemArmorTextured> helmet, chestplate, leggings, boots;

	public ArmorSet(Class<?> mod, ObjectName name, Item resource, int durability, int reductionHelmet, int reductionChestplate, int reductionLeggings, int reductionBoots, int enchantability) {
		this(mod, name, new ItemStack(resource), durability, reductionHelmet, reductionChestplate, reductionLeggings, reductionBoots, enchantability);
	}
	public ArmorSet(Class<?> mod, ObjectName name, ItemStack resource, int durability, int reductionHelmet, int reductionChestplate, int reductionLeggings, int reductionBoots, int enchantability) {
		this.mod = mod.getAnnotation(Mod.class);
		this.resource = resource;

		this.baseName = name;
		this.material = EnumHelper.addArmorMaterial(
				this.baseName.toString(Format.UPPER), durability, new int[] {reductionHelmet, reductionChestplate, reductionLeggings, reductionBoots}, enchantability);

		this.helmet = new ItemWrapper<ItemArmorTextured>(this.mod, new ItemArmorTextured(this.mod, this.material, 0, 0, this.baseName), ObjectName.suffix(this.baseName, "helmet"));
		this.chestplate = new ItemWrapper<ItemArmorTextured>(this.mod, new ItemArmorTextured(this.mod, this.material, 0, 1, this.baseName), ObjectName.suffix(this.baseName, "chestplate"));
		this.leggings = new ItemWrapper<ItemArmorTextured>(this.mod, new ItemArmorTextured(this.mod, this.material, 0, 2, this.baseName), ObjectName.suffix(this.baseName, "leggings"));
		this.boots = new ItemWrapper<ItemArmorTextured>(this.mod, new ItemArmorTextured(this.mod, this.material, 0, 3, this.baseName), ObjectName.suffix(this.baseName, "boots"));
	}

	public void init() {
		this.helmet.init();
		this.chestplate.init();
		this.leggings.init();
		this.boots.init();

		GameRegistry.addShapedRecipe(new ItemStack(this.helmet.item), this.getHelmetTemplate());
		GameRegistry.addShapedRecipe(new ItemStack(this.chestplate.item), this.getChestplateTemplate());
		GameRegistry.addShapedRecipe(new ItemStack(this.leggings.item), this.getLeggingsTemplate());
		GameRegistry.addShapedRecipe(new ItemStack(this.boots.item), this.getBootsTemplate());
	}

	public Object[] getHelmetTemplate() {
		return new Object[] {"###", "# #", '#', this.resource};
	}
	public Object[] getChestplateTemplate() {
		return new Object[] {"# #", "###", "###", '#', this.resource};
	}
	public Object[] getLeggingsTemplate() {
		return new Object[] {"###", "# #", "# #", '#', this.resource};
	}
	public Object[] getBootsTemplate() {
		return new Object[] {"# #", "# #", '#', this.resource};
	}

	public class ItemArmorTextured extends ItemArmor {
		private final String[] armorTextures;

		public ItemArmorTextured(Mod mod, ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_, ObjectName name) {
			super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
			this.armorTextures = new String[] {
				new ResourceLocation(mod.modid(), "textures/models/armor/" + ObjectName.suffix(name, "layer", "1").toString(Format.UNDERSCORED_LOWER) + ".png").toString(),
				new ResourceLocation(mod.modid(), "textures/models/armor/" + ObjectName.suffix(name, "layer", "2").toString(Format.UNDERSCORED_LOWER) + ".png").toString()
			};
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
			return this.armorTextures[this.armorType == 2 ? 1 : 0];
		}
	}
}
