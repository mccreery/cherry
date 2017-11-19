package nukeduck.functionaldecor.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import nukeduck.functionaldecor.FunctionalDecor;
import nukeduck.functionaldecor.client.renderers.Renderers;
import nukeduck.functionaldecor.util.BoundingBox;

public class BlockPeripheral extends BlockDecor {
	protected BlockPeripheral() {
		super(DecorBlocks.IRON_HAND, Renderers.peripheral);
		this.setBlockTextureName("stone");
		this.setStepSound(Block.soundTypeMetal);
		this.setBlockBounds(0.34375F, 0.0F, 0.34375F, 0.65625F, 0.4375F, 0.65625F);
	}

	public BoundingBox getBoundsBasedOnType(Type type) {
		switch(type) {
			case MICROPHONE:
				return new BoundingBox(5, 11, 5).translate(5.5F, 0, 5.5F).scale(0.0625F);
			case MOUSE:
				return new BoundingBox(3, 2, 5).translate(6.5F, 0, 5.0F).scale(0.0625F);
			case MONITOR:
				return BoundingBox._join(
					new BoundingBox(16, 10, 2).translate(0, 3, 7).scale(0.0625F),
					new BoundingBox(6, 1, 4).translate(5, 0, 6).scale(0.0625F)
				);
			case KEYBOARD:
				return new BoundingBox(14, 1, 5).translate(1, 0, 5).scale(0.0625F);
			case TOWER:
				return new BoundingBox(6, 12, 14).translate(5, 0, 1).scale(0.0625F);
		}
		return null;
	}

	public BoundingBox getBaseBB(IBlockAccess world, int x, int y, int z) {
		TileEntityDecor decor = (TileEntityDecor) world.getTileEntity(x, y, z);
		if(decor == null) return BoundingBox.EMPTY;

		Type type = Type.fromId(decor.getCustomData().getByte("ID"));
		BoundingBox bb;
		if(type == Type.MONITOR && decor.getCustomData().hasKey("Extras")) {
			int extras = decor.getCustomData().getByte("Extras");
			bb = BoundingBox._join(this.getBoundsBasedOnType(type));

			if((extras & (0x1 << Type.KEYBOARD.ordinal())) == (0x1 << Type.KEYBOARD.ordinal())) {
				bb.scale(16.0F);
				bb.translate(0.0F, 0.0F, 5.0F);
				bb.scale(0.0625F);

				bb.join(this.getBoundsBasedOnType(Type.KEYBOARD).scale(16.0F).translate(0.0F, 0.0F, -3.0F).scale(0.0625F));
			} else if((extras & (0x1 << Type.MOUSE.ordinal())) == (0x1 << Type.MOUSE.ordinal())) {
				bb.scale(16.0F);
				bb.translate(0.0F, 0.0F, 5.0F);
				bb.scale(0.0625F);

				bb.join(this.getBoundsBasedOnType(Type.MOUSE).scale(16.0F).translate(0.0F, 0.0F, -3.0F).scale(0.0625F));
			}
		} else {
			bb = BoundingBox._join(this.getBoundsBasedOnType(type));
		}
		bb.rotateY(0.5F, 0.5F, world.getBlockMetadata(x, y, z) / 16.0F * 360.0F);
		bb.clamp(BoundingBox.ONES);

		return bb;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return this.getBaseBB(world, x, y, z).translate(x, y, z).toAABB();
	}
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		return this.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.getBaseBB(world, x, y, z).applyTo(this);
	}

	public enum Type {
		TOWER, MICROPHONE, MOUSE, MONITOR, KEYBOARD;

		public static final int[] ordinals = new int[values().length];
		static {
			for(int i = 0; i < ordinals.length; i++) {
				ordinals[i] = i << 8;
			}
		}

		public static Type fromMeta(int metadata) {
			return fromId((byte) (metadata >> 8));
		}

		public byte getId() {
			return (byte) this.ordinal();
		}
		public static Type fromId(byte id) {
			return values()[id & 0xFF];
		}
	}

	@Override
	public int[] getMetaList() {
		return Type.ordinals;
	}

	@Override
	public NBTTagCompound getNBTForItemMeta(int metadata) {
		NBTTagCompound compound = new NBTTagCompound();
		Type id = Type.fromMeta(metadata);
		compound.setByte("ID", id.getId());

		switch(id) {
			case MICROPHONE:
				compound.setByte("Rotation", (byte) 0);
				break;
			default:
		}
		return compound;
	}

	private final String button = new ResourceLocation(FunctionalDecor.MODID, "button").toString();

	@Override
	public int getItemMetaForNBT(NBTTagCompound compound) {
		return Type.fromId(compound.getByte("ID")).getId() << 8;
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return true;

		TileEntityDecor decor = (TileEntityDecor) world.getTileEntity(x, y, z);
		NBTTagCompound data = decor.getCustomData();
		switch(Type.fromId(data.getByte("ID"))) {
			case MICROPHONE:
				byte rotationIndex = data.getByte("Rotation");
				if(hitY >= this.maxY * 0.5) {
					if(rotationIndex > -5) data.setByte("Rotation", --rotationIndex);
				} else {
					if(rotationIndex < 5) data.setByte("Rotation", ++rotationIndex);
				}
				break;
			case MONITOR:
				ItemStack held = player.getHeldItem();

				if(held != null) {
					Block block = Block.getBlockFromItem(held.getItem());

					if(block == DecorBlocks.peripheral) {
						Type type = Type.fromMeta(held.getItemDamage());

						if(type == Type.KEYBOARD || type == Type.MOUSE) {
							if(data.hasKey("Extras")) break;
							if(!player.capabilities.isCreativeMode && --held.stackSize <= 0) {
								player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
							}
							data.setByte("Extras", (byte) (data.getByte("Extras") | 0x1 << type.ordinal()));

							return true;
						}
					}
				}

				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, button, 0.3F, 0.8F + FunctionalDecor.RANDOM.nextFloat() * 0.3F);
				data.setByte("Channel", (byte) ((data.getByte("Channel") + 1) % 8));
				break;
			default:
		}
		return true;
	}

	private final String[] itemNames = new String[] {
		"tower", "microphone", "mouse", "monitor", "keyboard"
	};

	@Override
	public String getUnlocalizedName(int metadata) {
		byte id = Type.fromMeta(metadata).getId();
		return super.getUnlocalizedName() + "." + itemNames[id < itemNames.length ? id : 0];
	}
}
