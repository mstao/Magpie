package me.mingshan.logger.async.api;

import java.io.Serializable;
import java.util.Arrays;

public class Message implements Serializable {
    private String methodName;
    private String serviceName;
    private Object[] args;
    private Object result;
    private long executedTime;
    private String message;
    private Level level;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getExecutedTime() {
        return executedTime;
    }

    public void setExecutedTime(long executedTime) {
        this.executedTime = executedTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Message{" +
                "methodName='" + methodName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", args=" + Arrays.toString(args) +
                ", result=" + result +
                ", executedTime=" + executedTime +
                ", message='" + message + '\'' +
                ", level=" + level +
                '}';
    }
}
