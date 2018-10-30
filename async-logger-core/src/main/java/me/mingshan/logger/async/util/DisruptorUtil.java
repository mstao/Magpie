/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.mingshan.logger.async.util;

import com.lmax.disruptor.*;
import me.mingshan.logger.async.common.Constants;
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
