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
package me.mingshan.logger.async;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import me.mingshan.logger.async.common.Constants;
import me.mingshan.logger.async.util.DisruptorUtil;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 封装Disruptor，提供常用操作
 *
 * @author mingshan
 */
public class AsyncLoggerDisruptor {
    private volatile Disruptor<RingBufferLogEvent> disruptor;

    public Disruptor<RingBufferLogEvent> getDisruptor() {
        return disruptor;
    }

    public synchronized void start() {

        // 创建线程工厂
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "LoggerAsyncTask #" + mCount.getAndIncrement());
            }
        };

        int ringBufferSize = DisruptorUtil.calculateRingBufferSize();
        final WaitStrategy waitStrategy = DisruptorUtil.createWaitStrategy(DisruptorWaitStrategy.SLEEP);
        EventFactory factory = RingBufferLogEvent::new;
        disruptor = new Disruptor<RingBufferLogEvent>(factory,
                ringBufferSize, threadFactory, ProducerType.SINGLE, waitStrategy);

        final ExceptionHandler<RingBufferLogEvent> errorHandler = new AsyncLoggerConfigDefaultExceptionHandler();
        disruptor.setDefaultExceptionHandler(errorHandler);

        final SequenceReportingEventHandler[] handlers = {new RingBufferLogEventHandler()};
        disruptor.handleEventsWith(handlers);
        disruptor.start();
        System.out.println("Disruptor started");
    }

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
}
