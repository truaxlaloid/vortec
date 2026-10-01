package dev.vortec.math;

public final class FastMath {
    private static final int TABLE_SIZE = 65536;
    private static final int MASK = TABLE_SIZE - 1;
    private static final float RAD_TO_INDEX = TABLE_SIZE / ((float) Math.PI * 2.0F);
    private static final float[] SIN_TABLE = new float[TABLE_SIZE];

    static {
        for (int i = 0; i < TABLE_SIZE; ++i) {
            SIN_TABLE[i] = (float) Math.sin((double) i * Math.PI * 2.0 / (double) TABLE_SIZE);
        }
    }

    private FastMath() {}

    public static float sin(float rad) {
        int index = (int) (rad * RAD_TO_INDEX) & MASK;
        return SIN_TABLE[index];
    }

    public static float cos(float rad) {
        int index = (int) (rad * RAD_TO_INDEX + (TABLE_SIZE / 4)) & MASK;
        return SIN_TABLE[index];
    }
}
