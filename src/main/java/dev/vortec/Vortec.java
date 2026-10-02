package dev.vortec;

import dev.vortec.azdo.PersistentMappedPool;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Vortec.MOD_ID)
public class Vortec {
    public static final String MOD_ID = "vortec";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    static {
        // Dedicate 6 cores to background loading/compilation, preventing starvation of
        // the Server thread, Client render loop, and Sable's Rapier physics simulation.
        if (System.getProperty("max.bg.threads") == null) {
            System.setProperty("max.bg.threads", "6");
        }
    }

    public Vortec(IEventBus modEventBus) {
        LOGGER.info("[Vortec] Initializing AZDO & SIMD Engine tailored for i7-9700 + RDNA 2.");
        modEventBus.addListener(this::onClientSetup);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            PersistentMappedPool.init();
            LOGGER.info("[Vortec] AZDO Persistent Coherent Staging Pool initialized successfully.");
        });
    }
}
