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

import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceReportingEventHandler;

public class RingBufferLogEventHandler implements
        SequenceReportingEventHandler<RingBufferLogEvent> {
    private static final int NOTIFY_PROGRESS_THRESHOLD = 50;
    private Sequence sequenceCallback;
    private int counter;

    @Override
    public void setSequenceCallback(Sequence sequenceCallback) {
        this.sequenceCallback = sequenceCallback;
    }

    @Override
    public void onEvent(RingBufferLogEvent event, long sequence, boolean endOfBatch) throws Exception {
        // 日志处理逻辑
        System.out.println("Event - " + event.getMessage() + ", Sequence - " + sequence);
        event.clear();

        if (++counter > NOTIFY_PROGRESS_THRESHOLD) {
            sequenceCallback.set(sequence);
            counter = 0;
        }
    }
}
