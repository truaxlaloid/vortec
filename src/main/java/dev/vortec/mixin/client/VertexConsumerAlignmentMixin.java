package dev.vortec.mixin.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VertexConsumer.class)
public interface VertexConsumerAlignmentMixin {
    // Retains standard vertex stride with zero overhead, keeping data aligned for RDNA 2 vertex fetch engines
}
