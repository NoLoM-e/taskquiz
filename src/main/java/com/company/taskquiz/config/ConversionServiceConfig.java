package com.company.taskquiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

public class ConversionServiceConfig {

    @Bean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }
}
