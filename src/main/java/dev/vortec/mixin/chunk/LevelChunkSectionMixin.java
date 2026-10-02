package dev.vortec.mixin.chunk;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelChunkSection.class)
public abstract class LevelChunkSectionMixin {

    @Shadow public abstract boolean hasOnlyAir();

    @Inject(method = "getBlockState", at = @At("HEAD"), cancellable = true)
    private void vortec$fastAirBlockState(int x, int y, int z, CallbackInfoReturnable<BlockState> cir) {
        if (this.hasOnlyAir()) {
            cir.setReturnValue(Blocks.AIR.defaultBlockState());
        }
    }
}
