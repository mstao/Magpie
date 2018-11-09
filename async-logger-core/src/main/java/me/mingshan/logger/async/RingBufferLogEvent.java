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

import me.mingshan.logger.async.api.LogEvent;

/**
 * 事件
 *
 * @author mingshan
 */
public class RingBufferLogEvent implements LogEvent {
    private long threadId;
    private String threadName;
    private byte[] message;
    private boolean endOfBatch;

    @Override
    public byte[] getMessage() {
        return this.message;
    }

    @Override
    public String getThreadName() {
        return this.threadName;
    }

    @Override
    public long getThreadId() {
        return this.threadId;
    }

    @Override
    public boolean isEndOfBatch() {
        return this.isEndOfBatch();
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
