package me.mingshan.logger.async;

import me.mingshan.logger.async.api.Message;

public class Test {
    public static void main(String[] args) {
        AsyncLoggerContext.start();
        AsyncLogger asyncLogger = AsyncLoggerContext.getAsyncLogger();
        Message message = new Message();
        for (int i = 0; i < 2; i++) {
            message.setServiceName("aa " + i);
            asyncLogger.logMessage(message);
        }
    }
}
