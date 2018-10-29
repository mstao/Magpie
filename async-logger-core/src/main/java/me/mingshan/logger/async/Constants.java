package me.mingshan.logger.async;

public class Constants {
    /*** RINGBUFFER ***/
    public static final int RINGBUFFER_MIN_SIZE = 128;
    public static final int RINGBUFFER_DEFAULT_SIZE = 256 * 1024;
    public static final int RINGBUFFER_NO_GC_DEFAULT_SIZE = 4 * 1024;

    public static final long BLOCKING_WAIT_STRAGETY_TIMEOUT_MILLIS = 123L;
    public static final boolean ENABLE_THREADLOCALS = true;

}
