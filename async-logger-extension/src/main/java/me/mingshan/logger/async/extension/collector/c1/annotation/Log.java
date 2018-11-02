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
package me.mingshan.logger.async.extension.collector.c1.annotation;

import me.mingshan.logger.async.extension.collector.c1.enums.LogType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 *
 * @author mingshan
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 标记日志类型，默认为{@code LogType.VISITOR}
     *
     * @return 日志类型
     */
    LogType type() default LogType.VISITOR;

    /**
     * 是否记录参数，默认为{@code true}
     *
     * @return 返回{@code true}，记录参数，返回{@code false}, 不记录
     */
    boolean recordParams() default true;

    /**
     * 是否记录返回结果，默认为{@code true}
     *
     * @return 返回{@code true}，记录返回结果，返回{@code false}, 不记录
     */
    boolean recordResult() default true;
}
