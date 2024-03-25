package com.itau.pixkeyregistration.commons.mappers;

import com.itau.pixkeyregistration.application.web.dto.AccountStatusDto;
import com.itau.pixkeyregistration.application.web.dto.AccountTypeDto;
import com.itau.pixkeyregistration.application.web.dto.CreateAccountRequestDto;
import com.itau.pixkeyregistration.application.web.dto.CreateAccountResponseDto;
import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.account.enums.AccountStatus;
import com.itau.pixkeyregistration.domain.account.enums.AccountType;
import com.itau.pixkeyregistration.domain.pix.PixKey;
import com.itau.pixkeyregistration.domain.valueobjects.Email;
import com.itau.pixkeyregistration.domain.valueobjects.Password;
import com.itau.pixkeyregistration.domain.valueobjects.PersonalDocument;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities.AccountTable;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.entities.PersonTable;
import de.huxhorn.sulky.ulid.ULID;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class AccountMapper {
    public Account toDomain(CreateAccountRequestDto request, AccountStatus status, String accountNumber) {
        return Account.AccountBuilder.anAccount()
                .aId(new ULID().nextULID())
                .aAgencyNumber(request.getAgencyNumber())
                .aEmail(new Email(request.getEmail()))
                .aPassword(new Password(request.getPassword()))
                .aPersonId(request.getPersonId())
                .aType(AccountType.valueOf(request.getAccountType().name()))
                .aPersonalDocument(new PersonalDocument(request.getPersonalDocument()))
                .aStatus(status)
                .aNumber(accountNumber)
                .build();
    }

    public CreateAccountResponseDto toResponse(Account account) {
        return new CreateAccountResponseDto(
                account.getId(),
                account.getAgencyNumber(),
                AccountTypeDto.fromValue(account.getType().name()),
                AccountStatusDto.fromValue(account.getStatus().name())
        );
    }

    public AccountTable toTable(Account account, PersonTable personTable) {
        return AccountTable.builder()
                .id(account.getId())
                .agencyNumber(account.getAgencyNumber())
                .email(account.getEmail().value())
                .password(account.getPassword().value())
                .person(personTable)
                .type(account.getType())
                .status(account.getStatus())
                .personalDocument(account.getPersonalDocument().value())
                .number(account.getNumber())
                .build();
    }

    public Account toDomain(AccountTable accountTable, HashSet<PixKey> pixKeys) {
        return Account.AccountBuilder.anAccount()
                .aId(accountTable.getId())
                .aAgencyNumber(accountTable.getAgencyNumber())
                .aEmail(new Email(accountTable.getEmail()))
                .aPassword(new Password(accountTable.getPassword()))
                .aPersonId(accountTable.getPerson().getId())
                .aType(accountTable.getType())
                .aStatus(accountTable.getStatus())
                .aPersonalDocument(new PersonalDocument(accountTable.getPersonalDocument()))
                .aNumber(accountTable.getNumber())
                .aPixKeys(pixKeys)
                .build();
    }
}
