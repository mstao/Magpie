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
