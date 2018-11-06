package me.mingshan.logger.async.property;

/**
 * Provides the way to get system property.
 *
 * @author mingshan
 */
public class AsyncLoggerSystemProperties implements AsyncLoggerProperties {

    /**
     * No public
     */
    private AsyncLoggerSystemProperties() {}

    private static class AsyncLoggerSystemPropertiesHolder {
        private static final AsyncLoggerSystemProperties INSTANCE = new AsyncLoggerSystemProperties();
    }

    public static AsyncLoggerSystemProperties getInstance() {
        return AsyncLoggerSystemPropertiesHolder.INSTANCE;
    }

    @Override
    public AsyncLoggerProperty<String> getString(String name, String fallback) {
        return new AsyncLoggerProperty<String>() {
            @Override
            public String get() {
                return System.getProperty(name, fallback);
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }

    @Override
    public AsyncLoggerProperty<Integer> getInteger(String name, Integer fallback) {
        return new AsyncLoggerProperty<Integer>() {
            @Override
            public Integer get() {
                return Integer.getInteger(name, fallback);
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }

    @Override
    public AsyncLoggerProperty<Boolean> getBoolean(String name, Boolean fallback) {
        return new AsyncLoggerProperty<Boolean>() {
            @Override
            public Boolean get() {
                String v = null;
                try {
                    v = System.getProperty(name);
                } catch (IllegalArgumentException | NullPointerException e) {
                }
                if (v == null) {
                    return fallback;
                }
                return Boolean.getBoolean(name);
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }

    @Override
    public AsyncLoggerProperty<Long> getLong(String name, Long fallback) {
        return new AsyncLoggerProperty<Long>() {
            @Override
            public Long get() {
                return Long.getLong(name, fallback);
            }

            @Override
            public String getName() {
                return name;
            }
        };
    }
}
