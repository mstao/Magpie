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
package me.mingshan.magpie;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import me.mingshan.magpie.util.DisruptorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Encapsulates the Disruptor, providing common operations.
 *
 * @author mingshan
 */
public class MagpieDisruptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MagpieDisruptor.class);
    private volatile Disruptor<RingBufferLogEvent> disruptor;

    public Disruptor<RingBufferLogEvent> getDisruptor() {
        return disruptor;
    }

    /**
     * Start Disruptor
     */
    public synchronized void start() {
        if (disruptor != null) {
            return;
        }

        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "MagpieAsyncTask #" + mCount.getAndIncrement());
            }
        };

        int ringBufferSize = DisruptorUtil.calculateRingBufferSize();
        final WaitStrategy waitStrategy = DisruptorUtil.createWaitStrategy(DisruptorWaitStrategy.TIMEOUT);
        EventFactory factory = RingBufferLogEvent::new;
        disruptor = new Disruptor<RingBufferLogEvent>(factory,
                ringBufferSize, threadFactory, ProducerType.SINGLE, waitStrategy);

        final ExceptionHandler<RingBufferLogEvent> errorHandler = new DefaultExceptionHandler();
        disruptor.setDefaultExceptionHandler(errorHandler);

        final EventHandler[] handlers = {new RingBufferLogEventHandler()};
        disruptor.handleEventsWith(handlers);
        disruptor.start();
        LOGGER.info("Disruptor started");
    }

    /**
     * Close Disruptor
     */
    public void stop() {
        disruptor.shutdown();
    }

    public boolean tryPublish(RingBufferLogEventTranslator translator) {
        try {
            return disruptor.getRingBuffer().tryPublishEvent(translator);
        } catch (final NullPointerException npe) {
            return false;
        }
    }

    /**
     * Checks the status of disruptor.
     *
     * @return returns {@code true}, the disruptor is started, returns {@code false},
     * the disruptor
     */
    public boolean checkDisruptorStatus() {
        if (disruptor == null) {
            return false;
        }

        return true;
    }
}
