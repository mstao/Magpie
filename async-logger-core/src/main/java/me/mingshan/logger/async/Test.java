package me.mingshan.logger.async;

import me.mingshan.logger.async.api.Message;

public class Test {
    public static void main(String[] args) {
        AsyncLoggerContext.start();
        AsyncLogger<Message> asyncLogger = AsyncLoggerContext.getAsyncLogger();
        for (int i = 0; i < 1222222; i++) {
            Message message = new Message();
            message.setServiceName("aa " + i);
            asyncLogger.logMessage(message);
        }
    }

}
