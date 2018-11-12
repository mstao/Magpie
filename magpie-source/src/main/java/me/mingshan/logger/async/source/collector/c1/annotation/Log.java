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
package me.mingshan.logger.async.source.collector.c1.annotation;

import me.mingshan.logger.async.source.collector.c1.enums.LogType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that puts it on the method to record the method's access and execution,
 * If you want to record log via aspect, just use it.
 *
 * @author mingshan
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * Mark the type of log，the default value is {@code LogType.VISITOR}
     *
     * @return the type of log
     */
    LogType type() default LogType.VISITOR;

    /**
     * Whether to record the parameters of method，the default value is{@code true}.
     *
     * @return If returns {@code true}，records method parameters，returns {@code false}, no record
     */
    boolean recordParams() default true;

    /**
     * Whether to record the result of method，the default value is{@code true}.
     *
     * @return If returns {@code true}，records the result of method，returns {@code false}, no record
     */
    boolean recordResult() default true;
}
