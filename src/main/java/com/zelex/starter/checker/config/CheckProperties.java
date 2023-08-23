package com.zelex.starter.checker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("check")
public class CheckProperties {
    // 默认为true，表示开启校验
    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
