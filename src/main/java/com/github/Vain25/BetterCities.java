package com.github.Vain25;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(BetterCities.MOD_ID)
public class BetterCities {
    public static final String MOD_ID = "better_cities";

    public static final Logger LOGGER = LogUtils.getLogger();

    public BetterCities() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        StructureRegistry.STRUCTURES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

}
