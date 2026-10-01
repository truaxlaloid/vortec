package dev.vortec.mixin.physics;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VoxelShape.class)
public abstract class VoxelShapeCacheMixin {

    @Shadow public abstract AABB bounds();

    @Unique private AABB vortec$cachedBounds = null;

    @Inject(method = "bounds", at = @At("HEAD"), cancellable = true)
    private void vortec$cacheBounds(CallbackInfoReturnable<AABB> cir) {
        if (this.vortec$cachedBounds != null) {
            cir.setReturnValue(this.vortec$cachedBounds);
            return;
        }
        // Cache initial lookup
        this.vortec$cachedBounds = this.bounds();
        cir.setReturnValue(this.vortec$cachedBounds);
    }
}
