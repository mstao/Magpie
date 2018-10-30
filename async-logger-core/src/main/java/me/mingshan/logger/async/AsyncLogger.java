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

import com.lmax.disruptor.EventTranslatorVararg;
import me.mingshan.logger.async.api.Logger;
import me.mingshan.logger.async.api.Message;
import me.mingshan.logger.async.common.Constants;

/**
 * 日志记录器，相当于生产者
 *
 * @author mingshan
 */
public class AsyncLogger implements Logger<RingBufferLogEvent>, EventTranslatorVararg<RingBufferLogEvent> {

    private final ThreadLocal<RingBufferLogEventTranslator> threadLocalTranslator = new ThreadLocal<>();
    private final AsyncLoggerDisruptor loggerDisruptor;

    public AsyncLogger(AsyncLoggerDisruptor loggerDisruptor) {
        this.loggerDisruptor = loggerDisruptor;
    }

    @Override
    public void logMessage(Message message) {
        if (Constants.ENABLE_THREADLOCALS) {
            logWithThreadLocalTranslator(message);
        } else {
            logWithVarargTranslator(message);
        }
    }

    private void logWithThreadLocalTranslator(Message message) {
        final RingBufferLogEventTranslator translator = getCachedTranslator();
        initTranslator(translator, message);
        publish(translator);
    }

    private void publish(RingBufferLogEventTranslator translator) {
        if (!loggerDisruptor.tryPublish(translator)) {
            // TODO
        }
    }

    private RingBufferLogEventTranslator getCachedTranslator() {
        RingBufferLogEventTranslator result = threadLocalTranslator.get();
        if (result == null) {
            result = new RingBufferLogEventTranslator();
            threadLocalTranslator.set(result);
        }
        return result;
    }

    private void initTranslator(RingBufferLogEventTranslator translator, Message message) {
        Thread currentThread = Thread.currentThread();
        translator.setValues(message, currentThread.getId(), currentThread.getName());
    }

    private void logWithVarargTranslator(Message message) {
        System.out.println("Message ->" + message.getServiceName());
        if (!this.loggerDisruptor.getDisruptor().getRingBuffer()
                .tryPublishEvent(this, message)) {
            handleRingBufferFull(message);
        }
    }

    private void handleRingBufferFull(Message message) {
        // TODO
    }

    @Override
    public void translateTo(RingBufferLogEvent event, long sequence, Object... args) {
        // 封装数据
        Thread currentThread = Thread.currentThread();
        Message message = (Message) args[0];
        event.setValues(message, currentThread.getId(), currentThread.getName());
    }
}
