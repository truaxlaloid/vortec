package dev.vortec.mixin.physics;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Shapes.class)
public abstract class ShapesMixin {

    @Inject(method = "joinIsNotEmpty", at = @At("HEAD"), cancellable = true)
    private static void vortec$earlyAABBCollisionReject(VoxelShape shape1, VoxelShape shape2, BooleanOp op, CallbackInfoReturnable<Boolean> cir) {
        if (op == BooleanOp.AND) {
            // If either shape is empty, or outer bounding boxes don't intersect, voxels cannot collide
            if (shape1.isEmpty() || shape2.isEmpty() || !shape1.bounds().intersects(shape2.bounds())) {
                cir.setReturnValue(false);
            }
        }
    }
}
