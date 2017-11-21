package tk.nukeduck.magma;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockAsh extends Block
{
	public BlockAsh()
	{
		super(Material.sand);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(Block.soundTypeSand);
		this.setHardness(0.1F);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon)
	{
		blockIcon = icon.registerIcon("magma:ash");
	}
	
    Random random = new Random();
    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        double d5;
        double d6;
        double d7;
        
    	if(par1World.getBlock(par2, par3 + 1, par4).getMaterial() == Material.air && !par1World.getBlock(par2, par3 + 1, par4).isOpaqueCube())
    	{
    		if(par5Random.nextInt(100) == 0)
    		{
    			d5 = (double)((float)par2 + par5Random.nextFloat());
    			d7 = (double)par3 + this.maxY;
    			d6 = (double)((float)par4 + par5Random.nextFloat());
    			par1World.spawnParticle("lava", d5, d7, d6, 0.0D, 0.0D, 0.0D);
    			par1World.playSound(d5, d7, d6, "liquid.lavapop", 0.2F + par5Random.nextFloat() * 0.2F, 0.9F + par5Random.nextFloat() * 0.15F, false);
    		}
    		
    		if(par5Random.nextInt(200) == 0)
    		{
    			par1World.playSound((double)par2, (double)par3, (double)par4, "liquid.lava", 0.2F + par5Random.nextFloat() * 0.2F, 0.9F + par5Random.nextFloat() * 0.15F, false);
    		}
    	}
    	
        for(int i = 0; i < random.nextInt(5); i++)
        {
            d5 = (double)((float)par2 + par5Random.nextFloat());
            d6 = (double)((float)par4 + par5Random.nextFloat());
            
            par1World.spawnParticle("smoke", d5, par3 + 0.0625F, d6, 0.0D, 0.0D, 0.0D);
        }
    }
}