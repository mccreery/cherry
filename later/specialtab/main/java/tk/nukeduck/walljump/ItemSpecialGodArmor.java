package tk.nukeduck.walljump;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSpecialGodArmor extends ItemSpecial {
	private static final Enchantment[] enchantments = new Enchantment[] {
		Enchantment.protection, Enchantment.blastProtection,
		Enchantment.projectileProtection, Enchantment.fireProtection
	};
	
	private final boolean isBoots, isHelmet;
	
	public ItemSpecialGodArmor(Item item) {
		super(item);
		this.isBoots = item == Items.diamond_boots;
		this.isHelmet = item == Items.diamond_helmet;
	}
	
	@Override
	public ItemStack item() {
		ItemStack armorPiece = new ItemStack(this.baseItem);
		addModifier(armorPiece, "generic.attackDamage", "Damage Boost", 1000000000000D, 0);
		for(Enchantment ench : enchantments) {
			addEnchantment(armorPiece, ench, 1000);
		}
		addEnchantment(armorPiece, Enchantment.thorns, 32767);
		
		if(this.isBoots) {
			addEnchantment(armorPiece, Enchantment.featherFalling, 32767);
			addEnchantment(armorPiece, Enchantment.depthStrider, 32767);
		} else if(isHelmet) {
			addEnchantment(armorPiece, Enchantment.respiration, 5);
			addEnchantment(armorPiece, Enchantment.aquaAffinity, 32767);
		}
		
		setUnbreakable(armorPiece, true);
		armorPiece.setStackDisplayName(ChatFormatting.RESET + "" + ChatFormatting.DARK_PURPLE + "Godly " + armorPiece.getDisplayName());
		return armorPiece;
	}
}