package me.mingshan.magpie.demo.web.config;

import me.mingshan.logger.async.source.collector.c1.aspect.BrowserLoggerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mingshan
 */
@Configuration
public class BrowserLoggerAspectConfiguration {

    @Bean(value = "browserLoggerAspect")
    public BrowserLoggerAspect loggerAspect() {
        System.out.println("ooooooo ");
        return new BrowserLoggerAspect();
    }
}
