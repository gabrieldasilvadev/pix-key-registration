package com.itau.pixkeyregistration.commons.mappers;

import com.itau.pixkeyregistration.application.web.dto.*;
import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.account.enums.AccountType;
import com.itau.pixkeyregistration.domain.valueobjects.Address;
import com.itau.pixkeyregistration.domain.valueobjects.Email;
import com.itau.pixkeyregistration.domain.valueobjects.Password;

public class AccountMapper {
    public Account toDomain(CreateAccountRequestDto request) {
        return Account.AccountBuilder.anAccount()
                .aAgencyNumber(request.getAgencyNumber())
                .aAgencyLastDigit(request.getAgencyLastDigit())
                .aEmail(Email.builder()
                        .value(request.getEmail())
                        .build())
                .aPassword(Password.builder()
                        .value(request.getPassword())
                        .build())
                .aPersonId(request.getPersonId())
                .aType(AccountType.valueOf(request.getAccountType().name()))
                .aPersonalDocument(request.getPersonalDocument())
                .build();
    }

    public CreateAccountResponseDto toResponse(Account account) {
        return new CreateAccountResponseDto(
                account.getId(),
                account.getPersonId(),
                AccountTypeDto.fromValue(account.getType().name()),
                AccountStatusDto.fromValue(account.getStatus().name())
        );
    }

    public Address toAddress(CreateAddressRequestDto request) {
        return new Address(
            request.getCity(),
                request.getState(),
                request.getNeighbourhood(),
                request.getNumber(),
                request.getComplement()
        );
    }
}
