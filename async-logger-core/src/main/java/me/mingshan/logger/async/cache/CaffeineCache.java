package me.mingshan.logger.async.cache;

import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.Objects;

/**
 * The cache implementation with Caffeine.
 *
 */
public class CaffeineCache implements Cache {
    private com.github.benmanes.caffeine.cache.Cache<Object, Object> cache;

    /**
     * No Public.
     */
    public CaffeineCache() {
        cache = CaffeineConfig.getCache();
    }

    /**
     * Inner class for Lazy load.
     */
    private static final class CaffeineCacheHolder {
        private static final CaffeineCache INSTANCE = new CaffeineCache();
    }

    /**
     * Returns the instance of {@link CaffeineCache}.
     *
     * @return the instance of {@link CaffeineCache}.
     */
    public static CaffeineCache getInstance() {
        return CaffeineCacheHolder.INSTANCE;
    }

    @Override
    public String getName() {
        return CaffeineCache.class.getSimpleName();
    }

    @Override
    public Object get(Object key) {
        Objects.requireNonNull(key);
        return this.cache.getIfPresent(key);
    }

    @Override
    public void put(Object key, Object value) {
        Objects.requireNonNull(key);
        this.cache.put(key, value);
    }

    @Override
    public Object putIfPresent(Object key, Object value) {
        Objects.requireNonNull(key);
        Object existingValue = this.cache.getIfPresent(key);
        if (existingValue == null) {
            cache.put(key, value);
            return null;
        } else {
            return existingValue;
        }
    }

    @Override
    public void evict(Object key) {
        Objects.requireNonNull(key);
        this.cache.invalidate(key);
    }

    @Override
    public void clear() {
        this.cache.invalidateAll();
    }
}
