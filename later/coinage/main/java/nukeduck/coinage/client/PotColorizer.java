package nukeduck.coinage.client;

import java.util.Random;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.BlockPos;
import nukeduck.coinage.Constants;

public class PotColorizer {
    private static int[] colorBuffer = new int[256 * 256];
    private static int[] randomBuffer = new int[256 * 256];
    static {reloadRandoms();}
    
    public static final void reloadRandoms() {
    	Random random = new Random(Constants.SEED); // Just any old seed
    	for(int i = 0; i < randomBuffer.length; i++) {
    		randomBuffer[i] = (int) (random.nextDouble() * 256);
    	}
    }
    
    public static void setGrassBiomeColorizer(int[] p_77479_0_) {
        colorBuffer = p_77479_0_;
    }
    
    public static int getColor(BlockPos pos) {
    	int n = (pos.getX() & 0xff) << 8 | pos.getZ() & 0xff;
        int x = randomBuffer[n % randomBuffer.length];
        
        return getColor(x, pos.getY());
    }
    
    public static int getColor(int x, int y) {
    	return colorBuffer[(255 - y) * 256 + x];
    }
}
