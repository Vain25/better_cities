package com.github.Vain25.util;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;

public final class StructureUtils {

    private StructureUtils(){}

    public static boolean isChunkAreaFlat(Structure.GenerationContext context, int chunkRadius, int tolerance) {
        ChunkPos origin = context.chunkPos();
        int min = 256;
        int max = 0;
        for(int i = -chunkRadius; i < chunkRadius; i+=2) {
            for(int j = -chunkRadius; j < chunkRadius; j+=2) {
                ChunkPos chunkPos = new ChunkPos(origin.x + i, origin.z + j);
                int[] range = guessSurfaceHeightRange(context, chunkPos);
                min = Math.min(range[0], min);
                max = Math.max(range[1], max);
                if(max - min > tolerance) {
                    return false;
                }
            }
        }
        return true;
    }

    // Slightly quicker way of estimating height. Should work fine most of the time with less performance impact
    public static int[] guessSurfaceHeightRange(Structure.GenerationContext context, ChunkPos chunkPos) {
        int x = chunkPos.getMinBlockX();
        int z = chunkPos.getMinBlockZ();
        int min = 256;
        int max = 0;
        for(int i = 0; i < 15; i+=3) {
            int height = context.chunkGenerator().getBaseHeight(x + i, z + i, Heightmap.Types.OCEAN_FLOOR_WG, context.heightAccessor(), context.randomState());
            min = Math.min(height, min);
            max = Math.max(height, max);
        }
        return new int[]{min, max};
    }
}
