package me.mingshan.logger.async;

import me.mingshan.logger.async.api.LogEvent;
import me.mingshan.logger.async.api.Message;

import java.util.Objects;

public class RingBufferLogEvent implements LogEvent {
    private long threadId;
    private String threadName;
    private Message message;
    private boolean endOfBatch;

    @Override
    public Message getMessage() {
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

    public void setValues(Message message, long threadId, String threadName) {
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
