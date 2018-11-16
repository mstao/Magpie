package me.mingshan.magpie.demo.web.config;

import me.mingshan.logger.async.source.collector.c1.aspect.LoggerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mingshan
 */
@Configuration
public class AsyncLoggerConfiguration {

    @Bean(value = "loggerAspect")
    public LoggerAspect loggerAspect() {
        System.out.println("iiiiiiiiiiii ");
        return new LoggerAspect();
    }
}
