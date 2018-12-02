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
 * Interface that providers the common methods to get {@link MagpieProperty}.
 *
 * @author mingshan
 */
public interface MagpieProperties {

    /**
     * Gets a {@code String} property that maybe not exist.
     *
     * @param name the key of property
     * @param fallback the default value, maybe is {@code null}
     * @return never {@code null}
     */
    MagpieProperty<String> getString(String name, String fallback);

    /**
     * Gets a {@code Integer} property that maybe not exist.
     *
     * @param name the key of property
     * @param fallback the default value, maybe is {@code null}
     * @return never {@code null}
     */
    MagpieProperty<Integer> getInteger(String name, Integer fallback);

    /**
     * Gets a {@code Boolean} property that maybe not exist.
     *
     * @param name the key of property
     * @param fallback the default value, maybe is {@code null}
     * @return never {@code null}
     */
    MagpieProperty<Boolean> getBoolean(String name, Boolean fallback);

    /**
     * Gets a {@code Long} property that maybe not exist.
     *
     * @param name the key of property
     * @param fallback the default value, maybe is {@code null}
     * @return never {@code null}
     */
    MagpieProperty<Long> getLong(String name, Long fallback);
}
