package me.mingshan.magpie.demo;

import me.mingshan.logger.async.api.LogExport;
import me.mingshan.logger.async.api.Message;
import me.mingshan.logger.async.serialize.Serializer;
import me.mingshan.logger.async.serialize.SerializerHolder;

public class ConsoleLogExportImpl implements LogExport {

    @Override
    public void export(byte[] message) {
        Serializer serializer = SerializerHolder.serializerImpl();
        System.out.println(serializer.readObject(message, Message.class));
        System.out.println(message);
    }

}
