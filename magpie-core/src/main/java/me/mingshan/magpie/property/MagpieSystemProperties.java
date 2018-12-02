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
package me.mingshan.magpie.property;

/**
 * Provides the way to get configuration via system property.
 *
 * @author mingshan
 */
public class MagpieSystemProperties implements MagpieProperties {

    /**
     * No public
     */
    private MagpieSystemProperties() {}

    /**
     * Inner class for lazy load.
     */
    private static class AsyncLoggerSystemPropertiesHolder {
        private static final MagpieSystemProperties INSTANCE = new MagpieSystemProperties();
    }

    /**
     * Returns the instance of {@link MagpieSystemProperties}.
     *
     * @return the instance of {@link MagpieSystemProperties}
     */
    public static MagpieSystemProperties getInstance() {
        return AsyncLoggerSystemPropertiesHolder.INSTANCE;
    }

    @Override
    public MagpieProperty<String> getString(String name, String fallback) {
        return new MagpieProperty<String>() {
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
    public MagpieProperty<Integer> getInteger(String name, Integer fallback) {
        return new MagpieProperty<Integer>() {
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
    public MagpieProperty<Boolean> getBoolean(String name, Boolean fallback) {
        return new MagpieProperty<Boolean>() {
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
    public MagpieProperty<Long> getLong(String name, Long fallback) {
        return new MagpieProperty<Long>() {
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
