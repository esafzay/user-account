package com.marionete.useraccount.dto;

import com.marionete.useraccount.dto.AccountInfo;
import com.marionete.useraccount.dto.UserInfo;
import lombok.Data;

@Data
public class UserAccountResponse {

    private final AccountInfo accountInfo;
    private final UserInfo userInfo;
}
