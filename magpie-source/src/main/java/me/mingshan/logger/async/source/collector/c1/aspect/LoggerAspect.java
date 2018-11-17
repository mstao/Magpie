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

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import me.mingshan.logger.async.AsyncLogger;
import me.mingshan.logger.async.AsyncLoggerContext;
import me.mingshan.logger.async.api.Level;
import me.mingshan.logger.async.source.collector.c1.annotation.Log;
import me.mingshan.logger.async.source.collector.c1.enums.LogType;
import me.mingshan.logger.async.source.collector.c1.message.BrowserMessage;
import me.mingshan.logger.async.source.collector.c1.message.Message;
import me.mingshan.logger.async.source.util.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static me.mingshan.logger.async.source.util.AopUtils.*;


/**
 * If you use Spring AOP in your project then you need to add specific configuration using Spring AOP namespace
 * in order to make Spring capable to manage aspects which were written using AspectJ and declare `LoggerAspect`
 * as Spring bean like below:
 *
 * <pre> {@code
 *     <aop:aspectj-autoproxy/>
 *     <bean id="loggerAspect" class="me.mingshan.logger.async.source.collector.c1.aspect.LoggerAspect"></bean>
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
        Log logAnnotation = getMethodAnnotation(joinPoint,
                me.mingshan.logger.async.source.collector.c1.annotation.Log.class);

        // Determines if log annotations exist
        if (logAnnotation != null) {
            // Gets log types.
            LogType[] logTypes =  logAnnotation.type();
            Message message = new Message();
            for (LogType logType : logTypes) {
                // execute
                if (logType.getNumber() == 3) {
                    message = generateExecutedInfo(joinPoint, method, logAnnotation, message);
                } else if (logType.getNumber() == 4) {
                    // access
                    BrowserMessage browserMessage = generateAccessInfo(joinPoint);
                    message.setBrowserMessage(browserMessage);
                }
            }

            long startTime = System.currentTimeMillis();
            long executedTime = 0L;
            try {
                result = joinPoint.proceed();
                executedTime = System.currentTimeMillis() - startTime;
            } catch (Throwable t) {
                // Records the message of exception
                message.setE(t);
                message.setLevel(Level.ERROR);
                message.setResult(logAnnotation.recordResult() ? getExectuedResult(result) : null);
                writeMessage(message);
                throw t;
            }

            message.setExecutedTime(executedTime);
            message.setLevel(level);
            message.setResult(logAnnotation.recordResult() ? getExectuedResult(result) : null);
            writeMessage(message);
        } else {
            result = joinPoint.proceed();
        }

        return result;
    }

    private Message generateExecutedInfo(final ProceedingJoinPoint joinPoint, Method method,
                                        Log logAnnotation, Message message) {
        // Gets interfaces of class
        Class[] intfs = joinPoint.getTarget().getClass().getInterfaces();
        // Handles interfaces
        String serviceName = intfs.length > 0 ? intfs[0].getName()
                : joinPoint.getTarget().getClass().getName();
        // Gets the name of method
        String methodName = method.getName();
        // Determines if the parameters are obtained
        Object[] params = null;
        if (logAnnotation.recordParams()) {
            params = getParameters(joinPoint);
        }

        String paramStr =  Optional.ofNullable(params)
                .map(JsonUtils::arrayToString)
                .orElseGet(null);

        message.setServiceName(serviceName);
        message.setMethodName(methodName);
        message.setArgs(logAnnotation.recordParams() ? paramStr : null);
        writeMessage(message);
        return message;
    }

    private BrowserMessage generateAccessInfo(final ProceedingJoinPoint joinPoint) {
        Object[] params = getParameters(joinPoint);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = "http://" + request.getServerName()
                + ":"
                + request.getServerPort()
                + request.getContextPath()
                + request.getServletPath()
                + "?" + (request.getQueryString());
        String ip = request.getRemoteAddr();

        // get UA info
        String userAgentStr = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        Browser browser = userAgent.getBrowser();
        Version browserVersion = browser.getVersion(userAgentStr);
        OperatingSystem os = userAgent.getOperatingSystem();
        String browserName = browser.getName()+"/"+browserVersion;
        String osName = os.getName();

        // 防止与文件下载发生冲突，去除response参数
        for (int i = 0; i < params.length ; i++) {
            if (params[i] instanceof HttpServletResponse) {
                params[i] = params[params.length-1];
                params = Arrays.copyOf(params,params.length-1);
            }
        }
        // get http response code
        HttpServletResponse response = attributes.getResponse();
        String statusCode = String.valueOf(response.getStatus());
        BrowserMessage browserMessage = new BrowserMessage();
        browserMessage.setBrowser(browserName);
        browserMessage.setIpAddress(ip);
        browserMessage.setOs(osName);
        browserMessage.setStatus(statusCode);

        return browserMessage;
    }

    private String getExectuedResult(Object result) {
        return Optional.ofNullable(result)
                .map(JsonUtils::objectToString)
                .orElseGet(null);
    }

    private void writeMessage(Message message) {
        AsyncLoggerContext.start();
        AsyncLogger asyncLogger = AsyncLoggerContext.getAsyncLogger();

        try {
            asyncLogger.logMessage(message);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
