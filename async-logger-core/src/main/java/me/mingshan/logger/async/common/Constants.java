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
package me.mingshan.logger.async.common;

public class Constants {
    /*** RINGBUFFER ***/
    public static final int RINGBUFFER_MIN_SIZE = 128;
    public static final int RINGBUFFER_DEFAULT_SIZE = 256 * 1024;
    public static final int RINGBUFFER_NO_GC_DEFAULT_SIZE = 4 * 1024;

    public static final long BLOCKING_WAIT_STRAGETY_TIMEOUT_MILLIS = 123L;
    public static final boolean ENABLE_THREADLOCALS = true;

}
