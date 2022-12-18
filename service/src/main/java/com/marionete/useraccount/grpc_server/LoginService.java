package com.marionete.useraccount.grpc_server;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc;
import java.util.UUID;

/**
 * In production this GRPC server will be in a separate module
 */
@GrpcService
public class LoginService extends LoginServiceGrpc.LoginServiceImplBase {

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {

        if (isAuthentic(request.getUsername(), request.getPassword())) {
            responseObserver.onNext(LoginResponse.newBuilder().setToken(generateToken()).build());

        } else {
            responseObserver.onNext(LoginResponse.getDefaultInstance());
        }

        responseObserver.onCompleted();
    }

    private boolean isAuthentic(String username, String password) {

        if (username == null || username.isEmpty()) {
            return false;
        }

        if (password == null || password.isEmpty() || password.length() < 8) {
            return false;
        }

        // In production the username and password will be validated against the stored data

        return true;
    }

    private String generateToken() {
        // TODO: it could be extended into generating a proper JWT
        return UUID.randomUUID().toString();
    }

}
