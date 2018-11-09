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
 * 异步日志启动上下文
 *
 * @author mingshan
 */
public class AsyncLoggerContext {
    private static final AsyncLoggerDisruptor loggerDisruptor = new AsyncLoggerDisruptor();
    private static AsyncLogger asyncLogger;

    public static void start() {
        loggerDisruptor.start();
    }

    public static void stop() {
        loggerDisruptor.stop();
    }

    @SuppressWarnings("unchecked")
    public static AsyncLogger getAsyncLogger() {
        if (asyncLogger == null) {
            synchronized (AsyncLogger.class) {
                if (asyncLogger == null) {
                    asyncLogger = new AsyncLogger(loggerDisruptor);
                }
            }
        }

        return asyncLogger;
    }
}
