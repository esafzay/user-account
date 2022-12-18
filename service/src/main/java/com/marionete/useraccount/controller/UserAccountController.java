package com.marionete.useraccount.controller;

import com.marionete.useraccount.dto.UserAccountRequest;
import com.marionete.useraccount.dto.UserAccountResponse;
import com.marionete.useraccount.exception.AuthenticationException;
import com.marionete.useraccount.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/marionete")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/useraccount")
    public Mono<UserAccountResponse> getUserAccount(
            @RequestBody UserAccountRequest userAccountRequest) throws AuthenticationException {
        return userAccountService.getUserAccount(userAccountRequest);
    }
}
