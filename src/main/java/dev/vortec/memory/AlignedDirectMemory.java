package dev.vortec.memory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class AlignedDirectMemory {
    public static final int CACHE_LINE_SIZE = 64;

    private AlignedDirectMemory() {}

    public static ByteBuffer allocateAligned(int capacity) {
        // Over-allocate by 64 bytes to guarantee a 64-byte aligned boundary
        ByteBuffer raw = ByteBuffer.allocateDirect(capacity + CACHE_LINE_SIZE).order(ByteOrder.nativeOrder());
        long address = getDirectBufferAddress(raw);
        int offset = (int) ((CACHE_LINE_SIZE - (address & (CACHE_LINE_SIZE - 1))) & (CACHE_LINE_SIZE - 1));
        raw.position(offset);
        raw.limit(offset + capacity);
        return raw.slice().order(ByteOrder.nativeOrder());
    }

    private static long getDirectBufferAddress(ByteBuffer buffer) {
        try {
            var addressField = java.nio.Buffer.class.getDeclaredField("address");
            addressField.setAccessible(true);
            return addressField.getLong(buffer);
        } catch (Exception e) {
            return 0L;
        }
    }
}
