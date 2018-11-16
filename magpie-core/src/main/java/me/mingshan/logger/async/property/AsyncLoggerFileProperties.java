/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.mingshan.logger.async.property;

import me.mingshan.logger.async.cache.Cache;
import me.mingshan.logger.async.cache.CachePrefix;
import me.mingshan.logger.async.cache.CaffeineCache;
import me.mingshan.logger.async.common.Constants;
import me.mingshan.logger.async.util.PropertyUtil;
import me.mingshan.logger.async.util.StringUtil;

import java.util.Properties;

/**
 * Provides the way to get configuration via property file.
 *
 * @author mingshan
 */
public class AsyncLoggerFileProperties implements AsyncLoggerProperties {

    /**
     * No Public
     */
    private AsyncLoggerFileProperties() {
    }

    /**
     * Inner class for lazy load.
     */
    private static final class AsyncLoggerFilePropertiesHolder {
        private static final AsyncLoggerFileProperties INSTANCE = new AsyncLoggerFileProperties();
    }

    /**
     * Returns the instance of {@link AsyncLoggerFileProperties}.
     *
     * @return the instance of {@link AsyncLoggerFileProperties}
     */
    public static AsyncLoggerFileProperties getInstance() {
        return AsyncLoggerFilePropertiesHolder.INSTANCE;
    }

    @Override
    public AsyncLoggerProperty<String> getString(String name, String fallback) {
        Cache caffeineCache = CaffeineCache.getInstance();
        Object cachedValue = caffeineCache.get(CachePrefix.STRING.getValue() + name);
        String value = null;
        if (cachedValue == null) {
            Properties properties = PropertyUtil.loadProperties(Constants.PLUGIN_PROPERTY_FILE_NAME);
            Object resource = properties.get(name);
            if (resource != null) {
                String tempValue = String.valueOf(resource);
                if (!StringUtil.isEmpty(tempValue)) {
                    value = tempValue;
                    caffeineCache.put(CachePrefix.STRING.getValue() + name, value);
                }
            }
        } else {
            value = String.valueOf(cachedValue);
        }

        final String endValue = value;
        return new AsyncLoggerProperty<String>() {

            @Override
            public String get() {
                return endValue;
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }

    @Override
    public AsyncLoggerProperty<Integer> getInteger(String name, Integer fallback) {
        Cache caffeineCache = CaffeineCache.getInstance();
        Object cachedValue = caffeineCache.get(CachePrefix.INTEGER.getValue() + name);
        Integer value;
        if (cachedValue == null) {
            Properties properties = PropertyUtil.loadProperties(Constants.PLUGIN_PROPERTY_FILE_NAME);
            value = Integer.valueOf(properties.get(name).toString());
            caffeineCache.put(CachePrefix.INTEGER.getValue() + name, value);
        } else {
            value = Integer.valueOf(cachedValue.toString());
        }

        return new AsyncLoggerProperty<Integer>() {

            @Override
            public Integer get() {
                return value;
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }

    @Override
    public AsyncLoggerProperty<Boolean> getBoolean(String name, Boolean fallback) {
        Cache caffeineCache = CaffeineCache.getInstance();
        Object cachedValue = caffeineCache.get(CachePrefix.BOOLEAN.getValue() + name);
        Boolean value;
        if (cachedValue == null) {
            Properties properties = PropertyUtil.loadProperties(Constants.PLUGIN_PROPERTY_FILE_NAME);
            value = Boolean.valueOf(properties.get(name).toString());
            caffeineCache.put(CachePrefix.BOOLEAN.getValue() + name, value);
        } else {
            value = Boolean.valueOf(cachedValue.toString());
        }

        return new AsyncLoggerProperty<Boolean>() {

            @Override
            public Boolean get() {
                return value;
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }

    @Override
    public AsyncLoggerProperty<Long> getLong(String name, Long fallback) {
        Cache caffeineCache = CaffeineCache.getInstance();
        Object cachedValue = caffeineCache.get(CachePrefix.LONG.getValue() + name);
        Long value;
        if (cachedValue == null) {
            Properties properties = PropertyUtil.loadProperties(Constants.PLUGIN_PROPERTY_FILE_NAME);
            value = Long.valueOf(properties.get(name).toString());
            caffeineCache.put(CachePrefix.LONG.getValue() + name, value);
        } else {
            value = Long.valueOf(cachedValue.toString());
        }

        return new AsyncLoggerProperty<Long>() {

            @Override
            public Long get() {
                return value;
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }
}
