package com.marionete.useraccount.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marionete.useraccount.dto.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserClientTest {

    private final UserClient userClient;

    public UserClientTest() {
        userClient = new UserClient(createWebClientBuilder(), "http://localhost:8898");
    }

    private WebClient.Builder createWebClientBuilder() {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder().codecs(
                clientCodecConfigurer -> clientCodecConfigurer.customCodecs().register(new Jackson2JsonDecoder(
                        new ObjectMapper(), MimeTypeUtils.APPLICATION_OCTET_STREAM))).build();

        return WebClient.builder().exchangeStrategies(exchangeStrategies);
    }

    @Test
    public void testGetUserInfo_when_token_is_valid() {
        Mono<UserInfo> mono = userClient.getUserInfo("some-valid-token");

        UserInfo userInfo = mono.block();

        assertThat(userInfo).isNotNull();
        assertThat(userInfo.getName()).isNotNull().isEqualTo("John");
        assertThat(userInfo.getSurname()).isNotNull().isEqualTo("Doe");
        assertThat(userInfo.getSex()).isNotNull().isEqualTo("male");
        assertThat(userInfo.getAge()).isNotNull().isEqualTo(32);
    }

    @Test
    public void testGetUserInfo_when_token_is_not_valid() {
        Mono<UserInfo> mono = userClient.getUserInfo(null);

        assertThatThrownBy(() -> {
            mono.block();
        }).isInstanceOf(WebClientResponseException.InternalServerError.class);
    }
}