package dev.vortec.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PoseStack.class)
public abstract class PoseStackMixin {

    @Inject(method = "mulPose(Lorg/joml/Quaternionf;)V", at = @At("HEAD"), cancellable = true)
    private void vortec$skipIdentityMulPose(Quaternionf quaternion, CallbackInfo ci) {
        if (quaternion.x == 0.0F && quaternion.y == 0.0F && quaternion.z == 0.0F && (quaternion.w == 1.0F || quaternion.w == -1.0F)) {
            ci.cancel();
        }
    }

    @Inject(method = "translate(FFF)V", at = @At("HEAD"), cancellable = true)
    private void vortec$skipZeroTranslateFloat(float x, float y, float z, CallbackInfo ci) {
        if (x == 0.0F && y == 0.0F && z == 0.0F) {
            ci.cancel();
        }
    }

    @Inject(method = "translate(DDD)V", at = @At("HEAD"), cancellable = true)
    private void vortec$skipZeroTranslateDouble(double x, double y, double z, CallbackInfo ci) {
        if (x == 0.0 && y == 0.0 && z == 0.0) {
            ci.cancel();
        }
    }

    @Inject(method = "scale", at = @At("HEAD"), cancellable = true)
    private void vortec$skipUnitScale(float x, float y, float z, CallbackInfo ci) {
        if (x == 1.0F && y == 1.0F && z == 1.0F) {
            ci.cancel();
        }
    }
}
