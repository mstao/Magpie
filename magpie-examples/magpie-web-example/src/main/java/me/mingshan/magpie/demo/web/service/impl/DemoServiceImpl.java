package me.mingshan.magpie.demo.web.service.impl;

import me.mingshan.logger.async.source.collector.c1.annotation.Log;
import me.mingshan.magpie.demo.web.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * @author mingshan
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Log
    public String test(String name) {
        return "hello" + name;
    }
}
