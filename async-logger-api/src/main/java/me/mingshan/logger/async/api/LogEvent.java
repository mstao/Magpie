package me.mingshan.logger.async.api;

import java.io.Serializable;

public interface LogEvent extends Serializable {
    Message getMessage();
    String getThreadName();
    long getThreadId();
    boolean isEndOfBatch();
}
