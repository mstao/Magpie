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

import com.lmax.disruptor.EventTranslatorOneArg;
import me.mingshan.logger.async.api.Logger;
import me.mingshan.logger.async.common.Constants;

/**
 * 日志记录器，相当于生产者
 *
 * @author mingshan
 */
public class AsyncLogger<E> implements Logger<RingBufferLogEvent, E>, EventTranslatorOneArg<RingBufferLogEvent<E>, E> {

    private final ThreadLocal<RingBufferLogEventTranslator<E>> threadLocalTranslator = new ThreadLocal<>();
    private final AsyncLoggerDisruptor<E> loggerDisruptor;

    public AsyncLogger(AsyncLoggerDisruptor<E> loggerDisruptor) {
        this.loggerDisruptor = loggerDisruptor;
    }

    @Override
    public void logMessage(E message) {
        if (Constants.ENABLE_THREADLOCALS) {
            logWithThreadLocalTranslator(message);
        } else {
            logWithVarargTranslator(message);
        }
    }

    private void logWithThreadLocalTranslator(E message) {
        final RingBufferLogEventTranslator<E> translator = getCachedTranslator();
        initTranslator(translator, message);
        publish(translator);
    }

    private void publish(RingBufferLogEventTranslator<E> translator) {
        if (!loggerDisruptor.tryPublish(translator)) {
            // TODO
        }
    }

    private RingBufferLogEventTranslator<E> getCachedTranslator() {
        RingBufferLogEventTranslator<E> result = threadLocalTranslator.get();
        if (result == null) {
            result = new RingBufferLogEventTranslator<>();
            threadLocalTranslator.set(result);
        }
        return result;
    }

    private void initTranslator(RingBufferLogEventTranslator<E> translator, E message) {
        Thread currentThread = Thread.currentThread();
        translator.setValues(message, currentThread.getId(), currentThread.getName());
    }

    private void logWithVarargTranslator(E message) {
        if (!this.loggerDisruptor.getDisruptor().getRingBuffer()
                .tryPublishEvent(this, message)) {
            handleRingBufferFull(message);
        }
    }

    private void handleRingBufferFull(E message) {
        // TODO
    }


    @Override
    public void translateTo(RingBufferLogEvent<E> event, long sequence, E arg) {
        // 封装数据
        Thread currentThread = Thread.currentThread();
        event.setValues(arg, currentThread.getId(), currentThread.getName());
    }

    public void actualAsyncLog(final RingBufferLogEvent event) {

    }
}
