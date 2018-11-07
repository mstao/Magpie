package me.mingshan.logger.async.cache;

import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.Objects;

/**
 * The cache implementation with Caffeine.
 *
 * @param <T> the generics class
 */
public class CaffeineCache<T> implements Cache<T> {
    private com.github.benmanes.caffeine.cache.LoadingCache<Object, Object> cache;

    /**
     * No Public.
     */
    private CaffeineCache() {
        cache = CaffeineConfig.getCache();
    }

    /**
     * Inner class for Lazy load.
     */
    private static final class CaffeineCacheHolder {
        private static final CaffeineCache  INSTANCE = new CaffeineCache();
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
    public T get(Object key) {
        Objects.requireNonNull(key);
        return this.get(key);
    }

    @Override
    public void put(Object key, T value) {
        Objects.requireNonNull(key);
        this.cache.put(key, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T putIfPresent(Object key, T value) {
        Objects.requireNonNull(key);
        Object existingValue = this.cache.get(key);
        if (existingValue == null) {
            cache.put(key, value);
            return null;
        } else {
            return (T) existingValue;
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
