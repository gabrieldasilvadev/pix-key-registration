package com.itau.pixkeyregistration.domain.gateways;

import com.itau.pixkeyregistration.domain.account.Account;

import java.util.List;

public interface AccountStorageGateway {
    void save(Account account);

    List<Account> getByPersonalDocument(String personalDocument);

    Account findByAccountNumber(String accountNumber);

    Account findById(String accountId);
}
