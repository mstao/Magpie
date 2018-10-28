package me.mingshan.logger.async;

import com.lmax.disruptor.dsl.Disruptor;

public class AsyncLoggerDisruptor {
    private volatile Disruptor<RingBufferLogEvent> disruptor;
    private int ringBufferSize;
    private long backgroundThreadId;

    private void start() {

    }
}
