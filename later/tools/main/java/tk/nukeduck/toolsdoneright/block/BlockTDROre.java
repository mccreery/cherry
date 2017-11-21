package tk.nukeduck.toolsdoneright.block;

import java.util.Random;

import tk.nukeduck.toolsdoneright.ToolsDoneRight;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class BlockTDROre extends BlockTDR {
	public BlockTDROre(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return this == ToolsDoneRight.rubyOre ? ToolsDoneRight.rubyItem : this == ToolsDoneRight.topazOre ? ToolsDoneRight.topazItem : this == ToolsDoneRight.sapphireOre ? ToolsDoneRight.sapphireItem : Item.getItemFromBlock(this);
    }
    
    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_) {
        if (p_149679_1_ > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, p_149679_2_, p_149679_1_))
        {
            int j = p_149679_2_.nextInt(p_149679_1_ + 1) - 1;

            if (j < 0)
            {
                j = 0;
            }

            return this.quantityDropped(p_149679_2_) * (j + 1);
        }
        else
        {
            return this.quantityDropped(p_149679_2_);
        }
    }
    
    private Random rand = new Random();
    
    @Override
    public int getExpDrop(IBlockAccess p_149690_1_, int p_149690_5_, int p_149690_7_) {
    	return MathHelper.getRandomIntegerInRange(rand, 3, 7);
    }
}