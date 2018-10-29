package me.mingshan.logger.async;

/**
 * Alternative Wait Strategies
 *
 */
public enum DisruptorWaitStrategy {

    /**
     * Like the BlockingWaitStrategy the SleepingWaitStrategy it attempts to be conservative with CPU usage, by using a simple busy wait loop,
     * but uses a call to LockSupport.parkNanos(1) in the middle of the loop.
     */
    SLEEP("SleepingWaitStrategy"),

    /**
     *
     */
    YIELD("YieldingWaitStrategy"),

    /**
     *
     */
    BLOCK("BlockingWaitStrategy"),

    /**
     *
     */
    BUSYSPIN("BusySpinWaitStrategy"),

    /**
     *
     */
    TIMEOUT("TimeoutBlockingWaitStrategy");

    private String desc;
    DisruptorWaitStrategy(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}

