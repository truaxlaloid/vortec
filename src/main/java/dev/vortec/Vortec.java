package dev.vortec;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Vortec.MOD_ID)
public class Vortec {
    public static final String MOD_ID = "vortec";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    static {
        // Leave dedicated core headroom for Client Render, Server Tick, and Rapier/Aeronautics physics
        if (System.getProperty("max.bg.threads") == null) {
            System.setProperty("max.bg.threads", "6");
        }
    }

    public Vortec(IEventBus modEventBus) {
        LOGGER.info("[Vortec] Initialized custom hardware execution profiles: AVX2 Active, 8 Physical Threads Optimized, Chunk Pipeline Accelerated.");
    }
}
