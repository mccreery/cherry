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
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemDisc extends Item {
	public static final String COUNT_KEY = "BlockCount";
	public static final String ALLOCATION_KEY = "Pointed";
	public static final String PROGRAMS_KEY = "Programs";

	public final short[] capacities = {256, 512, 1024, 4096};
	private final IIcon[] types = new IIcon[4];
	public IIcon overlay;

	public static IIcon background;

	public ItemDisc() {
		this.setCreativeTab(Generation.COMPUTER_TAB);
		this.setTextureName("alchemy:optical_disc");
		this.setUnlocalizedName("opticalDisc");
		this.setMaxStackSize(1);
	}

	public short getPointer(ItemStack stack) {
		return stack.getTagCompound().getShort(ALLOCATION_KEY);
	}
	public boolean hasPointer(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(ALLOCATION_KEY, 2);
	}

	public void updateFull(ItemStack stack) {
		short length = 0;
		NBTTagList programs = stack.getTagCompound().getTagList(PROGRAMS_KEY, 10);
		for(int i = 0; i < programs.tagCount(); i++) {
			NBTTagCompound programTag = programs.getCompoundTagAt(i);

			BlockProgram program = new BlockProgram();
			if(!program.readFromNBT(programTag)) continue;

			length += program.length;
		}
		stack.getTagCompound().setShort(COUNT_KEY, length);
	}

	public void writeProgram(ItemStack stack, BlockProgram program) {
		NBTTagList programs = stack.getTagCompound().getTagList(PROGRAMS_KEY, 10);
		program.start = this.getPointer(stack);

		NBTTagCompound tag = new NBTTagCompound();
		program.writeToNBT(tag);
		programs.appendTag(tag);

		this.updateFull(stack);
	}

	public boolean hasSufficientSpace(ItemStack stack, short size) {
		if(!this.hasPointer(stack)) return false;

		NBTTagCompound tag = stack.getTagCompound();
		if(!tag.hasKey(PROGRAMS_KEY, 9)) return true;

		short start = tag.getShort(ALLOCATION_KEY);
		short end = (short) (start + size);

		NBTTagList programs = stack.getTagCompound().getTagList(PROGRAMS_KEY, 10);
		for(int i = 0; i < programs.tagCount(); i++) {
			NBTTagCompound programTag = programs.getCompoundTagAt(i);

			BlockProgram program = new BlockProgram();
			if(!program.readFromNBT(programTag)) continue;

			short programEnd = (short) (program.start + program.length);
			if((program.start >= start && program.start < end) || (programEnd >= start && programEnd < end)) {
				return false;
			}
		}
		return true;
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
		short full = stack.getTagCompound().getShort(COUNT_KEY);
		text.add(I18n.format("item.opticalDisc.sub", full, capacities[stack.getItemDamage()]));
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		int full = stack.hasTagCompound() ? stack.getTagCompound().getShort(COUNT_KEY) : 0;
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
