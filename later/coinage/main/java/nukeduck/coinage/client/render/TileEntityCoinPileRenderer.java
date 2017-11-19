package nukeduck.coinage.client.render;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import nukeduck.coinage.Constants;
import nukeduck.coinage.block.tileentity.TileEntityCoinPile;

public class TileEntityCoinPileRenderer extends TileEntitySpecialRenderer {
	public static final int MAX_PILE = 10;
	public static ItemStack[] coinStacks = new ItemStack[3];
	public static final int[] stackHeights = new int[256];

	static {
		Random random = new Random(Constants.SEED);
		for(int i = 0; i < stackHeights.length; i++) {
			stackHeights[i] = (int) (random.nextDouble() * 25) + 5;
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTick, int p_180535_9_) {
		TileEntityCoinPile tileEntityPile = (TileEntityCoinPile) tileEntity;
		ArrayList<CoinPile> piles = this.getPiles(tileEntityPile);

		ItemRenderer itemRenderer = Minecraft.getMinecraft().getItemRenderer();
		float[][] pilePos = {
				{0.44f, 0.53f},
				{0.63f, 0.4f},
				{0.34f, 0.3f},
				{0.25f, 0.68f},
				{0.63f, 0.75f},
				{0.58f, 0.14f},
				{0.1f, 0.46f},
				{0.85f, 0.58f},
				{0.82f, 0.22f},
				{0.15f, 0.12f},
				{0.39f, 0.83f},
				{0.89f, 0.88f}
		};

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		for(int i = 0; i < piles.size(); i++) {
			if(i >= pilePos.length) break;

			CoinPile pile = piles.get(i);

			int pileX = (int) (i / 4);
			int pileZ = i % 4;

			float coinScale = 0.25f;

			GL11.glPushMatrix();
			GL11.glTranslatef(pilePos[i][0], 0, pilePos[i][1]/*pileX * 0.25f, 0, pileZ * 0.25f*/);
			GL11.glScalef(coinScale, coinScale * pile.getCount() * (pile.getMeta() == 2 ? 2 : 1), coinScale);
			GL11.glTranslatef(0, 0.03125f, 0);
			GL11.glRotatef(90, 1, 0, 0);
			itemRenderer.renderItem(null, coinStacks[pile.getMeta()], TransformType.NONE);
			GL11.glPopMatrix();
			/*for(int j = 0; j < pile.getCount(); j++) {
				GL11.glPushMatrix();
				GL11.glTranslatef(pileX * 0.25f, 0, pileZ * 0.25f);
				GL11.glScalef(coinScale, coinScale * (pile.getMeta() == 2 ? 2 : 1), coinScale);
				GL11.glTranslatef(0, j * 0.0625f + 0.03125f, 0);

				GL11.glRotatef(j * 385, 0, 1, 0);
				GL11.glRotatef(90, 1, 0, 0);
				itemRenderer.renderItem(null, coinStacks[pile.getMeta()], TransformType.NONE);
				GL11.glPopMatrix();
			}*/
		}
		GL11.glPopMatrix();
	}

	public ArrayList<CoinPile> getPiles(TileEntityCoinPile tileEntity) {
		ArrayList<CoinPile> piles = new ArrayList<CoinPile>();
		for(int i = 2; i >= 0; i--) {
			int total = tileEntity.getCoin(i);

			// Create as many new full piles as needed
			int currentSize = stackHeights[piles.size() % stackHeights.length];
			while(total >= currentSize) {
				total -= currentSize;
				piles.add(new CoinPile(i, currentSize, currentSize));
				currentSize = stackHeights[piles.size() % stackHeights.length];
			}
			// Add any non-full piles
			if(total > 0) piles.add(new CoinPile(i, total, currentSize));
		}
		return piles;
	}

	private static class CoinPile {
		private int metadata;
		private int count;
		private int max;

		public CoinPile(int metadata, int count, int max) {
			this.metadata = metadata;
			this.count = count;
			this.max = max;
		}

		public int getMeta() {
			return this.metadata;
		}

		public int getCount() {
			return this.count;
		}

		public int getMax() {
			return this.max;
		}

		public int getSpace() {
			return this.getMax() - this.getCount();
		}

		public boolean add() {return add(1);}
		public boolean add(int n) {
			return set(this.count + n);
		}

		public boolean set(int n) {
			if(this.getSpace() == 0) return false;
			this.count = n;
			return true;
		}
	}
}
