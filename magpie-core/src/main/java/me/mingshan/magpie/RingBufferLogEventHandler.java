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

import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceReportingEventHandler;
import me.mingshan.magpie.api.Export;
import me.mingshan.magpie.plugin.MagpiePlugins;
import me.mingshan.magpie.common.Constants;

import java.util.List;

/**
 * Handles the event that disruptor publish.
 *
 * @author mingshan
 */
public class RingBufferLogEventHandler implements
        SequenceReportingEventHandler<RingBufferLogEvent> {
    private Sequence sequenceCallback;
    private int batchCounter = Constants.NOTIFY_PROGRESS_THRESHOLD;

    @Override
    public void setSequenceCallback(Sequence sequenceCallback) {
        this.sequenceCallback = sequenceCallback;
    }

    @Override
    public void onEvent(RingBufferLogEvent event, long sequence, boolean endOfBatch) throws Exception {
        final boolean pseudoEndOfBatch = endOfBatch || --batchCounter == 0;

        // Do work...
        List<Export> logExports = MagpiePlugins.getInstance().getlogExports();
        for (Export logExport : logExports) {
            logExport.export(event.getMessage());
        }
        event.clear();

        // ----
        if (pseudoEndOfBatch) {
            batchCounter = Constants.NOTIFY_PROGRESS_THRESHOLD;
            sequenceCallback.set(sequence);
        }
    }
}
