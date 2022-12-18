package com.marionete.useraccount.service;

import com.marionete.useraccount.client.AccountClient;
import com.marionete.useraccount.client.LoginClient;
import com.marionete.useraccount.client.UserClient;
import com.marionete.useraccount.dto.AccountInfo;
import com.marionete.useraccount.dto.UserAccountRequest;
import com.marionete.useraccount.dto.UserAccountResponse;
import com.marionete.useraccount.dto.UserInfo;
import com.marionete.useraccount.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final LoginClient loginClient;
    private final AccountClient accountClient;
    private final UserClient userClient;

    public Mono<UserAccountResponse> getUserAccount(UserAccountRequest request) throws AuthenticationException {

        String token = loginClient.authenticate(request.getUsername(), request.getPassword());

        if (token == null || token.isEmpty()) {
            throw new AuthenticationException("Authentication failed");
        }

        Mono<AccountInfo> accountInfo = accountClient.getAccountInfo(token);
        Mono<UserInfo> userInfo = userClient.getUserInfo(token);

        return accountInfo.zipWith(userInfo)
                .map((tuple) -> new UserAccountResponse(tuple.getT1(), tuple.getT2()));
    }

}
