package tk.nukeduck.generation.registry;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tk.nukeduck.generation.block.BlockC;
import tk.nukeduck.generation.block.BlockCamoDispenser;
import tk.nukeduck.generation.client.GuiProgrammer;
import tk.nukeduck.generation.client.codeblocks.BlockStack;
import tk.nukeduck.generation.client.codeblocks.action.CodeBlockChatMessage;
import tk.nukeduck.generation.client.codeblocks.control.CodeBlockFunction;
import tk.nukeduck.generation.client.codeblocks.control.CodeBlockIf;
import tk.nukeduck.generation.client.codeblocks.data.CodeBlockTextBox;
import tk.nukeduck.generation.client.codeblocks.logic.integer.CodeBlockGreater;
import tk.nukeduck.generation.util.BlockItemName;
import tk.nukeduck.generation.util.BlockItemName.NameFormat;

public class GenerationBlocks {
	public static Block camoDispenser;

	public static final void init() {
		camoDispenser = new BlockCamoDispenser().setCreativeTab(CreativeTabs.tabRedstone);
		registerBlock(camoDispenser, new BlockItemName("camo", "dispenser"));

		registerBlock(new BlockC(Material.cloth) {
			private final Random random = new Random();

			@Override
			public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
				/*ILootGenerator chest = new LootGeneratorJson(random, new ResourceLocation("alchemy", "loot/dungeon.json"));
				WorldUtils.setContainer(world, x, y, z, Blocks.chest, 0, chest, random, true);*/
				GuiProgrammer programmer = new GuiProgrammer(null);
				
				BlockStack stack = new BlockStack(0, 0);
				
				CodeBlockFunction func = new CodeBlockFunction().setName("HelloWorld");
				CodeBlockChatMessage msg = new CodeBlockChatMessage();
				msg.container.setChild(new CodeBlockTextBox("Hik!"));
				func.addToBlock(msg);
				
				CodeBlockIf ifStatement = new CodeBlockIf();
				ifStatement.container.setChild(new CodeBlockGreater());
				ifStatement.blockBlocks.add(new CodeBlockChatMessage());
				func.addToBlock(ifStatement);

				stack.append(func);
				programmer.stacks.add(stack);
				
				Minecraft.getMinecraft().displayGuiScreen(programmer);

				return true;
			}

			@Override
			public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
				//new StructureRoom(Dimension.OVERWORLD).generate(world, x, y, z, this.random);
				return super.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, metadata);
			}
		}.setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName("endframe_top"), new BlockItemName("test", "block"));
	}

	public static final void registerBlock(Block block, BlockItemName name) {
		block.setBlockName(name.format(NameFormat.HEADLESS_CAMELCASE));
		GameRegistry.registerBlock(block, name.format(NameFormat.UNDERSCORED_LOWERCASE));
	}
}
