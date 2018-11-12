package me.mingshan.logger.async;

import me.mingshan.logger.async.cache.Cache;
import me.mingshan.logger.async.cache.CaffeineCache;

/**
 * @author mingshan
 */
public class CaffeineCacheDemo {
    public static void main(String[] args) {
        Cache caffeineCache = CaffeineCache.getInstance();
        caffeineCache.put("String#", "zzzzzzz");
        Object cachedValue = caffeineCache.get("String#");
        System.out.println(cachedValue);
    }
}
