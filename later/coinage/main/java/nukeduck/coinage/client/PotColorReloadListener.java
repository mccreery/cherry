package nukeduck.coinage.client;

import java.io.IOException;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerGrass;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nukeduck.coinage.Constants;

@SideOnly(Side.CLIENT)
public class PotColorReloadListener implements IResourceManagerReloadListener
{
    private static final ResourceLocation LOC_GRASS_PNG = new ResourceLocation(Constants.MODID, "textures/colormap/pot.png");
    private static final String __OBFID = "CL_00001078";

    public void onResourceManagerReload(IResourceManager resourceManager)
    {
        try
        {
            PotColorizer.setGrassBiomeColorizer(TextureUtil.readImageData(resourceManager, LOC_GRASS_PNG));
        }
        catch (IOException ioexception) {}
    }
}