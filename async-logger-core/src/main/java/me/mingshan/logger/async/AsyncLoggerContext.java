package me.mingshan.logger.async;

public class AsyncLoggerContext {
    private static AsyncLoggerDisruptor disruptor = new AsyncLoggerDisruptor();
    private static AsyncLogger asyncLogger;

    public static void start() {
        disruptor.start();
    }

    public static void stop() {
        disruptor.stop();
    }

    public static AsyncLogger getAsyncLogger() {
        if (asyncLogger == null) {
            return new AsyncLogger(disruptor);
        } else {
            return asyncLogger;
        }
    }
}
