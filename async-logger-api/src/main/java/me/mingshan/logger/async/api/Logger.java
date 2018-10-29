package me.mingshan.logger.async.api;

import java.util.logging.Level;

public interface Logger<E extends LogEvent> {
    void logMessage(Message message);
}
