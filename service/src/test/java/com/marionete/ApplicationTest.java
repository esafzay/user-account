package com.marionete;

import com.marionete.backends.AccountInfoMock;
import com.marionete.backends.UserInfoMock;
import com.marionete.useraccount.client.AccountClient;
import com.marionete.useraccount.client.LoginClient;
import com.marionete.useraccount.client.UserClient;
import com.marionete.useraccount.service.UserAccountService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationTest {

    @Autowired
    private LoginClient loginClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private AccountClient accountClient;
    @Autowired
    private UserAccountService userAccountService;

    @BeforeAll
    public static void startMockServers() {
        AccountInfoMock.start();
        UserInfoMock.start();
    }

    @Test
    void contextLoads() {
        assertThat(loginClient).isNotNull();
        assertThat(userClient).isNotNull();
        assertThat(accountClient).isNotNull();
        assertThat(userAccountService).isNotNull();
    }

}