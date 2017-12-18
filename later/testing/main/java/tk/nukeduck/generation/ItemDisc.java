package tk.nukeduck.generation;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemDisc extends Item {
	public static final String COUNT_KEY = "BlockCount";

	public final int[] capacities = {256, 512, 1024, 4096};
	private final IIcon[] types = new IIcon[4];
	public IIcon overlay;

	public static IIcon background;

	public ItemDisc() {
		this.setCreativeTab(Generation.COMPUTER_TAB);
		this.setTextureName("alchemy:optical_disc");
		this.setUnlocalizedName("opticalDisc");
		this.setMaxStackSize(1);
	}

	@Override
	public void registerIcons(IIconRegister register) {
		for(int i = 0; i < this.types.length; i++) {
			this.types[i] = register.registerIcon(this.getIconString() + "_" + String.valueOf(i));
		}
		this.overlay = register.registerIcon(this.getIconString() + "_overlay");
		if(background == null) background = register.registerIcon(this.getIconString() + "_background");
	}

	@Override
	public IIcon getIconFromDamage(int metadata) {
		return this.types[metadata];
	}

	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, List items) {
		for(int i = 0; i < this.types.length; i++) {
			items.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "." + String.valueOf(stack.getItemDamage());
	}

	protected void attemptCreateTagCompound(ItemStack stack) {
		if(stack.getTagCompound() == null) {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setInteger(COUNT_KEY, 0);
			stack.setTagCompound(compound);
		}
	}

	public void onUpdate(ItemStack stack, World world, Entity entity, int a, boolean b) {
		this.attemptCreateTagCompound(stack);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List text, boolean advanced) {
		this.attemptCreateTagCompound(stack);
		int full = stack.getTagCompound().getInteger(COUNT_KEY);
		text.add(I18n.format("item.opticalDisc.sub", full, capacities[stack.getItemDamage()]));
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		int full = stack.hasTagCompound() ? stack.getTagCompound().getInteger(COUNT_KEY) : 0;
		return 1.0 - (double) full / (double) this.capacities[stack.getItemDamage()];
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.values()[stack.getItemDamage()];
	}

	@Override
	public boolean hasEffect(ItemStack stack, int pass) {
		return stack.getItemDamage() > 1;
	}
}
