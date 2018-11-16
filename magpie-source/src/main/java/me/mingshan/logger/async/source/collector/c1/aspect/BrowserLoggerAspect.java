package me.mingshan.logger.async.source.collector.c1.aspect;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import me.mingshan.logger.async.AsyncLogger;
import me.mingshan.logger.async.AsyncLoggerContext;
import me.mingshan.logger.async.source.collector.c1.message.BrowserMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import static me.mingshan.logger.async.source.util.AopUtils.getParameters;

/**
 * record user`s access info
 * @author lyf
 * @date 2018-11-16 17:54:11
 */
@Aspect
public class BrowserLoggerAspect {

    @Pointcut("@annotation(me.mingshan.logger.async.source.collector.c1.annotation.AccessLog)")
    private void browserLogPointCut() {
    }

    @Around("browserLogPointCut()")
    public Object methodsAnnotatedWithLogger(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Object[] params = getParameters(joinPoint);
        try{
            result = joinPoint.proceed();
        } catch (Throwable t) {
            throw t;
        } finally {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            writeMessage(attributes,params,result);
        }
        return result;
    }

    /**
     * @param attributes
     * @param params
     */
    public void writeMessage(ServletRequestAttributes attributes, Object[] params,Object result) {
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
        AsyncLoggerContext.start();
        AsyncLogger asyncLogger = AsyncLoggerContext.getAsyncLogger();

        try {
            asyncLogger.logMessage(browserMessage);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
