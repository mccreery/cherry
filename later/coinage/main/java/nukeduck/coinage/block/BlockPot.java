package nukeduck.coinage.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nukeduck.coinage.client.PotColorizer;

public class BlockPot extends Block {
	public static final PropertyInteger POT_TYPE = PropertyInteger.create("type", 0, 3);
	
	public BlockPot() {
		super(Material.clay);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {POT_TYPE});
    }

	@Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(POT_TYPE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((Integer) state.getValue(POT_TYPE));
    }

	@Override
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
		return PotColorizer.getColor(pos);
	}

	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return PotColorizer.getColor(127, 127);
	}

	@SideOnly(Side.CLIENT)
	public int getRenderColor(IBlockState state) {
		return this.getBlockColor();
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
