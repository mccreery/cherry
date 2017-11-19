package tk.nukeduck.walljump;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemSpecialMobSpawner extends ItemSpecial {
	private final String entityId;
	
	public ItemSpecialMobSpawner(String entityId) {
		super(Item.getItemFromBlock(Blocks.mob_spawner));
		this.entityId = entityId;
	}
	
	@Override
	public ItemStack item() {
		Entity e = EntityList.createEntityByName(this.entityId, Minecraft.getMinecraft().theWorld);
		if(e instanceof EntityLiving) {
			String name = e.getName();
			name = name == null ? "Unknown" : name;
			
			ItemStack spawner = new ItemStack(this.baseItem);
			NBTTagCompound nbt = new NBTTagCompound();
			NBTTagCompound blockData = new NBTTagCompound();
			blockData.setString("EntityId", this.entityId);
			nbt.setTag("BlockEntityTag", blockData);
			spawner.setTagCompound(nbt);
			spawner.setStackDisplayName(ChatFormatting.RESET + name + " Spawner");
			return spawner;
		}
		return null;
	}
}