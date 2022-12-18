package com.marionete.useraccount.service;

import com.marionete.backends.AccountInfoMock;
import com.marionete.backends.UserInfoMock;
import com.marionete.useraccount.dto.AccountInfo;
import com.marionete.useraccount.dto.UserAccountRequest;
import com.marionete.useraccount.dto.UserAccountResponse;
import com.marionete.useraccount.dto.UserInfo;
import com.marionete.useraccount.exception.AuthenticationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class UserAccountServiceTest {

    @Autowired
    private UserAccountService userAccountService;

    @Test
    public void testGetUserAccount_with_valid_username_and_password() {
        UserAccountRequest userAccountRequest = new UserAccountRequest();
        userAccountRequest.setUsername("valid_username");
        userAccountRequest.setPassword("valid_password");

        try {
            Mono<UserAccountResponse> mono = userAccountService.getUserAccount(userAccountRequest);

            UserAccountResponse userAccountResponse = mono.block();
            assertThat(userAccountResponse).isNotNull();

            AccountInfo accountInfo = userAccountResponse.getAccountInfo();
            assertThat(accountInfo).isNotNull();
            assertThat(accountInfo.getAccountNumber()).isNotNull().isEqualTo("12345-3346-3335-4456");

            UserInfo userInfo = userAccountResponse.getUserInfo();
            assertThat(userInfo).isNotNull();
            assertThat(userInfo.getName()).isNotNull().isEqualTo("John");
            assertThat(userInfo.getSurname()).isNotNull().isEqualTo("Doe");
            assertThat(userInfo.getSex()).isNotNull().isEqualTo("male");
            assertThat(userInfo.getAge()).isNotNull().isEqualTo(32);

        } catch (AuthenticationException ex) {
            assertThat(ex).isNull();
        }
    }

    @Test
    public void testGetUserAccount_with_invalid_username_and_password() {
        UserAccountRequest userAccountRequest = new UserAccountRequest();
        userAccountRequest.setUsername("valid_username");
        userAccountRequest.setPassword("");

        assertThatThrownBy(() -> {

            Mono<UserAccountResponse> mono = userAccountService.getUserAccount(userAccountRequest);
            mono.block();

        }).isInstanceOf(AuthenticationException.class);
    }
}