package me.mingshan.logger.async.api;

public interface LogExport<E> {
    void export(E e);
}
