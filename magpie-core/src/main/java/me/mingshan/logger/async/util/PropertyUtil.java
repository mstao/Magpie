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
package me.mingshan.logger.async.util;

import me.mingshan.logger.async.property.AsyncLoggerProperties;
import me.mingshan.logger.async.property.AsyncLoggerProperty;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * The util of property.
 *
 * @author mingshan
 */
public class PropertyUtil {

    /**
     * Gets the specified {@link AsyncLoggerProperty} by {@code type},
     * also sets default value if the origin value is {@code null}.
     *
     * @param properties the source properties
     * @param name the key
     * @param fallback the default value
     * @param type the type of value
     * @param <T> the generics class
     * @return the specified {@link AsyncLoggerProperty}
     */
    @SuppressWarnings("unchecked")
    public static <T> AsyncLoggerProperty<T> getProperty(AsyncLoggerProperties properties, String name,
        T fallback, Class<T> type) {

        if (type == Integer.class) {
            return (AsyncLoggerProperty<T>) properties.getInteger(name, (Integer) fallback);
        }

        if (type == Boolean.class) {
            return (AsyncLoggerProperty<T>) properties.getBoolean(name, (Boolean) fallback);
        }

        if (type == String.class) {
            return (AsyncLoggerProperty<T>) properties.getString(name, (String) fallback);
        }

        if (type == Long.class) {
            return (AsyncLoggerProperty<T>) properties.getLong(name, (Long) fallback);
        }

        throw new IllegalStateException();
    }

    /**
     * Loads property file information.
     *
     * @param fileName the filname
     * @return {@link Properties}
     */
    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        InputStream input = ClassUtil.getClassLoader().getResourceAsStream(fileName);
        if (input == null) {
            return properties;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return properties;
    }
}
