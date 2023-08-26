package com.zelexstarter.checker.config;

import com.zelexstarter.checker.aop.NeedCheckPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 注意@ConditionalOnProperty注解要放在后面两个注解的前面，这样才会优先通过配置文件判断是否要开启自动装配。
@ConditionalOnProperty(value = "check.enabled", havingValue = "true")
@ConditionalOnClass(CheckProperties.class)
@EnableConfigurationProperties(CheckProperties.class)
public class CheckAutoConfigure {

    @Bean
    public NeedCheckPoint needCheckPoint() {
        return new NeedCheckPoint();
    }

}



