package me.mingshan.logger.async;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorVararg;
import me.mingshan.logger.async.api.Message;

public class RingBufferLogEventTranslator implements EventTranslatorVararg<RingBufferLogEvent> {

    @Override
    public void translateTo(RingBufferLogEvent event, long sequence, Object... args) {
        // 封装数据
        final Thread currentThread = Thread.currentThread();
        final Message message = (Message) args[1];

        event.setValues(message, currentThread.getId(), currentThread.getName());
    }
}
