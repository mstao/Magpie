package me.mingshan.magpie.demo.web.controller;

import me.mingshan.logger.async.source.collector.c1.annotation.AccessLog;
import me.mingshan.magpie.demo.web.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mingshan
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @AccessLog
    @RequestMapping("/test1")
    public String test1(@RequestParam("name") String name) {
        return demoService.test(name);
    }
}
