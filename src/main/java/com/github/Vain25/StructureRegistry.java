package com.github.Vain25;

import com.github.Vain25.structures.UkSub;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class StructureRegistry {

    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, BetterCities.MOD_ID);

    public static final RegistryObject<StructureType<UkSub>> UK_SUB = STRUCTURES.register("uk_sub", () -> explicitStructureTypeTyping(UkSub.CODEC));

    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(Codec<T> structureCodec) {
        return () -> structureCodec;
    }

}

