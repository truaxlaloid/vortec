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
}
