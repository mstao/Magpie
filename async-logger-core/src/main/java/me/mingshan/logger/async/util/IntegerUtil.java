package me.mingshan.logger.async.util;

public class IntegerUtil {
    private static final int BITS_PER_INT = 32;

    private IntegerUtil() {
    }

    public static int ceilingNextPowerOfTwo(final int x) {
        return 1 << (BITS_PER_INT - Integer.numberOfLeadingZeros(x - 1));
    }
}
