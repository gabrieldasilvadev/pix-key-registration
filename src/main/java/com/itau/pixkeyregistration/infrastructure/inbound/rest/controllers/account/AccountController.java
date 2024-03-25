package com.itau.pixkeyregistration.infrastructure.inbound.rest.controllers.account;

import com.itau.pixkeyregistration.application.account.AccountApplicationService;
import com.itau.pixkeyregistration.application.web.api.AccountApi;
import com.itau.pixkeyregistration.application.web.dto.CreateAccountRequestDto;
import com.itau.pixkeyregistration.application.web.dto.CreateAccountResponseDto;
import com.itau.pixkeyregistration.commons.mappers.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {
    private final AccountApplicationService accountService;
    private final AccountMapper accountMapper;

    @Override
    public ResponseEntity<CreateAccountResponseDto> createAccount(CreateAccountRequestDto createAccountRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountMapper.toResponse(accountService.create(createAccountRequestDto)));
    }

}
