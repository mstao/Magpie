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

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The plugins of async logger.
 *
 * @author mingshan
 */
public class AsyncLoggerPlugins<E> {
    private final AtomicReference<List<LogExport<E>>> logExports = new AtomicReference<>();

    private static class AsyncLoggerPluginsHolder {
        private static final AsyncLoggerPlugins instance = new AsyncLoggerPlugins<>();
    }

    @SuppressWarnings("unchecked")
    public static<E> AsyncLoggerPlugins<E> getInstance() {
        return AsyncLoggerPluginsHolder.instance;
    }

    @SuppressWarnings("unchecked")
    public List<LogExport<E>> getlogExports() {
        if (logExports.get() == null) {
            Object impl = getPluginImplementation(LogExport.class);
            logExports.compareAndSet(null, (List<LogExport<E>>) impl);
        }

        return logExports.get();
    }

    public void registerLogExports(List<LogExport<E>> impl) {
        if (!logExports.compareAndSet(null, impl)) {
            throw new IllegalStateException("Another LogExport was already registered.");
        }
    }

    private <T> List<T> getPluginImplementation(Class<T> clazz) {
        T t = getPluginImplementationByProperty(clazz);

        if (t != null) {
            List<T> objs = new ArrayList<>();
            objs.add(t);
            return objs;
        }

        return findClass(clazz);
    }

    private <T> T getPluginImplementationByProperty(Class<T> clazz) {
        String className = clazz.getSimpleName();
        String key = Constants.PLUGIN_PROPERTY_PREFIX + className + "implementation";

        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> findClass(Class<T> clazz) {
        List<T> objs = new ArrayList<>();
        // SPI
        ServiceLoader<T> serviceLoader =  ServiceLoader.load(clazz);
        for (T t : serviceLoader) {
            if (t != null) {
                objs.add(t);
            }
        }

        // If property and spi are null, choose the default implementation.
        if (objs.isEmpty()) {
            T result = null;
            ClassLoader classLoader = AsyncLoggerPlugins.class.getClassLoader();
            try {
                result = (T) classLoader.loadClass(Constants.DEFAULT_LOG_EXPORT_IMPL);
            } catch (ClassNotFoundException e) {
                System.out.println("Class not find ....");
                e.printStackTrace();
            }
            objs.add(result);
        }

        return objs;
    }

}
