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

/**
 * 日志记录器，相当于生产者
 *
 * @author mingshan
 */
public class AsyncLogger<E> implements Logger<RingBufferLogEvent, E>, EventTranslatorOneArg<RingBufferLogEvent<E>, E> {
    private final AsyncLoggerDisruptor<E> loggerDisruptor;

    public AsyncLogger(AsyncLoggerDisruptor<E> loggerDisruptor) {
        this.loggerDisruptor = loggerDisruptor;
    }

    @Override
    public void logMessage(E message) {
        System.out.println("发布数据 -- " + message);
        logWithOneArgTranslator(message);
    }

    /**
     * 发布Event
     * @param message
     */
    private void logWithOneArgTranslator(E message) {
        // 使用{@link RingBuffer#tryPublishEvent} 会先尝试放入event，当RingBuffer会返回false，
        // 放入失败，此时需要进行处理
//        if (!this.loggerDisruptor.getDisruptor().getRingBuffer()
//                .tryPublishEvent(this, message)) {
//            handleRingBufferFull(message);
//        }
        this.loggerDisruptor.getDisruptor().getRingBuffer().publishEvent(this, message);
    }

    /**
     * 处理队列满的情况
     * @param message
     */
    private void handleRingBufferFull(E message) {
        // TODO
        System.out.println("队列满了--- " + message);
    }

    @Override
    public void translateTo(RingBufferLogEvent<E> event, long sequence, E arg) {
        // 封装数据
        Thread currentThread = Thread.currentThread();
        event.setValues(arg, currentThread.getId(), currentThread.getName());
    }
}
