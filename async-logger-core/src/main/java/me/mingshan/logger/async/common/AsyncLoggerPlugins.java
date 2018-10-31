package me.mingshan.logger.async.common;

import me.mingshan.logger.async.api.LogExport;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 异步日志相关插件
 *
 * @author mingshan
 */
public class AsyncLoggerPlugins<E> {
    private final AtomicReference<List<LogExport<E>>> logExports = new AtomicReference<>();

    private static class AsyncLoggerPluginsHolder {
        private static final AsyncLoggerPlugins instance = new AsyncLoggerPlugins<>();
    }

    @SuppressWarnings("unchecked")
    public static<E> AsyncLoggerPlugins<E> getInstance() {
        return AsyncLoggerPluginsHolder.instance;
    }

    @SuppressWarnings("unchecked")
    public List<LogExport<E>> getlogExports() {
        if (logExports.get() == null) {
            Object impl = getPluginImplementation(LogExport.class);
            logExports.compareAndSet(null, (List<LogExport<E>>) impl);
        }

        return logExports.get();
    }

    public void registerLogExports(List<LogExport<E>> impl) {
        if (!logExports.compareAndSet(null, impl)) {
            throw new IllegalStateException("Another LogExport was already registered.");
        }
    }

    private <T> List<T> getPluginImplementation(Class<T> clazz) {
        return findClass(clazz);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> findClass(Class<T> clazz) {
        List<T> objs = new ArrayList<>();
        // SPI
        ServiceLoader<T> serviceLoader =  ServiceLoader.load(clazz);
        for (T t : serviceLoader) {
            if (t != null) {
                objs.add(t);
            }
        }

        // 如果没有提供实现，则使用默认实现
        if (objs.isEmpty()) {
            T result = null;
            ClassLoader classLoader = AsyncLoggerPlugins.class.getClassLoader();
            try {
                result = (T) classLoader.loadClass(Constants.DEFAULT_LOG_EXPORT_IMPL);
            } catch (ClassNotFoundException e) {
                System.out.println("Class not find ....");
                e.printStackTrace();
            }
            objs.add(result);
        }

        return objs;
    }

}
