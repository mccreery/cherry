package tk.nukeduck.generation.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import scala.util.Random;
import tk.nukeduck.generation.entity.EntityMimic;

public class BlockMimic extends Block implements ITileEntityProvider {
	public BlockMimic(Material material) {
		super(material);
		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMimic();
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, l, 3);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
		EntityMimic mimic = this.getMimic(world, x, y, z);
		mimic.setDead();
		world.spawnEntityInWorld(mimic);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if(world.isRemote) return false;
		for(int i = 0; i < 20; i++) {
			double d2 = world.rand.nextGaussian() * 0.02D;
			double d0 = world.rand.nextGaussian() * 0.02D;
			double d1 = world.rand.nextGaussian() * 0.02D;
			world.spawnParticle("explode", x + world.rand.nextDouble(), y + world.rand.nextDouble(), z + world.rand.nextDouble(), d0, d1, d2);
		}
		world.playSoundEffect(x, (double) y + 0.5D, z, "random.chestopen", 0.5F, world.rand.nextFloat() * 0.1F + 0.7F);
		EntityMimic m = this.getMimic(world, x, y, z);
		m.setItems(2);
		m.setItem(0, new ItemStack(Items.stick));
		m.setItem(1, new ItemStack(Item.getItemFromBlock(Blocks.stone)));

		world.spawnEntityInWorld(m);
		m.rotationYaw = world.getBlockMetadata(x, y, z) * 90.0F + 180.0F;
		world.setBlockToAir(x, y, z);
		return true;
	}

	public EntityMimic getMimic(World world, int x, int y, int z) {
		EntityMimic mimic = new EntityMimic(world);
		mimic.setPosition(x + 0.5D, y, z + 0.5D);
		mimic.rotationYaw = mimic.prevRotationYaw = world.getBlockMetadata(x, y, z) * 90.0F + 180.0F;
		return mimic;
	}
}
