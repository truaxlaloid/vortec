package dev.vortec.mixin.client;

import com.mojang.blaze3d.platform.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GlStateManager.class, remap = false)
public abstract class GlStateManagerMixin {

    @Unique private static boolean vortec$depthMaskState = true;
    @Unique private static boolean vortec$cullState = true;

    @Inject(method = "_depthMask", at = @At("HEAD"), cancellable = true)
    private static void vortec$filterRedundantDepthMask(boolean flag, CallbackInfo ci) {
        if (vortec$depthMaskState == flag) {
            ci.cancel();
            return;
        }
        vortec$depthMaskState = flag;
    }

    @Inject(method = "_enableCull", at = @At("HEAD"), cancellable = true)
    private static void vortec$filterRedundantEnableCull(CallbackInfo ci) {
        if (vortec$cullState) {
            ci.cancel();
            return;
        }
        vortec$cullState = true;
    }

    @Inject(method = "_disableCull", at = @At("HEAD"), cancellable = true)
    private static void vortec$filterRedundantDisableCull(CallbackInfo ci) {
        if (!vortec$cullState) {
            ci.cancel();
            return;
        }
        vortec$cullState = false;
    }
}
