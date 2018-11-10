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
package me.mingshan.logger.async.plugin;

import me.mingshan.logger.async.api.LogExport;
import me.mingshan.logger.async.common.Constants;
import me.mingshan.logger.async.property.AsyncLoggerFileProperties;
import me.mingshan.logger.async.property.AsyncLoggerProperties;
import me.mingshan.logger.async.property.AsyncLoggerProperty;
import me.mingshan.logger.async.property.AsyncLoggerSystemProperties;
import me.mingshan.logger.async.util.ClassUtil;
import me.mingshan.logger.async.util.StringUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The plugins of async logger.
 *
 * @author mingshan
 */
public class AsyncLoggerPlugins<E> {
    private final AtomicReference<List<LogExport>> logExports = new AtomicReference<>();
    private AsyncLoggerProperties asyncLoggerProperties;

    /**
     * Inner class for lazy load.
     */
    private static class AsyncLoggerPluginsHolder {
        private static final AsyncLoggerPlugins instance = AsyncLoggerPlugins.create();
    }

    /**
     * Returns the instance of {@link AsyncLoggerPlugins}.
     *
     * @param <E> the generics class
     * @return the instance of {@link AsyncLoggerPlugins}
     */
    @SuppressWarnings("unchecked")
    public static<E> AsyncLoggerPlugins<E> getInstance() {
        return AsyncLoggerPluginsHolder.instance;
    }

    /**
     * No Public
     */
    private AsyncLoggerPlugins() {
        asyncLoggerProperties = resolveDynamicProperties(LoadConfigType.SYSTEM);
    }

    /**
     * Creates an instance of {@link AsyncLoggerPlugins}.
     *
     * @return the instance of {@link AsyncLoggerPlugins}
     */
    private static AsyncLoggerPlugins create() {
        return new AsyncLoggerPlugins<>();
    }

    /**
     * Gets the implementations of {@link LogExport}.
     *
     * @return the implementations of {@link LogExport}
     */
    @SuppressWarnings("unchecked")
    public List<LogExport> getlogExports() {
        if (logExports.get() == null) {
            List<LogExport> impl = getPluginImplementation(LogExport.class);
            logExports.compareAndSet(null, impl);
        }

        return logExports.get();
    }

    /**
     * Registers a instance of {@link LogExport}.
     *
     * @param impl the implementation of {@link LogExport}
     */
    public void registerLogExports(List<LogExport> impl) {
        if (!logExports.compareAndSet(null, impl)) {
            throw new IllegalStateException("Another LogExport was already registered.");
        }
    }

    /**
     * Gets the implementations of plugin.
     *
     * @param clazz the class of plugin
     * @param <T> the generics class
     * @return the implementations of plugin
     */
    private <T> List<T> getPluginImplementation(Class<T> clazz) {
        Objects.requireNonNull(clazz);
        // Gets configuration via system property.
        List<T> ts = getPluginImplementationByProperty(asyncLoggerProperties, clazz);
        System.out.println("Find plugin " +  clazz.getSimpleName() + " by system property, implementations："
                + (ts != null ? Arrays.toString(ts.toArray()) : ""));

        if (ts.size() > 0) {
            return ts;
        } else {
            // Gets configuration via file property.
            asyncLoggerProperties = resolveDynamicProperties(LoadConfigType.FILE);
            ts = getPluginImplementationByProperty(asyncLoggerProperties, clazz);
            System.out.println("Find plugin " +  clazz.getSimpleName() + " by file property, implementations："
                    + (ts != null ? Arrays.toString(ts.toArray()) : ""));
            if (ts.size() > 0) {
                return ts;
            }
        }

        return findClass(clazz);
    }

    /**
     * Gets plugin implementation via property.
     *
     * @param asyncLoggerProperties the {@link AsyncLoggerProperties} implementation
     * @param clazz the interface which will be get
     * @param <T> the generics class
     * @return the implementation
     */
    @SuppressWarnings("unchecked")
    private <T> List<T> getPluginImplementationByProperty(AsyncLoggerProperties asyncLoggerProperties,
        Class<T> clazz) {
        String className = clazz.getSimpleName();
        String propertyName = Constants.PLUGIN_PROPERTY_PREFIX + className + ".implementation";
        AsyncLoggerProperty<String> property = asyncLoggerProperties.getString(propertyName, null);

        if (property != null) {
            List<T> values = new ArrayList<>();
            // Notice, considers that there are more than one implementations for a plugin.
            String implementClassStr = property.get();
            String[] implementClasses = StringUtil.split(implementClassStr, Constants.PLUGIN_PROPERTY_MULTI_DEFAULT_SEPARATOR);
            if (implementClasses != null) {
                for (String implementClass : implementClasses) {
                    try {
                        if (StringUtil.isEmpty(implementClass)) {
                            return Collections.EMPTY_LIST;
                        }
                        Class<?> cls = Class.forName(implementClass);
                        cls = cls.asSubclass(clazz);
                        Constructor constructor = cls.getConstructor();
                        T t = (T) constructor.newInstance();
                        values.add(t);
                    } catch (ClassCastException e) {
                        throw new RuntimeException(className + " implementation is not an instance of "
                                + className + ": " + implementClass);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(className + " implementation class not found: " + implementClass, e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(className + " implementation not able to be instantiated: "
                                + implementClass, e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(className + " implementation not able to be accessed: " + implementClass, e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(className + " implementation class can't get constructor: ", e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(className + " implementation class get instance failed: ", e);
                    }
                }

                return values;
            } else {
                return Collections.EMPTY_LIST;
            }
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * This method needs to be extended.
     *
     * @return {@code syncLoggerProperties} implementation
     */
    private AsyncLoggerProperties resolveDynamicProperties(LoadConfigType type) {
        AsyncLoggerProperties asyncLoggerProperties;

        switch (type) {
            case SYSTEM:
                asyncLoggerProperties = AsyncLoggerSystemProperties.getInstance();
                break;
            case FILE:
                asyncLoggerProperties = AsyncLoggerFileProperties.getInstance();
                break;
            default: throw new RuntimeException("Can not find the type of loading configuration.");
        }

        return asyncLoggerProperties;
    }

    /**
     * Finds class via SPI or default implementation.
     *
     * @param clazz the interface which will be get
     * @param <T> the generics class
     * @return the implementation
     */
    @SuppressWarnings("unchecked")
    private <T> List<T> findClass(Class<T> clazz) {
        List<T> objs = new ArrayList<>();
        // SPI
        ServiceLoader<T> serviceLoader =  ServiceLoader.load(clazz);
        for (T t : serviceLoader) {
            if (t != null) {
                System.out.println("Find plugin " +  clazz.getSimpleName() + " by SPI, implementation："  + t);
                objs.add(t);
            }
        }

        // If property and spi are null, chooses the default implementation.
        if (objs.isEmpty()) {
            T result;
            try {
                result = (T) ClassUtil.getClassLoader().loadClass(Constants.DEFAULT_LOG_EXPORT_IMPL);
                System.out.println("Find plugin " +  clazz.getSimpleName() + " by Default, implementation："  + result);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Class " + clazz + " not found ", e);
            }
            objs.add(result);
        }

        return objs;
    }

}
