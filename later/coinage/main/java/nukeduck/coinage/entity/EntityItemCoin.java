package nukeduck.coinage.entity;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import nukeduck.coinage.Coinage;

public class EntityItemCoin extends EntityItem {
	public EntityItemCoin(World worldIn, double x, double y, double z, ItemStack stack, boolean allowGreed) {
		super(worldIn, x, y, z, stack);
		this.getEntityData().setBoolean("AllowGreed", allowGreed);
		this.allowsGreed = allowGreed;
	}
	
    public EntityItemCoin(World worldIn) {
        super(worldIn);
		this.getEntityData().setBoolean("AllowGreed", false);
		this.allowsGreed = false;
    }
    
    double lastYVel = 0;
    private boolean allowsGreed;
    
    public boolean allowsGreed() {
    	return this.allowsGreed;
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
    	super.writeEntityToNBT(tagCompound);
    	tagCompound.setBoolean("AllowGreed", this.allowsGreed);
    }
    
    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompound) {
    	super.readEntityFromNBT(tagCompound);
    	this.allowsGreed = tagCompound.getBoolean("AllowGreed");
    }
    
    @Override
    public void onUpdate()
    {
        super.onUpdate();
        
        if(this.onGround && this.lastYVel < -0.05) {
        	this.motionY = -this.lastYVel * 0.9;
        	this.isAirBorne = true;
        	
        	this.worldObj.playSoundAtEntity(this, "random.orb", 0.1F, Coinage.random.nextFloat() * 0.2F + 1.6F);
        }
        
        this.lastYVel = this.motionY;
    }
}
