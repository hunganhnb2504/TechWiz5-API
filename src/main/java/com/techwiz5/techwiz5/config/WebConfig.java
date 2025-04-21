package com.techwiz5.techwiz5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
@EnableScheduling
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new FormHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<>());
        converters.add(new AllEncompassingFormHttpMessageConverter());
    }
}