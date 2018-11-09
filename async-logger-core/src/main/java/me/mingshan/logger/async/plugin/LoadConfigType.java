package me.mingshan.logger.async.plugin;

/**
 * Support type of getting configuration.
 *
 * @author mingshan
 */
public enum LoadConfigType {

    /**
     * Load configuration from system property.
     */
    SYSTEM("Load configuration from system property"),

    /**
     * Load configuration from system property.
     */
    FILE("Load configuration from system property");

    private String desc;

    LoadConfigType(String desc) {
        this.desc = desc;
    }

    private String getDesc() {
        return this.desc;
    }

}
