package tk.nukeduck.alchemy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockEmalgamescence extends BlockFluidClassic {
	@SideOnly(Side.CLIENT)
	protected IIcon still, flowing;
	
	public BlockEmalgamescence(Fluid fluid, Material material) {
		super(fluid, material);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1) ? still : flowing;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		still = register.registerIcon("alchemy:emalgamescence_still");
		flowing = register.registerIcon("alchemy:emalgamescence_flow");
	}
	
	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		if (world.getBlock(x,y,z).getMaterial().isLiquid()) return false;
		return super.canDisplace(world, x, y, z);
	}
	
	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {
		if(world.getBlock(x,y,z).getMaterial().isLiquid()) return false;
		return super.displaceIfPossible(world, x, y, z);
	}
}