package nukeduck.gmasign.item;

import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import nukeduck.gmasign.block.GMASBlocks;

public class ItemModernSign extends Item {
    public ItemModernSign() {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    /**
     * Called when a Block is right-clicked with this Item
     *  
     * @param pos The block being right-clicked
     * @param side The side being right-clicked
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(side == EnumFacing.DOWN) {
            return false;
        } else if(!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid()) {
            return false;
        } else {
            pos = pos.offset(side);

            if(!playerIn.canPlayerEdit(pos, side, stack)) {
                return false;
            } else if(!GMASBlocks.modernSign.canPlaceBlockAt(worldIn, pos)) {
                return false;
            } else if(worldIn.isRemote) {
                return true;
            } else {
                if(side == EnumFacing.UP) {
                    int i = MathHelper.floor_double((double)((playerIn.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                    worldIn.setBlockState(pos, GMASBlocks.standingModernSign.getDefaultState().withProperty(BlockStandingSign.ROTATION, Integer.valueOf(i)), 3);
                } else {
                    worldIn.setBlockState(pos, GMASBlocks.modernSign.getDefaultState().withProperty(BlockWallSign.FACING, side), 3);
                }

                --stack.stackSize;
                TileEntity tileentity = worldIn.getTileEntity(pos);

                if(tileentity instanceof TileEntitySign && !ItemBlock.setTileEntityNBT(worldIn, pos, stack, playerIn)) {
                    playerIn.openEditSign((TileEntitySign)tileentity);
                }

                return true;
            }
        }
    }
}
