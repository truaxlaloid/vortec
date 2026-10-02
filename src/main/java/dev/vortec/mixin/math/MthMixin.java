package dev.vortec.mixin.math;

import dev.vortec.math.FastMath;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Mth.class)
public abstract class MthMixin {

    /**
     * @author Vortec
     * @reason L1-cache aligned fast trigonometry table
     */
    @Overwrite
    public static float sin(float value) {
        return FastMath.sin(value);
    }

    /**
     * @author Vortec
     * @reason L1-cache aligned fast trigonometry table
     */
    @Overwrite
    public static float cos(float value) {
        return FastMath.cos(value);
    }

    /**
     * @author Vortec
     * @reason Compiles to hardware VROUNDSS instruction on i7-9700 AVX2
     */
    @Overwrite
    public static int floor(float value) {
        return (int) Math.floor(value);
    }

    /**
     * @author Vortec
     * @reason Compiles to hardware VROUNDSD instruction on i7-9700 AVX2
     */
    @Overwrite
    public static int floor(double value) {
        return (int) Math.floor(value);
    }

    /**
     * @author Vortec
     * @reason Compiles to hardware VROUNDSS instruction on i7-9700 AVX2
     */
    @Overwrite
    public static int ceil(float value) {
        return (int) Math.ceil(value);
    }

    /**
     * @author Vortec
     * @reason Compiles to hardware VROUNDSD instruction on i7-9700 AVX2
     */
    @Overwrite
    public static int ceil(double value) {
        return (int) Math.ceil(value);
    }
}
