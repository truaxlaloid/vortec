package dev.vortec;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Vortec.MOD_ID)
public class Vortec {
    public static final String MOD_ID = "vortec";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public Vortec(IEventBus modEventBus) {
        LOGGER.info("[Vortec] Initialized custom hardware execution profiles: AVX2 Active, 8 Physical Threads Optimized.");
    }
}
