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

/**
 * Constants for core module.
 *
 * @author mingshan
 */
public class Constants {

    /**
     * RINGBUFFER minimum value.
     */
    public static final int RINGBUFFER_MIN_SIZE = 128;

    /**
     * RINGBUFFER default value.
     */
    public static final int RINGBUFFER_DEFAULT_SIZE = 256 * 1024;

    /**
     * RINGBUFFER NO GC default value.
     */
    public static final int RINGBUFFER_NO_GC_DEFAULT_SIZE = 4 * 1024;

    /**
     * BlockingWaitStrategy time-out period.
     */
    public static final long BLOCKING_WAIT_STRAGETY_TIMEOUT_MILLIS = 123L;

    /**
     * The fully qualified name of the default implementation class for exporting log.
     */
    public static final String DEFAULT_LOG_EXPORT_IMPL = "me.mingshan.logger.async.DefaultLogExportImpl";

    /**
     * {@code SequenceReportingEventHandler} The thresholds for the batch Event.
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
