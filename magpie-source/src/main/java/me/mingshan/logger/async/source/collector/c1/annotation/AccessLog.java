package me.mingshan.logger.async.source.collector.c1.annotation;

/**
 * Annotation that puts it on the method to record the browser information and user information,
 * If you want to record this information via aspect, just use it.
 *
 * @author mingshan
 */
public @interface AccessLog {

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
