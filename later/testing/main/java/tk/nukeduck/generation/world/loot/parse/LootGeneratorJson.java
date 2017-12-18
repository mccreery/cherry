package tk.nukeduck.generation.world.loot.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tk.nukeduck.generation.util.BlockPos;
import tk.nukeduck.generation.util.FuncUtils;
import tk.nukeduck.generation.util.IOHelper;
import tk.nukeduck.generation.util.WeightedRandom;
import tk.nukeduck.generation.world.loot.ILootGenerator;
import tk.nukeduck.generation.world.loot.parse.condition.ICondition;
import tk.nukeduck.generation.world.loot.parse.generator.GeneratorStatic;
import tk.nukeduck.generation.world.loot.parse.generator.IGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class LootGeneratorJson implements ILootGenerator {
	private WeightedRandom<LootData.StackData> stacks;
	IGenerator stackCount;

	public LootGeneratorJson(Random random, ResourceLocation resource) {
		this(random, IOHelper.getResource(resource));
	}

	public LootGeneratorJson(Random random, String json) {
		GsonBuilder builder = new GsonBuilder()
			.registerTypeAdapter(IGenerator.class, new GeneratorDeserializer())
			.registerTypeAdapter(ICondition.class, new ConditionDeserializer())
			.registerTypeAdapter(JsonObjectDeserializer.Wrapper.class, new JsonObjectDeserializer());

		Gson gson = builder.create();
		LootData data = gson.fromJson(json, LootData.class);

		this.stackCount = data.stackCount;
		this.stacks = new WeightedRandom<LootData.StackData>() {
			@Override
			public boolean includeValue(LootData.StackData value, Object... args) {
				World world = (World) args[0];
				BlockPos pos = (BlockPos) args[1];
				for(ICondition condition : value.conditions) {
					if(!condition.isMet(world, pos)) return false;
				}
				return true;
			}
		};
		for(LootData.StackData stack : data.stacks) {
			this.stacks.set(stack, stack.getWeight());
		}
	}

	@Override
	public ItemStack[] generate(World world, BlockPos pos, IInventory inventory, Random random) {
		ItemStack[] loot = new ItemStack[this.stackCount.next(random)];
		for(int i = 0; i < loot.length; i++) {
			LootData.StackData stack = stacks.next(random, world, pos);
			if(stack == null) return new ItemStack[0];
			loot[i] = stack.getStack(random);
		}
		return loot;
	}

	@Override
	public boolean isValidContainer(IInventory container) {
		return container instanceof TileEntityChest;
	}

	private static class LootData {
		private String name;
		private IGenerator stackCount;
		private List<StackData> stacks;

		private static class StackData {
			private float weight = 1.0F;
			private String item;
			private IGenerator metadata = new GeneratorStatic(0.0);
			private IGenerator size = new GeneratorStatic(1.0);
			private List<ICondition> conditions = new ArrayList<ICondition>();
			private List<EnchantmentData> enchantments = new ArrayList<EnchantmentData>();
			//private JsonObjectDeserializer.Wrapper tag = new JsonObjectDeserializer.Wrapper(new JsonObject());

			public ItemStack getStack(Random random) {
				Item item = (Item) Item.itemRegistry.getObject(this.item);
				if(item == null) {
					item = Item.getItemFromBlock((Block) Block.blockRegistry.getObject(this.item));
				}
				if(item == null) throw new JsonParseException("Invalid item id: " + this.item);

				ItemStack stack = new ItemStack(item, size.next(random), metadata.next(random));

				for(EnchantmentData enchantment : this.enchantments) {
					FuncUtils.addEnchantment(stack, Enchantment.enchantmentsList[enchantment.id], (short) enchantment.level.next(random));
				}
				/*try {
					stack.setTagCompound((NBTTagCompound) JsonToNBT.func_150315_a(this.tag.json.toString()));
				} catch (NBTException e) {
					e.printStackTrace();
				}*/
				return stack;
			}

			public float getWeight() {
				return this.weight;
			}

			public static class EnchantmentData {
				public byte id;
				public IGenerator level;
			}
		}
	}
}
