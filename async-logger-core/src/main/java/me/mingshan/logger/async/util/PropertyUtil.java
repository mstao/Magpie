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

/**
 * The util of property.
 */
public class PropertyUtil {

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
}
