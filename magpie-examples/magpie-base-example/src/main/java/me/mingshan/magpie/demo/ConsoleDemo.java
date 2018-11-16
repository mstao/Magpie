package me.mingshan.magpie.demo;

import me.mingshan.logger.async.AsyncLogger;
import me.mingshan.logger.async.AsyncLoggerContext;
import me.mingshan.logger.async.api.Message;

public class ConsoleDemo {
    public static void main(String[] args) {
        AsyncLoggerContext.start();
        AsyncLogger asyncLogger = AsyncLoggerContext.getAsyncLogger();
        for (int i = 0; i < 2; i++) {
            Message message = new Message();
            message.setServiceName("aa " + i);
            asyncLogger.logMessage(message);
        }
        AsyncLoggerContext.stop();
    }
}
