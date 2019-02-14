package com.glqdlt.tdd.clientresttest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientRestTemplate {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
