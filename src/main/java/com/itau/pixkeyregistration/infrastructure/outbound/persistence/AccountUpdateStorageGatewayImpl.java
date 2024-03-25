package com.itau.pixkeyregistration.infrastructure.outbound.persistence;

import com.itau.pixkeyregistration.domain.account.update.AccountUpdate;
import com.itau.pixkeyregistration.domain.exceptions.AccountNotFoundException;
import com.itau.pixkeyregistration.domain.gateways.AccountUpdateStorageGateway;
import com.itau.pixkeyregistration.domain.valueobjects.AccountUpdateTransient;
import com.itau.pixkeyregistration.infrastructure.outbound.persistence.jpa.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountUpdateStorageGatewayImpl implements AccountUpdateStorageGateway {
    private final AccountRepository accountRepository;

    @Override
    public void update(AccountUpdateTransient accountUpdateTransient) {
        accountRepository.update(accountUpdateTransient);
    }

    @Override
    public AccountUpdate findByAccountNumber(String accountNumber) {
        var account = accountRepository.findByNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException(
                "ACCOUNT_NOT_FOUND",
                String.format("The accountRequest with AccountNumber:%s was not found", accountNumber)
        ));

        return AccountUpdate.builder()
                .accountType(account.getType())
                .agencyNumber(account.getAgencyNumber())
                .accountNumber(account.getNumber())
                .accountId(account.getId())
                .build();
    }
}
