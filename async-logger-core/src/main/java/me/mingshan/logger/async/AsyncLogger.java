package me.mingshan.logger.async;

import com.lmax.disruptor.EventTranslatorVararg;
import me.mingshan.logger.async.api.Logger;
import me.mingshan.logger.async.api.Message;

/**
 * 日志记录器，相当于生产者
 */
public class AsyncLogger implements Logger<RingBufferLogEvent>, EventTranslatorVararg<RingBufferLogEvent> {

    private final ThreadLocal<RingBufferLogEvent> threadLocalTranslator = new ThreadLocal<>();
    private final AsyncLoggerDisruptor loggerDisruptor;

    public AsyncLogger(AsyncLoggerDisruptor loggerDisruptor) {
        this.loggerDisruptor = loggerDisruptor;
    }

    @Override
    public void logMessage(Message message) {
        if (loggerDisruptor.isUseThreadLocals()) {

        } else {
            logWithVarargTranslator(message);
        }
    }

    private void logWithVarargTranslator(Message message) {
        System.out.println("Message ->" + message.getServiceName());
        if (!this.loggerDisruptor.getDisruptor().getRingBuffer()
                .tryPublishEvent(this, message)) {
            handleRingBufferFull(message);
        }
    }

    private void handleRingBufferFull(Message message) {

    }

    @Override
    public void translateTo(RingBufferLogEvent event, long sequence, Object... args) {
        // 封装数据
        Thread currentThread = Thread.currentThread();
        Message message = (Message) args[0];
        event.setValues(message, currentThread.getId(), currentThread.getName());
    }
}
