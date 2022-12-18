package com.marionete.useraccount.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientBuilderConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder().codecs(
                clientCodecConfigurer -> clientCodecConfigurer.customCodecs().register(new Jackson2JsonDecoder(
                        new ObjectMapper(), MimeTypeUtils.APPLICATION_OCTET_STREAM))).build();

        return WebClient.builder().exchangeStrategies(exchangeStrategies);
    }
}
