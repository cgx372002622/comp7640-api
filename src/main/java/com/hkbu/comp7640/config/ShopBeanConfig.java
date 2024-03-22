package com.hkbu.comp7640.config;

import com.hkbu.comp7640.bean.Qiniu;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ShopBeanConfig {

    private final ShopBasicConfig shopBasicConfig;

    @Bean
    public Qiniu qiniu() {
        return shopBasicConfig.getQiniu();
    }

}
