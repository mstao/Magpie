package me.mingshan.logger.async.source.collector.c1.message;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * record browser info
 * @author lyf
 * @date 2018-11-16 17:50:24
 */
public class BrowserMessage implements Serializable {
    private static final long serialVersionUID = 1630695145167900386L;

    /**
     * ip
     */
    private String ipAddress;

    /**
     * browser / version
     */
    private String browser;

    /**
     * os
     */
    private String os;

    /**
     * http status code
     */
    private String status;

    /**
     * access time
     */
    private LocalDateTime createTime;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BrowserMessage{" +
                "ipAddress='" + ipAddress + '\'' +
                ", browser='" + browser + '\'' +
                ", os='" + os + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
