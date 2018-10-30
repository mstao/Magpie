package me.mingshan.logger.async.common;

import me.mingshan.logger.async.api.LogExport;

import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author mingshan
 */
public class AsyncLoggerPlugins<E> {
    private final AtomicReference<LogExport<E>> logExport = new AtomicReference<>();

    private static class AsyncLoggerPluginsHolder {
        private static final AsyncLoggerPlugins instance = new AsyncLoggerPlugins();
    }

    public static AsyncLoggerPlugins getInstance() {
        return AsyncLoggerPluginsHolder.instance;
    }

    public LogExport<E> getlogExport() {
        if (logExport.get() == null) {
            Object impl = getPluginImplementation(LogExport.class);
            logExport.compareAndSet(null, (LogExport<E>) impl);
        }

        return logExport.get();
    }

    public void registerLogExport(LogExport impl) {
        if (!logExport.compareAndSet(null, impl)) {
            throw new IllegalStateException("Another LogExport was already registered.");
        }
    }

    private <T> T getPluginImplementation(Class<?> clazz) {
        return (T) findClass(clazz);
    }

    private <T> T findClass(Class<T> clazz) {
        // SPI
        ServiceLoader<T> serviceLoader =  ServiceLoader.load(clazz);
        for (T t : serviceLoader) {
            if (t != null) {
                return t;
            }
        }

        // 如果没有提供实现，则使用默认实现
        T result = null;
        ClassLoader classLoader = AsyncLoggerPlugins.class.getClassLoader();
        try {
            result = (T) classLoader.loadClass(Constants.DEFAULT_LOG_EXPORT_IMPL);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not find ....");
            e.printStackTrace();
        }
        return result;
    }

}
