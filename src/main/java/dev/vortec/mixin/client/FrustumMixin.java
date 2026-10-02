package dev.vortec.mixin.client;

import net.minecraft.client.renderer.culling.Frustum;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Frustum.class)
public abstract class FrustumMixin {

    @Shadow @Final private Vector4f[] intersection;

    @Inject(method = "cubeInFrustum(FFFFFF)Z", at = @At("HEAD"), cancellable = true)
    private void vortec$unrolledFastFrustum(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, CallbackInfoReturnable<Boolean> cir) {
        Vector4f[] planes = this.intersection;

        // Plane 0 (Left)
        Vector4f p0 = planes[0];
        if (p0.x * (p0.x > 0.0F ? maxX : minX) + p0.y * (p0.y > 0.0F ? maxY : minY) + p0.z * (p0.z > 0.0F ? maxZ : minZ) <= -p0.w) {
            cir.setReturnValue(false);
            return;
        }

        // Plane 1 (Right)
        Vector4f p1 = planes[1];
        if (p1.x * (p1.x > 0.0F ? maxX : minX) + p1.y * (p1.y > 0.0F ? maxY : minY) + p1.z * (p1.z > 0.0F ? maxZ : minZ) <= -p1.w) {
            cir.setReturnValue(false);
            return;
        }

        // Plane 2 (Bottom)
        Vector4f p2 = planes[2];
        if (p2.x * (p2.x > 0.0F ? maxX : minX) + p2.y * (p2.y > 0.0F ? maxY : minY) + p2.z * (p2.z > 0.0F ? maxZ : minZ) <= -p2.w) {
            cir.setReturnValue(false);
            return;
        }

        // Plane 3 (Top)
        Vector4f p3 = planes[3];
        if (p3.x * (p3.x > 0.0F ? maxX : minX) + p3.y * (p3.y > 0.0F ? maxY : minY) + p3.z * (p3.z > 0.0F ? maxZ : minZ) <= -p3.w) {
            cir.setReturnValue(false);
            return;
        }

        // Plane 4 (Near)
        Vector4f p4 = planes[4];
        if (p4.x * (p4.x > 0.0F ? maxX : minX) + p4.y * (p4.y > 0.0F ? maxY : minY) + p4.z * (p4.z > 0.0F ? maxZ : minZ) <= -p4.w) {
            cir.setReturnValue(false);
            return;
        }

        // Plane 5 (Far)
        Vector4f p5 = planes[5];
        if (p5.x * (p5.x > 0.0F ? maxX : minX) + p5.y * (p5.y > 0.0F ? maxY : minY) + p5.z * (p5.z > 0.0F ? maxZ : minZ) <= -p5.w) {
            cir.setReturnValue(false);
            return;
        }

        cir.setReturnValue(true);
    }
}
