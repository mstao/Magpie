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

import com.lmax.disruptor.EventTranslatorOneArg;
import me.mingshan.magpie.api.Reader;
import me.mingshan.magpie.serialize.Serializer;
import me.mingshan.magpie.serialize.SerializerHolder;

/**
 * The implementation of logger.
 *
 * @author mingshan
 */
public class AsyncReader implements Reader, EventTranslatorOneArg<RingBufferLogEvent, byte[]> {
    private final MagpieDisruptor loggerDisruptor;

    public AsyncReader(MagpieDisruptor loggerDisruptor) {
        this.loggerDisruptor = loggerDisruptor;
    }

    /**
     * Log message.
     *
     * @param message the message
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
     * Deals with queue full cases.
     *
     * @param message the message
     */
    private void handleRingBufferFull(byte[] message) {
        // TODO
        System.out.println("队列满了--- " + message);
    }

    @Override
    public void translateTo(RingBufferLogEvent event, long sequence, byte[] arg) {
        Thread currentThread = Thread.currentThread();
        event.setValues(arg, currentThread.getId(), currentThread.getName());
    }

    @Override
    public <E> void read(E message) {
        Serializer serializer = SerializerHolder.serializerImpl();
        byte[] body = serializer.writeObject(message);
        read(body);
    }

    @Override
    public void read(byte[] message) {
        logWithOneArgTranslator(message);
    }
}
