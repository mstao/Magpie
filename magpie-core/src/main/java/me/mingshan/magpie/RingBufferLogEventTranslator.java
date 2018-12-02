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

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;

/**
 * Implementations translate (write) data representations into events claimed from the {@link RingBuffer}.
 *
 * @author mingshan
 */
public class RingBufferLogEventTranslator implements EventTranslator<RingBufferLogEvent> {
    private byte[] message;
    private long threadId;
    private String threadName;

    @Override
    public void translateTo(RingBufferLogEvent event, long sequence) {
        event.setValues(message, threadId, threadName);
    }

    public void setValues(byte[] message, long threadId, String threadName) {
        this.message = message;
        this.threadId = threadId;
        this.threadName = threadName;
    }

    public void clear() {
        this.message = null;
        this.threadName = null;
        this.threadId = 0L;
    }
}
