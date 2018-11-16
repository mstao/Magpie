package me.mingshan.magpie.demo.web.export;

import me.mingshan.logger.async.api.LogExport;
import me.mingshan.logger.async.api.Message;
import me.mingshan.logger.async.serialize.Serializer;
import me.mingshan.logger.async.serialize.SerializerHolder;
import me.mingshan.logger.async.source.collector.c1.message.BrowserMessage;

/**
 * @author mingshan
 */
public class AccessConsoleExportImpl implements LogExport {

    @Override
    public void export(byte[] message) {
        Serializer serializer = SerializerHolder.serializerImpl();
        System.out.println("yy = " + message);
        System.out.println(serializer.readObject(message, BrowserMessage.class));
    }
}
