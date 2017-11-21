package tk.nukeduck.generation.util;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import tk.nukeduck.generation.registry.ObjectName;
import tk.nukeduck.generation.registry.ObjectName.Format;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemWrapper<I extends Item> {
	private final Mod mod;
	public final I item;
	public final ObjectName name;

	public ItemWrapper(Mod mod, I item, ObjectName name) {
		this.mod = mod;
		this.item = item;
		this.name = name;
	}

	public ItemWrapper init() {
		this.item.setUnlocalizedName(this.name.toString(Format.CAMEL_CASE_HEADLESS));
		this.item.setTextureName(new ResourceLocation(this.mod.modid(), this.name.toString(Format.UNDERSCORED_LOWER)).toString());
		GameRegistry.registerItem(this.item, this.name.toString(Format.UNDERSCORED_LOWER));
		return this;
	}
}
