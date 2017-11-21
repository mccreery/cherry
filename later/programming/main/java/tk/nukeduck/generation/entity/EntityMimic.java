package tk.nukeduck.generation.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import tk.nukeduck.generation.block.TileEntityMimic;
import tk.nukeduck.generation.util.NBTConstants;
import tk.nukeduck.generation.util.NBTConstants.TagType;

public class EntityMimic extends EntityLiving implements IMob {
	ItemStack[] items;

	public float squishAmount;
	public float squishFactor;
	public float prevSquishFactor;
	/** ticks until this slime jumps again */
	private int slimeJumpDelay;

	public EntityMimic(World world) {
		super(world);
		this.items = new ItemStack[0];
		this.yOffset = 0.0F;
		this.slimeJumpDelay = this.rand.nextInt(20) + 10;
		this.setSize(0.875F, 0.875F);
	}

	public EntityMimic setItems(int itemCount) {
		return setItems(new ItemStack[itemCount]);
	}
	public EntityMimic setItems(ItemStack[] items) {
		this.items = items;
		return this;
	}
	public EntityMimic setItem(int i, ItemStack item) {
		this.items[i] = item;
		return this;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		NBTTagList items = new NBTTagList();
		for(ItemStack item : this.items) {
			items.appendTag(item.writeToNBT(new NBTTagCompound()));
		}
		compound.setTag(NBTConstants.CHEST_KEY, items);
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		NBTTagList items = compound.getTagList(NBTConstants.CHEST_KEY, TagType.COMPOUND);

		this.setItems(items.tagCount());
		for(int i = 0; i < items.tagCount(); i++) {
			this.setItem(i, ItemStack.loadItemStackFromNBT(items.getCompoundTagAt(i)));
		}
	}

	/** @return The sound this entity makes when it jumps */
	protected String getJumpSound() {
		return "step.wood";
	}

	public void onUpdate() {
		// Kill the mimic if we're on peaceful
		if(!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
			this.isDead = true;
		}

		this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
		this.prevSquishFactor = this.squishFactor;
		boolean prevOnGround = this.onGround;
		super.onUpdate();

		if(this.onGround && !prevOnGround) {
			this.playSound(this.getJumpSound(), this.getSoundVolume(), this.rand.nextFloat() * 0.5F + 0.3F);
			this.squishAmount = -0.5F;
		} else if(!this.onGround && prevOnGround) {
			this.squishAmount = 1.0F;
		}

		this.alterSquishAmount();
	}

	protected void alterSquishAmount() {
		this.squishAmount *= 0.9F;
	}

	protected void updateEntityActionState() {
		this.despawnEntity();
		EntityPlayer player = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
		boolean canSeePlayer = player != null;

		if(canSeePlayer) {
			this.faceEntity(player, 10.0F, 20.0F);
		}

		if(this.onGround && this.slimeJumpDelay-- <= 0) {
			this.slimeJumpDelay = this.getJumpDelay(canSeePlayer);
			this.isJumping = true;

			this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);

			this.moveStrafing = 1.0F - this.rand.nextFloat() * 2.0F;
			this.moveForward = 1.0F;
		} else {
			this.isJumping = false;

			if(this.onGround) {
				this.moveStrafing = this.moveForward = 0.0F;
			}
		}
	}

	/** Gets the amount of time the entity needs to wait between jumps. */
	protected int getJumpDelay(boolean canSeePlayer) {
		if(canSeePlayer) {
			return this.rand.nextInt(10) + 5;
		} else {
			return this.rand.nextInt(20) + 10;
		}
	}

	/** Called by a player entity when they collide with an entity */
	public void onCollideWithPlayer(EntityPlayer player) {
		if(this.canEntityBeSeen(player) && this.getDistanceSqToEntity(player) < 2.25D
				&& player.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttackStrength())) {
			this.playSound("mob.attack", 1.0F, 0.5F + this.rand.nextFloat() * 0.4F);
		}
	}

	private float getAttackStrength() {
		return 4.0F;
	}

	@Override
	protected String getHurtSound() {
		return "dig.wood";
	}

	@Override
	protected String getDeathSound() {
		return this.getHurtSound();
	}

	@Override
	protected float getSoundVolume() {
		return 0.8F;
	}

	@Override
	public int getVerticalFaceSpeed() {
		return 0;
	}

	@Override
	protected Item getDropItem() {
		return Item.getItemFromBlock(Blocks.chest);
	}

	protected int getDropBaseCount() {
		return 1;
	}

	@Override
	protected void dropFewItems(boolean recentlyHit, int looting) {
		this.dropItem(this.getDropItem(), 1);

		for(ItemStack i : this.getItems()) {
			this.entityDropItem(i, 0.0F);
		}
	}

	public ItemStack[] getItems() {
		return this.items;
	}
	public int getItemCount() {
		return this.items.length;
	}

	/** Causes this entity to do an upwards motion (jumping). */
	protected void jump() {
		this.motionY = 0.5D;
		this.isAirBorne = true;
		ForgeHooks.onLivingJump(this);
	}

	protected void fall(float p_70069_1_) {}
}