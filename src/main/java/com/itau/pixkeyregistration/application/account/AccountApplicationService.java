package com.itau.pixkeyregistration.application.account;

import com.itau.pixkeyregistration.application.web.dto.CreateAccountRequestDto;
import com.itau.pixkeyregistration.domain.account.Account;

public interface AccountApplicationService {
    Account create(CreateAccountRequestDto createAccountRequestDto);
}
