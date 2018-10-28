package me.mingshan.logger.async;

import me.mingshan.logger.async.api.LogEvent;
import me.mingshan.logger.async.api.Message;

public class RingBufferLogEvent implements LogEvent {
    private long threadId;
    private String threadName;
    private String loggerName;
    private Message message;

    @Override
    public Message getMessage() {
        return null;
    }

    @Override
    public String getThreadName() {
        return null;
    }

    @Override
    public long getThreadId() {
        return 0;
    }

    @Override
    public boolean isEndOfBatch() {
        return false;
    }
}
