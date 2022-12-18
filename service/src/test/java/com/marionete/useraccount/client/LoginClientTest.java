package com.marionete.useraccount.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LoginClientTest {

    @Autowired
    private LoginClient loginClient;

    @Test
    public void testAuthenticate_when_username_and_password_are_valid() {
        String token = loginClient.authenticate("valid_username", "valid_password");

        assertThat(token).isNotNull().isNotEmpty();
    }

    @Test
    public void testAuthenticate_when_username_is_empty() {
        String token = loginClient.authenticate("", "valid_password");

        assertThat(token).isNotNull().isEmpty();
    }

    @Test
    public void testAuthenticate_when_username_is_null() {
        String token = loginClient.authenticate(null, "valid_password");

        assertThat(token).isNotNull().isEmpty();
    }

    @Test
    public void testAuthenticate_when_password_is_empty() {
        String token = loginClient.authenticate("valid_username", "");

        assertThat(token).isNotNull().isEmpty();
    }

    @Test
    public void testAuthenticate_when_password_is_null() {
        String token = loginClient.authenticate("valid_username", null);

        assertThat(token).isNotNull().isEmpty();
    }

    @Test
    public void testAuthenticate_when_username_and_password_are_empty() {
        String token = loginClient.authenticate("", "");

        assertThat(token).isNotNull().isEmpty();
    }

}