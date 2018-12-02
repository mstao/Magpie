package me.mingshan.magpie.serialize.kryo;

import me.mingshan.magpie.serialize.Serializer;
import me.mingshan.magpie.util.KryoUtil;

/**
 * Serializer implementation via Kryo.
 *
 * @author mingshan
 */
public class KryoSerializer implements Serializer {

    @Override
    public <T> byte[] writeObject(T obj) {
        return KryoUtil.writeObjectToByteArray(obj);
    }

    @Override
    public <T> T readObject(byte[] bytes, Class<T> clazz) {
        return KryoUtil.readObjectFromByteArray(bytes, clazz);
    }
}
