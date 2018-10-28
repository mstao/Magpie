package me.mingshan.logger.async.api;

public interface Logger<E> {

    void write(E event);

    void write(E event, boolean endOfBatch);

    void close();
}
