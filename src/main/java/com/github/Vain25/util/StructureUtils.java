package com.github.Vain25.util;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;

public final class StructureUtils {

    private StructureUtils(){}

    public static boolean isChunkAreaFlat(Structure.GenerationContext context, int chunkRadius, int tolerance) {
        ChunkPos origin = context.chunkPos();
        int min = -1;
        int max = -1;
        for(int i = 0; i < chunkRadius; i++) {
            ChunkPos c = new ChunkPos(origin.x + i, origin.z + i);
            min = Math.min(guessSurfaceHeightRange(context, c), min);
            max = Math.max(guessSurfaceHeightRange(context, c), max);
            if(max - min > tolerance) {
                return false;
            }
        }
        for(int i = 0; i < chunkRadius; i++) {
            ChunkPos c = new ChunkPos(origin.x + chunkRadius - 1 - i, origin.z + i);
            min = Math.min(guessSurfaceHeightRange(context, c), min);
            max = Math.max(guessSurfaceHeightRange(context, c), max);
            if(max - min > tolerance) {
                return false;
            }
        }
        return true;
    }

    public static boolean isChunkFlat(Structure.GenerationContext context, int tolerance) {
        return isChunkFlat(context, context.chunkPos(), tolerance);
    }

    public static boolean isChunkFlat(Structure.GenerationContext context, ChunkPos chunkPos, int tolerance) {
        return guessSurfaceHeightRange(context, chunkPos) <= tolerance;
    }

    // Slightly quicker way of estimating height. Should work fine most of the time with less performance impact
    public static int guessSurfaceHeightRange(Structure.GenerationContext context, ChunkPos chunkPos) {
        int x = chunkPos.getMinBlockX();
        int z = chunkPos.getMinBlockZ();
        int min = -1;
        int max = -1;
        for(int i = 0; i < 15; i++) {
            int height = context.chunkGenerator().getBaseHeight(x + i, z + i, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
            min = Math.min(height, min);
            max = Math.max(height, max);
        }
        for(int i = 0; i < 15; i++) {
            int height = context.chunkGenerator().getBaseHeight(x + 15 - i, z + i, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
            min = Math.min(height, min);
            max = Math.max(height, max);
        }
        return max - min;
    }
}
