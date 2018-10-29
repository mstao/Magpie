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
package me.mingshan.logger.async;

/**
 * Alternative Wait Strategies
 *
 */
public enum DisruptorWaitStrategy {

    /**
     * Like the BlockingWaitStrategy the SleepingWaitStrategy it attempts to be conservative with CPU usage, by using a simple busy wait loop,
     * but uses a call to LockSupport.parkNanos(1) in the middle of the loop.
     */
    SLEEP("SleepingWaitStrategy"),

    /**
     *
     */
    YIELD("YieldingWaitStrategy"),

    /**
     *
     */
    BLOCK("BlockingWaitStrategy"),

    /**
     *
     */
    BUSYSPIN("BusySpinWaitStrategy"),

    /**
     *
     */
    TIMEOUT("TimeoutBlockingWaitStrategy");

    private String desc;
    DisruptorWaitStrategy(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}

