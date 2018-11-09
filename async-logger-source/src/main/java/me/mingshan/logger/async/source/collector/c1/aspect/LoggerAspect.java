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
package me.mingshan.logger.async.source.collector.c1.aspect;

import me.mingshan.logger.async.AsyncLogger;
import me.mingshan.logger.async.AsyncLoggerContext;
import me.mingshan.logger.async.api.Level;
import me.mingshan.logger.async.source.collector.c1.annotation.Log;
import me.mingshan.logger.async.source.collector.c1.message.Message;
import me.mingshan.logger.async.source.util.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;
import java.util.Optional;

import static me.mingshan.logger.async.source.util.AopUtils.getAnnotation;
import static me.mingshan.logger.async.source.util.AopUtils.getMethodFromTarget;
import static me.mingshan.logger.async.source.util.AopUtils.getParameters;


/**
 * If you use Spring AOP in your project then you need to add specific configuration using Spring AOP namespace
 * in order to make Spring capable to manage aspects which were written using AspectJ and declare `LoggerAspect`
 * as Spring bean like below:
 *
 * <pre> {@code
 *     <aop:aspectj-autoproxy/>
 *     <bean id="hystrixAspect" class="me.mingshan.logger.async.source.collector.c1.aspect.LoggerAspect"></bean>
 * }</pre>
 *
 * Or if you are using Spring code configuration:
 *
 * <pre> {@code
 * @Configuration
 * public class AsyncLoggerConfiguration {
 *
 *   @Bean
 *   public LoggerAspect loggerAspect() {
 *     return new LoggerAspect();
 *   }
 *
 * }}</pre>
 *
 * @author mingshan
 */
@Aspect
public class LoggerAspect {

    @Pointcut("@annotation(me.mingshan.logger.async.source.collector.c1.annotation.Log)")
    private void logPointCut() {
    }

    @Around("logPointCut()")
    public Object methodsAnnotatedWithLogger(final ProceedingJoinPoint joinPoint) throws Throwable{
        Object result = null;
        Level level = Level.INFO;

        Method method = getMethodFromTarget(joinPoint);
        Log logAnnotation = getAnnotation(joinPoint,
                me.mingshan.logger.async.source.collector.c1.annotation.Log.class)
                .orElseGet(null);
        // 判断日志注解是否存在
        if (logAnnotation != null) {
            // 获取接口
            Class[] intfs = joinPoint.getTarget().getClass().getInterfaces();
            // 处理接口
            String serviceName = intfs.length > 0 ? intfs[0].getName()
                    : joinPoint.getTarget().getClass().getName();
            // 获取方法名
            String methodName = method.getName();
            // 判断参数获取
            Object[] params = null;
            if (logAnnotation.recordParams()) {
                params = getParameters(joinPoint);
            }

            long startTime = System.currentTimeMillis();
            long executedTime = 0L;
            try {
                result = joinPoint.proceed();
                executedTime = System.currentTimeMillis() - startTime;
            } catch (Throwable t) {
                // 记录异常信息
                writeMessage(serviceName, methodName, params, logAnnotation.recordResult() ? result : null,
                        executedTime, Level.ERROR, t);
                throw t;
            }

            // 执行完毕，记录日志
            writeMessage(serviceName, methodName, params, logAnnotation.recordResult() ? result : null,
                    executedTime, Level.INFO, null);
        } else {
            result = joinPoint.proceed();
        }

        return result;
    }

    private void writeMessage(String serviceName, String methodName, Object[] params,
                              Object result, long executedTime, Level level, Throwable e) {
        String paramStr =  Optional.ofNullable(params)
                .map(JsonUtils::arrayToString)
                .orElseGet(null);

        String resultStr = Optional.ofNullable(result)
                .map(JsonUtils::objectToString)
                .orElseGet(null);

        AsyncLoggerContext.start();
        AsyncLogger asyncLogger = AsyncLoggerContext.getAsyncLogger();

        Message message = new Message();
        message.setServiceName(serviceName);
        message.setMethodName(methodName);
        message.setArgs(paramStr);
        message.setExecutedTime(executedTime);
        message.setLevel(level);
        message.setE(e);
        message.setResult(resultStr);
        try {
            asyncLogger.logMessage(message);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
