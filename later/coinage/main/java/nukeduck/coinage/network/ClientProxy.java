package nukeduck.coinage.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import nukeduck.coinage.Constants;
import nukeduck.coinage.block.tileentity.TileEntityCoinPile;
import nukeduck.coinage.client.PotColorReloadListener;
import nukeduck.coinage.client.render.ModelMerchant;
import nukeduck.coinage.client.render.RenderEntityItemCoin;
import nukeduck.coinage.client.render.RenderMerchant;
import nukeduck.coinage.client.render.TileEntityCoinPileRenderer;
import nukeduck.coinage.entity.EntityItemCoin;
import nukeduck.coinage.entity.EntityMerchant;

public class ClientProxy extends CommonProxy {
	private FontRenderer fontRendererCoins;
	@Override
	public FontRenderer getFontRendererCoins() {
		return this.fontRendererCoins;
	}
	
	@Override
	public void registerRenderThings() {
		fontRendererCoins = new FontRenderer(Minecraft.getMinecraft().gameSettings, new ResourceLocation(Constants.MODID, "textures/gui/coin_chars.png"), Minecraft.getMinecraft().getTextureManager(), false);
        if (Minecraft.getMinecraft().gameSettings.language != null) {
            fontRendererCoins.setUnicodeFlag(Minecraft.getMinecraft().isUnicode());
            fontRendererCoins.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());
        }
		
		RenderingRegistry.registerEntityRenderingHandler(EntityMerchant.class, new RenderMerchant(Minecraft.getMinecraft().getRenderManager(), new ModelMerchant(), 0.5f));
		RenderingRegistry.registerEntityRenderingHandler(EntityItemCoin.class, new RenderEntityItemCoin(Minecraft.getMinecraft().getRenderManager(), Minecraft.getMinecraft().getRenderItem()));
		
		IReloadableResourceManager mcResourceManager = (IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
		mcResourceManager.registerReloadListener(new PotColorReloadListener());
		mcResourceManager.registerReloadListener(fontRendererCoins);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoinPile.class, new TileEntityCoinPileRenderer());
	}
}
