package com.itau.pixkeyregistration.domain.gateways;

import com.itau.pixkeyregistration.domain.account.Account;
import com.itau.pixkeyregistration.domain.valueobjects.Address;

import java.util.List;
import java.util.Optional;

public interface AccountStorageGateway {
    Account save(Account account);

    Optional<Account> getById(String accountId);

    Account registerAddress(Account account, Address address);

    List<Account> getByPersonalDocument(String personalDocument);
}
