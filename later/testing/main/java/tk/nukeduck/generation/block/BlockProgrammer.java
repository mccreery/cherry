package tk.nukeduck.generation.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tk.nukeduck.generation.Generation;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockProgrammer extends Block implements ITileEntityProvider {
	protected IIcon frontIcon, sideIcon, backIcon;

	public BlockProgrammer() {
		super(Material.iron);
		this.setCreativeTab(Generation.COMPUTER_TAB);
		this.setBlockTextureName("alchemy:programmer_top");
		this.setBlockName("programmer");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityProgrammer();
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if(((TileEntityProgrammer) world.getTileEntity(x, y, z)).isUseableByPlayer(player)) {
			player.openGui(Generation.instance, 0, world, x, y, z);
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		super.registerBlockIcons(register);
		this.frontIcon = register.registerIcon("alchemy:programmer_front");
		this.sideIcon = register.registerIcon("alchemy:programmer_side");
		this.backIcon = register.registerIcon("alchemy:programmer_back");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if(side < 2) return super.getIcon(side, meta);

		side = (side - 2 + meta) % 4;
		if(side == 0) {
			return this.frontIcon;
		} else if(side == 1) {
			return this.backIcon;
		} else {
			return this.sideIcon;
		}
	}
}
