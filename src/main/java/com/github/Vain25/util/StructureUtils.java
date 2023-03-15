package com.github.Vain25.util;

import com.github.Vain25.BetterCities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;

public final class StructureUtils {

    private StructureUtils(){}

    public static boolean isChunkAreaFlat(Structure.GenerationContext context, int chunkRadius, int tolerance) {
        ChunkPos origin = context.chunkPos();
        int min = -1;
        int max = -1;
        for(int i = -chunkRadius; i < chunkRadius; i+=3) {
            for(int j = -chunkRadius; j < chunkRadius; j+=3) {
                ChunkPos chunkPos = new ChunkPos(origin.x + i, origin.z + j);
                int[] range = guessSurfaceHeightRange(context, chunkPos);
                min = Math.min(range[0], min);
                max = Math.min(range[1], max);
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
        int min = -1;
        int max = -1;
        for(int i = 0; i < 15; i+=2) {
            int height = context.chunkGenerator().getBaseHeight(x + i, z + i, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
            min = Math.min(height, min);
            max = Math.max(height, max);

        }
        for(int i = 0; i < 15; i+=2) {
            int height = context.chunkGenerator().getBaseHeight(x + 15 - i, z + i, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
            min = Math.min(height, min);
            max = Math.max(height, max);
        }
        return new int[]{min, max};
    }
}
