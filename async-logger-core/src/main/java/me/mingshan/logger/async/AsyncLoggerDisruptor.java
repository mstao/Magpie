package me.mingshan.logger.async;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import me.mingshan.logger.async.util.DisruptorUtil;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncLoggerDisruptor {
    private volatile Disruptor<RingBufferLogEvent> disruptor;
    private boolean isUseThreadLocals = false;

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

    public boolean isUseThreadLocals() {
        return isUseThreadLocals;
    }

}
