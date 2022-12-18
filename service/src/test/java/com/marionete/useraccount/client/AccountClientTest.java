package com.marionete.useraccount.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marionete.useraccount.dto.AccountInfo;
import org.junit.jupiter.api.Test;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AccountClientTest {

    private final AccountClient accountClient;

    public AccountClientTest() {
        accountClient = new AccountClient(createWebClientBuilder(), "http://localhost:8899");
    }

    private WebClient.Builder createWebClientBuilder() {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder().codecs(
                clientCodecConfigurer -> clientCodecConfigurer.customCodecs().register(new Jackson2JsonDecoder(
                        new ObjectMapper(), MimeTypeUtils.APPLICATION_OCTET_STREAM))).build();

        return WebClient.builder().exchangeStrategies(exchangeStrategies);
    }

    @Test
    public void testGetAccountInfo_when_token_is_valid() {
        Mono<AccountInfo> mono = accountClient.getAccountInfo("some-valid-token");

        AccountInfo accountInfo = mono.block();

        assertThat(accountInfo).isNotNull();
        assertThat(accountInfo.getAccountNumber()).isNotNull().isEqualTo("12345-3346-3335-4456");
    }

    @Test
    public void testGetAccountInfo_when_token_is_not_valid() {
        Mono<AccountInfo> mono = accountClient.getAccountInfo(null);

        assertThatThrownBy(() -> {
            mono.block();
        }).isInstanceOf(WebClientResponseException.InternalServerError.class);
    }

}