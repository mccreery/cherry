package nukeduck.coinage.block.tileentity;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.util.Color;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nukeduck.coinage.item.ItemCoin;

public class TileEntityCoinPile extends TileEntity {
	private final int[] coins = new int[3];
	
	public int getCoin(int metadata) {
		if(metadata < 0 || metadata > 2) return 0;
		return this.coins[metadata];
	}
	public void setCoin(int metadata, int value) {
		if(metadata < 0 || metadata > 2) return;
		this.coins[metadata] = value;
	}

	public int[] getCoins() {
		return this.coins;
	}
	public int getTotalCoins() {
		int total = 0;
		for(int i = 0; i < 3; i++) {
			total += this.getCoin(i) * ItemCoin.getCoinMultiplier(i);
		}
		return total;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new S35PacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.getNbtCompound());
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setIntArray("Coins", coins);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		int[] coins = compound.getIntArray("Coins");Math.max(1, 2);
		for(int i = 0; i < this.coins.length; i++) {
			this.coins[i] = i >= coins.length ? 0 : coins[i];
		}
	}
}
