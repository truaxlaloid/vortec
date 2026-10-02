package dev.vortec.mixin.math;

import dev.vortec.math.FastMath;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Mth.class)
public abstract class MthMixin {

    /**
     * @author Vortec
     * @reason L1-cache aligned fast trigonometry table for physics and kinematics
     */
    @Overwrite
    public static float sin(float value) {
        return FastMath.sin(value);
    }

    /**
     * @author Vortec
     * @reason L1-cache aligned fast trigonometry table for physics and kinematics
     */
    @Overwrite
    public static float cos(float value) {
        return FastMath.cos(value);
    }
}
