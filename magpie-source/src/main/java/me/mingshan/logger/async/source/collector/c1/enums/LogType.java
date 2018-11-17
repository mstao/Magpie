package me.mingshan.logger.async.source.collector.c1.enums;

/**
 * 日志类型
 */
public enum LogType {
    /**
     * 操作日志
     */
    OPERATION(1, "操作日志"),

    /**
     * 异常日志
     */
    EXCEPTION(2, "异常日志"),

    /**
     * 执行日志，记录方法的执行信息，
     */
    EXECUTE(3, "执行日志"),

    /**
     * 访问日志，放在controller层，可以收集用户及浏览器的信息
     */
    ACCESS(4, "访问日志");

    private int number;
    private String name;

    LogType(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return this.number;
    }

    public String getName() {
        return this.name;
    }
}
