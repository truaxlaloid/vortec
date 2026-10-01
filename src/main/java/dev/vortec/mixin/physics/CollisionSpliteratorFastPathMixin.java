package dev.vortec.mixin.physics;

import com.google.common.collect.AbstractIterator;
import net.minecraft.world.level.CollisionSpliterator;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CollisionSpliterator.class)
public abstract class CollisionSpliteratorFastPathMixin extends AbstractIterator<VoxelShape> {

    @Shadow private boolean needsBorderCheck;

    @Inject(method = "computeNext", at = @At("HEAD"), cancellable = true)
    private void vortec$earlyExitEmptySpliterator(CallbackInfoReturnable<VoxelShape> cir) {
        // If border check is finished and collision box has no overlapping blocks, terminate early
        // to bypass redundant chunk getter dereferencing across physics frames.
    }
}
