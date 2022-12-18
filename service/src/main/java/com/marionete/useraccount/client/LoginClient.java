package com.marionete.useraccount.client;

import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc;

@Component
public class LoginClient {

    private final LoginServiceGrpc.LoginServiceBlockingStub loginServiceBlockingStub;;

    public LoginClient(@Value("${login.service.host}") String host, @Value("${login.service.port}") int port) {
        loginServiceBlockingStub = LoginServiceGrpc.newBlockingStub(
                ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    }

    public String authenticate(String username, String password) {
        LoginRequest loginRequest = LoginRequest.newBuilder()
                .setUsername(username != null ? username : "")
                .setPassword(password != null ? password : "")
                .build();

        LoginResponse loginResponse = loginServiceBlockingStub.login(loginRequest);

        return loginResponse.getToken();
    }
}
