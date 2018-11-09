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
import me.mingshan.logger.async.serialize.Serializer;
import me.mingshan.logger.async.serialize.SerializerHolder;

/**
 * The implementation of logger.
 *
 * @author mingshan
 */
public class AsyncLogger implements Logger, EventTranslatorOneArg<RingBufferLogEvent, byte[]> {
    private final AsyncLoggerDisruptor loggerDisruptor;

    public AsyncLogger(AsyncLoggerDisruptor loggerDisruptor) {
        this.loggerDisruptor = loggerDisruptor;
    }

    /**
     * 发布Event
     * @param message
     */
    private void logWithOneArgTranslator(byte[] message) {
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
    private void handleRingBufferFull(byte[] message) {
        // TODO
        System.out.println("队列满了--- " + message);
    }

    @Override
    public void translateTo(RingBufferLogEvent event, long sequence, byte[] arg) {
        // 封装数据
        Thread currentThread = Thread.currentThread();
        event.setValues(arg, currentThread.getId(), currentThread.getName());
    }

    @Override
    public <E> void logMessage(E message) {
        Serializer serializer = SerializerHolder.serializerImpl();
        byte[] body = serializer.writeObject(message);
        logMessage(body);
    }

    @Override
    public void logMessage(byte[] message) {
        logWithOneArgTranslator(message);
    }
}
