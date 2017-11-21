package tk.nukeduck.magma;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

public class BlockAshCloud extends BlockBN {
	public BlockAshCloud(Material mat) {
		super(mat);
	}
	
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    /*public boolean isOpaqueCube()
    {
        return false;
    }*/
    
    @Override
    public int getRenderBlockPass() {
    	return 1;
    }
    
    /*@Override
    public boolean canRenderInPass(int i) {
    	return i == 1;
    }*/
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, 1 - par5);
    }
}