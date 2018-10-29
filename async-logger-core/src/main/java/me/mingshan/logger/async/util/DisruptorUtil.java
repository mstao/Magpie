package me.mingshan.logger.async.util;

import com.lmax.disruptor.*;
import me.mingshan.logger.async.Constants;
import me.mingshan.logger.async.DisruptorWaitStrategy;
import me.mingshan.logger.async.RingBufferLogEvent;

import java.util.concurrent.TimeUnit;

public class DisruptorUtil {

    public static WaitStrategy createWaitStrategy(DisruptorWaitStrategy strategyUp) {
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
                return new TimeoutBlockingWaitStrategy(Constants.BLOCKING_WAIT_STRAGETY_TIMEOUT_MILLIS,
                        TimeUnit.MILLISECONDS);
            default:
                return new TimeoutBlockingWaitStrategy(Constants.BLOCKING_WAIT_STRAGETY_TIMEOUT_MILLIS,
                        TimeUnit.MILLISECONDS);
        }
    }

    public static int calculateRingBufferSize() {
        int ringBufferSize = Constants.ENABLE_THREADLOCALS ? Constants.RINGBUFFER_NO_GC_DEFAULT_SIZE
                : Constants.RINGBUFFER_DEFAULT_SIZE;
        return IntegerUtil.ceilingNextPowerOfTwo(ringBufferSize);
    }

    public static ExceptionHandler<RingBufferLogEvent> getAsyncLoggerExceptionHandler() {
        return null;
    }
}
