package me.mingshan.logger.async;

import me.mingshan.logger.async.api.Logger;

public class AsyncLogger<E> implements Logger<E> {

    @Override
    public void write(E event) {

    }

    @Override
    public void write(E event, boolean endOfBatch) {

    }

    @Override
    public void close() {

    }
}
