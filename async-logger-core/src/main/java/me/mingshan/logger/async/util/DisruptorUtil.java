package me.mingshan.logger.async.util;

import com.lmax.disruptor.*;
import me.mingshan.logger.async.Constants;
import me.mingshan.logger.async.DisruptorWaitStrategy;

import java.util.concurrent.TimeUnit;

public class DisruptorUtil {

    static WaitStrategy createWaitStrategy(DisruptorWaitStrategy strategyUp) {
        switch (strategyUp.toString()) {
            case "SLEEP":
                return new SleepingWaitStrategy();
            case "YIELD":
                return new YieldingWaitStrategy();
            case "BLOCK":
                return new BlockingWaitStrategy();
            case "BUSYSPIN":
                return new BusySpinWaitStrategy();
            case "TIMEOUT":
                return new TimeoutBlockingWaitStrategy(timeoutMillis, TimeUnit.MILLISECONDS);
            default:
                return new TimeoutBlockingWaitStrategy(timeoutMillis, TimeUnit.MILLISECONDS);
    }

    static int calculateRingBufferSize(final String propertyName) {
        int ringBufferSize = Constants.ENABLE_THREADLOCALS ? RINGBUFFER_NO_GC_DEFAULT_SIZE : RINGBUFFER_DEFAULT_SIZE;
        final String userPreferredRBSize = PropertiesUtil.getProperties().getStringProperty(propertyName,
                String.valueOf(ringBufferSize));
        try {
            int size = Integer.parseInt(userPreferredRBSize);
            if (size < RINGBUFFER_MIN_SIZE) {
                size = RINGBUFFER_MIN_SIZE;
                LOGGER.warn("Invalid RingBufferSize {}, using minimum size {}.", userPreferredRBSize,
                        RINGBUFFER_MIN_SIZE);
            }
            ringBufferSize = size;
        } catch (final Exception ex) {

        }
        return Integers.ceilingNextPowerOfTwo(ringBufferSize);
    }

}
