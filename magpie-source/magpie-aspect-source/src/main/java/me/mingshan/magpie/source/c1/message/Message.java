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
package me.mingshan.magpie.source.c1.message;

import me.mingshan.logger.async.api.Level;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 5692493460944679784L;

    private String serviceName;
    private String methodName;
    private String args;
    private String result;
    private long executedTime;
    private String message;
    private Level level;
    private Throwable e;
    private BrowserMessage browserMessage;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
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

    public Throwable getE() {
        return e;
    }

    public void setE(Throwable e) {
        this.e = e;
    }

    public BrowserMessage getBrowserMessage() {
        return browserMessage;
    }

    public void setBrowserMessage(BrowserMessage browserMessage) {
        this.browserMessage = browserMessage;
    }

    @Override
    public String toString() {
        return "Message{" +
                "serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", args='" + args + '\'' +
                ", result='" + result + '\'' +
                ", executedTime=" + executedTime +
                ", message='" + message + '\'' +
                ", level=" + level +
                ", e=" + e +
                ", browserMessage=" + browserMessage +
                '}';
    }
}
