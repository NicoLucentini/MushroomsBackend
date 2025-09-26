package org.lucentininicolas.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {

    @Bean
    @ConditionalOnProperty(name = "jwt.filter.enabled", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/tandas/*"); // which paths to filter
        registrationBean.setOrder(1); // filter order if you have multiple filters
        return registrationBean;
    }
}