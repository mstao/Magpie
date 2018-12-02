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
package me.mingshan.magpie.api;

/**
 * Interface that logger.
 *
 * @author mingshan
 */
public interface Reader {

    /**
     * Records info via entity.
     *
     * @param message the entity of message
     * @param <E> the generics class
     */
    <E> void read(E message);

    /**
     * Records info via byte array.
     *
     * @param message the byte array of message
     */
    void read(byte[] message);
}
