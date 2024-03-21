package com.itau.pixkeyregistration.infrastructure.inbound.rest.controllers.account;

import com.itau.pixkeyregistration.application.account.AccountApplicationService;
import com.itau.pixkeyregistration.application.web.api.AccountApi;
import com.itau.pixkeyregistration.application.web.dto.*;
import com.itau.pixkeyregistration.commons.mappers.AccountMapper;
import com.itau.pixkeyregistration.commons.mappers.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {
    private final AccountApplicationService accountService;
    private final AccountMapper accountMapper;
    private final AddressMapper addressMapper;

    @Override
    public ResponseEntity<CreateAccountResponseDto> createAccount(CreateAccountRequestDto createAccountRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountMapper.toResponse(accountService.create(createAccountRequestDto)));
    }

    @Override
    public ResponseEntity<CreateAddressResponseDto> createAccountAddress(CreateAddressRequestDto createAddressRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.registerAddress(createAddressRequestDto));
    }


    @Override
    public ResponseEntity<PixKeyRegistrationResponseDto> createAccountPixKey(PixKeyRegistrationRequestDto pixKeyRegistrationRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.registerPix(pixKeyRegistrationRequestDto));
    }

    @Override
    public ResponseEntity<PixKeyUpdateResponseDto> updateAccountPixKey(PixKeyUpdateAccountRequestDto pixKeyUpdateAccountRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.updatePixKey(pixKeyUpdateAccountRequestDto));
    }

    @Override
    public ResponseEntity<InactivePixKeyResponseDto> inactivateAccountsPixKey(String keyId) {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(accountService.inactivePixKey(keyId));
    }
}
