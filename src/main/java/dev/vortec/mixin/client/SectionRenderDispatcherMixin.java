package dev.vortec.mixin.client;

import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SectionRenderDispatcher.class)
public abstract class SectionRenderDispatcherMixin {

    @ModifyVariable(
        method = "<init>",
        at = @At("HEAD"),
        argsOnly = true,
        ordinal = 0
    )
    private static int vortec$tuneWorkerThreadsForI79700(int count) {
        // Intel Core i7-9700 has 8 physical cores without SMT/HyperThreading.
        // We designate 6 threads exclusively for geometry baking, leaving 2 for Server/Render ticks & physics.
        return 6;
    }
}
