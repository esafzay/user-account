package com.marionete.useraccount.client;

import com.marionete.useraccount.dto.UserInfo;
import com.marionete.useraccount.exception.TooManyRequestsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import java.time.Duration;

@Service
public class UserClient {

    private final WebClient webClient;
    private static final int MAX_RETRIES = 4;

    public UserClient(WebClient.Builder webClientBuilder, @Value("${user.service.host}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<UserInfo> getUserInfo(String token) {
        return webClient.get()
                .uri("/marionete/user/")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .onStatus(status -> status == HttpStatus.SERVICE_UNAVAILABLE,
                        response -> Mono.error(new TooManyRequestsException("Too many requests")))
                .bodyToMono(UserInfo.class)
                .retryWhen(Retry.backoff(MAX_RETRIES, Duration.ofSeconds(2))
                    .filter(throwable -> throwable instanceof TooManyRequestsException));
    }

}
