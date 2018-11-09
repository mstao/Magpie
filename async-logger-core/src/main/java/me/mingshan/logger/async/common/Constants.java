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
    /**
     * RINGBUFFER 最小值
     */
    public static final int RINGBUFFER_MIN_SIZE = 128;

    /**
     * RINGBUFFER 默认值
     */
    public static final int RINGBUFFER_DEFAULT_SIZE = 256 * 1024;

    /**
     * RINGBUFFER NO GC 默认值
     */
    public static final int RINGBUFFER_NO_GC_DEFAULT_SIZE = 4 * 1024;

    /**
     * BlockingWaitStrategy超时时间
     */
    public static final long BLOCKING_WAIT_STRAGETY_TIMEOUT_MILLIS = 123L;

    /**
     * 导出日志默认实现类全限定名
     */
    public static final String DEFAULT_LOG_EXPORT_IMPL = "me.mingshan.logger.async.DefaultLogExportImpl";

    /**
     * {@code SequenceReportingEventHandler} 批处理Event的阈值
     */
    public static final int NOTIFY_PROGRESS_THRESHOLD = 50;

    /**
     * The prefix of property.
     */
    public static final String PLUGIN_PROPERTY_PREFIX = "asynclogger.plugin.";

    /**
     * The name of property file.
     */
    public static final String PLUGIN_PROPERTY_FILE_NAME = "asynclogger.properties";

    /**
     * The default separator of multi implementations for a plugin.
     */
    public static final String PLUGIN_PROPERTY_MULTI_DEFAULT_SEPARATOR = ",";
}
