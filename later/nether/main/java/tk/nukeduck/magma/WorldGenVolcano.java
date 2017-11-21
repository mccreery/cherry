package tk.nukeduck.magma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenVolcano extends WorldGenerator {
	@Override
	public boolean generate(World arg0, Random arg1, int arg2, int arg3, int arg4) {
		/*for(int x = arg2; x < arg2 + 20; x++) {
			for(int y = arg3; y < arg3 + 20; y++) {
				for(int z = arg4; z < arg4 + 20; z++) {
					double dx = x - arg2 + 10;
					double dy = y - arg3 + 10;
					double dz = z - arg4 + 10;
					
					//if(Math.sqrt((dx * dx) + (dy * dy) + (dz * dz)) <= 10) {
						arg0.setBlock(x, y, z, Magma.basalt);
					//}
				}
			}
		}*/
		
		if(arg1.nextBoolean() && arg1.nextBoolean() && arg1.nextBoolean() && arg1.nextBoolean()) {
			double[][] nan = generateVolcano(arg1);
			
			double maxValue = Double.MIN_VALUE;
			for (int i = 0; i < nan.length; i++) {
			    for (int j = 0; j < nan[i].length; j++) {
			        if (nan[i][j] > maxValue) {
			           maxValue = nan[i][j];
			        }
			    }
			}
		    
			for(int x = 0; x < nan.length; x++) {
				for(int z = 0; z < nan[0].length; z++) {
					double yTop = arg3 + (nan[x][z] * ((256 - distSquared(x, z, nan.length / 2, nan[0].length / 2))) / 768);
					for(int y = arg3; y < /*(yTop > maxValue ? maxValue : yTop)*/ yTop; y++) {
						double s = distSquared(x, z, nan.length / 2, nan[0].length / 2);
						arg0.setBlock(x + arg2, y, z + arg4, s < 15 ? Blocks.lava : s < 37 ? (arg1.nextDouble() > 0.7 ? Magma.basalt : Blocks.obsidian) : Magma.basalt);
					}
				}
			}
		}
		return true;
	}
	
	public double distSquared(int x, int z, int x2, int z2) {
		double dx = x2 - x;
		double dz = z2 - z;
		return (dx * dx) + (dz * dz);
	}
	
	public double smoothStep(float x) {
		return x*x*x*(x*(x*6 - 15) + 10);
	}
	
	public double[][] generateVolcano(Random random) {
		final int size = 33; // (2 ^ n) + 1
		
		double[][] data = new double[size][size];
		data[0][0] = data[0][size - 1] = data[size - 1][0] = data[size - 1][size - 1] = 20;
		
		double h = 75.0;//the range (-h -> +h) for the average offset
		for(int sideLength = size - 1; sideLength >= 2; sideLength /= 2, h /= 2.0) {
			int halfSide = sideLength / 2;
			for(int x=0; x < size - 1; x += sideLength) {
				for(int y = 0; y < size-1; y += sideLength) {
					double avg = data[x][y] + data[x + sideLength][y] + data[x][y + sideLength] + data[x + sideLength][y + sideLength];
					avg /= 4.0;
					data[x+halfSide][y+halfSide] = avg + (random.nextDouble()*2*h) - h;
				}
			}
			for(int x=0;x<size-1;x+=halfSide){
				for(int y=(x+halfSide)%sideLength;y<size-1;y+=sideLength){
					double avg = 
					        data[(x-halfSide+size-1)%(size-1)][y] + //left of center
					        data[(x+halfSide)%(size-1)][y] + //right of center
					        data[x][(y+halfSide)%(size-1)] + //below center
					        data[x][(y-halfSide+size-1)%(size-1)]; //above center
					avg /= 4.0;
					
					avg = avg + (random.nextDouble()*2*h) - h;
					data[x][y] = avg;
					if(x == 0)  data[size-1][y] = avg;
					if(y == 0)  data[x][size-1] = avg;
				}
			}
		}
		
		return data;
	}
}