package com.um.carrental.vehiclemanagement.configuration;

import com.um.carrental.vehiclemanagement.services.MatchingUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MatchingUtilConfig {

    @Bean
    public MatchingUtil matchingUtilFactory() {
        return new MatchingUtil();
    }

}