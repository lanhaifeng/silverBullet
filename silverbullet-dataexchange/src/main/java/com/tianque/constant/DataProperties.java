package com.tianque.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * Created by QQ on 2018/4/23.
 */
@Component
@ConfigurationProperties(prefix="data-properties")
public class DataProperties {
    private String mode;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
