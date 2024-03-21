package com.itau.pixkeyregistration.domain.account.usecases;

import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.exceptions.AccountNotFoundException;
import com.itau.pixkeyregistration.domain.gateways.AccountStorageGateway;
import com.itau.pixkeyregistration.domain.valueobjects.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterAddressToAccountUseCase {
    private final AccountStorageGateway accountStorage;

    public Account register(Address address, String accountId) {

        var account = accountStorage.getById(accountId).orElseThrow(() -> new AccountNotFoundException(
                "ACCOUNT_NOT_FOUND",
                String.format("The given account not exists. AccountId[%s]", accountId)
        ));

        account.addAddress(address);
        return accountStorage.save(account);
    }
}
