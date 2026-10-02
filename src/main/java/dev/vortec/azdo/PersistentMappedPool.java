package dev.vortec.azdo;

import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL15C;
import org.lwjgl.opengl.GL30C;
import org.lwjgl.opengl.GL44C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public final class PersistentMappedPool {
    private static final Logger LOGGER = LoggerFactory.getLogger("Vortec-AZDO");

    // 64 MB coherent staging ring buffer aligned to 64-byte boundaries
    public static final int POOL_SIZE = 64 * 1024 * 1024;
    public static final int ALIGNMENT = 64;

    private static int bufferId = 0;
    private static ByteBuffer mappedAddress = null;
    private static int writeHead = 0;
    private static boolean supported = false;

    private PersistentMappedPool() {}

    public static void init() {
        try {
            bufferId = GL15C.glGenBuffers();
            GL15C.glBindBuffer(GL15C.GL_ARRAY_BUFFER, bufferId);

            int flags = GL44C.GL_MAP_WRITE_BIT | GL44C.GL_MAP_PERSISTENT_BIT | GL44C.GL_MAP_COHERENT_BIT;
            GL44C.glBufferStorage(GL15C.GL_ARRAY_BUFFER, POOL_SIZE, flags);

            mappedAddress = GL30C.glMapBufferRange(GL15C.GL_ARRAY_BUFFER, 0, POOL_SIZE, flags);
            GL15C.glBindBuffer(GL15C.GL_ARRAY_BUFFER, 0);

            if (mappedAddress != null) {
                supported = true;
                LOGGER.info("Successfully mapped 64MB Persistent Coherent Buffer on RDNA 2 device.");
            }
        } catch (Throwable t) {
            LOGGER.warn("Persistent mapping unavailable, falling back to standard staging.", t);
            supported = false;
        }
    }

    public static synchronized int allocate(int bytes) {
        if (!supported) return -1;

        // Align to 64 bytes (i7-9700 cache-line & RX 6600M memory burst)
        int aligned = (bytes + (ALIGNMENT - 1)) & ~(ALIGNMENT - 1);
        if (writeHead + aligned > POOL_SIZE) {
            writeHead = 0; // Wrap ring buffer
        }

        int offset = writeHead;
        writeHead += aligned;
        return offset;
    }

    public static ByteBuffer getBuffer() {
        return mappedAddress;
    }

    public static boolean isSupported() {
        return supported;
    }

    public static int getBufferId() {
        return bufferId;
    }
}
